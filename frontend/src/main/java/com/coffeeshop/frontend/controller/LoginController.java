package com.coffeeshop.frontend.controller;

import com.coffeeshop.frontend.config.QrLoginStore;
import com.coffeeshop.frontend.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.InetAddress;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final QrLoginStore store;
    private final UserRepository userRepository;

    private String getLocalIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            return "localhost";
        }
    }

    public LoginController(QrLoginStore store, UserRepository userRepository) {
        this.store = store;
        this.userRepository = userRepository;
    }


    //Making a url so login.js can parse it to QR
    @GetMapping("/qr-link")
    public Map<String, String> getQrLink(HttpServletRequest request) {
        String sessionId = UUID.randomUUID().toString();

        String baseUrl = ServletUriComponentsBuilder
                .fromRequestUri(request)
                .replacePath(null)
                .replaceQuery(null)
                .build()
                .toUriString();

        String url = baseUrl + "/qr-login.html?session=" + sessionId;

        return Map.of(
                "url", url,
                "sessionId", sessionId
        );
    }


    //This is what you see on your phone while login
    @PostMapping("/qr-login")
    public ResponseEntity<?> qrLogin(
            @RequestParam String session,
            @RequestBody Map<String, String> body
    ) {
        String email = body.get("email");
        String password = body.get("password");

        return userRepository
                .findByEmailAndPassword(email, password)
                .map(user -> {
                    store.authenticate(session, user.getEmail());
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() ->
                        ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
                );
    }


    @GetMapping("/status")
    public ResponseEntity<String> loginStatus(@RequestParam String session) {
        String user = store.getAuthenticatedUser(session);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        store.remove(session);
        return ResponseEntity.ok(user);
    }

}
