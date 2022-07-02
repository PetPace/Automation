package Tests.API.Auth;


import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import Communications.RestClient;
import General.Constants;
import Model.AuthModel;
import Tools.JsonToMapper;

public class AuthLibrary {

    @Test
    public void login() throws IOException {

		String api = Constants.DEV_API + "/" + "auth/login";

        String email = "daniel.galitzky@petpace.com";
        String password = "12345";
       
        AuthModel authModel = userLogin(api, email, password);
        String token = authModel.payload.token.token;
        assertEquals(email, authModel.payload.user.email);
        Constants.TOKEN = token;
	}

    public AuthModel userLogin(String api, String email, String password) throws IOException {

		String url = api + "/" + "auth/login";
       
        Map<String,String> payload = new HashMap<>();
		payload.put("email", email);
		payload.put("password",password);

        String json = JsonToMapper.GetJsonFromMap(payload);

		String response = RestClient.sendPostOrPutRequest("POST", url, null, json);

		System.out.println(response);

        ObjectMapper om = new ObjectMapper();
        AuthModel authModel = om.readValue(response, AuthModel.class); 
       
        return authModel;
	}
}
