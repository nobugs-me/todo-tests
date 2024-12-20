package com.todo.get;


import com.todo.BaseTest;
import com.todo.annotations.BeforeEachExtension;
import com.todo.annotations.Mobile;
import com.todo.annotations.PrepareTodo;
import com.todo.specs.response.IncorrectDataResponse;
import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.todo.generators.TestDataGenerator.generateTestData;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import com.todo.models.Todo;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

@Epic("TODO Management")
@Feature("Get Todos API")
@ExtendWith(BeforeEachExtension.class)
public class GetTodosTests extends BaseTest {
    @Test
    @PrepareTodo(5)
    @Description("Авторизованный юзер может получить список всех todo")
    public void testGetTodosWithExistingEntries() {
        var createdTodos = todoRequester.getValidatedRequest().readAll();

        softly.assertThat(createdTodos).hasSize(5);
    }

    @Test
    @Mobile
    @PrepareTodo(5)
    @Description("Авторизованный юзер может получать список todo с учетом offset и limit для пагинации")
    public void testGetTodosWithOffsetAndLimit() {
        var createdTodos = todoRequester.getValidatedRequest().readAll(2,2);

        softly.assertThat(createdTodos).hasSize(2);
    }

    @Test
    @DisplayName("Передача некорректных значений в offset и limit")
    public void testGetTodosWithInvalidOffsetAndLimit() {
        todoRequester.getRequest().readAll(-1,-1)
                .then().assertThat().spec(IncorrectDataResponse.offsetOrLimitHaveIncorrectValues());
    }

    @Test
    @DisplayName("Проверка ответа при превышении максимально допустимого значения limit")
    public void testGetTodosWithExcessiveLimit() {
        var piginatedTodos = todoRequester.getValidatedRequest().readAll(1, 1000);
        var allTodos = todoRequester.getValidatedRequest().readAll();

        softly.assertThat(piginatedTodos).isEqualTo(allTodos);
    }
}
