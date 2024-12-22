package ru.skillbox.notifications_service.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.skillbox.notifications_service.dto.webclient.AuthRequest;
import ru.skillbox.notifications_service.dto.webclient.AuthResponseDto;
import ru.skillbox.notifications_service.dto.webclient.LoginRequestDto;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final WebClient webClient;

    @Value("${auth.admin.emailAdmin}")
    private String emailAdmin;

    @Value("${auth.admin.passwordAdmin}")
    private String passwordAdmin;

    public AuthResponseDto getAccessTokens() {
        LoginRequestDto loginRequest = new LoginRequestDto(emailAdmin, passwordAdmin);

//        return webClientBuilder.build()
//                .get()
//                .uri(AUTH_SERVICE_URL)
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
//                .retrieve()
//                .bodyToMono(Boolean.class)
//                .onErrorReturn(false);
//    }

        return webClient
                .post()
                .uri("/api/v1/auth/validate")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(loginRequest)
                .retrieve()
                .bodyToMono(AuthResponseDto.class)
                .block();
    }

}
