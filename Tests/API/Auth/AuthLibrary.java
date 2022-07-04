package Tests.API.Auth;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import Communications.RestClient;
import General.Constants;
import Helper.JsonToMapper;
import Model.AuthModel;

public class AuthLibrary {

    @Rule
    public TestName name = new TestName();
    
    @Test
    public void login() throws IOException {

        System.out.println(Constants.TEST_STARTED + name.getMethodName());

		String api = Constants.DEV_API + "/" + "auth/login";

        String email = "daniel.galitzky@petpace.com";
        String password = "12345";
       
        AuthModel authModel = userLogin(api, email, password);
        String token = authModel.payload.token.token;
        assertEquals(email, authModel.payload.user.email);
        Constants.TOKEN = token;

        System.out.println(Constants.TEST_COMPLETED + name.getMethodName());
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
