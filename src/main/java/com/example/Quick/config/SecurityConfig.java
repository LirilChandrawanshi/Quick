package com.example.Quick.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig implements WebMvcConfigurer {

    @Value("${api.key:api-key-secret}")
    private String apiKey;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ApiKeyInterceptor(apiKey))
                .addPathPatterns("/api/**")
                .excludePathPatterns("/swagger-ui/**", "/api-docs/**");
    }

    public class ApiKeyInterceptor implements HandlerInterceptor {

        private final String apiKey;

        public ApiKeyInterceptor(String apiKey) {
            this.apiKey = apiKey;
        }

        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            String providedApiKey = request.getHeader("X-API-KEY");


            if (request.getServerName().equals("localhost") || request.getServerName().equals("127.0.0.1")) {
                return true;
            }

            if (providedApiKey == null || !providedApiKey.equals(apiKey)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid or missing API key");
                return false;
            }

            return true;
        }
    }
}
