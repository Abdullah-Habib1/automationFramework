package tests;

import java.util.concurrent.TimeUnit;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.github.javafaker.Faker;
import pages.UserAccounttPage;
import pages.UserLoginPage;
import pages.UserRegisterationPage;

public class UserRegisterationTest extends TestBase {

	UserRegisterationPage registerObject;
	UserAccounttPage accountObject;
	UserLoginPage loginObject;

	//Using Faker lib to generate test data

	Faker RandomData = new Faker();

	String firstName = RandomData.name().firstName();
	String lastName = RandomData.name().lastName();
	String firstNameSmall = RandomData.name().firstName().replaceAll("[A-Z]", "a");
	String lastNameSmall = RandomData.name().lastName().replaceAll("[A-Z]", "a");
	String email = RandomData.internet().emailAddress();
	// The next line will make sure that PW contains capital beside small letters, min-length is 6,max length 8 
	String password = RandomData.internet().password(6, 8, true);
	String phoneNo = RandomData.number().digits(9).toString();
	String numericPassword = RandomData.number().digits(8).toString();
	// The next row will create password with min length 9 and max length 10
	String longPassword = RandomData.internet().password(9, 10);

	//Testing Registering with invalid email contain 2 @ signs
	@Test(priority = 1)
	public void RegisterWithInvalidEmail() {

		registerObject = new UserRegisterationPage(driver);
		registerObject.UserRegisteration(firstName, lastName, email+'@', password  ,phoneNo);
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		Assert.assertTrue(registerObject.Alertmessage.getText().contains("The Email field must contain a valid email address."));
	}

	// Testing register with empty phone number
	@Test(priority = 2)
	public void RegisterWithemptyPhoneNo() {
		driver.navigate().refresh();
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		registerObject = new UserRegisterationPage(driver);
		registerObject.UserRegisteration(firstName, lastName, email, password , "");
		driver.manage().timeouts().implicitlyWait(200, TimeUnit.SECONDS);
		Assert.assertEquals(driver.getCurrentUrl(),"https://phptravels.net/register");
	}

	// Testing Happy path as required
	@Test(priority = 3)
	public void RegisterSuccefully() {
		driver.navigate().refresh();
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		registerObject = new UserRegisterationPage(driver);
		registerObject.UserRegisteration(firstName, lastName, email, password , phoneNo);
		driver.manage().timeouts().implicitlyWait(120000, TimeUnit.SECONDS);
		accountObject = new UserAccounttPage(driver);
		Assert.assertTrue(accountObject.WelcomeMessage.getText().contains("Hi,"));
	}
	// Login After Registration
	@Test(priority = 4)
	public void loginAfterRegister() {

		driver.manage().deleteAllCookies();
		driver.navigate().to("https://phptravels.net/login");
		driver.manage().timeouts().implicitlyWait(2000, TimeUnit.SECONDS);
		loginObject = new UserLoginPage(driver);
		loginObject.UserLogin(email, password);
		driver.manage().timeouts().implicitlyWait(2000, TimeUnit.SECONDS);
		accountObject = new UserAccounttPage(driver);
		Assert.assertTrue(accountObject.MyProfile.isDisplayed());

	}
	// Back to Registration page
		@Test(priority = 5)
		public void BAckToRegisterpagee() {

			driver.manage().deleteAllCookies();
			driver.navigate().to("https://phptravels.net/register");

		}
	// Testing email should be unique for every user
	@Test(priority = 6)
	public void RegisterWithSameEmailUsed() {
		registerObject = new UserRegisterationPage(driver);
		registerObject.UserRegisteration(firstName, lastName, email, password , phoneNo);
		Assert.assertTrue(registerObject.Alertmessage.getText().contains("Email Already Exists"));
	}

	// Testing Names Must start with Capital letters
	@Test(priority = 7)
	public void RegisterWithSmallLetterNames() {
		driver.navigate().refresh();
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		registerObject = new UserRegisterationPage(driver);
		registerObject.UserRegisteration(firstNameSmall, lastNameSmall, firstName+email, password , phoneNo);
		accountObject = new UserAccounttPage(driver);
		SoftAssert SA = new SoftAssert();
		SA.assertFalse(accountObject.MyProfile.isDisplayed());
		SA.assertAll();
	}
	// Back to Registration page
	@Test(priority = 8)
	public void backToRegisterpagee() {

		driver.manage().deleteAllCookies();
		driver.navigate().to("https://phptravels.net/register");

	}

	// Testing first name shouldn`t be same as last name
	@Test(priority = 9)
	public void RegisterWithSameNames() {
		registerObject = new UserRegisterationPage(driver);
		registerObject.UserRegisteration(firstName, firstName, lastName+email, password , phoneNo);
		accountObject = new UserAccounttPage(driver);
		SoftAssert SA = new SoftAssert();
		SA.assertFalse(accountObject.MyProfile.isDisplayed());
		SA.assertAll();
	}
	// Back to Registration page
	@Test(priority = 10)
	public void backToRegisterPagee() {

		driver.manage().deleteAllCookies();
		driver.navigate().to("https://phptravels.net/register");

	}
	// Testing that password should contain upper letters and lower letter
	@Test(priority = 11)
	public void RegisterWithNumericPW() {
		registerObject = new UserRegisterationPage(driver);
		registerObject.UserRegisteration(firstName, lastName, password+email, numericPassword , phoneNo);
		accountObject = new UserAccounttPage(driver);
		SoftAssert SA = new SoftAssert();
		SA.assertFalse(accountObject.MyProfile.isDisplayed());
		SA.assertAll();
	}
	// Back to Registration page
	@Test(priority = 12)
	public void BackToRegisterPagee() {

		driver.manage().deleteAllCookies();
		driver.navigate().to("https://phptravels.net/register");

	}
	// Testing that password should have limit of 8 characters
	@Test(priority = 13)
	public void RegisterWithLongPassword() {
		registerObject = new UserRegisterationPage(driver);
		registerObject.UserRegisteration(firstName, lastName, longPassword+email, longPassword , phoneNo);
		accountObject = new UserAccounttPage(driver);
		Assert.assertFalse(accountObject.MyProfile.isDisplayed());
	}
}