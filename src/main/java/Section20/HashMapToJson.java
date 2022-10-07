package Section20;

import Files.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class HashMapToJson {

    @Test
    public void addBook() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "Learn Appium Automation with Java");
        map.put("isbn", "g21wj78");
        map.put("aisle", "332");
        map.put("author", "sakif");

        RestAssured.baseURI = "http://216.10.245.166";

        String response = given().log().all()
                .header("Content-Type", "application/json")
                .body(map)
                .when().post("Library/Addbook.php")
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response().asString();

        JsonPath js = ReUsableMethods.rawToJson(response);
        String ID = js.get("ID");
        System.out.println(ID);
    }
}
