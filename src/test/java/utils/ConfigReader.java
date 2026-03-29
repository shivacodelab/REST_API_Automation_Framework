package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

	Properties properties;//using the properties file
	//setting the path
	private static final String CONFIG_FILE_PATH=".\\src\\test\\resources\\config.properties";
	
	
	  public ConfigReader() {//constructor - it will only load the file
		  //creating the obj of propertes class
	        properties = new Properties();
	        try (FileInputStream fileInputStream = new FileInputStream(CONFIG_FILE_PATH)) {
	            properties.load(fileInputStream);//using load method to load the file
	        } catch (IOException e) {
	            e.printStackTrace();
	            throw new RuntimeException("Failed to load config.properties file");
	        }
	  }
	  
	  public String getProperty(String key)//for getting string type data
	  {
		  return properties.getProperty(key);
	  }
	  
	  public int getIntProperty(String key)//for getting int type data
	  {
		  return Integer.parseInt(properties.getProperty(key));
	  }
}