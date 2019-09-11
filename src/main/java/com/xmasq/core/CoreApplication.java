package com.xmasq.core;

import com.xmasq.core.base.repository.BaseRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author guoyu.huang
 * @version 1.0.0
 */
@SpringBootApplication
@EnableCaching
@EnableJpaRepositories(basePackages = "com.xmasq", repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class)
@ComponentScan(basePackages = "com.xmasq")
@EntityScan(basePackages = "com.xmasq")
public class CoreApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(CoreApplication.class);
    }

    public static void main(String[] args) {
            SpringApplication.run(CoreApplication.class, args);
        }

}
