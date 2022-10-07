package Section16.stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinition {

    @Given("User is on NetBanking landing page")
    public void userIsOnNetBankingLandingPage()  {
        System.out.println("navigate to login url");
    }

    @When("User login into application with username and password")
    public void userLoginIntoApplicationWithUsernameAndPassword() {
        System.out.println("logged in success");
    }

    @Then("Home page is populated")
    public void homePageIsPopulated()  {
        System.out.println("validated home page");
    }


    @When("User login into application with {string} and password {string}")
    public void userLoginIntoApplicationWithAndPassword(String arg0, String arg1) {
        System.out.println(arg0);
        System.out.println(arg1);
    }

    @And("Cards displayed are {string}")
    public void cardsDisplayedAre(String arg0) {
        System.out.println(arg0);
    }
}
