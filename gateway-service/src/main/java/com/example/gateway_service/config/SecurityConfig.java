package com.example.gateway_service.config;

import com.example.gateway_service.exception.CustomAccessDeniedHandler;
import com.example.gateway_service.exception.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;


    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(
            ServerHttpSecurity http
    ) {

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges

                        .pathMatchers("/api/auth/**", "/")
                        .permitAll()

                        .pathMatchers("/api/notifications/ws/**")
                        .permitAll()

                        .pathMatchers(
                                "/api/category/salon-owner/**",
                                "/api/notification/salon-owner/**",
                                "/api/service-offering/salon-owner/**"
                        )
                        .hasRole("SALON_OWNER")

                        // General routes AFTER
                        .pathMatchers(
                                "/api/salons/**",
                                "/api/category/**",
                                "/api/bookings/**",
                                "/api/notifications/**",
                                "/api/payments/**",
                                "/api/service-offering/**",
                                "/api/users/**",
                                "/api/reviews/**"
                        )
                        .hasAnyRole(
                                "CUSTOMER",
                                "SALON_OWNER",
                                "ADMIN"
                        )

                        .anyExchange()
                        .authenticated()
                )

                .oauth2ResourceServer(oauth2 ->
                        oauth2
                                .jwt(jwt ->
                                        jwt.jwtAuthenticationConverter(
                                                jwtAuthenticationConverter()
                                        )
                                )
                                .accessDeniedHandler(accessDeniedHandler)
                                .authenticationEntryPoint(authenticationEntryPoint)
                )

                .build();
    }

    /**
     * Converts JWT into Spring Security Authentication.
     *
     * WebFlux requires:
     *
     * Converter<Jwt, Mono<? extends AbstractAuthenticationToken>>
     */
    @Bean
    public Converter<Jwt, Mono<AbstractAuthenticationToken>>
    jwtAuthenticationConverter() {

        JwtAuthenticationConverter converter =
                new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(
                jwtGrantedAuthoritiesConverter()
        );

        return new ReactiveJwtAuthenticationConverterAdapter(
                converter
        );
    }

    /**
     * Converts Keycloak roles into Spring Security authorities.
     *
     * JWT:
     *
     * "roles": [
     *     "CUSTOMER",
     *     "SALON_OWNER",
     *     "ADMIN"
     * ]
     *
     * becomes:
     *
     * ROLE_CUSTOMER
     * ROLE_SALON_OWNER
     * ROLE_ADMIN
     */
    @Bean
    public Converter<Jwt, Collection<GrantedAuthority>>
    jwtGrantedAuthoritiesConverter() {

        return jwt -> {

            Map<String, Object> resourceAccess =
                    jwt.getClaimAsMap("resource_access");

            if (resourceAccess == null) {
                return List.of();
            }

            Object clientAccess =
                    resourceAccess.get("salon-booking-client");

            if (!(clientAccess instanceof Map<?, ?> client)) {
                return List.of();
            }

            Object rolesObject = client.get("roles");

            if (!(rolesObject instanceof Collection<?> roles)) {
                return List.of();
            }

            return roles.stream()
                    .filter(String.class::isInstance)
                    .map(role ->
                            (GrantedAuthority)
                                    new SimpleGrantedAuthority(
                                            "ROLE_" + role
                                    )
                    )
                    .toList();
        };
    }
}