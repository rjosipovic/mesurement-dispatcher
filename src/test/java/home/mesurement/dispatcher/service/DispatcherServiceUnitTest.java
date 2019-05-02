package home.mesurement.dispatcher.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

@RunWith(SpringRunner.class)
@RestClientTest(DispatcherService.class)
@ComponentScan(basePackages = "home.mesurement.dispatcher.config")
public class DispatcherServiceUnitTest {
	
	private static final Float temperatureValue = 29.5F;
	private static final Float pressureValue = 1010.5F;
	private static final Float humidityValue = 55.5F;
	
	@Autowired
	private DispatcherService service;
	
	@Autowired
    private MockRestServiceServer server;
	
	private void prepareTemperatureResponse() {
		server.expect(requestTo("http://ghost:8080/EnviromentCollector/webresources/collector/temperature?value=" + temperatureValue)).andRespond(withSuccess(String.format("Temperature[%f]", temperatureValue), MediaType.TEXT_PLAIN));		
	}
	
	private void prepareHumidityResponse() {
		server.expect(requestTo("http://ghost:8080/EnviromentCollector/webresources/collector/humidity?value=" + humidityValue)).andRespond(withSuccess(String.format("Humidity[%f]", humidityValue), MediaType.TEXT_PLAIN));	
	}
	
	private void preparePressureResponse() {
		server.expect(requestTo("http://ghost:8080/EnviromentCollector/webresources/collector/pressure?value=" + pressureValue)).andRespond(withSuccess(String.format("Pressure[%f]", pressureValue), MediaType.TEXT_PLAIN));		
	}
	
	private void prepareError() {
		server.expect(requestTo("http://ghost:8080/EnviromentCollector/webresources/collector/pressure?value=" + pressureValue)).andRespond(withServerError());
	}
	
	@Test
	public void error() {
		prepareError();
		String res = service.notifyPressure(pressureValue);
		assertNull(res);
	}
	
	@Test
	public void temperature() {
		prepareTemperatureResponse();
		String tempResponse = service.notifyTemperature(temperatureValue);
		assertEquals(String.format("Temperature[%f]", temperatureValue), tempResponse);		
	}
	
	@Test
	public void humidity() {
		prepareHumidityResponse();
		String humidityResponse = service.notifyHumidity(humidityValue);
		assertEquals(String.format("Humidity[%f]", humidityValue), humidityResponse);
	}
	
	@Test
	public void pressure() {
		preparePressureResponse();
		String pressureResponse = service.notifyPressure(pressureValue);
		assertEquals(String.format("Pressure[%f]", pressureValue), pressureResponse);
	}
}
