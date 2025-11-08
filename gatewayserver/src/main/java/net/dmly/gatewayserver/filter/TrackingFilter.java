package net.dmly.gatewayserver.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

@Order(1)
@Component
@Slf4j
@RequiredArgsConstructor
public class TrackingFilter implements GlobalFilter {

    private final FilterUtils filterUtils;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        if (isCorrelationIdPresent(headers)) {
            log.info("tmx-correlation-id found in tracking filter: {}. ", filterUtils.getCorrelationId(headers));
        } else {
            String correlationID = generateCorrelationId();
            exchange = filterUtils.setCorrelationId(exchange, correlationID);
            log.debug("tmx-correlation-id generated in tracking filter: {}.", correlationID);
        }

        return chain.filter(exchange);
    }

    private boolean isCorrelationIdPresent(HttpHeaders requestHeaders) {
        return filterUtils.getCorrelationId(requestHeaders) != null;
    }

    private String generateCorrelationId() {
        return java.util.UUID.randomUUID().toString();
    }
}
