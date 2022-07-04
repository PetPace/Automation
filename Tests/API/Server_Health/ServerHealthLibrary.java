package Tests.API.Server_Health;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import Communications.RestClient;
import General.Constants;
import Helper.JsonToMapper;

public class ServerHealthLibrary {

	@Rule
    public TestName name = new TestName();

	@Test
	public void health() {

		System.out.println(Constants.TEST_STARTED + name.getMethodName());

		String url = Constants.DEV_API + "/" + "health";

		String response = RestClient.sendGetRequest(url, null);

		System.out.println(response);

		Map<String, Object> data = JsonToMapper.getMapFromJson(response);

		String healthStatus = data.get("health").toString();

		assertEquals("true", healthStatus);
		
		System.out.println(Constants.TEST_COMPLETED + name.getMethodName());
	}
}
