package com.trnd.trndapi.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

//    private final SignatureVerificationInterceptor signatureVerificationInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(signatureVerificationInterceptor)
//                .addPathPatterns("/api/**"); // Specify the URL patterns to apply this interceptor
//    }
}
