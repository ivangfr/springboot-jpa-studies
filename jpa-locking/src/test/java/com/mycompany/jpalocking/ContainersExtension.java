package com.mycompany.jpalocking;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Collections;

@Slf4j
@Testcontainers
public class ContainersExtension implements BeforeAllCallback, AfterAllCallback {

    @Container
    private MySQLContainer mySQLContainer;

    @Container
    private PostgreSQLContainer postgreSQLContainer;

    private String activeProfile;

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        String[] activeProfiles = context.getEnvironment().getActiveProfiles();

        validateActiveProfiles(activeProfiles);
        activeProfile = activeProfiles[0];

        if (activeProfile.equals("postgres-test")) {
            postgreSQLContainer = new PostgreSQLContainer("postgres:12.3")
                    .withDatabaseName("studiesdb-test")
                    .withUsername("postgres-test")
                    .withPassword("postgres-test");
            postgreSQLContainer.setPortBindings(Collections.singletonList("54322:5432"));
            postgreSQLContainer.start();
        } else {
            mySQLContainer = new MySQLContainer("mysql:8.0.20")
                    .withDatabaseName("studiesdb-test")
                    .withUsername("root-test")
                    .withPassword("secret-test");
            mySQLContainer.setPortBindings(Collections.singletonList("33066:3306"));
            mySQLContainer.start();
        }
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) {
        if (activeProfile.equals("postgres-test")) {
            postgreSQLContainer.stop();
        } else {
            mySQLContainer.stop();
        }
    }

    private void validateActiveProfiles(String[] activeProfiles) {
        if (activeProfiles.length != 1 || !(activeProfiles[0].equals("mysql-test") || activeProfiles[0].equals("postgres-test")) ) {
            String errorMessage = "The spring active profile was not informed or it's invalid! It must be mysql-test or postgres-test.";
            log.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
