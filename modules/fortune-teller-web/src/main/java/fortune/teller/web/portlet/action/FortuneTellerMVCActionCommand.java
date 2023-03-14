package fortune.teller.web.portlet.action;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import fortune.teller.web.constants.FortuneTellerWebPortletKeys;
import fortune.teller.web.constants.MVCCommandNames;

import org.osgi.service.component.annotations.Component;

@Component(immediate = true, property = { "javax.portlet.name=" + FortuneTellerWebPortletKeys.FORTUNE_TELLER_WEB,
		"mvc.command.name=" + MVCCommandNames.GET_FORTUNE_TELLER_RESULT }, service = MVCActionCommand.class)
public class FortuneTellerMVCActionCommand extends BaseMVCActionCommand {
	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		// Get parameters from the request.

		//ArrayList
		String horoscope = ParamUtil.getString(actionRequest, "horoscope", StringPool.BLANK);
		String dayOption = ParamUtil.getString(actionRequest, "day", StringPool.BLANK);

		// Call the service api to get fortune teller result
		Map<String, String> params = new HashMap<String, String>();
		params.put("sign", horoscope);
		params.put("day", dayOption);

		Map<String, String> headers = new HashMap<String, String>();
		headers.put("X-RapidAPI-Key", FortuneTellerWebPortletKeys.FORTUNE_TELLER_API_KEY);
		headers.put("X-RapidAPI-Host", FortuneTellerWebPortletKeys.FORTUNE_TELLER_API_HOST);
		headers.put("User-Agent", FortuneTellerWebPortletKeys.USER_AGENT);
		headers.put("Accept", "application/json");

		String result = FortuneTellerMVCActionCommand.doCallout(FortuneTellerWebPortletKeys.FORTUNE_TELLER_API_BASE_URL, params, headers, "POST");
		ObjectMapper mapper = new ObjectMapper();
		FortuneTellerResponse ftRestult = null;
		
		try {
			ftRestult = mapper.readValue(result, FortuneTellerResponse.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		actionRequest.setAttribute("result", result);		
		actionRequest.setAttribute("chosenHoroscope", horoscope);
		actionRequest.setAttribute("fortuneTellerResult", ftRestult);
		sendRedirect(actionRequest, actionResponse);
	}

	public static String doCallout(String targetURL, Map<String, String> params, Map<String, String> headers, String method) throws IOException {
		List<String> urlParams = new ArrayList<String>();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			urlParams.add(entry.getKey() + "=" + entry.getValue());
		}
		targetURL = targetURL + "?" + String.join("&", urlParams);
		URL obj = new URL(targetURL);

		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		for (Map.Entry<String, String> entry : headers.entrySet()) {
			con.setRequestProperty(entry.getKey(), entry.getValue());
		}
		
		con.setRequestMethod(method);

		if (method.equalsIgnoreCase("post")) {
			con.setDoOutput(true);
			OutputStream os = con.getOutputStream();
			//os.write(paramsFormatString.getBytes());
			os.flush();
			os.close();
		}

		int responseCode = con.getResponseCode();

		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			return response.toString();
		} else {
			System.out.println("POST request did not work.");
			return null;
		}
	}
}
