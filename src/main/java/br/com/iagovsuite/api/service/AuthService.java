package br.com.iagovsuite.api.service;

import br.com.iagovsuite.api.controller.auth.dto.RegisterDto;
import br.com.iagovsuite.api.domain.user.User;
import br.com.iagovsuite.api.domain.user.UserRole;
import br.com.iagovsuite.api.domain.user.UserRepository;
import br.com.iagovsuite.api.exception.EmailAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User registerUser(RegisterDto registerDto) {
        if (userRepository.existsByEmail(registerDto.email())) {
            throw new EmailAlreadyExistsException("O e-mail '" + registerDto.email() + "' já está em uso.");
        }

        String hashedPassword = passwordEncoder.encode(registerDto.password());

        User newUser = new User();
        newUser.setFirstName(registerDto.firstName());
        newUser.setLastName(registerDto.lastName());
        newUser.setEmail(registerDto.email());
        newUser.setPassword(hashedPassword);
        newUser.setRole(UserRole.ROLE_USER);

        return userRepository.save(newUser);
    }

}
