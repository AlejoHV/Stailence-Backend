package com.example.sistemas.stailenceback.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.sistemas.stailenceback.repository")
@EnableTransactionManagement
public class DatabaseConfig {

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.username}")
    private String datasourceUser;

    @Value("${spring.datasource.password}")
    private String datasourcePassword;

    // Si necesita configuraciones avanzadas de DataSource o EntityManagerFactory,
    // pueden añadirse aquí. Por ahora dejamos que Spring Boot auto-configure.
}

