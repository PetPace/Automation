package Tests.API.Server_Health;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import Communications.RestClient;
import General.Constants;
import Tools.JsonToMapper;

public class ServerHealthLibrary {

@Test
public void health() {

		String url = Constants.DEV_API + "/" + "health";

		String response = RestClient.sendGetRequest(url, null);

		System.out.println(response);

        Map<String, Object> data = JsonToMapper.getMapFromJson(response);

        String healthStatus = data.get("health").toString();

		assertEquals("true", healthStatus);
	}
}
