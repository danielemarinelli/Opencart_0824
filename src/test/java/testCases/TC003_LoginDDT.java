package testCases;

//VALID DATA (A)-->login SUCCESSFUL --> Test PASS (need logout)
//VALID DATA (B)-->login UNSUCCESSFUL --> Test FAIL (NO need logout)

//INVALID DATA (C)-->login SUCCESSFUL --> Test FAIL (NO need logout)
//INVALID DATA (D)-->login UNSUCCESSFUL --> Test PASS (need logout)

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.Login;
import pageObjects.MyAccount;
import testBase.BaseClass;
import utilities.DataProviders;


public class TC003_LoginDDT extends BaseClass {

    HomePage hp;
    Login login;
    MyAccount account;

    @Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class,groups="DataDriven")  //getting data provider from different class
    public void verify_loginDDT(String email, String pw, String expRes) {
        logger.info("###### Starting T003_LoginDDT_Test ######");
        try {
            hp = new HomePage(driver);
            hp.clickMyAccount();
            hp.clickLogin();
            logger.info("# Clicked Login #");
            login = new Login(driver);
            login.insertEmail(email);
            login.insertPassword(pw);
            logger.info("# Inserted Customers Credentials #");
            account = new MyAccount(driver);
            login.loginAction();
            //Assert.assertEquals(account.doesMyOrdersExists(), true, "Login Failed!");

            // VALIDATION OF DATA CREDENTIALS
            if (expRes.equalsIgnoreCase("Valid")) {   // VALID DATA
                if (account.doesMyOrdersExists() == true) {   // case A
                    account.logoutFromPersonalAccount();
                    Assert.assertTrue(true);
                } else {
                    Assert.assertTrue(false);  // case B
                }
            }

            if (expRes.equalsIgnoreCase("InValid")) {    // INVALID DATA
                if (account.doesMyOrdersExists() == true) {   // case C
                    account.logoutFromPersonalAccount();
                    Assert.assertTrue(false);
                } else {
                    Assert.assertTrue(true);  // case D
                }
            }
        }catch(Exception e){
            Assert.fail();
        }
        logger.info("###### Finished T003_LoginDDT_Test ######");
    }

}
