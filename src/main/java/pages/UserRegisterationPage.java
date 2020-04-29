package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UserRegisterationPage extends PageBase
{

	public UserRegisterationPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(name="firstname")
	WebElement FirstNameTxtBox;

	@FindBy(name="lastname")
	WebElement LastNameTxtBox;

	@FindBy(name="phone")
	WebElement PhoneTxtBox;	

	@FindBy(name="email")
	WebElement EmailTxtBox;

	@FindBy(name="password")
	WebElement PasswordTxtBox;

	@FindBy(name="confirmpassword")
	WebElement ConfirmPasswordTxtBox;
	
	@FindBy(xpath="//div[@class=\"alert alert-danger\"]")
	public WebElement Alertmessage;

	@FindBy(xpath = "//button[@type='submit'][@class=\"signupbtn btn_full btn btn-success btn-block btn-lg\"]")
	WebElement SignUpBtn;
	
	public void UserRegisteration(String firstName , String lastName , String email , String password , String phonenumber)

	{
		setTextElementText(FirstNameTxtBox, firstName);
		setTextElementText(LastNameTxtBox, lastName);
		setTextElementText(PhoneTxtBox, phonenumber);
		setTextElementText(EmailTxtBox, email);
		setTextElementText(PasswordTxtBox, password);
		setTextElementText(ConfirmPasswordTxtBox, password);
		clickButton(SignUpBtn);
	}
}