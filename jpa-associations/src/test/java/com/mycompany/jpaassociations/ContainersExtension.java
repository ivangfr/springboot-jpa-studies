package com.mycompany.jpaassociations;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Collections;

@Testcontainers
public class ContainersExtension implements BeforeAllCallback, AfterAllCallback {

    @Container
    private static MySQLContainer mySQLContainer;

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        // MySQL
        mySQLContainer = new MySQLContainer("mysql:5.7.24")
                .withDatabaseName("studiesdb-test")
                .withUsername("root-test")
                .withPassword("secret-test");
        mySQLContainer.setNetworkAliases(Collections.singletonList("mysql"));
        mySQLContainer.setPortBindings(Collections.singletonList("3306:3306"));
        mySQLContainer.start();
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) {
        mySQLContainer.stop();
    }
}
