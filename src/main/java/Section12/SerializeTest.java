package Section12;

import Section12.Pojo.AddPlace;
import Section12.Pojo.Location;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class SerializeTest {
    public static void main(String[] args) {

        RestAssured.baseURI = "https://rahulshettyacademy.com";

        AddPlace addPlaceObject = new AddPlace();
        addPlaceObject.setAccuracy(50);
        addPlaceObject.setName("Frontline house");
        addPlaceObject.setPhone_number("(+91) 983 893 3937");
        addPlaceObject.setAddress("29, side layout, cohen 09");
        addPlaceObject.setWebsite("http://google.com");
        addPlaceObject.setLanguage("French-IN");

        List<String> typesList = new ArrayList<>();
        typesList.add("shoe park");
        typesList.add("shop");
        addPlaceObject.setTypes(typesList);

        Location locationObject = new Location();
        locationObject.setLat(-38.383494);
        locationObject.setLng(33.427362);
        addPlaceObject.setLocation(locationObject);


        Response response = given().log().all()
                .queryParams("key","qaclick123")
                .body(addPlaceObject)
                .when().post("/maps/api/place/add/json")
                .then().assertThat().statusCode(200)
                .extract().response();

        String responseString = response.asString();
        System.out.println(responseString);
    }
}