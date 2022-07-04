package Helper;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import DB.DbConnector;
import General.Constants;
import Model.AuthUser;
import Model.User;

public class DBHelper {

    public static String getEmailAddressForTest() throws JSONException{

        String email = null;

        String sqlForID = "SELECT email FROM `" + Constants.DEV_DB_NAME + "`.user_create_request WHERE email like '%v2Automation%' ORDER BY ID DESC limit 1;";
        JSONArray jsonArrayForID = DbConnector.getData(sqlForID);

        Map<String, Object> mapFromJson = null;
       
        for (int i = 0; i < jsonArrayForID.length();) {
            JSONObject jo = jsonArrayForID.getJSONObject(i);
            String receivedJson = jo.toString();
            System.out.println(receivedJson);
            mapFromJson = JsonToMapper.getMapFromJson(receivedJson);
            
            break;
       }

       if(mapFromJson != null){
            String lastUsedEmail = mapFromJson.get("email").toString();
            int index = lastUsedEmail.indexOf( '+' );
            int number = 1;
            if(index > 0){
                String requiredString = lastUsedEmail.substring(lastUsedEmail.indexOf("+") + 1, lastUsedEmail.indexOf("@"));
                number = Integer.parseInt(requiredString) + 1;
            }
             
            email = "v2Automation+" + String.valueOf(number) + "@petpace.com";
       }

       return email;
    }

    public static User getUserDataByEmail(String userEmail) throws JSONException, JsonMappingException, JsonProcessingException{
        
        User user = null;

        String sqlForID = "SELECT * FROM `" + Constants.DEV_DB_NAME + "`.users WHERE email ='" + userEmail + "';";
        JSONArray jsonArrayForID = DbConnector.getData(sqlForID);
       
        for (int i = 0; i < jsonArrayForID.length();) {
            JSONObject jo = jsonArrayForID.getJSONObject(i);
            String receivedJson = jo.toString();
            System.out.println(receivedJson);
            ObjectMapper om = new ObjectMapper();
            user = om.readValue(receivedJson, User.class);
            break;
       }

       return user;
    }
    
}
