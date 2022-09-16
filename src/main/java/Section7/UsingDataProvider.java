package Section7;

import Files.PayLoad;
import Files.ReUsableMethods;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class UsingDataProvider { //2

    @Test(dataProvider = "BookData")
    public void addBook(String isbn, String aisle) {
        RestAssured.baseURI = "http://216.10.245.166";

        String response = given().log().all()
                .header("Content-Type", "application/json")
                .body(PayLoad.AddBook(isbn, aisle))
                .when().post("Library/Addbook.php")
                .then().assertThat().statusCode(200)
                .extract().response().asString();

        JsonPath js = ReUsableMethods.rawToJson(response);
        String ID = js.get("ID");
        System.out.println(ID);
    }


    @Test(dataProvider = "BookData")
    public void deleteBook(String isbn, String aisle) {
        RestAssured.baseURI = "http://216.10.245.166";

        given().log().all()
                .body(PayLoad.AddBook(isbn, aisle))
                .when().delete("Library/DeleteBook.php")
                .then().log().all()
                .assertThat().statusCode(200);
    }


    @Test(dataProvider = "BookData")
    public void getBook(String isbn, String aisle) {
        RestAssured.baseURI = "http://216.10.245.166";

        given().log().all()
                .queryParam("isbn", isbn)
                .queryParam("aisle", aisle)
                .when().get("Library/GetBook.php")
                .then().log().all()
                .assertThat().statusCode(200);
    }

    @DataProvider(name = "BookData")
    public Object[][] getData() {
        //array = collection of elements
        //multidimensional array = collection of arrays
        return new Object[][] {{"adeid", "7621"}, {"nwcdt", "9054"}, {"xthwq", "8926"}};
    }
}