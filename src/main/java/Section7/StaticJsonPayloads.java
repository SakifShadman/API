package Section7;

import io.restassured.RestAssured;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class StaticJsonPayloads { //3

    public static void main(String[] args) throws IOException {

        //content of the file to String -> content of file can be convert in to Byte -> Byte date to String

        RestAssured.baseURI = "https://rahulshettyacademy.com";

        String path = new String(Files.readAllBytes(Paths.get
                        ("C:\\Users\\arnob\\OneDrive\\Documents\\RSA API Testing\\addPlace.json")));

        given().log().all()
                .queryParam("key", "qaclick123")
                .header("Content_Type", "application/json")
                .body(path)
                .when().post("/maps/api/place/add/json")
                .then().log().all()
                .assertThat().statusCode(200)
                .body("scope", equalTo("APP"))
                .header("server", "Apache/2.4.41 (Ubuntu)");
    }
}