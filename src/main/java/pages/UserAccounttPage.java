package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UserAccounttPage extends PageBase {

	public UserAccounttPage(WebDriver driver) {
		super(driver);
		
	}
	@FindBy(linkText = "MY PROFILE")
	public WebElement MyProfile;
	
	@FindBy(css =".text-align-left" )
	public WebElement WelcomeMessage;
}
