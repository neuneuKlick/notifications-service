package ru.skillbox.notifications_service.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.skillbox.notifications_service.dto.RoleType;
import ru.skillbox.notifications_service.exception.UnAuthorizedException;

import java.util.Base64;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class JwtUtils {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CustomUserDetails getUserFromToken(String token) {
        log.info("getUserFromToken {}", token);
        try {
            log.info("Parsing JWT token: {}", token);
            String[] tokenParts = token.split("\\.");
            if (tokenParts.length != 3) {
                log.error("Invalid JWT token format");
                throw new IllegalArgumentException("Invalid JWT token format");
            }

            String payload = new String(Base64.getDecoder().decode(tokenParts[1]));
            log.trace("Token payload: {}", payload);

            Map<String, Object> claims = objectMapper.readValue(payload, Map.class);
            CustomUserDetails userDetails = new CustomUserDetails();
            userDetails.setEmail((String) claims.get("email"));
            userDetails.setUuid(UUID.fromString(claims.get("uuid").toString().replaceAll("\\[|\\]", "")));
            userDetails.setRole(RoleType.valueOf(claims.get("roles").toString().replaceAll("\\[|\\]", "")));
            userDetails.setToken(token);

            return userDetails;
        } catch (Exception e) {
            log.error("Error parsing token", e);
            throw new UnAuthorizedException("Token parsing failed");
        }
    }

}
