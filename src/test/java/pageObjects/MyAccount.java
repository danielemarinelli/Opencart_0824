package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccount extends BasePage{

    public MyAccount(WebDriver driver){ super(driver); }

    @FindBy(xpath="//h2[normalize-space()='My Orders']")
    WebElement myOrders;

    @FindBy(xpath="(.//a[text()='Logout'])[2]")
    WebElement btnLogout;


    public boolean doesMyOrdersExists(){
        try {
            return myOrders.isDisplayed();
        }catch (Exception e){
            return false;
        }
    }

    public void logoutFromPersonalAccount(){
        btnLogout.click();
    }

}
