package com.todo.delete;

import com.todo.BaseTest;
import com.todo.annotations.PrepareTodo;
import com.todo.models.Todo;
import com.todo.requests.TodoRequest;
import com.todo.specs.request.RequestSpec;
import com.todo.specs.response.AccessErrorResponse;
import com.todo.specs.response.IncorrectDataResponse;
import io.qameta.allure.Description;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class DeleteTodosTests extends BaseTest {
    @Test
    @PrepareTodo(1)
    @Description("TC1: Авторизированный юзер может удалить todo")
    public void testDeleteExistingTodoWithValidAuth() {
        Todo createdTodo = todoRequester.getValidatedRequest().readAll().getFirst();

        todoRequester.getValidatedRequest().delete(createdTodo.getId());

        softly.assertThat(todoRequester.getValidatedRequest().readAll()).hasSize(0);
    }

    @Test
    @PrepareTodo(1)
    @Description("TC2: Неавторизированный юзер не может удалить todo")
    public void testDeleteTodoWithoutAuthHeader() {
        Todo createdTodo = todoRequester.getValidatedRequest().readAll().getFirst();

        new TodoRequest(RequestSpec.unauthSpec()).delete(createdTodo.getId())
                .then().assertThat().spec(AccessErrorResponse.userIsUnauthorized());

        softly.assertThat(todoRequester.getValidatedRequest().readAll()).hasSize(1);
    }

    @Test
    @Description("TC4: Авторизованный юзер не может удалить todo с несуществующим id")
    public void testDeleteNonExistentTodo() {
        var nonExistingId = new Random().nextInt();
        todoRequester.getRequest().delete(nonExistingId)
                .then().assertThat().spec(IncorrectDataResponse.nonExistingId(nonExistingId));
    }
}
