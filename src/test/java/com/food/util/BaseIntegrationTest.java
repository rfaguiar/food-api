package com.food.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public abstract class BaseIntegrationTest {

    @LocalServerPort
    protected int port;
    @Autowired
    protected DatabaseCleaner databaseCleaner;
}
