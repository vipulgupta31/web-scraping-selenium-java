package test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.Test;

import base.BaseTest;

public class TestWebScraping_Youtube extends BaseTest {

	@Test
	public void testScrapingYoutube() throws InterruptedException {
		
		//Navigate to Lambdatest youtube channel
		driver.get("https://www.youtube.com/@LambdaTest/videos");

		//maximize the browser window
		driver.manage().window().maximize();
		
		//wait for the Lambdatest youtube page to load
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		
		
		
		//scroll to the end of the page to load all the videos
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		long scrollHeight = (long) js.executeScript("return document.documentElement.scrollHeight");
		long startHeight = 0;
		
		do {
			startHeight = scrollHeight;
			js.executeScript("window.scrollTo(0, "+ startHeight + ")");
			Thread.sleep(1000);
			scrollHeight = (long) js.executeScript("return document.documentElement.scrollHeight"); 
		}while(startHeight != scrollHeight);
		
		
		//fetch and store reference to all videos parent web element
		//nested locators 1
		List<WebElement> allVideos = driver.findElements(By.xpath("//*[@id='contents']//div[@id='content']"));
		
		
		//traverse webElement for each video to scrap the required data
		for(WebElement video : allVideos)
		{
			//use the parent web element of video to fetch the details element of same
			//nested locators 2
			WebElement details = video.findElement(By.xpath(".//*[@id='details']"));
			
			WebElement videoTitle = details.findElement(By.xpath(".//a[@id='video-title-link']"));
			List<WebElement> videoMetadata = details.findElements(By.xpath(".//div[@id='metadata-line']//span"));
			
			//store each video title, views and days in a json object
			JSONObject videoData = new JSONObject();
			videoData.put("video_title", videoTitle.getText());
			videoData.put("video_views", videoMetadata.get(0).getText());
			videoData.put("video_days", videoMetadata.get(1).getText());
			
			//add each video detail to json array to aggregate the data at one location 
			scrapedData.put(videoData);
		}
		
		//print all the scraped video data on console
		System.out.println(scrapedData.toString(1));
}
	
}
