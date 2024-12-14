package com.todo.specs.response;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;

public class IncorrectDataResponse {
    public ResponseSpecification sameId(long id) {
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectStatusCode(HttpStatus.SC_BAD_REQUEST);
        responseSpecBuilder.expectBody("message", Matchers.containsString("You are trying to use the same id:" + id) );
        return responseSpecBuilder.build();
    }
}
