package com.trnd.trndapi.security.interceptor;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;

//@Component
//public class SignatureVerificationInterceptor implements HandlerInterceptor {
//
//    private final SignatureVerificationServiceImpl verificationService;
//
//    public SignatureVerificationInterceptor(SignatureVerificationServiceImpl verificationService) {
//        this.verificationService = verificationService;
//    }
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(httpRequest);
//        String signature = request.getHeader("Signature");
//        String body = new String(wrappedRequest.getInputStream().readAllBytes());
//
//        if (verificationService.verifySignature(body, signature)) {
//            return true;
//        }else{
//            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//            response.getWriter().write("Invalid signature");
//            return false; // Stop the execution chain
//        }
//    }
//}
