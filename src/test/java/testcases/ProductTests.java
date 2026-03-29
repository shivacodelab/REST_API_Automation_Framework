package testcases;

import pojo.Product;
import routes.Routes;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import payloads.Payload;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import java.util.List;

public class ProductTests extends BaseClass{
	
	//1) Test to retrieve all products
	@Test(priority = 1)
	public void testGetAllProducts()
	{
		given()//generally no need to write anyhting in given in get request
		
		.when()
			.get(Routes.GET_ALL_PRODUCTS)//calling the endpoints from the Route class
		.then()
			.statusCode(200)
			.log().body()
			.body("size()",greaterThan(0));
	}
	
	//2) Test to retrieve a single product by ID
	@Test(priority = 2)
	public void testGetSingleProductById()
	{	//productId being fetched from config.properties file
		int productId=configReader.getIntProperty("productId");
		
		given()
			.pathParam("id", productId)// b/c the endpoint id is sent as path parameter = /products/{id}
		
		.when()
			.get(Routes.GET_PRODUCT_BY_ID)
		.then()
			.statusCode(200)
			.log().body();
	}
	
	//3) Test to retrive a limited number of products
	@Test(priority = 3)
	public void testGetLimitedProducts()
	{
		given()
			.pathParam("limit",3)/*although the limit is sent as query parameter 
			in URL -/products?limit={limit}, but in this block, we should be passing it as a path 
			parameter instead of query param. bcs we only have to append, the value as RestAssured 
			resolves it. Ideally we should use query parameter but then link should be = "/products"*/
		.when()
			.get(Routes.GET_PRODUCTS_WITH_LIMIT)
		.then()
			.statusCode(200)
			.log().body()
			.body("size()",equalTo(3));
	}

	//4) Test to retreive products sorted in descending order
	@Test(priority = 4)
	public void testGetSortedProducts()
	{
		Response response=given()
			.pathParam("order", "desc")
		.when()
			.get(Routes.GET_PRODUCTS_SORTED)
		.then()
			.statusCode(200)
			.extract().response();
		
		List<Integer> productIds=response.jsonPath().getList("id", Integer.class);
		 assertThat(isSortedDesceding(productIds), is(true));
	}
	
	//5) Test to retreive products sorted in Ascending order
		@Test(priority = 5)
		public void testGetSortedProductsAsc()
		{
			Response response=given()
				.pathParam("order", "asc")
			.when()
				.get(Routes.GET_PRODUCTS_SORTED)
			.then()
				.statusCode(200)
				.extract().response();
			
			List<Integer> productIds=response.jsonPath().getList("id", Integer.class);
			 assertThat(isSortedAsceding(productIds), is(true));//the method called is in base class
		}
	
	//6) Test to get all product categories
		@Test(priority = 6)
		public void testGetAllCategories()
		{
			given()
				
			.when()
				.get(Routes.GET_ALL_CATEGORIES)
			.then()
				.statusCode(200)
				.body("size()",greaterThan(0));
				
		}
	
	
	//7) Test to get products by category
		@Test(priority = 7)
		public void testGetProductsByCategory()
		{
			given()
				.pathParam("category", "electronics")
				.queryParam("limit", 3)
			.when()
				.get(Routes.GET_PRODUCTS_BY_CATEGORY)
			.then()
				.statusCode(200)
				.body("size()",greaterThan(0))
				.body("category", everyItem(notNullValue()))//to match every_time is of category=electronics
				.body("category", everyItem(equalTo("electronics")))
				.log().body();
		}
		

	//8) Test to add a new product
		@Test(priority = 8)
		public void testAddNewProduct()
		{
			Product newProduct = Payload.productPayload();//Payload being called from Payload class as static method
			
			int productId=given()//extracting and storing the product_id
				.contentType(ContentType.JSON)
				.body(newProduct)
				
			.when()
				.post(Routes.CREATE_PRODUCT)
			.then()
				.log().body()
				.statusCode(201)
				.body("id", notNullValue())
				.body("title", equalTo(newProduct.getTitle()))
				.extract().jsonPath().getInt("id"); //Extracting Id from response body
			
			System.out.println(productId);
		}
		
		//9) Test to update an existing product
		@Test(priority = 9)
		public void testUpdateProduct()
		{
			int productId=configReader.getIntProperty("productId");//reading prodId from config.prop file
			
			Product updatedPayload=Payload.productPayload();//Payload being called from Payload class
			
			given()
				.contentType(ContentType.JSON)
				.body(updatedPayload)
				.pathParam("id", productId)
				
			.when()
				.put(Routes.UPDATE_PRODUCT)
			.then()
				.log().body()
				.statusCode(200)
				.body("title", equalTo(updatedPayload.getTitle()));
		}
		
		//10) test to delete a product
		@Test(priority = 10)
		public void testDeleteProduct()
		{
			int productId=configReader.getIntProperty("productId");//reading prodId from config.prop file
			
			given()
				.pathParam("id",productId)
			.when()
				.delete(Routes.DELETE_PRODUCT)
			.then()
				.statusCode(200);
		}
}