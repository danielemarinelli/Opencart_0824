package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountRegistrationPage extends BasePage{

    public AccountRegistrationPage(WebDriver driver){ super(driver); }

    @FindBy(xpath="//input[@id='input-firstname']")
    WebElement firstName;

    @FindBy(xpath="//input[@id='input-lastname']")
    WebElement lastName;

    @FindBy(xpath="//input[@id='input-email']")
    WebElement email;

    @FindBy(xpath="//input[@id='input-telephone']")
    WebElement telephone;

    @FindBy(xpath="//input[@id='input-password']")
    WebElement password;

    @FindBy(xpath="//input[@id='input-confirm']")
    WebElement confirmPassword;

    @FindBy(xpath="//input[@type='checkbox']")
    WebElement policy;

    @FindBy(xpath="//input[@type='submit']")
    WebElement btnContinue;

    @FindBy(xpath="//h1[normalize-space()='Your Account Has Been Created!']")
    WebElement msgConfirmation;


    public void setFirstName(String name){ firstName.sendKeys(name); }

    public void setLastName(String lName){ lastName.sendKeys(lName); }

    public void setEmail(String e){ email.sendKeys(e); }

    public void setTelephone(String phone){ telephone.sendKeys(phone); }

    public void setPassword(String pwd){ password.sendKeys(pwd); }

    public void setConfirmPassword(String pwd){ confirmPassword.sendKeys(pwd); }

    public void setPolicy(){ policy.click();}

    public void clickContinueButton(){ btnContinue.click();}

    public String getConfirmationMsg(){
        try {
            return msgConfirmation.getText();
        }catch (Exception e){
        return (e.getMessage());
        }
    }
}
