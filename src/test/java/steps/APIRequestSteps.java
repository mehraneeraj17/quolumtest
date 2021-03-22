package steps;

import component.APIRequest;
import component.RequestAction;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class APIRequestSteps extends BaseStep {
    APIRequest apiRequest;
    Response response = null;

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
}


