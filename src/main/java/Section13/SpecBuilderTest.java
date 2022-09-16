package Section13;

import Section12.Pojo.AddPlace;
import Section12.Pojo.Location;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class SpecBuilderTest {
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


        RequestSpecification reqSpec = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .addQueryParam("key","qaclick123")
                .setContentType(ContentType.JSON).build();

        ResponseSpecification resSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON).build();

        RequestSpecification requestSpecification = given().spec(reqSpec).body(addPlaceObject);

        Response response = requestSpecification
                .when().post("/maps/api/place/add/json")
                .then().spec(resSpec)
                .extract().response();

        String responseString = response.asString();
        System.out.println(responseString);
    }
}