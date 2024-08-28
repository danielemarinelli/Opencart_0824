package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import testBase.BaseClass;

import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExtentReportManager implements ITestListener {

    public ExtentSparkReporter sparkReport;
    public ExtentReports report;
    public ExtentTest test;

    String reportName;

    public void onStart(ITestContext context){

        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd.hh.ss");
        Date date = new Date();
        String currentDate = df.format(date);

        reportName = "Test-Report_"+currentDate+".html";
        sparkReport = new ExtentSparkReporter(".\\reports\\"+reportName);  //location of the report

        sparkReport.config().setDocumentTitle("OpenCart Automation Report");
        sparkReport.config().setReportName("Functional Tests - OpenCart");
        sparkReport.config().setTheme(Theme.DARK);

        report = new ExtentReports();
        report.attachReporter(sparkReport);
        report.setSystemInfo("Application","Opencart");
        report.setSystemInfo("Module","Admin");
        report.setSystemInfo("User Name",System.getProperty("user.name"));
        report.setSystemInfo("Environment","QA");

        String os = context.getCurrentXmlTest().getParameter("os");
        report.setSystemInfo("Operating System", os);

        String browser = context.getCurrentXmlTest().getParameter("browser");
        report.setSystemInfo("Browser", browser);

        List<String> includedGroups = context.getCurrentXmlTest().getIncludedGroups();
            if(!includedGroups.isEmpty()) {
                report.setSystemInfo("Groups", includedGroups.toString());
            }
    }


    public void onTestSuccess(ITestResult result) {

        test = report.createTest(result.getTestClass().getName());  //creates a new entry in the report
        test.assignCategory(result.getMethod().getGroups());  //to display Groups in report
        test.log(Status.PASS, "Test case PASSED is: "+result.getName());  //updates status f/p/s

    }


    public void onTestFailure(ITestResult result) {

        test = report.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.FAIL, "Test case FAILED is: "+result.getName());
        test.log(Status.INFO, "Test case FAILED and cause is: "+result.getThrowable().getMessage());
    try{
        String imgPath = new BaseClass().captureScreen(result.getName());
        test.addScreenCaptureFromPath(imgPath);
    }catch(Exception e1){
        e1.printStackTrace();
    }

    }

    public void onTestSkipped(ITestResult result) {

        test = report.createTest(result.getTestClass().getName());  //creates a new entry in the report
        test.assignCategory(result.getMethod().getGroups());  //to display Groups in report
        test.log(Status.SKIP, "Test case SKIPPED is: "+result.getName());  //updates status f/p/s
        test.log(Status.INFO, "Test case SKIPPED and cause is: "+result.getThrowable().getMessage());
    }

    public void onFinish(ITestContext result){
        report.flush();
        String pathOfExtentReport = System.getProperty("user.dir")+"\\reports\\"+reportName;
        File extReport = new File(pathOfExtentReport);
        try {
            Desktop.getDesktop().browse(extReport.toURI());
        }catch(Exception e){
            e.printStackTrace();
        }

        // try catch block for emails
       /* try{
            URL url = new URL("file:///"+System.getProperty("user.dir")+"\\reports\\"+reportName);

            ImageHtmlEmail email = new ImageHtmlEmail();
            email.setDataSourceResolver(new DataSourceUrlResolver(url));
            email.setHostName("smtp.google.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator(".....@gmail.com","pwd"));
            email.setSSLOnConnect(true);
            email.setFrom("......@gmail.com");
            email.setSubject("Test Results");
            email.setMsg("Report below");
            email.addTo(".....@otheremails.com");
            email.attach(url,"extent report","Please check report");
            email.send();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (EmailException e) {
            e.printStackTrace();
        }*/


    }

}





