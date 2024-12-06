package com.example.bookstore.common.config;

import com.example.bookstore.filter.AuthGlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.WebFilter;

/**
 * @author chaoluo
 */
@Configuration
public class FilterConfig {

    @Bean
    public WebFilter illegalUrlFilter() {
        return new AuthGlobalFilter();
    }
}
