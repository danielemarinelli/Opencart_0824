package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.Login;
import pageObjects.MyAccount;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass {

    HomePage hp;
    Login login;
    MyAccount account;

    @Test(groups={"Sanity","Master"})
    public void verify_login(){
        logger.info("###### Starting T002_LoginTest ######");
        try {
            hp = new HomePage(driver);
            hp.clickMyAccount();
            hp.clickLogin();
            logger.info("# Clicked Login #");
            login = new Login(driver);
            login.insertEmail(prop.getProperty("email"));
            login.insertPassword(prop.getProperty("password"));
            logger.info("# Inserted Customers Credentials #");
            account = new MyAccount(driver);
            login.loginAction();
            Assert.assertEquals(account.doesMyOrdersExists(), true, "Login Failed!");
        }catch(Exception e){
            Assert.fail();
        }
        logger.info("# Logout from Personal Account #");
        account.logoutFromPersonalAccount();
        logger.info("###### Ended T002_LoginTest ######");
    }
}
