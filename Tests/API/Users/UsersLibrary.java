package Tests.API.Users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import Communications.RestClient;
import DB.DbConnector;
import General.Constants;
import General.Manager;
import Helper.DBHelper;
import Helper.JsonToMapper;
import Model.CreatedUserRequest;
import Model.ErrorResponse;
import Model.Response;
import Model.User;
import Selenium.SeleniumManager;

public class UsersLibrary {

    @Rule
    public TestName name = new TestName();

    @Test
    public void createNewUser() throws Exception {

        try {

            Manager.getInstance().WriteInfoToLogger(Constants.TEST_STARTED + name.getMethodName());
            Manager.getInstance().WriteInfoToLogger("Environment: " + Constants.DEV_API);

            // Get next available email

            String emailForTest = DBHelper.getEmailAddressForTest();

            Manager.getInstance().WriteInfoToLogger("Email for test: " + emailForTest);

            String url = Constants.DEV_API + "/" + "user";

            // Create request structure

            String email = emailForTest;
            String password = "12345";
            String phoneNumber = "1111111";
            String firstName = "A";
            String lastName = "B";
            String timezone = "Asia/Jerusalem";
            String country = "Israel";
            String city = "Haifa";
            String address = "Klebanov 29/24";
            String region = "North";
            String mainland = "Asia";

            // Make JSON from structure

            Map<String, String> payload = new HashMap<>();
            payload.put("email", email);
            payload.put("password", password);
            payload.put("timezone", timezone);
            payload.put("phoneNumber", phoneNumber);
            payload.put("firstName", firstName);
            payload.put("lastName", lastName);
            payload.put("country", country);
            payload.put("city", city);
            payload.put("address", address);
            payload.put("region", region);
            payload.put("mainland", mainland);

            String json = JsonToMapper.GetJsonFromMap(payload);

            Manager.getInstance().WriteInfoToLogger("API Request: " + json);

            // Activate API

            String response = RestClient.sendPostOrPutRequest("PUT", url, null, json);

            Manager.getInstance().WriteInfoToLogger("API Response: " + response);

            // Parse API response into dedicated structure

            ObjectMapper om = new ObjectMapper();
            Response responseObj = om.readValue(response, Response.class);

            assertEquals(responseObj.code, 0);

            // Check user_create_request table

            String sql = "SELECT * FROM `" + Constants.DEV_DB_NAME + "`.user_create_request WHERE email = '" + email
                    + "';";
            Manager.getInstance().WriteInfoToLogger("SQL: " + sql);

            JSONArray jsonArray = DbConnector.getData(sql);

            CreatedUserRequest createdUserRequest = null;

            for (int i = 0; i < jsonArray.length();) {
                JSONObject jo = jsonArray.getJSONObject(i);
                String receivedJson = jo.toString();
                Manager.getInstance().WriteInfoToLogger("Data from DB: " + receivedJson);
                ObjectMapper omForUser = new ObjectMapper();
                createdUserRequest = omForUser.readValue(receivedJson, CreatedUserRequest.class);
                break;
            }

            assertNotNull(createdUserRequest);

            assertEquals(email, createdUserRequest.email);
            assertEquals(timezone, createdUserRequest.timezone);
            assertEquals(phoneNumber, createdUserRequest.phone_number);
            assertEquals(firstName, createdUserRequest.first_name);
            assertEquals(lastName, createdUserRequest.last_name);
            assertEquals(country, createdUserRequest.country);
            assertEquals(city, createdUserRequest.city);
            assertEquals(address, createdUserRequest.address);
            assertEquals(region, createdUserRequest.region);
            assertEquals(mainland, createdUserRequest.mainland);

            // Login to gmail and click the verification link

            String linkText = "click here, I am not a fraud ;)";
            String newUserAddResult = SeleniumManager.loginToGmailAndClickVerificationLinkByText(Constants.GMAIL_USER,
                    Constants.GMAIL_PASS, linkText);
            ObjectMapper omNewUserResult = new ObjectMapper();
            Response responseNewUser = omNewUserResult.readValue(newUserAddResult, Response.class);

            assertNotNull(responseNewUser);
            assertEquals(0, responseNewUser.code);

            // Get created user from DB

            User user = DBHelper.getUserDataByEmail(email);
            Manager.getInstance().user = user;

            assertNotNull(user);
            assertEquals(user.email, email);

            Manager.getInstance().WriteInfoToLogger(Constants.TEST_COMPLETED + name.getMethodName());

        } catch (Exception e) {
            Manager.getInstance().WriteExceptionToLogger(e);
            assertNull(e);
        }

    }

    @Test
    public void createUserShouldFailDueToSameEmail() throws JSONException, IOException {

        try {
            Manager.getInstance().WriteInfoToLogger(Constants.TEST_STARTED + name.getMethodName());
            Manager.getInstance().WriteInfoToLogger("Environment" + Constants.DEV_API);

            String url = Constants.DEV_API + "/" + "user";

            String emailForTest = "daniel.galitzky@petpace.com";

            if (Manager.getInstance().user != null) {
                emailForTest = Manager.getInstance().user.email;
            }

            // Create request structure

            String email = emailForTest;
            String password = "12345";
            String phoneNumber = "1111111";
            String firstName = "A";
            String lastName = "B";
            String timezone = "Asia/Jerusalem";
            String country = "Israel";
            String city = "Haifa";
            String address = "Klebanov 29/24";
            String region = "North";
            String mainland = "Asia";

            // Make JSON from structure

            Map<String, String> payload = new HashMap<>();
            payload.put("email", email);
            payload.put("password", password);
            payload.put("timezone", timezone);
            payload.put("phoneNumber", phoneNumber);
            payload.put("firstName", firstName);
            payload.put("lastName", lastName);
            payload.put("country", country);
            payload.put("city", city);
            payload.put("address", address);
            payload.put("region", region);
            payload.put("mainland", mainland);

            String json = JsonToMapper.GetJsonFromMap(payload);
            Manager.getInstance().WriteInfoToLogger("API Request: " + json);

            // Activate API
            
            String response = RestClient.sendPostOrPutRequest("PUT", url, null, json);
            Manager.getInstance().WriteInfoToLogger("API Response: " + response);

            Manager.getInstance().WriteInfoToLogger(response);

            // Parse API response into dedicated structure

            ObjectMapper omForUser = new ObjectMapper();
            ErrorResponse errorResponse = omForUser.readValue(response, ErrorResponse.class);
            assertNotNull(errorResponse);

            assertEquals(-12001, errorResponse.code);

            Manager.getInstance().WriteInfoToLogger(Constants.TEST_COMPLETED + name.getMethodName());
        } catch (Exception e) {
            Manager.getInstance().WriteExceptionToLogger(e);
            assertNull(e);
        }
    }

    // @Test
    // public void getNewMail() throws Exception {

    // String mailURL = "https://tempmail.dev/en";

    // SeleniumManager.openBrowserInstance(mailURL);

    // String userEmail = SeleniumManager.getTextByElementID("current-mail");

    // int retries = 60;

    // boolean emailArrived = false;

    // for(int i = 1; i <= retries; i++){
    // emailArrived =
    // !SeleniumManager.isElementExistByXPathAndText("//*[@id='mail-area']/div[2]/div/div[2]/div[2]/div[2]/div[1]",
    // "Your inbox is empty");
    // if(emailArrived == true){
    // break;
    // }
    // SeleniumManager.clickByElementID("click-to-refresh2");
    // Thread.sleep(1000);
    // }

    // if(emailArrived == true){
    // SeleniumManager.clickByElementID("inbox-dataList");
    // SeleniumManager.clickByElementXPath("//*[@id='ReadContent']/div/div[2]/div[2]/div/div/div/a");
    // }
    // }

}
