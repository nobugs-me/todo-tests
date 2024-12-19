package com.todo;

import com.todo.annotations.BeforeEachExtension;
import com.todo.conf.Configuration;
import com.todo.requests.TodoRequest;
import com.todo.requests.TodoRequester;
import com.todo.specs.request.RequestSpec;
import com.todo.storages.TestDataStorage;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(BeforeEachExtension.class)
public class BaseTest {
    protected TodoRequester todoRequester;
    protected SoftAssertions softly;

    @BeforeAll
    public static void setup() {
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.baseURI = Configuration.getProperty("baseUrl");
        RestAssured.port = 8080;
    }

    @BeforeEach
    public void setupTest() {
        todoRequester = new TodoRequester(RequestSpec.authSpecAsAdmin());
        softly = new SoftAssertions();
    }

    @AfterEach
    public void clean() {
        TestDataStorage.getInstance().getStorage()
                .forEach((k, v) ->
                    new TodoRequest(RequestSpec.authSpecAsAdmin())
                            .delete(k));

        TestDataStorage.getInstance().clean();
    }

    @AfterEach
    public void assertAll() {
        softly.assertAll();
    }
}
