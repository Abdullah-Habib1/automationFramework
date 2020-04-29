package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UserLoginPage extends PageBase {

	public UserLoginPage(WebDriver driver) {
		super(driver);

	}
	@FindBy(xpath = "//input[@type=\"email\"]")
	WebElement LoginEmailTxtBox;
	
	@FindBy(xpath = "//input[@type=\"password\"]")
	WebElement LoginPwTxtBox;
	
	@FindBy(xpath = "//button[@class=\"btn btn-primary btn-lg btn-block loginbtn\"]")
	WebElement LoginBtn;
	
	public void UserLogin(String email , String password)

	{
		
		setTextElementText(LoginEmailTxtBox, email);
		setTextElementText(LoginPwTxtBox, password);
		clickButton(LoginBtn);
	}

}
