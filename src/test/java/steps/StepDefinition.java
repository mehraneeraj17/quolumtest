package steps;

import component.APIRequest;
import component.RequestAction;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import pages.InboxPage;
import pages.SignInPage;
import utils.ConfigReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class StepDefinition extends BaseStep {

    APIRequest apiRequest;
    Response response = null;

    // Pages
    SignInPage signInPage;
    InboxPage inboxPage;

    @Given("the base URL is {string}")
    public void the_base_url_is(String baseURL) {
        apiRequest = new APIRequest();
        apiRequest.setBaseURL(baseURL);
    }

    @Given("the end point is {string}")
    public void the_end_point_is(String endPoint) {
        apiRequest.setEndPoint(endPoint);
    }

    @Given("the request type is {string}")
    public void the_request_type_is(String requestType) {
        apiRequest.setRequestType(requestType);
    }

    @When("the request is sent")
    public void the_request_is_sent() {
        RequestAction requestAction = new RequestAction();
        switch (apiRequest.getRequestType().toUpperCase()) {
            case "GET":
                response = requestAction.get(apiRequest);
        }
    }

    @Then("the response should be {int}")
    public void the_response_should_be(int statusCode) {
        Assert.assertEquals(response.getStatusCode(), statusCode, "Status code is not same");
    }

    @Then("response should be as expected")
    public void response_should_be_as_expected() throws IOException {
        FileReader fr = new FileReader(System.getProperty("user.dir") + File.separator + "src/test/resources/expectedResponse.json");
        int i;
        String content = "";
        while ((i = fr.read()) != -1) {
            content = content + (char) i;
        }
        fr.close();
        System.out.println(content);
        Assert.assertEquals(response.getBody().prettyPrint().replaceAll("\\s+", ""), content.replaceAll("\\s+", ""), "Response content is not as expected");
    }

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


