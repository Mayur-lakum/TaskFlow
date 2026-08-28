package com.mayur.taskflowai.config;

import com.mayur.taskflowai.security.JwtAuthenticationFilter;
import com.mayur.taskflowai.service.CustomUserDetailsService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            CustomUserDetailsService customUserDetailsService) {

        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration =
                new CorsConfiguration();

        /*
         * Frontend origins
         *
         * We use allowedOriginPatterns because Vercel
         * creates different deployment URLs.
         */
        configuration.setAllowedOriginPatterns(List.of(
                "http://localhost:*",
                "https://*.vercel.app"
        ));

        /*
         * HTTP methods allowed from frontend.
         */
        configuration.setAllowedMethods(List.of(
                "GET",
                "POST",
                "PUT",
                "DELETE",
                "PATCH",
                "OPTIONS"
        ));

        /*
         * Allow request headers such as:
         *
         * Authorization
         * Content-Type
         */
        configuration.setAllowedHeaders(List.of("*"));

        /*
         * Allow browser credentials.
         */
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration
        );

        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http)
            throws Exception {

        http

                /*
                 * Enable CORS.
                 */
                .cors(cors ->
                        cors.configurationSource(
                                corsConfigurationSource()
                        )
                )

                /*
                 * REST API → CSRF disabled.
                 */
                .csrf(csrf ->
                        csrf.disable()
                )

                /*
                 * JWT authentication is stateless.
                 */
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                /*
                 * Authorization rules.
                 */
                .authorizeHttpRequests(auth -> auth

                        /*
                         * VERY IMPORTANT:
                         *
                         * Browser sends OPTIONS before
                         * POST when doing CORS.
                         *
                         * Allow it without authentication.
                         */
                        .requestMatchers(
                                HttpMethod.OPTIONS,
                                "/**"
                        )
                        .permitAll()

                        /*
                         * Login APIs.
                         */
                        .requestMatchers(
                                "/api/auth/**"
                        )
                        .permitAll()

                        /*
                         * Swagger.
                         */
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/v3/api-docs",
                                "/swagger-resources/**",
                                "/webjars/**"
                        )
                        .permitAll()

                        /*
                         * Employee APIs.
                         */
                        .requestMatchers(
                                "/api/v1/employees/**"
                        )
                        .hasAnyRole(
                                "ADMIN",
                                "MANAGER"
                        )

                        /*
                         * Project APIs.
                         */
                        .requestMatchers(
                                "/api/v1/projects/**"
                        )
                        .hasAnyRole(
                                "ADMIN",
                                "MANAGER"
                        )

                        /*
                         * Skill APIs.
                         */
                        .requestMatchers(
                                "/api/v1/skills/**"
                        )
                        .hasRole("ADMIN")

                        /*
                         * Everything else requires login.
                         */
                        .anyRequest()
                        .authenticated()
                )

                /*
                 * JWT filter runs before Spring's
                 * username/password authentication filter.
                 */
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}