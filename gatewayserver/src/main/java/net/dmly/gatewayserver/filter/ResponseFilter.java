package net.dmly.gatewayserver.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class ResponseFilter {
    private final FilterUtils  filterUtils;

    @Bean
    public GlobalFilter postResponseCorrelationIdFilter() {
        return (exchange, chain) ->
        {
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                var headers = exchange.getRequest().getHeaders();
                String correlationId = filterUtils.getCorrelationId(headers);
                log.debug("Adding the correlation id to the outbound headers. {}", correlationId);
                exchange.getResponse().getHeaders().add(FilterUtils.CORRELATION_ID, correlationId);
                log.debug("Completing outgoing request for {}.", exchange.getRequest().getURI());
            }));
        };
    }
}
