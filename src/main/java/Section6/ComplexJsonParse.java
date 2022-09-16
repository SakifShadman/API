package Section6;

import Files.PayLoad;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse { //1
    public static void main(String[] args) {

        JsonPath js = new JsonPath(PayLoad.CoursePrice());

        //1. Print No of courses returned by API
        int count = js.getInt("courses.size()");
        System.out.println(count);
        System.out.println();


        //2. Print Purchase Amount
        int totalAmount = js.getInt("dashboard.purchaseAmount");
        System.out.println(totalAmount);
        System.out.println();


        //3. Print Title of the first course
        String titleFirstCourse = js.get("courses[0].title");
        System.out.println(titleFirstCourse);
        System.out.println();


        //4. Print All course titles and their respective Prices
        for (int i=0; i<count; i++) {
            String courseTitles = js.get("courses["+ i +"].title");
            System.out.println(courseTitles);
            System.out.println(js.get("courses["+ i +"].price").toString());
        }
        System.out.println();


        //5. Print no of copies sold by RPA Course
        for (int i=0; i<count; i++) {
            String courseTitles = js.get("courses["+ i +"].title");
            if (courseTitles.equalsIgnoreCase("RPA")) {
                int copies = js.get("courses["+ i +"].copies");
                System.out.println(copies);
                break;
            }
        }
    }
}