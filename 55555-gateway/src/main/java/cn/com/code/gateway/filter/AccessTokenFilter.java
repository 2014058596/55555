package cn.com.code.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @ClassName: AccessTokenFilter
 * @Description: token校验
 * @author: 55555
 * @date: 2020年04月08日 3:55 下午
 */
public class AccessTokenFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders headers = exchange.getRequest().getHeaders();

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
