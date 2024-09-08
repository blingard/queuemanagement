package com.example.messagequeuemanagement;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@OpenAPIDefinition(
        servers = {
                @Server(url = "http://localhost:8080/")//
        },
        info = @Info(
                title = "Queue Management",
                version = "1.0",
                contact = @Contact(name = "Anonymous"),
                description = "Queue Management",
                license = @License(name = "Licence API")))
@SecurityScheme(name = "auth", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public class MessagequeuemanagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessagequeuemanagementApplication.class, args);
    }

    @Bean("passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
