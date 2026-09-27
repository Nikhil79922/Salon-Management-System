package com.example.gateway_service.exception;

import com.example.gateway_service.dto.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements ServerAccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(
            ServerWebExchange exchange,
            AccessDeniedException denied
    ) {

        ServerHttpResponse response = exchange.getResponse();

        response.setStatusCode(HttpStatus.FORBIDDEN);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        ErrorResponse errorResponse = new ErrorResponse(
                false,
                "You do not have permission to access this resource",
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                exchange.getRequest().getPath().value()
        );

        try {

            byte[] bytes = objectMapper.writeValueAsBytes(errorResponse);

            DataBuffer buffer = response.bufferFactory()
                    .wrap(bytes);

            return response.writeWith(Mono.just(buffer));

        } catch (JsonProcessingException e) {
            return Mono.error(e);
        }
    }
}