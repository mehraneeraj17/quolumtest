package component;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RequestAction {

    RequestSpecification requestSpecification = null;
    Response response = null;

    public Response get(APIRequest apiRequest) {
        RestAssured.baseURI = apiRequest.getBaseURL();
        requestSpecification = RestAssured.given();
        response = requestSpecification.get(apiRequest.getEndPoint());
        response.asPrettyString();
        return response;
    }
}
