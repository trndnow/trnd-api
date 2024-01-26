package com.trnd.trndapi.config;

import com.trnd.trndapi.validation.SpringConstraintValidatorFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ValidatorConfig {

    @Bean
    public LocalValidatorFactoryBean validatorFactoryBean(ApplicationContext applicationContext){
        LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
        validatorFactoryBean.setConstraintValidatorFactory(new SpringConstraintValidatorFactory(applicationContext));
        return validatorFactoryBean;
    }
}
