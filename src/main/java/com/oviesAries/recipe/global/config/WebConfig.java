package com.oviesAries.recipe.global.config;

import com.oviesAries.recipe.global.argument.AuthPrincipalResolver;
import com.oviesAries.recipe.global.interceptor.AuthenticationInterceptor;
import com.oviesAries.recipe.global.interceptor.UserAuthorityInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthenticationInterceptor authenticationInterceptor;
    private final UserAuthorityInterceptor userAuthorityInterceptor;
    private final AuthPrincipalResolver authPrincipalResolver;

    @Value("${cors.allowed-origin}")
    private String allowedOrigin;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry
                .addInterceptor(authenticationInterceptor)
                .addPathPatterns("/**")
                .order(0);

        registry
                .addInterceptor(userAuthorityInterceptor)
                .addPathPatterns("/**")
                .order(1);

    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authPrincipalResolver);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedOrigins(allowedOrigin)
                .allowedMethods("*");
        WebMvcConfigurer.super.addCorsMappings(registry);
    }
}
