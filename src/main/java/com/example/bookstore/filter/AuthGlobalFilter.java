package com.example.bookstore.filter;

import cn.hutool.core.collection.CollUtil;
import com.example.bookstore.common.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author chaoluo
 */
@Component
public class AuthGlobalFilter implements WebFilter {

    @Resource
    private RedisUtil redisUtil;

    private static final String AUTHORIZE_TOKEN = "token";

    //放行请求集合
    public static List<String> URI_LIST = CollUtil.newArrayList("/user/register","/user/login");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        //1. 获取请求
        ServerHttpRequest request = exchange.getRequest();
        //2. 则获取响应
        ServerHttpResponse response = exchange.getResponse();
        //3. 如果是登录请求则放行
        for (String str : URI_LIST) {
            if (request.getURI().getPath().contains(str)) {
                return chain.filter(exchange);
            }
        }
        //4. 获取请求头
        HttpHeaders headers = request.getHeaders();
        //5. 请求头中获取令牌
        String token = headers.getFirst(AUTHORIZE_TOKEN);
        //6. 判断请求头中是否有令牌
        if (StringUtils.isEmpty(token)) {
            //7. 响应中放入返回的状态吗, 没有权限访问
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            //8. 返回
            return response.setComplete();
        }
        //9. 如果请求头中有令牌则解析令牌
        String str = (String)redisUtil.get("login:token:"+ token);
        if (StringUtils.isEmpty(str)){
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            //10. 说明令牌过期或者伪造等不合法情况出现
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            //11. 返回
            return response.setComplete();
        }
        //12. 放行
        return chain.filter(exchange);
    }

}
