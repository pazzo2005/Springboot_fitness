package com.fitness.fitnesss.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitness.fitnesss.auth.dto.AuthResponse;
import com.fitness.fitnesss.auth.dto.LoginRequest;
import com.fitness.fitnesss.auth.dto.RegisterRequest;
import com.fitness.fitnesss.entity.AppUser;
import com.fitness.fitnesss.repo.AppUserRepo;
import com.fitness.fitnesss.security.AppUserDetailsService;
import com.fitness.fitnesss.security.JwtService;

@Service
public class AuthService {

    private final AppUserRepo appUserRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AppUserDetailsService appUserDetailsService;

    public AuthService(
            AppUserRepo appUserRepo,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager,
            AppUserDetailsService appUserDetailsService) {
        this.appUserRepo = appUserRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.appUserDetailsService = appUserDetailsService;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (appUserRepo.existsByUsername(request.username())) {
            throw new IllegalArgumentException("Username already exists");
        }

        AppUser user = new AppUser();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole("USER");
        appUserRepo.save(user);

        UserDetails userDetails = appUserDetailsService.loadUserByUsername(user.getUsername());
        String token = jwtService.generateToken(userDetails);
        return new AuthResponse(token, "Bearer");
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        UserDetails userDetails = appUserDetailsService.loadUserByUsername(request.username());
        String token = jwtService.generateToken(userDetails);
        return new AuthResponse(token, "Bearer");
    }
}

