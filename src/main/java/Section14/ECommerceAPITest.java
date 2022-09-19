package Section14;

import Section14.Pojo.CreateOrder.OrderDetail;
import Section14.Pojo.CreateOrder.OrderResponse;
import Section14.Pojo.CreateOrder.Orders;
import Section14.Pojo.Login.LoginRequest;
import Section14.Pojo.Login.LoginResponse;
import Section14.Pojo.ViewOrder.ViewOrderResponse;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class ECommerceAPITest {
    public static void main(String[] args) {

        //Login
        RequestSpecification loginBaseReq = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .setContentType(ContentType.JSON)
                .build();

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserEmail("arnob5055@gmail.com");
        loginRequest.setUserPassword("Arnob$5055");

        RequestSpecification reqLogin = given()
                .relaxedHTTPSValidation()
                .log().all()
                .spec(loginBaseReq)
                .body(loginRequest);

        LoginResponse loginResponse = reqLogin.when()
                .post("/api/ecom/auth/login")
                .then().log().all()
                .extract().response().as(LoginResponse.class);

        String token = loginResponse.getToken();
        String userId = loginResponse.getUserId();


        //Add Product
        RequestSpecification addProductBaseReq = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .addHeader("authorization", token)
                .build();

        RequestSpecification reqAddProduct = given().log().all()
                .spec(addProductBaseReq)
                .param("productName", "Laptop")
                .param("productAddedBy", userId)
                .param("productCategory", "tech")
                .param("productSubCategory", "computer")
                .param("productPrice", "111500")
                .param("productDescription", "Dell XPS 15")
                .param("productFor", "man")
                .multiPart("productImage", new File("C://Users//arnob//Downloads//laptop.png"));

        String addProductResponse = reqAddProduct.when()
                .post("/api/ecom/product/add-product")
                .then().log().all()
                .extract().response().asString();

        JsonPath js = new JsonPath(addProductResponse);
        String productId = js.get("productId");


        //Create Order
        RequestSpecification createOrderBaseReq = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .addHeader("authorization", token)
                .setContentType(ContentType.JSON)
                .build();

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setCountry("Afghanistan");
        orderDetail.setProductOrderedId(productId);

        List<OrderDetail> orderDetailList = new ArrayList<>();
        orderDetailList.add(orderDetail);

        Orders orders = new Orders();
        orders.setOrders(orderDetailList);

        RequestSpecification reqCreateOrder = given().log().all()
                .spec(createOrderBaseReq)
                .body(orders);

        OrderResponse orderResponse = reqCreateOrder.when()
                .post("/api/ecom/order/create-order")
                .then().log().all()
                .extract().response().as(OrderResponse.class);

        List<String> orderId = orderResponse.getOrders();


        //View Order Details
        RequestSpecification viewOrderBaseReq = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .addHeader("authorization", token)
                .build();

        RequestSpecification reqViewOrder = given().log().all()
                .spec(viewOrderBaseReq)
                .queryParams("id", orderId);

        ViewOrderResponse viewOrderResponse = reqViewOrder.when()
                .get("/api/ecom/order/get-orders-details")
                .then().log().all()
                .extract().response().as(ViewOrderResponse.class);

        Assert.assertEquals(viewOrderResponse.getData().getProductOrderedId(), productId);


        //Delete Product
        RequestSpecification deleteProductBaseReq = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .addHeader("authorization", token)
                .setContentType(ContentType.JSON)
                .build();

        RequestSpecification reqDeleteProduct = given().log().all()
                .spec(deleteProductBaseReq)
                .pathParam("productId", productId);

        String deleteProductResponse = reqDeleteProduct.when()
                .delete("/api/ecom/product/delete-product/{productId}")
                .then().log().all()
                .extract().response().asString();

        JsonPath js2 = new JsonPath(deleteProductResponse);
        String message = js2.get("message");
        Assert.assertEquals(message, "Product Deleted Successfully");
    }
}