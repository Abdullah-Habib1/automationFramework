# automationFramework
Selenium framework project using Java, Maven , TestNg and POM

--> A data driven automation framework for phptravels.net
 Using Java, Seleneium WD, TestNG, Maven. 
Test data is generated from Java Faker library.

###################################################################################################
-->Requirements was as following:
1.	Enter First Name which must start with capital letter.
2.	Enter Last Name which must start with capital letter and can’t be equal First Name.
3.	Enter a valid Mobile Number.
4.	Enter a valid E-mail that should be unique for every user.
5.	Enter a Password and check that it must have capital letter, small letter, with a limit of 8 characters.
6.	After successful registration, verify that the user can login.
###################################################################################################
--> Test Cases covered was as following

started with test cases that will lead a user to remain at the registeration page.

--- Tested registeratin with invalid email that contains 2 @ signs [email created from java faker lib +'@']. (Passed)
--- Tested registeratin with invalid Phone number and here I used empty phone number. (Passed)

Then proceeded with test cases that will lead a user to go to account page.
--- Tested successful registeration as per requirements. (passed)
--- Tested Login after a succeful registeration (Passed)
--- Tested the email account uniqueness through trying to register again with same email account (Passed)
--- Tested password format acceptance through using a numeric password that does not contain the required format (Failed)
--- Tested Password limit, That I used to register a pw more than 8 ltters (Failed)
--- Tested First and Last names should start with capital letters, That I changed any capital letters in the names created from java faker with a small letter and tried to register with. (Failed)
--- Tested first name should not be same as last name (Failed).
--- To overcome that an email should register only once, I concatinated some variables to a valid email address and unique ones.
###################################################################################################

--> As there are multiple failures as mentioned above, I used soft assertions for some of cases to continue my test and catch as much as issue automation can get.

--> The last case was failed and terminated the browser and created the TestNG report as required.

--> On Failures, ScreenShots are taken and are supposed to be saved at the screenshots folder.

--> The response of hitting the register API is supposed to be saved to a text file in the Logs folder. 
