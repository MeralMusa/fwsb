package com.fwsb.test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.fwsb.utilities.ConfigurationReader;
import com.fwsb.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class TestBase {
    protected WebDriver driver;
    protected Actions action;
    protected WebDriverWait wait;
    protected static ExtentReports report;
    protected static ExtentHtmlReporter htmlReporter;
    protected static ExtentTest extentLogger;
    protected String url;

    @BeforeTest
    public void setUpTest(){
        //initialisieren der Klasse
        report = new ExtentReports();

        //create report path
        String projectPath = System.getProperty("user.dir");
        String path = projectPath +"/test-output/report.html";

        //Initialisierung der html report mit den Report pfad
        htmlReporter= new ExtentHtmlReporter(path);

        //Anhängen der html Report zum Report Object
        report.attachReporter(htmlReporter);


        htmlReporter.config().setReportName("FWSB smoke test");

        // Environment information bereitstellen
        report.setSystemInfo("Environment","QA");
        report.setSystemInfo("Browser", ConfigurationReader.get("browser"));
        report.setSystemInfo("OS",System.getProperty("os.name"));
    }

    @AfterTest
    public void tearDownTest(){
        //Wenn das Report aktuel erstellt wird
        report.flush();
    }


    @BeforeMethod
    @Parameters("env")
    public void setUpMethod(@Optional String env){
        System.out.println("env = " + env);

        //Wenn die env-Variable null ist, verwenden Sie die Standard-URL
        //Wenn es nicht null ist, rufen Sie die URL basierend auf env ab
        if(env==null){
            url=ConfigurationReader.get("url");
        }else{
            url=ConfigurationReader.get(env+"_url");
        }

        driver = Driver.get();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        action = new Actions(driver);
        wait = new WebDriverWait(driver,10);

        driver.get(url);

        driver.manage().window().maximize();

    }
    //Die ITestResult-Klasse beschreibt das Ergebnis eines Tests in TestNg
    @AfterMethod
    public void tearDownMethod(ITestResult result) throws InterruptedException, IOException {
        //wenn Test "Failed"
        if(result.getStatus()==ITestResult.FAILURE){
            //Notieren der Namen des fehlgeschlagenen Testfalls
            extentLogger.fail(result.getName());

            //Erfassen von "Exception"
            extentLogger.fail(result.getThrowable());

        }else if(result.getStatus()==ITestResult.SKIP){
            extentLogger.skip("Test Skipped: "+result.getName());
        }
        //Schließen des drivers
        Thread.sleep(1000);
        Driver.closeDriver();

    }

}