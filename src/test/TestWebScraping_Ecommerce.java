package test;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import org.json.JSONObject;

import base.BaseTest;

public class TestWebScraping_Ecommerce extends BaseTest{
	
	@Test
	public void testScrapingEcommerce() {
		
		//Navigate to Lambdatest e-commerce website
		driver.get("https://ecommerce-playground.lambdatest.io/");
		
		//maximize the browser window
		driver.manage().window().maximize();
		
		//wait for element to be visible and then click on it
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(.,'Shop by Category')]")));
		driver.findElement(By.xpath("//a[contains(.,'Shop by Category')]")).click();
		
		//wait for phones, tablets category element to be visible and then click on it
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(.,'Phone, Tablets & Ipod')]")));
		driver.findElement(By.xpath("//span[contains(.,'Phone, Tablets & Ipod')]")).click();		
		
		//wait for all products to load on the screen
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='entry_212408']//div[@class='row']")));
		
		
		//fetch and store reference to all products parent web element
		//nested locators 1
		WebElement allProducts = driver.findElement(By.xpath("//*[@id='entry_212408']//div[@class='row']"));

		//use the parent web element to fetch list of web elements for each individual product
		//nested locators 2
		List<WebElement> productList = allProducts.findElements(By.xpath(".//div[contains(@class,'product-layout product-grid no-desc')]"));
		
		
		//traverse the list of product web elements to scrap the required data
		for(WebElement product : productList) 
		{
			WebElement detail = product.findElement(By.xpath(".//a[@class='text-ellipsis-2']"));
			WebElement price = product.findElement(By.xpath(".//span[@class='price-new']"));
			
			//store product image link, name and price in a json object
			JSONObject productMetaData = new JSONObject();
			productMetaData.put("product_image", detail.getAttribute("href"));
			productMetaData.put("product_name", detail.getText());
			productMetaData.put("product_price", price.getText());
			
			//add each product detail to json array to aggregate the data at one location 
			scrapedData.put(productMetaData);
		}
		
		//print all the scraped products data on console
		System.out.println(scrapedData.toString(1));
		
	}
}
