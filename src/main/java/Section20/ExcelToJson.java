package Section20;

import Files.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ExcelToJson {

    @Test
    public void excelTesting() {
        DataDriven dataDriven = new DataDriven();
        ArrayList data = dataDriven.getData("Rest Assured", "JavaBook");

        Map<String, Object> map = new HashMap<>();
        map.put("name", data.get(1));
        map.put("isbn", data.get(2));
        map.put("aisle", data.get(3));
        map.put("author", data.get(4));

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
