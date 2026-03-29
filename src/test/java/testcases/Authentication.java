package testcases;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import payloads.Payload;
import pojo.Auth;
import routes.Routes;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class Authentication extends BaseClass{
	
	@Test
	public void generateToken() {
		
		String username = configReader.getProperty("username");
		String pswd = configReader.getProperty("password");
		
		Auth authPayload = new Auth(username, pswd);// In here there is no role of Payload methos, the payload is created direcly from POJO class
		
		given()
			.contentType(ContentType.JSON)
			.body(authPayload)// object is passed
		.when()
			.post(Routes.GENERATE_TOKEN)
		.then()
			.statusCode(201)
			.log().body()
			.body("size()", equalTo(1))
			.body("token", notNullValue());
	}
	
	@Test
	public void generateInvalidToken() {
		
		Auth authPayload = Payload.generateToken();// In here there is no role of Payload methos, the payload is created direcly from POJO class
		
		given()
			.contentType(ContentType.JSON)
			.body(authPayload)// object is passed
		.when()
			.post(Routes.GENERATE_TOKEN)
		.then()
			.statusCode(401)
			.log().body()
			.body(equalTo("username or password is incorrect"));
	}
}