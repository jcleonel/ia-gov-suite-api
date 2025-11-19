package br.com.iagovsuite.api.controller.auth;

import br.com.iagovsuite.api.controller.auth.dto.*;
import br.com.iagovsuite.api.domain.user.User;
import br.com.iagovsuite.api.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody @Valid LoginDto data) {
        TokenDto token = authService.login(data);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody @Valid RegisterDto data) {
        User newUser = authService.registerUser(data);
        UserDto responseDto = UserDto.fromEntity(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
