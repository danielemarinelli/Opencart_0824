package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Login extends BasePage{

    public Login(WebDriver driver){ super(driver); }

    @FindBy(xpath="//input[@id='input-email']")
    WebElement emailField;

    @FindBy(xpath="//input[@id='input-password']")
    WebElement pwdField;

    @FindBy(xpath="//input[@value='Login']")
    WebElement loginBtn;


    public void insertEmail(String email){
        emailField.sendKeys(email);
    }

    public void insertPassword(String pass){
        pwdField.sendKeys(pass);
    }

    public void loginAction(){
        loginBtn.click();
    }
}
