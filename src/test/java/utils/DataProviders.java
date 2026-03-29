package utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.annotations.DataProvider;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class DataProviders {
	
	/*Why 2-Dimensional Array in @DataProvider?
	Because:
	First dimension (Object[]) → number of test runs
	Second dimension (Object[]) → parameters passed to the test method
	
	The method returns Object[][] (2D) because TestNG needs multiple 
	rows of test data, where each row contains the parameters for a single test execution.*/
	
	@DataProvider
	public Object[][] jsonDataProvider() throws IOException {
		// Path to your JSON file
		String filePath = ".\\testdata\\product.json";

		// Read JSON file and map it to a List of Maps
		ObjectMapper objectMapper = new ObjectMapper();
		List<Map<String, String>> dataList = objectMapper.readValue(new File(filePath),
				new TypeReference<List<Map<String, String>>>() {
				});

		// Convert List<Map<String, String>> to Object[][]
		Object[][] dataArray = new Object[dataList.size()][];
		for (int i = 0; i < dataList.size(); i++) {
			dataArray[i] = new Object[] { dataList.get(i) };
		}

		return dataArray;
	}
	
	@DataProvider
	public Object[][] csvDataProvider() throws IOException {
        // Path to the CSV file
        String filePath = ".\\testdata\\product.csv";

        // Read the CSV file and store data in a List
        List<String[]> dataList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Skip the first line (header)
            br.readLine();  

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(","); // Splitting by comma
                dataList.add(data);
            }
        }

        // Convert List<String[]> to Object[][]
        Object[][] dataArray = new Object[dataList.size()][];
        for (int i = 0; i < dataList.size(); i++) {
            dataArray[i] = dataList.get(i);
        }

        return dataArray;
    }
	
}
