package com.dewafer.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaConfig {

    private String defaultAuditor = "demo-app";

    @Bean
    public AuditorAware<String> stringAuditorAware() {
        return new AuditorAware<String>() {
            @Override
            public String getCurrentAuditor() {
                return defaultAuditor;
            }
        };
    }
}
