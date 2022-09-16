package Section8;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.io.File;

import static io.restassured.RestAssured.*;

public class JiraTest {
    public static void main(String[] args) {

        RestAssured.baseURI = "http://localhost:8080";

        //login scenario
        SessionFilter session = new SessionFilter();

        String response = given().relaxedHTTPSValidation()
                        .header("Content-Type", "application/json")
                        .body("{\n" +
                                "    \"username\": \"xxboy638\",\n" +
                                "    \"password\": \"Arnob5055\"\n" +
                                "}")
                        .log().all().filter(session)
                        .when().post("/rest/auth/1/session")
                        .then().log().all()
                        .extract().response().asString();


        //add comment
        String expectedMessage = "Hi!!! How Are You?";

        String addCommentResponse = given().pathParam("id", "10106")
                .log().all()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"body\": \""+ expectedMessage +"\",\n" +
                        "    \"visibility\": {\n" +
                        "        \"type\": \"role\",\n" +
                        "        \"value\": \"Administrators\"\n" +
                        "    }\n" +
                        "}")
                .filter(session)
                .when().post("/rest/api/2/issue/{id}/comment")
                .then().log().all().assertThat().statusCode(201)
                .extract().response().asString();

        JsonPath js = new JsonPath(addCommentResponse);
        String commentId = js.get("id");


        //add attachment
        given().header("X-Atlassian-Token","no-check").filter(session)
                .pathParam("id", "10106")
                .header("Content-Type","multipart/form-data")
                .multiPart("file", new File("src/jira.txt"))
                .when().post("/rest/api/2/issue/{id}/attachments")
                .then().log().all().assertThat().statusCode(200);


        /*
        //get issue(for all fields)
        String issueDetails = given().filter(session)
                .pathParam("id", "10106").log().all()
                .when().get("/rest/api/2/issue/{id}")
                .then().log().all().extract().response().asString();

        System.out.println(issueDetails);
        */


        //get issue (for specified field)
        String issueDetails = given().filter(session)
                .pathParam("id", "10106")
                .queryParam("fields", "comment").log().all()
                .when().get("/rest/api/2/issue/{id}")
                .then().log().all().extract().response().asString();
        //System.out.println(issueDetails);

        JsonPath js1 = new JsonPath(issueDetails);
        int commentsCount = js1.getInt("fields.comment.comments.size()");

        for (int i=0; i<commentsCount; i++) {
            String commentIdIssue = js1.get("fields.comment.comments["+ i +"].id");
            //System.out.println(commentIdIssue);
            if (commentIdIssue.equalsIgnoreCase(commentId)) {
                String message = js1.get("fields.comment.comments["+ i +"].body");
                //System.out.println(message);
                Assert.assertEquals(message, expectedMessage);
            }
        }
    }
}