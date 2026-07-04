package pe.edu.untels.certificadosdrsu.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.untels.certificadosdrsu.config.AuthService;
import pe.edu.untels.certificadosdrsu.dtos.ChangePasswordRequest;
import pe.edu.untels.certificadosdrsu.dtos.LoginRequest;
import pe.edu.untels.certificadosdrsu.dtos.LoginResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordRequest request,
                                               Authentication authentication) {
        authService.changePassword(authentication.getName(), request);
        return ResponseEntity.ok().build();
    }
}