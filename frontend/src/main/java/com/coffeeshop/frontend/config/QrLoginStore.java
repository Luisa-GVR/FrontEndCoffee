package com.coffeeshop.frontend.config;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class QrLoginStore {

    private final Map<String, String> authenticatedSessions = new ConcurrentHashMap<>();

    public void authenticate(String sessionId, String username) {
        authenticatedSessions.put(sessionId, username);
    }

    public String getAuthenticatedUser(String sessionId) {
        return authenticatedSessions.get(sessionId);
    }

    public void remove(String sessionId) {
        authenticatedSessions.remove(sessionId);
    }
}
