package ru.skillbox.notifications_service.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

@Slf4j
@Component
public class AuthenticationFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
    private final WebClient webClient;
    private final JwtUtils jwtUtils;
    public String tokenAuth;

    @Autowired
    public AuthenticationFilter(JwtUtils jwtUtils, WebClient.Builder webClientBuilder) {
        logger.debug("Initializing AuthenticationFilter...");
        this.jwtUtils = jwtUtils;
        this.webClient = webClientBuilder.baseUrl("http://79.174.80.223:8085").build();
        logger.debug("AuthenticationFilter initialized with WebClient and JwtUtils.");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        logger.info("doFilter called.");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String authToken = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        String requestURI = httpRequest.getRequestURI();
        log.info("httpRequest.getRequestURI() {}", authToken);
        logger.debug("Request URI: {}", requestURI);
        logger.debug("Authorization Header: {}", authToken);

        httpRequest.getHeaderNames().asIterator().forEachRemaining(headerName ->
                logger.info("Header {}: {}", headerName, httpRequest.getHeader(headerName))
        );

        if (isSwaggerRequest(requestURI)) {
            logger.debug("Swagger request detected, skipping token validation for: {}", requestURI);
            chain.doFilter(request, response);
            return;
        }

        if (authToken != null && authToken.startsWith("Bearer ")) {
            authToken = authToken.substring(7);
            logger.info("Token extracted: {}", authToken);
        } else if (httpRequest.getHeader("sec-websocket-key") != null) {
            logger.info("WebSocket connection detected.");
            chain.doFilter(request, response);
            return;
        } else {
            logger.warn("Authorization token is missing or malformed.");
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization token is missing or malformed");
            return;
        }
        log.info("httpRequest.getRequestURI(): {}", authToken);
        tokenAuth = authToken;
        if (authToken != null) {
            logger.info("Token provided. Attempting authentication...");

            try {
                if (isValidToken(authToken, httpResponse)) {
                    CustomUserDetails userDetails = jwtUtils.getUserFromToken(authToken);
                    userDetails.setToken(authToken);

                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    logger.info("Authenticated user: {}", userDetails.getUsername());
                } else {
                    logger.warn("Invalid token.");
                    httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                    return;
                }
            } catch (Exception e) {
                logger.error("Authentication failed for token {}: {}", authToken, e.getMessage());
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                return;
            }
        }
        chain.doFilter(request, response);
    }

    private boolean isValidToken(String token, HttpServletResponse response) throws IOException {
        try {
            Boolean isValid = webClient.get()
                    .uri("/api/v1/auth/validate")
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();

            if (Boolean.TRUE.equals(isValid)) {
                logger.info("Token validation successful.");
                return true;
            } else {
                logger.warn("Token validation failed.");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Authorization Token");
                return false;
            }
        } catch (Exception e) {
            logger.error("Auth service is unavailable: {}", e.getMessage());
            response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, "Auth service is unavailable");
            return false;
        }
    }

    private boolean isSwaggerRequest(String requestURI) {
        logger.debug("Checking if request URI {} is a Swagger request.", requestURI);
        return requestURI.startsWith("/swagger") ||
                requestURI.startsWith("/v3/api-docs") ||
                requestURI.startsWith("/swagger-ui");
    }
}