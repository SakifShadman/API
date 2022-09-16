package Section7;

import Files.PayLoad;
import Files.ReUsableMethods;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

public class DynamicJson { //1

    @Test
    public void addBook() {
        RestAssured.baseURI = "http://216.10.245.166";

        String response = given().log().all()
                .header("Content-Type", "application/json")
                .body(PayLoad.AddBook("adsdfe", "3232"))
                .when().post("Library/Addbook.php")
                .then().assertThat().statusCode(200)
                .extract().response().asString();

        JsonPath js = ReUsableMethods.rawToJson(response);
        String ID = js.get("ID");
        System.out.println(ID);
    }
}