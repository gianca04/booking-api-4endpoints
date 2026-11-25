package com.nttdata.testing.tasks;

import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

import static net.serenitybdd.screenplay.Tasks.instrumented;

// Endpoint 3: POST /auth
public class PostToken implements Task {

    private final String username, password;

    public PostToken(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static Performable generateToken(String username, String password) {
        return instrumented(PostToken.class, username, password);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Post.to("/auth")
                        .with(requestSpecification -> requestSpecification
                                .contentType(ContentType.JSON)
                                .body("""
                                        {
                                            "username": "%s",
                                            "password": "%s"
                                        }""".formatted(username, password))
                                .log().all()));

        SerenityRest.lastResponse().body().prettyPrint();
        if (SerenityRest.lastResponse().statusCode() == 200) {
            actor.remember("token", SerenityRest.lastResponse().path("token").toString());
        }
    }
}
