package cn.com.code.gateway.decorator;

import cn.com.code.base.bean.StandardResult;
import cn.com.code.base.utils.JsonUtils;
import com.alibaba.fastjson.JSONObject;
import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;

/**
 * @ClassName: ResponseDecorator
 * @Description: 统一响应装饰器
 * @author: 55555
 * @date: 2020年04月08日 4:31 下午
 */
public class ResponseDecorator extends ServerHttpResponseDecorator {

    private ServerWebExchange exchange;

    public ResponseDecorator(ServerHttpResponse delegate) {
        super(delegate);
    }

    public ResponseDecorator(ServerWebExchange exchange) {
        super(exchange.getResponse());
        this.exchange = exchange;
    }



    @Override
    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
        if (body instanceof Flux) {
            Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
            return super.writeWith(fluxBody.map(dataBuffer -> {
                byte[] content = new byte[dataBuffer.readableByteCount()];
                dataBuffer.read(content);
                ServerHttpResponse originalResponse = exchange.getResponse();
                //释放掉内存
                DataBufferUtils.release(dataBuffer);
                Object jsonObject = JSONObject.parseObject(new String(content, Charset.forName("UTF-8")));
                DataBufferFactory bufferFactory = originalResponse.bufferFactory();
                if(originalResponse.getStatusCode().is2xxSuccessful()){
                    //原始响应
                    return bufferFactory.wrap(JsonUtils.objectToJson(StandardResult.ok(jsonObject)).getBytes());
                }else {
                    return bufferFactory.wrap(JsonUtils.objectToJson(jsonObject).getBytes());
                }
            }));
        }
        return super.writeWith(body);
    }



}
