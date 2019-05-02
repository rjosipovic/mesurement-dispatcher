package home.mesurement.dispatcher.service;

import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import home.mesurement.dispatcher.MesurementDispatcherApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MesurementDispatcherApplication.class)
public class DispatcherServiceIntegrationTest {

	@Autowired
	private DispatcherService service;
	
	private static final Float temperatureValue = 29.5f;
	private static final Float humidityValue = 55.5f;
	private static final Float pressureValue = 1012.5f;
	
	private static final String temperatureTemplate = "Temperature\\[(\\d*\\.?\\d*)\\]";
	private static final String humidityTemplate = "Humidity\\[(\\d*\\.?\\d*)\\]";
	private static final String pressureTemplate = "Pressure\\[(\\d*\\.?\\d*)\\]";

	private Float findResponse(String response, String template) {
		Pattern p = Pattern.compile(template);
		Matcher m = p.matcher(response);
		if(m.find()) {
			return Float.parseFloat(m.group(1));
		}
		return null;
	}
	
	@Test
	public void serviceInstanceCreated() {
		assertNotNull(service);
	}
	
	//@Test
	public void temperature() throws Exception {
		String tempResponse = service.notifyTemperature(temperatureValue);
		Float tempResponseValue = findResponse(tempResponse, temperatureTemplate);
		assertEquals(temperatureValue, tempResponseValue);
	}
	
	//@Test
	public void humidity() {
		String humidityResponse = service.notifyHumidity(humidityValue);
		Float humidityResponseValue = findResponse(humidityResponse, humidityTemplate);
		assertEquals(humidityValue, humidityResponseValue);
	}
	
	//@Test
	public void pressure() {
		String pressureResponse = service.notifyPressure(pressureValue);
		Float pressureResponeValue = findResponse(pressureResponse, pressureTemplate);
		assertEquals(pressureValue, pressureResponeValue);
	}
}
