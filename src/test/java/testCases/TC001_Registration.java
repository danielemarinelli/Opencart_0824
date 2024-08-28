package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_Registration extends BaseClass {

    HomePage hp;
    AccountRegistrationPage arp;

    @Test(groups={"Regression","Master"})
    public void verify_registration(){

        logger.info("###### Starting TC001_Registration ######");
        try {

        hp = new HomePage(driver);
        hp.clickMyAccount();
        logger.info("# Clicked on MyAccount link #");
        hp.clickRegistration();
        logger.info("# Clicked on Register link #");
        arp = new AccountRegistrationPage(driver);
        logger.info("# Providing customer information #");
        arp.setFirstName(randomString().toLowerCase());
        arp.setLastName(randomString().toLowerCase());
        arp.setEmail(randomString()+"@aol.com");
        arp.setTelephone(randomNumber());

        String password = randomAlphaNumber();
        arp.setPassword(password);
        arp.setConfirmPassword(password);
        arp.setPolicy();
        arp.clickContinueButton();

        logger.info("# Validating expected message #");
        Assert.assertEquals(arp.getConfirmationMsg(),"Your Account Has Been Created!");
        //System.out.println("Test ran....");
        }
        catch (Exception e){
            logger.error("%% Test Failed!!! %%");
            logger.debug("Debug logs....");
            Assert.fail();
        }
        logger.info("###### Finished TC001_Registration ######");
    }



}
