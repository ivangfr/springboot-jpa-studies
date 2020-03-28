package com.mycompany.jpabatch;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Collections;

@Testcontainers
public class ContainersExtension implements BeforeAllCallback, AfterAllCallback {

    @Container
    private MySQLContainer mySQLContainer;

    @Container
    private PostgreSQLContainer postgreSQLContainer;

    @Override
    public void beforeAll(ExtensionContext extensionContext) {

        // MySQL
        mySQLContainer = new MySQLContainer("mysql:5.7.29")
                .withDatabaseName("studiesdb-test")
                .withUsername("root-test")
                .withPassword("secret-test");
        mySQLContainer.setPortBindings(Collections.singletonList("3306:3306"));
        mySQLContainer.start();

        // PostgreSQL
        postgreSQLContainer = new PostgreSQLContainer("postgres:12.2")
                .withDatabaseName("studiesdb-test")
                .withUsername("postgres-test")
                .withPassword("postgres-test");
        postgreSQLContainer.setPortBindings(Collections.singletonList("5432:5432"));
        postgreSQLContainer.start();
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) {
        mySQLContainer.stop();
        postgreSQLContainer.stop();
    }
}
