package Tools;

import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;  
import com.fasterxml.jackson.databind.ObjectMapper;  

public class JsonToMapper {
    public static Map<String, Object> getMapFromJson(String json){

        Map<String, Object> resultData = null;

        ObjectMapper mapper = new ObjectMapper();  

        try {  
            resultData = mapper.readValue( json, new TypeReference<Map<String, Object>>() {});   
           
        } catch (Exception e) {  
            
            e.printStackTrace();  
        } 
        
        return resultData;
    }

    public static String GetJsonFromMap(Map<String,String> payload){

        String json = null;

        ObjectMapper mapper = new ObjectMapper();  

        try {  
            json = mapper.writeValueAsString(payload);
           
        } catch (Exception e) {  
            
            e.printStackTrace();  
        } 
        
        return json;
    }
}
