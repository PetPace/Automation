package Tests.API.Users;


import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Communications.RestClient;
import DB.DbConnector;
import General.Constants;
import Model.AuthModel;
import Selenium.SeleniumManager;
import Tools.JsonToMapper;


public class UsersLibrary {

    @Test
    public void createNewUser() throws JSONException, IOException {
    
            String url = Constants.DEV_API + "/" + "user";
    
            String email = "v2Automation@petpace.com";
            String password = "12345";
            String phoneNumber = "1111111";
            String firstName = "A";
            String lastName = "B";
            String timeZone = "Asia/Jerusalem";
            String country = "Israel";
            String city = "Haifa";
            String address = "Klebanov 29/24";
            String region = "North";
            String mainland = "Asia";
           
            Map<String,String> payload = new HashMap<>();
            payload.put("email", email);
            payload.put("password", password);
            payload.put("timezone", timeZone);
            payload.put("phoneNumber", phoneNumber);
            payload.put("firstName", firstName);
            payload.put("lastName", lastName);
            payload.put("country", country);
            payload.put("city", city);
            payload.put("address", address);
            payload.put("region", region);
            payload.put("mainland", mainland);

            String json = JsonToMapper.GetJsonFromMap(payload);

            RestClient.sendPostOrPutRequest("PUT", url, null, json);

            //String response = RestClient.sendPutRequest(url, null, json);
    
            //System.out.println(response);
    
            // String sql = "SELECT * FROM `api-v2`.pet_insights_template;";
    
            // JSONArray jsonArray = DbConnector.getData(sql);
    
            // for (int i = 0; i < jsonArray.length(); i++) {
            //     jsonArray.getJSONObject(i);
            // }
        }
    


@Test
public void createUserShouldFailDueToSameEmail() throws JSONException, IOException {

		String url = Constants.DEV_API + "/" + "user";

        String email = "daniel.galitzky@petpace.com";
       
        Map<String,String> payload = new HashMap<>();
		payload.put("email", email);
		payload.put("timezone","Asia/Jerusalem");
        payload.put("phoneNumber","1234567890");

        String json = JsonToMapper.GetJsonFromMap(payload);

		String response = RestClient.sendPostOrPutRequest("PUT", url, null, json);

		System.out.println(response);

        String sql = "SELECT * FROM `api-v2`.pet_insights_template;";

        JSONArray jsonArray = DbConnector.getData(sql);

        for (int i = 0; i < jsonArray.length(); i++) {
            jsonArray.getJSONObject(i);
        }
	}

    @Test   
    public void getNewMail() throws Exception {     
        
        String mailURL = "https://tempmail.dev/en";

        SeleniumManager.openBrowserInstance(mailURL);

        String userEmail = SeleniumManager.getTextByElementID("current-mail");

        int retries = 60;

        boolean emailArrived = false;

        for(int i = 1; i <= retries; i++){
            emailArrived = !SeleniumManager.isElementExistByXPathAndText("//*[@id='mail-area']/div[2]/div/div[2]/div[2]/div[2]/div[1]", "Your inbox is empty");
            if(emailArrived == true){
                break;
            }
            SeleniumManager.clickByElementID("click-to-refresh2");
            Thread.sleep(1000);
        }

        if(emailArrived == true){
            SeleniumManager.clickByElementID("inbox-dataList");
            SeleniumManager.clickByElementXPath("//*[@id='ReadContent']/div/div[2]/div[2]/div/div/div/a");
        }
    } 

    @Test   
    public void getGMail() throws Exception {     
               
        String textLink = "click here, I am not a fraud ;)";
        SeleniumManager.loginToGmailAndClickEmailLinkByText(Constants.GMAIL_USER, Constants.GMAIL_PASS, textLink);
    } 
}


