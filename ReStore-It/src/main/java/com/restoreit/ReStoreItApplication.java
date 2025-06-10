package com.restoreit;

import com.restoreit.filters.JWTAuthenticationFilter;
import com.restoreit.services.JWTService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ReStoreItApplication {

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
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/chat/history/{chatRoomId}").permitAll()
                        .requestMatchers("/chat/{chatRoomId}").permitAll()
                        .requestMatchers("/chat/guest").permitAll()
                        .requestMatchers("/categories/**").permitAll()
                        .requestMatchers("/products/**").permitAll()
                        .requestMatchers("/user/**").permitAll()
                        .requestMatchers("/business/login/validate").permitAll()
                        .requestMatchers("/order/submit").permitAll()
                        .requestMatchers("/order/**").permitAll()
                        .requestMatchers("/chat/business").hasAuthority("ROLE_BUSINESS")
                        .requestMatchers("/order/business/**").hasAuthority("ROLE_BUSINESS")
                        .requestMatchers("/products/business/**").hasAuthority("ROLE_BUSINESS") // rearrange the priority of these endpoints so that it can work
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JWTAuthenticationFilter(new JWTService()), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}