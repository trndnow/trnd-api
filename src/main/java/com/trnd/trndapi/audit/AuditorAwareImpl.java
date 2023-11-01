package com.trnd.trndapi.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){
            return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getName());
        }
        return Optional.empty();
    }
}
