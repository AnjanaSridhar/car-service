package com.as.spring;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.as.persistence.repo")
@EntityScan("com.as.persistence.model")
@ComponentScan({
        "com.as.services",
        "com.as.controllers",
        "com.as.validators",
        "com.as.exceptions",
        "com.as.model",
        "com.as.persistence"
})
@SpringBootApplication
public class ServiceApplication {
    public static void main(String args[]) {
        new SpringApplicationBuilder(ServiceApplication.class).web(WebApplicationType.SERVLET).run(args);
    }
}
