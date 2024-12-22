package ru.skillbox.notifications_service.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;


public class SecurityUtils {
    public static CustomUserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            return (CustomUserDetails) authentication.getPrincipal();
        }
        return null;
    }

    public static String getCurrentUserId() {
        CustomUserDetails user = getCurrentUser();
        return user != null ? user.getUsername() : null;
    }
    public static UUID getPrincipalUserId() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        return userDetails.getUuid();
    }

}
