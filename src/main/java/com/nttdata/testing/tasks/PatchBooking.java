package com.nttdata.testing.tasks;

import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Patch;

import static net.serenitybdd.screenplay.Tasks.instrumented;

// Endpoint 4: PATCH /booking/{id}  (usa el bookingId y el token guardados antes)
public class PatchBooking implements Task {

    private final String firstname, lastname, totalprice;

    public PatchBooking(String firstname, String lastname, String totalprice) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.totalprice = totalprice;
    }

    public static Performable fromPage(String firstname, String lastname, String totalprice) {
        return instrumented(PatchBooking.class, firstname, lastname, totalprice);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        String bookingId = actor.recall("bookingId");
        String token = actor.recall("token");

        actor.attemptsTo(
                Patch.to("/booking/" + bookingId)
                        .with(requestSpecification -> requestSpecification
                                .contentType(ContentType.JSON)
                                .header("Accept", "application/json")
                                .header("Cookie", "token=" + token)
                                .body("""
                                        {
                                            "firstname": "%s",
                                            "lastname": "%s",
                                            "totalprice": %s
                                        }""".formatted(firstname, lastname, totalprice))
                                .log().all()));

        SerenityRest.lastResponse().body().prettyPrint();
    }
}
