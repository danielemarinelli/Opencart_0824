package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class HomePage extends BasePage{

    public HomePage(WebDriver driver){ super(driver); }

    @FindBy(xpath="//span[normalize-space()='My Account']")
    WebElement myAccount;

    @FindBy(xpath="//a[normalize-space()='Register']")
    WebElement registration;

    @FindBy(xpath="//a[normalize-space()='Login']")
    WebElement loginbtn;


    public void clickMyAccount(){ myAccount.click(); }

    public void clickRegistration(){
        registration.click();
    }

    public void clickLogin(){
        loginbtn.click();
    }
}
