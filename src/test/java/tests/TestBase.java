package tests;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import utilities.Helper;

public class TestBase {

	public static WebDriver driver;

	@BeforeSuite
	public void StartDriver() {

		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\resources\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		driver.navigate().to("https://phptravels.net/register");

	}
	@AfterSuite
	public void StopDriver() {

		driver.quit();
	}
	
	//Take screen shots on test cases failures and adding them to their path
	@AfterMethod
	public void screenshotOnFailure(ITestResult result){
		
	if (result.getStatus()==ITestResult.FAILURE) {
		System.out.println("Failed");
		System.out.println("Taking ScreenShot...");
		try {
			Helper.captureScreenShot(driver, result.getName());
		} catch (IOException e) {
			}
	}
}
}