package payloads;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.github.javafaker.*;

import pojo.Geolocation;
import pojo.Name;
import pojo.Product;
import pojo.Users;
import pojo.Address;
import pojo.Auth;
import pojo.Cart;
import pojo.CartProduct;

public class Payload {// this class will only be used for post/put request

	private static final Faker faker = new Faker();
	static Random random = new Random();//a static method cannot directly access non-static (instance) variables.
	
	//here i want the category to be used randomly for diff. products, from the foll.
	private static final String categories[]= {"electronics", "furniture", "clothing", "books", "beauty"};
	
	//Product
	public static Product productPayload() {
		
		String title = faker.commerce().productName();
		float price = Float.parseFloat(faker.commerce().price());
		//it will choose the categories randomly from the array
	    String category = categories[random.nextInt(categories.length)];
	    String description = faker.lorem().sentence(50);
	    String image = "https://i.pravatar.cc/100";
	     
		return new Product(title, price, category, description, image);/*the Parameterized Constructor from
		the POJO class is directly being called here. 
		new keyword is used here because, we have to create an object first, then return
		conventionally we can also write like, 
		Product product = new Product(--all the variables--)
		return product;
		*/
	}
	
	//User
	public static Users userPayload() {
		//name
		String firstname=faker.name().firstName();
		String lastname=faker.name().lastName();
		
		Name name=new Name(firstname,lastname);
		
		//location
		String lat=faker.address().latitude();
		String lng=faker.address().longitude();
		Geolocation location=new Geolocation(lat,lng);
		
		//Address
		String city=faker.address().city();
		String street=faker.address().streetName();
		int number=random.nextInt(100);
		String zipcode=faker.address().zipCode();
		Address address=new Address(city,street,number,zipcode,location);
		
		//User
		String email=faker.internet().emailAddress();
		String username=faker.name().username();
		String password=faker.internet().password();
		String phonenumber=faker.phoneNumber().cellPhone();
		
		return new Users(email,username,password,name,address,phonenumber);
	}
	
	//Cart Payload
	public static Cart cartPayload(int userId) throws ParseException {
		List<CartProduct> products = new ArrayList<CartProduct>();
		
		//Add a product in cart
		int productId = random.nextInt(100);
		int quantity = random.nextInt(10)+1;
		
		CartProduct cartProduct = new CartProduct(productId, quantity);//calling the const. from POJO - CartProduct
		
		products.add(cartProduct);//adding the cartProduct object to products List

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);//specifying the format
		
		Date startDate = sdf.parse("2020-01-01");
		Date endDate = sdf.parse("2024-12-31");
		
		// Faker gives you a java.util.Date directly
		Date fakedate = faker.date().between(startDate, endDate);

		// Format it
		String date = sdf.format(fakedate);
		
		return new Cart(userId, date, products);
	}
	
	public static Auth generateToken(){
		
		String username = faker.lorem().word();
		String password = faker.lorem().word();
		
		return new Auth(username, password);
	}
}