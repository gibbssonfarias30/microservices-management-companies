package com.backfcdev.gatewayserver.filters;

import com.backfcdev.gatewayserver.dtos.TokenDto;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
public class AuthFilter implements GatewayFilter {

    private final WebClient webClient;

    private static final String AUTH_VALIDATE_URI = "http://ms-auth:3030/auth-server/auth/jwt";
    private static final String ACCESS_TOKEN_HEADER_NAME = "accessToken";

    public AuthFilter() {
        this.webClient = WebClient.builder().build();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (!exchange.getRequest().getHeaders().containsKey(AUTHORIZATION)) return this.onError(exchange);

        final var tokenHeader = Objects.requireNonNull(exchange
                .getRequest()
                .getHeaders()
                .get(AUTHORIZATION)).get(0);

        final var chunks = tokenHeader.split(" ");
        if (chunks.length != 2 || !chunks[0].equals("Bearer")) return this.onError(exchange);
        final var token = chunks[1];

        return this.webClient
                .post()
                .uri(AUTH_VALIDATE_URI)
                .header(ACCESS_TOKEN_HEADER_NAME, token)
                .retrieve()
                .bodyToMono(TokenDto.class)
                .map(response -> exchange)
                .flatMap(chain::filter);
    }

    private Mono<Void> onError(ServerWebExchange exchange) {
        final var response = exchange.getResponse();
        response.setStatusCode(BAD_REQUEST);
        return response.setComplete();
    }
}
