package com.ca.worldproperties.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;

import org.testng.annotations.DataProvider;

import com.jayway.jsonpath.JsonPath;

public class TestDataProvider {
	private static String testCaseData;
	private static String testDataFilePath = System.getProperty("user.dir")+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"testData"+File.separator;
	
	private synchronized static LinkedHashMap<String, String> testDataReader(String testName, String testFile) throws IOException{
		String json = new FileUtils().fileContentToString(testDataFilePath+testFile+".json");
		LinkedHashMap<String, LinkedHashMap<String, String>> testCaseWithData = JsonPath.parse(json).read("$");
		if(testCaseWithData.keySet().contains(testName)){
			testCaseData = "$."+testName;
		}
		LinkedHashMap<String, String> testData = JsonPath.parse(json).read(testCaseData);
		return testData;
	}
	
	@DataProvider(name = "propertiesTestDataProvider")
	public synchronized static Object[][] propertiesData(Method m) throws IOException{
		Object[][] testData = new Object[1][1];
		testData[0][0] = testDataReader(m.getName(), "propertiesTestData");
		return testData;
	}
}
