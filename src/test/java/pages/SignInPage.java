package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SignInPage extends BasePage {
    private final By emailTextBox = By.id("identifierId");
    private final By nextButton = By.id("identifierNext");
    private final By passwordTextBox = By.xpath("//input[@name='password']");
    private final By passwordNextButton = By.id("passwordNext");

    public SignInPage(WebDriver driver) {
        BasePage.driver = driver;
    }

    public void setEmailTextBox(String username) {
        driver.findElement(emailTextBox).sendKeys(username);
    }

    public void clickNextButton() {
        driver.findElement(nextButton).click();
    }

    public void setPasswordTextBox(String password) {
        driver.findElement(passwordTextBox).sendKeys(password);
    }

    public InboxPage clickPasswordNextButton() {
        driver.findElement(passwordNextButton).click();
        return new InboxPage(driver);
    }
}
