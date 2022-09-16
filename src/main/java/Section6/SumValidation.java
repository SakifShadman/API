package Section6;

import Files.PayLoad;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SumValidation { //2

    @Test
    public void sumOfCourses() {

        JsonPath js = new JsonPath(PayLoad.CoursePrice());
        int count = js.getInt("courses.size()");

        int sum = 0;

        for (int i=0; i<count; i++) {
            int price = js.getInt("courses["+ i +"].price");
            int copies = js.getInt("courses["+ i +"].copies");
            int amount = price * copies;
            //System.out.println(amount);
            sum = sum + amount;
        }
        //System.out.println(sum);

        int purchaseAmount = js.getInt("dashboard.purchaseAmount");
        Assert.assertEquals(purchaseAmount, sum);
    }
}