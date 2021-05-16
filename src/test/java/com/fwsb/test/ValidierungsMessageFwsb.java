package com.fwsb.test;

import com.fwsb.utilities.ConfigurationReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class ValidierungsMessageFwsb extends TestBase {

    @Test
    public void test1() {
        driver.get(ConfigurationReader.get("url"));

        driver.manage().window().maximize();
        WebElement Job = driver.findElement(By.xpath("//a[.='Jobs']")); //Xpath: Finde Element "a" egal
                                                                       // welches Atribut(.), dessen Text (Jobs) ist.

        Job.click();
        WebElement Popup =driver.findElement(By.xpath("/html/body/div[2]/div/a"));//xpath

        Popup.click();

       WebElement OBButton= driver.findElement(By.xpath("(//a[.='Online-Bewerbung'])[2]"));//Xpath index

       OBButton.click();

       WebElement SButton= driver.findElement(By.xpath("(//span)[31]"));//Xpatt index

        SButton.click();

        List<WebElement> VMessage= driver.findElements(By.xpath("//div[contains(text(),\"Bitte\")]")); //xpath Contains Text

        System.out.println("Anzahl der Validierungs-Messages= "+VMessage.size());//Gibt die Anzahl der Erhaltenen Valiedierungen an:


        int count =1;
        for (WebElement Message : VMessage) {
            System.out.println("Validierungs-Message = "+count+" "+Message.getText()); //Gibt die jeweilige Validierung an:
            count++;
        }

        //Vergleicht die jeweiligen Validierungen, welche wir aus dem Web entnommen haben mit den zu erwartenen Validierungen.
        Assert.assertEquals(VMessage.get(0).getText(),"Bitte geben Sie einen Vornamen ein.");
        Assert.assertEquals(VMessage.get(1).getText(),"Bitte geben Sie einen Nachnamen ein.");
        Assert.assertEquals(VMessage.get(2).getText(),"Bitte geben Sie eine gültige EMail-Adresse ein.");
        Assert.assertEquals(VMessage.get(3).getText(),"Bitte geben Sie eine Telefonnummer ein.");
        Assert.assertEquals(VMessage.get(4).getText(),"Bitte geben Sie ein Datum im Format tt.mm.jjjj ein.");
        Assert.assertEquals(VMessage.get(5).getText(),"Bitte laden Sie eine Datei mit Ihrem Lebenslauf.");
        Assert.assertEquals(VMessage.get(6).getText(),"Bitte bestätigen Sie, dass Sie die Datenschutzeinstellungen gelesen haben und diese akzeptieren.");



        driver.quit();



















    }

}
