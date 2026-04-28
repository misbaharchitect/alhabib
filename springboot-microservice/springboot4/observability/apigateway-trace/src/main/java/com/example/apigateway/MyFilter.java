package com.example.apigateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.opentelemetry.api.trace.Span;

@Component
public class MyFilter implements GlobalFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyFilter.class);

    @Autowired
    private ObservationRegistry observationRegistry;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Observation observation = Observation.createNotStarted("my-filter", observationRegistry);
        return observation.observe(() -> {
            ServerWebExchange finalExchange = exchange;
            LOGGER.info("****** API GATEWAY ******** ");
            LOGGER.info("Request path: {}", finalExchange.getRequest().getPath());
            LOGGER.info("Request method: {}", finalExchange.getRequest().getMethod());
            LOGGER.info("Request id: {}", finalExchange.getRequest().getId());

            // Add trace propagation headers
            Span currentSpan = Span.current();
            ServerWebExchange mutatedExchange = finalExchange;
            if (currentSpan != null && currentSpan.getSpanContext().isValid()) {
                String traceId = currentSpan.getSpanContext().getTraceId();
                String spanId = currentSpan.getSpanContext().getSpanId();
                String traceparent = "00-" + traceId + "-" + spanId + "-01"; // sampled
                mutatedExchange = finalExchange.mutate().request(r -> r.header("traceparent", traceparent)).build();
            }

            return chain.filter(mutatedExchange);
        });
    }
}
