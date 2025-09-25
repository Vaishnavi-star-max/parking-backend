package com.example.parking.config;


import com.example.parking.security.JwtAuthTokenFilter;
import com.example.parking.security.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    
    private final JwtAuthenticationEntryPoint unauthorizedHandler;
    // private final RateLimitingFilter rateLimitingFilter; // Disabled for development
    private final ApplicationContext applicationContext;
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.disable())
            .csrf(csrf -> csrf.disable())
            .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/", "/health").permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/docs", "/swagger").permitAll()
                .requestMatchers("/api/floors/**").hasAnyRole("ADMIN", "CUSTOMER")
                .requestMatchers("/api/slots/**").hasAnyRole("ADMIN", "CUSTOMER")
                .anyRequest().authenticated()
            );
        
        // http.addFilterBefore(rateLimitingFilter, UsernamePasswordAuthenticationFilter.class); // Disabled for development
        http.addFilterBefore(applicationContext.getBean(JwtAuthTokenFilter.class), UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
}