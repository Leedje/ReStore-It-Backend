package com.restoreit;

import com.restoreit.filters.JWTAuthenticationFilter;
import com.restoreit.services.JWTService;
import com.restoreit.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ReStoreItApplication {

    @Autowired
    UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(ReStoreItApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200", "http://localhost:4300")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder(14));
        authProvider.setUserDetailsService(userService);
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/chat/**",
                                "/categories",
                                "/products/**",
                                "/products/{id}",
                                "/user/**",
                                "/login/**",
                                "/orders/submit",
                                "/orders/{orderId}").permitAll()
                        .requestMatchers("/chat/user-chats",
                                "/orders/pending",
                                "/orders/completed",
                                "orders/set-complete",
                                "/products/create",
                                "/products/delete/{id}",
                                "/products/edit",
                                "/products/user-products/**").hasAuthority("ROLE_BUSINESS")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JWTAuthenticationFilter(new JWTService()), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}