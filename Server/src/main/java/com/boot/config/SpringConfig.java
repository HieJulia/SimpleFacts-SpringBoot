package com.boot.config;

import com.boot.repository.ConnectedUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * This enables the usage of the JPA Repositories which can be found in
 * com.boot.repository
 */
@Configuration
@EnableJpaRepositories("com.boot.repository.JPA")
@ComponentScan
class SpringConfig {

    @Bean
    @Description("Keeps track of all currently connected users")
    public ConnectedUserRepository connectedUserRepository() {
        return new ConnectedUserRepository();
    }
}
