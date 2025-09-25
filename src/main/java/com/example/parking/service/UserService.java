package com.example.parking.service;

import com.example.parking.dto.AuthResponse;
import com.example.parking.dto.LoginRequest;
import com.example.parking.dto.SignupRequest;
import com.example.parking.entity.Role;
import com.example.parking.entity.User;
import com.example.parking.exception.ResourceAlreadyExistsException;
import com.example.parking.repository.UserRepository;
import com.example.parking.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements UserDetailsService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    
    public AuthResponse signup(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("Email is already taken!");
        }
        
        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new ResourceAlreadyExistsException("Phone number is already taken!");
        }
        
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole(Role.CUSTOMER);
        
        User savedUser = userRepository.save(user);
        
        // Generate JWT token for auto login after signup
        String jwt = jwtUtils.generateJwtToken(savedUser.getEmail());
        
        return new AuthResponse(jwt, savedUser.getId(), savedUser.getEmail(), 
                savedUser.getFirstName(), savedUser.getLastName(), savedUser.getRole());
    }
    
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }
        
        String jwt = jwtUtils.generateJwtToken(user.getEmail());
        
        return new AuthResponse(jwt, user.getId(), user.getEmail(), 
                user.getFirstName(), user.getLastName(), user.getRole());
    }
    
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }
    
    @Transactional(readOnly = true)
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}