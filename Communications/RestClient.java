package Communications;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;



import General.Constants;
import Tools.JsonToMapper;

public class RestClient {

	public static String sendGetRequest(String apiURL, String token) {

		String result = "";

		try {
			String urlStr = apiURL;

			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("accept", "application/json");

			if (token != null) {
				conn.setRequestProperty("authorization", "Bearer " + token);
			}

			int response = conn.getResponseCode();

			if (response != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			result = br.readLine();
			conn.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();

		}

		return result;

	}

	// public static String sendPostRequest(String apiURL, String token, String requestJson) {

	// 	String result = "";

	// 	try {
	// 		String urlStr = apiURL;

	// 		URL url = new URL(urlStr);
	// 		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	// 		conn.setRequestMethod("POST");
	// 		conn.setRequestProperty("accept", "application/json");
	// 		conn.setRequestProperty("Content-Type", "application/json");
	// 		if (token != null) {
	// 			conn.setRequestProperty("authorization", "Bearer " + token);
	// 		}

	// 		conn.setDoOutput(true);

	// 		try (OutputStream os = conn.getOutputStream()) {
	// 			byte[] input = requestJson.getBytes("utf-8");
	// 			os.write(input, 0, input.length);
	// 		}

	// 		Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
	// 		StringBuilder sb = new StringBuilder();
	// 		for (int c; (c = in.read()) >= 0;)
	// 			sb.append((char) c);
	// 		result = sb.toString();
	// 	} catch (MalformedURLException e) {
	// 		e.printStackTrace();
	// 	} catch (IOException e) {
	// 		e.printStackTrace();

	// 	}

	// 	return result;

	// }

	public static String sendPostOrPutRequest(String requestType, String apiURL, String token, String json) throws IOException{
		URL url = new URL(apiURL);
		HttpURLConnection http = (HttpURLConnection)url.openConnection();
		http.setRequestMethod(requestType);
		http.setDoOutput(true);
		http.setRequestProperty("Content-Type", "application/json");
		if (token != null) {
			http.setRequestProperty("authorization", "Bearer " + token);
		}
		byte[] out = json.getBytes(StandardCharsets.UTF_8);

		OutputStream stream = http.getOutputStream();
		stream.write(out);

		BufferedReader br = null;
		if (100 <= http.getResponseCode() && http.getResponseCode() <= 399) {
			br = new BufferedReader(new InputStreamReader(http.getInputStream()));
		} else {
			br = new BufferedReader(new InputStreamReader(http.getErrorStream()));
		}

		StringBuilder sb = new StringBuilder();
			for (int c; (c = br.read()) >= 0;)
				sb.append((char) c);
		String result = sb.toString();

		System.out.println(http.getResponseCode() + " " + http.getResponseMessage() + " " + result);
		http.disconnect();

		return result;
	}

}