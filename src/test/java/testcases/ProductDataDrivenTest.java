package testcases;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Map;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import pojo.Product;
import routes.Routes;

public class ProductDataDrivenTest extends BaseClass{
	
	/*why data driven testing?
	 * to validate positive and negative testing scenarios by 
	 * inputting valid/invalid data
	*/
	int productId;
	
	@Test(dataProvider = "jsonDataProvider", dataProviderClass = utils.DataProviders.class)
	public void testAddNewProduct(Map<String, String> data) {
		
		//extracting the data from the hashmap for each iteration using HashMap method
		String title=data.get("title");
		float price=Float.parseFloat(data.get("price"));
		String category=data.get("category");
		String description=data.get("description");
		String image=data.get("image");
		
		//the order of param. constr. is imp here
		//String title, float price, String category, String description, String image
		Product newProductPayload = new Product(title, price, category, description, image);
		
		productId=given()//extracting and storing the product_id
			.contentType(ContentType.JSON)
			.body(newProductPayload)
			
		.when()
			.post(Routes.CREATE_PRODUCT)
		.then()
			.log().body()
			.statusCode(201)
			.body("id", notNullValue())
			.body("title", equalTo(title))
			.extract().jsonPath().getInt("id"); //Extracting Id from response body
		
		System.out.println(productId);
		
	}
	
	@Test(dependsOnMethods = "testAddNewProduct")
	public void deleteProduct() {
		given()
			.pathParam("id",productId)
		.when()
			.delete(Routes.DELETE_PRODUCT)
		.then()
			.statusCode(200);
	};
}