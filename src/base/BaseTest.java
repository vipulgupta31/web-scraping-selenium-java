package base;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.json.JSONArray;

public class BaseTest {

	public RemoteWebDriver driver = null;
	public WebDriverWait wait;
	public JSONArray scrapedData = new JSONArray();

	String username = System.getenv("LT_USERNAME") == null ? "<lambdatest_username>" : System.getenv("LT_USERNAME");
	String accessKey = System.getenv("LT_ACCESS_KEY") == null ? "<lambdatest_accesskey>" : System.getenv("LT_ACCESS");

	@BeforeTest
	public void setup() {
		try {
			SafariOptions safariOptions = new SafariOptions();
			safariOptions.setPlatformName("MacOS Ventura");
			safariOptions.setBrowserVersion("16.0");

			HashMap<String, Object> ltOptions = new HashMap<String, Object>();
			ltOptions.put("build", "Web Scraping Using Selenium Java");
			ltOptions.put("name", "Web Scraping");
			ltOptions.put("w3c", true);
			safariOptions.setCapability("LT:Options", ltOptions);

			driver = new RemoteWebDriver(
					new URL("https://" + username + ":" + accessKey + "@hub.lambdatest.com/wd/hub"), safariOptions);
			wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
