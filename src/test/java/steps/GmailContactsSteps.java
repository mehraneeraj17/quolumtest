package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import pages.InboxPage;
import pages.SignInPage;
import utils.ConfigReader;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class GmailContactsSteps extends BaseStep {
    // Pages
    SignInPage signInPage;
    InboxPage inboxPage;

    private void initialisation() {
        // TODO Ideally it should be in Cucumber Hook: @Before
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + File.separator + "/src/test/resources/drivers/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Given("the user is in sign in page")
    public void the_user_is_in_sign_in_page() {
        properties = ConfigReader.readProperties();
        initialisation();
        driver.get(properties.getProperty("gmail.url"));
        signInPage = new SignInPage(driver);
    }

    @Given("user enters the username")
    public void user_enters_the_username() {
        signInPage.setEmailTextBox(properties.getProperty("gmail.username"));
    }

    @Given("user clicks on the Next Button")
    public void user_clicks_on_the_Next_Button() {
        signInPage.clickNextButton();
    }

    @Given("user enters the password")
    public void user_enters_the_password() {
        signInPage.setPasswordTextBox(properties.getProperty("gmail.password"));
    }

    @Given("user clicks on the Next Button in Password page")
    public void user_clicks_on_the_Next_Button_in_Password_page() {
        inboxPage = signInPage.clickPasswordNextButton();
    }

    @Given("user should be in the Inbox")
    public void user_should_be_in_the_Inbox() {
        Assert.assertTrue(inboxPage.isPrimaryTabAvailable(), "Browser is not in Inbox");
    }

    @Given("user opens the contacts")
    public void user_opens_the_contacts() {
        inboxPage.openContacts();
    }

    @Given("contacts should contain list of users")
    public void contacts_should_contain_list_of_users(DataTable expectedContactsList) {
        Assert.assertEquals(inboxPage.getAllContactsName(), expectedContactsList.asList(), "Contacts retrieved are not same as the contacts expected");
        // TODO Ideally it should be in Cucumber Hook: @After
        driver.quit();
    }
}


