package com.xmasq.core;

import com.xmasq.core.error.CommonErrorPageRegistrar;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author guoyu.huang
 * @version 1.0.0
 */
@Configuration
public class CommonCoreConfig {

    @Bean
    @ConditionalOnMissingBean(ErrorPageRegistrar.class)
    public ErrorPageRegistrar commonErrorPageRegistrar(){
        return new CommonErrorPageRegistrar();
    }
}
