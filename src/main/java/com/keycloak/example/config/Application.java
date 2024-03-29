package com.keycloak.example.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@SpringBootApplication(scanBasePackages = { "com.keycloak.example.controller" }, exclude = {
		UserDetailsServiceAutoConfiguration.class })
@EntityScan(basePackages = { "com.keycloak.example.model" })
@ComponentScan(basePackages = { "com.keycloak.example" })
@EnableJpaRepositories("com.keycloak.example.repository")
@EnableTransactionManagement(proxyTargetClass = true)
@SecurityScheme(name = "Keycloak", openIdConnectUrl = "http://127.0.0.1:8500/realms/keycloak-test/.well-known/openid-configuration", scheme = "bearer", type = SecuritySchemeType.OPENIDCONNECT, in = SecuritySchemeIn.HEADER)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}
}
