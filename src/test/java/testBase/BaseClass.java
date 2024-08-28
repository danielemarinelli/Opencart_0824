package testBase;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

public class BaseClass {

    public static WebDriver driver;
    public Logger logger;
    public Properties prop;

    @BeforeClass(groups={"Sanity","Regression","Master","DataDriven"})
    //@BeforeClass(groups="Regression")
    @Parameters({"os","browser"})
    public void startWebPage(String os, String browser) throws IOException {

        //loading config.properties file
        FileReader file = new FileReader(".//src//test//resources//config.properties");
        prop = new Properties();
        prop.load(file);

        logger = LogManager.getLogger(this.getClass());

        // SET CONFIG FOR SELENIUM GRID...
        // if run env remotely
        if(prop.getProperty("execution_env").equalsIgnoreCase("remote")){

            DesiredCapabilities dc = new DesiredCapabilities();
            //os
            switch(os.toLowerCase()){
                case "windows" : dc.setPlatform(Platform.WIN11);break;
                case "mac" : dc.setPlatform(Platform.MAC);break;
                case "linux" : dc.setPlatform(Platform.LINUX);break;
                default : System.out.println("Invalid OpSystem!");return;
            }
            //browser
                switch(browser.toLowerCase()){
                    case "chrome" : dc.setBrowserName("chrome");break;
                    case "edge" : dc.setBrowserName("MicrosoftEdge");break;
                    case "firefox" : dc.setBrowserName("firefox");break;
                    default : System.out.println("Invalid Browser!");return;
                }
            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),dc);
        }
        // if run env locally
        if(prop.getProperty("execution_env").equalsIgnoreCase("local")) {
        ChromeOptions co = new ChromeOptions();
        co.addArguments("--incognito");
        co.setExperimentalOption("excludeSwitches",new String[]{"enable-automation"});

         switch(browser.toLowerCase()){
            case "chrome" : driver = new ChromeDriver(co);break;
            case "edge" : driver = new EdgeDriver();break;
            case "firefox" : driver = new FirefoxDriver();break;
            default : System.out.println("Invalid browser name!");return;
            }
        }

        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Long.parseLong(prop.getProperty("timeOut"))));
        driver.get(prop.getProperty("appURL"));
        driver.manage().window().maximize();
    }

    @AfterClass(groups={"Sanity","Regression","Master","DataDriven"})
    //@AfterClass(groups="Regression")
    public void tearDown(){
        driver.quit();
    }


    public String randomString(){
        String newRandomString = RandomStringUtils.randomAlphabetic(8);
        return newRandomString;
    }

    public String randomNumber(){
        String newRandomNumber = RandomStringUtils.randomNumeric(10);
        return newRandomNumber;
    }

    public String randomAlphaNumber(){
        String genString = RandomStringUtils.randomAlphabetic(8);
        String genNumber = RandomStringUtils.randomNumeric(10);
        return (genString+"@"+genNumber);
    }


    public String captureScreen(String tname) {
        String timestamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        String targetFilePath = System.getProperty("user.dir")+"\\screenshots\\"+tname+"_"+timestamp+".png";
        File targetFile = new File(targetFilePath);
        sourceFile.renameTo(targetFile);

        return targetFilePath;
    }
}
