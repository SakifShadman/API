package Section5;

import Files.PayLoad;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Basics { //1
    public static void main(String[] args) {

        //validate if Add place API is working as expected

        //given - all import details
        //when - submit the API - resource, http method
        //then - validate the response

        RestAssured.baseURI = "https://rahulshettyacademy.com";

        given().log().all()
                .queryParam("key", "qaclick123")
                .header("Content_Type", "application/json")
                .body(PayLoad.AddPlace())
                .when().post("/maps/api/place/add/json")
                .then().log().all()
                .assertThat().statusCode(200)
                .body("scope", equalTo("APP"))
                .header("server", "Apache/2.4.41 (Ubuntu)");

        //Add place -> Update Place with New Address -> Get Place to validate if New address is present in response
    }
}