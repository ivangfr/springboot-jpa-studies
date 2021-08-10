package com.mycompany.jpabatch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Slf4j
@Testcontainers
public abstract class AbstractTestcontainers {

    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13.3");

    private static final MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0.26")
            .withUrlParam("characterEncoding", "UTF-8")
            .withUrlParam("serverTimezone", "UTC");

    @DynamicPropertySource
    private static void dynamicProperties(DynamicPropertyRegistry registry) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        String[] activeProfiles = context.getEnvironment().getActiveProfiles();

        validateActiveProfiles(activeProfiles);
        String activeProfile = activeProfiles[0];

        if (activeProfile.equals("postgres-test")) {
            postgreSQLContainer.start();
            setDynamicProperties(registry, postgreSQLContainer.getJdbcUrl(), postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword());
        } else {
            mySQLContainer.start();
            setDynamicProperties(registry, mySQLContainer.getJdbcUrl(), mySQLContainer.getUsername(), mySQLContainer.getPassword());
        }
    }

    private static void setDynamicProperties(DynamicPropertyRegistry registry, String url, String username, String password) {
        registry.add("spring.datasource.url", () -> url);
        registry.add("spring.datasource.username", () -> username);
        registry.add("spring.datasource.password", () -> password);
    }

    private static void validateActiveProfiles(String[] activeProfiles) {
        if (activeProfiles.length != 1 || !(activeProfiles[0].equals("mysql-test") || activeProfiles[0].equals("postgres-test"))) {
            String errorMessage = "The spring active profile was not informed or it's invalid! It must be mysql-test or postgres-test.";
            log.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
