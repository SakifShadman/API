package Section5;

import Files.PayLoad;
import Files.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class AddUpdateGetPlace { //2
    public static void main(String[] args) {

        //Add place -> Update Place with New Address -> Get Place to validate if New address is present in response

        RestAssured.baseURI = "https://rahulshettyacademy.com";

        //Add place
        String response = given().log().all()
                .queryParam("key", "qaclick123")
                .header("Content_Type", "application/json")
                .body(PayLoad.AddPlace())
                .when().post("/maps/api/place/add/json")
                .then().assertThat().statusCode(200)
                .body("scope", equalTo("APP"))
                .header("server", "Apache/2.4.41 (Ubuntu)")
                .extract().response().asString();

        //System.out.println(response);

        JsonPath js = new JsonPath(response); //for parsing Json
        String placeID = js.getString("place_id");

        System.out.println(placeID);
        System.out.println();


        //Update place
        String newAddress = "11124 Liverpool St";

        given().log().all()
                .queryParam("key", "qaclick123")
                .header("Content_Type", "application/json")
                .body("{\n" +
                        "\"place_id\":\""+ placeID +"\",\n" +
                        "\"address\":\""+ newAddress +"\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}\n")
                .when().put("/maps/api/place/update/json")
                .then().log().all()
                .assertThat().statusCode(200)
                .body("msg", equalTo("Address successfully updated"));


        //Get place
        String getPlaceResponse = given().log().all()
                .queryParam("key", "qaclick123")
                .queryParam("place_id", placeID)
                .when().get("/maps/api/place/get/json")
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response().asString();

        JsonPath js1 = ReUsableMethods.rawToJson(getPlaceResponse);
        String actualAddress = js1.getString("address");

        System.out.println(actualAddress);
        Assert.assertEquals(actualAddress, newAddress);
    }
}