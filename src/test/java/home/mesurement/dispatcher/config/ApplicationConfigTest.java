package home.mesurement.dispatcher.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ApplicationConfigTest {
	
	@Autowired
	private ApplicationConfig applicationConfig;

	@Test
	public void testConfigLoaded() throws Exception {
		assertNotNull(applicationConfig);
	}
	
	@Test
	public void testCollectorEndpointLoaded() throws Exception {
		assertNotNull(applicationConfig.getCollector());
		assertEquals("http://ghost:8080/EnviromentCollector/webresources/collector/", applicationConfig.getCollector().getEndpoint());
	}
	
	@Test
	public void testMesurementsLoaded() throws Exception {
		assertNotNull(applicationConfig.getMesurements());
		assertFalse(applicationConfig.getMesurements().isEmpty());
	}
}
