package testcases;

import org.testng.annotations.Test;

import routes.Routes;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import payloads.Payload;
import pojo.Cart;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.text.ParseException;
import java.util.List;

public class CartTests extends BaseClass {

	@Test
	public void getAllCarts() {
		
		given()
		.when()
			.get(Routes.GET_ALL_CARTS)
		.then()
			.statusCode(200)
			.log().body()
			.body("size()",greaterThan(0));
	}
	
	@Test
	public void getAllCartsByID() {
		
		int cartId = Integer.parseInt(configReader.getProperty("cartId"));
		
		given()
			.pathParam("id", cartId)
		.when()
			.get(Routes.GET_ALL_CARTS_BY_ID)
		.then()
			.statusCode(200)
			.log().body()
			.body("size()",greaterThan(0))
			.body("id", equalTo(cartId));
	}
	
	@Test
	public void getAllCartsByLimit() {
		
		given()
			.pathParam("limit", configReader.getProperty("limit"))
		.when()
			.get(Routes.GET_ALL_CARTS_BY_LIMIT)
		.then()
			.statusCode(200)
			.log().body()
			.body("size()",greaterThan(0));
	}
	
	@Test
	public void getAllCartsSortedDESC() {
		
		String orderString = configReader.getProperty("orderDESC");
		
		Response response = given()
			.pathParam("order", orderString)
		.when()
			.get(Routes.GET_CARTS_SORTED_BY_ID)
		.then()
			.statusCode(200)
			.log().body()
			.body("size()",greaterThan(0))
			.extract().response();
		
		List<Integer> iDList = response.jsonPath().getList("id", Integer.class);
		
		assertThat(isSortedDesceding(iDList), is(true));
	}
		
	@Test
	public void getAllCartsSortedASC() {
		
		String orderString = configReader.getProperty("orderASC");
		
		Response response = given()
			.pathParam("order", orderString)
		.when()
			.get(Routes.GET_CARTS_SORTED_BY_ID)
		.then()
			.statusCode(200)
			.log().body()
			.body("size()",greaterThan(0))
			.extract().response();
		
		List<Integer> iDList = response.jsonPath().getList("id", Integer.class);
		
		assertThat(isSortedAsceding(iDList), is(true));
	}
	
	@Test
	public void getAllCartsInDateRange() {
		
		String startDate = configReader.getProperty("startdate");
		String endDate = configReader.getProperty("enddate");
		
		Response response = given()
			.pathParam("start_date", startDate)
			.pathParam("end_date", endDate)
		.when()
			.get(Routes.GET_ALL_CARTS_IN_DATE_RANGE)
		.then()
			.statusCode(200)
			.log().body()
			.body("size()",greaterThan(0))
			.extract().response();
		
		List<String> dateList = response.jsonPath().getList("date", String.class);
		//calling helper method to confirm
		assertThat(validateCartDatesWithinRange(dateList, startDate, endDate), is(true));
	}
	
	@Test
	public void getAllCartByUserID() {
		
		int userId = configReader.getIntProperty("userId");
		given()
		.pathParam("userId", userId)
	.when()
		.get(Routes.GET_ALL_CARTS_BY_USERID)
	.then()
		.statusCode(200)
		.log().body()
		.body("size()",greaterThan(0))
		.body("userId", everyItem(equalTo(userId)));// Validate that the response contains the correct user ID
	}
	
	@Test
	public void createCart() throws ParseException {
		
		int userId = configReader.getIntProperty("userId");
		Cart cart_payload= Payload.cartPayload(userId);
		
		given()
			.contentType(ContentType.JSON)
			.body(cart_payload)
		.when()
			.post(Routes.CREATE_CART)
		.then()
			.statusCode(201)
			.log().body()
            .body("id", notNullValue()) // Validate that the cart ID in response is not null
            .body("userId", notNullValue()) // Validate that the user ID in response is not null
			.body("products.size()", greaterThan(0));;
	}
	
	@Test
	public void updateCart() throws ParseException {
		
		int userId = configReader.getIntProperty("userId");
		int cartId = configReader.getIntProperty("cartId");//cart to be updated
		
		Cart update_payload= Payload.cartPayload(userId);//cart of a paticular user
		
		given()
			.contentType(ContentType.JSON)
			.pathParam("id", cartId)
			.body(update_payload)
		.when()
			.put(Routes.UPDATE_CART)
		.then()
			.statusCode(200)
			.log().body()
            .body("id", equalTo(cartId)) // Validate that the response contains the correct cart ID
            .body("userId", notNullValue())
			.body("products.size()", equalTo(1));;
	}
	@Test
	public void deleteCart() {
		
		int cartId = configReader.getIntProperty("cartId");
		
		given()
			.pathParam("id", cartId)
		.when()
			.delete(Routes.DELETE_CART)
		.then()
			.statusCode(200)
			.log().body();
	}
}