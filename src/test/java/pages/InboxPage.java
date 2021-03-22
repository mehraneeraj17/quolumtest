package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class InboxPage extends BasePage {
    private final By primaryTab = By.xpath("//*[@aria-label='Primary']");
    private final By contactButton = By.xpath("//*[@aria-label='Contacts']");
    private final By contactFrame = By.xpath("//iframe[contains(@title,'Contacts')]");
    private final By contactNames = By.cssSelector(".iAmyCb.AL18ce");

    public InboxPage(WebDriver driver) {
        BasePage.driver = driver;
    }

    public boolean isPrimaryTabAvailable() {
        return driver.findElement(primaryTab).isDisplayed();
    }

    public void openContacts() {
        if (driver.findElements(contactFrame).size() == 0) {
            driver.findElement(contactButton).click();
        }
    }

    public List<String> getAllContactsName() {
        WebElement contactFrameWebElement = driver.findElement(contactFrame);
        driver.switchTo().frame(contactFrameWebElement);

        List<WebElement> contactWebElements = driver.findElements(contactNames);
        List<String> gmailContacts = new ArrayList<>();
        for (WebElement contact : contactWebElements) {
            gmailContacts.add(contact.getText());
        }
        return gmailContacts;
    }
}
