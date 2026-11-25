package com.nttdata.testing.stepDefinitions;

import com.nttdata.testing.questions.ResponseCode;
import com.nttdata.testing.tasks.GetAllBookings;
import com.nttdata.testing.tasks.PatchBooking;
import com.nttdata.testing.tasks.PostBooking;
import com.nttdata.testing.tasks.PostToken;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.CoreMatchers.equalTo;

public class BookingStepDefinition {

    @Before
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
    }

    @Given("el {actor} establece el endpoint de booking")
    public void elActorEstableceElEndpointDeBooking(Actor actor) {
        actor.whoCan(CallAnApi.at("https://restful-booker.herokuapp.com"));
    }

    @When("el {actor} realiza una solicitud GET")
    public void elActorRealizaUnaSolicitudGET(Actor actor) {
        actor.attemptsTo(GetAllBookings.fromEndpoint("/booking"));
    }

    @When("el actor crea un booking con el {string} {string} {string} {string} {string} {string} {string}")
    public void elActorCreaUnBookingConEl(String firstname, String lastname, String totalprice, String depositpaid,
                                          String checkin, String checkout, String additionalneeds) {
        theActorInTheSpotlight().attemptsTo(
                PostBooking.fromPage(firstname, lastname, totalprice, depositpaid, checkin, checkout, additionalneeds));
    }

    @When("el actor genera un token con {string} {string}")
    public void elActorGeneraUnTokenCon(String username, String password) {
        theActorInTheSpotlight().attemptsTo(PostToken.generateToken(username, password));
    }

    @When("el actor actualiza un booking con los datos {string} {string} {string}")
    public void elActorActualizaUnBookingConLosDatos(String firstname, String lastname, String totalprice) {
        theActorInTheSpotlight().attemptsTo(PatchBooking.fromPage(firstname, lastname, totalprice));
    }

    @Then("el codigo de respuesta debe ser {int}")
    public void elCodigoDeRespuestaDebeSer(int responseCode) {
        theActorInTheSpotlight().should(
                seeThat("El código de respuesta", ResponseCode.getStatus(), equalTo(responseCode)));
    }
}
