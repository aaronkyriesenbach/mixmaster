package com.kyriesenbach.mixmaster.config;

import com.kyriesenbach.mixmaster.auth.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer
{
    private final AuthInterceptor authInterceptor;
    
    @Autowired
    public WebMvcConfiguration(AuthInterceptor authInterceptor)
    {
        this.authInterceptor = authInterceptor;
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry)
    {
        interceptorRegistry.addInterceptor(authInterceptor);
    }
    
    @Override
    public void addCorsMappings(CorsRegistry registry)
    {
        registry.addMapping("/**").allowedOriginPatterns("*").allowCredentials(true);
    }
}
