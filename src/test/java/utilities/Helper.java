package utilities;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
 
public class Helper {

	//Method to capture screen shot on failures
	public static void captureScreenShot(WebDriver driver, String screenShotName) throws IOException {

		Path destination = Paths.get("./ScreenShots",screenShotName+".png");
		try {
			Files.createDirectories(destination.getParent());
			FileOutputStream outResult = new FileOutputStream(destination.toString());
			outResult.write(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES));
			outResult.close();
		} catch (Exception e) {
			System.out.println("Excpetion while taking screenshot"+ e.getMessage());
		}
	}
	//---------------------------------Getting The API response--------------------------------------

	public static void createDriver() {
		ChromeOptions options = new ChromeOptions();
		options.setHeadless(true);

		LoggingPreferences logPrefs = new LoggingPreferences();
		logPrefs.enable(LogType.PERFORMANCE, java.util.logging.Level.ALL);

		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(ChromeOptions.CAPABILITY, options);
		cap.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);


	}


	private static void DownloadPage(String url)
	{
		ChromeDriver driver = null;

		try
		{
			ChromeOptions options = new ChromeOptions();
			// add whatever extensions you need
			// for example I needed one of adding proxy, and one for blocking
			// images
			// options.addExtensions(new File(file, "proxy.zip"));
			// options.addExtensions(new File("extensions",
			// "Block-image_v1.1.crx"));

			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setCapability(ChromeOptions.CAPABILITY, options);

			// set performance logger
			// this sends Network.enable to chromedriver
			LoggingPreferences logPrefs = new LoggingPreferences();
			logPrefs.enable(LogType.PERFORMANCE, java.util.logging.Level.ALL);;
			cap.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);

			driver = new ChromeDriver(cap);

			// navigate to the page
			System.out.println("Navigate to " + url);
			driver.navigate().to(url);

			// and capture the last recorded url (it may be a redirect, or the
			// original url)
			String currentURL = driver.getCurrentUrl();

			// then ask for all the performance logs from this request
			// one of them will contain the Network.responseReceived method
			// and we shall find the "last recorded url" response
			LogEntries logs = driver.manage().logs().get("performance");

			int status = -1;

			System.out.println("\nList of log entries:\n");

			for (java.util.Iterator<org.openqa.selenium.logging.LogEntry> it = logs.iterator(); it.hasNext();)
			{
				org.openqa.selenium.logging.LogEntry entry = it.next();

				try
				{
					JSONObject json = new JSONObject(entry.getClass());

					System.out.println(json.toString());

					JSONObject message = json.getJSONObject("message");
					String method = message.getString("method");

					if (method != null
							&& "Network.responseReceived".equals(method))
					{
						JSONObject params = message.getJSONObject("params");

						JSONObject response = params.getJSONObject("response");
						String messageUrl = response.getString("url");

						if (currentURL.equals(messageUrl))
						{
							status = response.getInt("status");

							System.out.println(
									"---------- bingo !!!!!!!!!!!!!! returned response for "
											+ messageUrl + ": " + status);

							System.out.println(
									"---------- bingo !!!!!!!!!!!!!! headers: "
											+ response.get("headers"));
						}
					}
				} catch (JSONException e)
				{
				
					e.printStackTrace();
				}
			}

			System.out.println("\nstatus code: " + status);
		
			Path destination = Paths.get("./Logs","ResponseApi.txt");
			try {
				Files.createDirectories(destination.getParent());
				PrintStream apiResponse = new PrintStream(destination.toString());
				apiResponse.println("\nstatus cod:"+ status);
				apiResponse.close();
			} catch (Exception e) {
				System.out.println("Excpetion while taking screenshot"+ e.getMessage());
			}

		} finally
		{
			if (driver != null)
			{
				driver.quit();
			}
		}
	}
}