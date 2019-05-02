package home.mesurement.dispatcher.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import home.mesurement.dispatcher.config.ApplicationConfig;
import home.mesurement.dispatcher.config.ApplicationConfig.Mesurement;
import home.mesurement.dispatcher.service.MesurementService;

@Service
public class MesurementServiceImpl implements MesurementService {
	
	private Logger log = LoggerFactory.getLogger(MesurementServiceImpl.class.getSimpleName());

	private final ApplicationConfig applicationConfig;
	
	public MesurementServiceImpl(ApplicationConfig applicationConfig) {
		this.applicationConfig = applicationConfig;
	}
	
	@Override
	public Map<Mesurement, String> readMesurements() {
		Map<Mesurement, String> mesurementsMap = new HashMap<>();
		for(Mesurement mesurement : applicationConfig.getMesurements()) {
			String value = read(mesurement.getScript());
			mesurementsMap.put(mesurement, value);
		}
		return mesurementsMap;
	}
	
	private String read(String script) {
		String[] commands = new String[]{"/bin/bash", script};
		ProcessBuilder pb = new ProcessBuilder(commands);
		Process p = null;

		try {
			log.debug(String.format("About to execute command: %s", Arrays.toString(commands)));
			p = pb.start();
			int responseCode = p.waitFor();
			if(responseCode == 0) {
				log.debug("Command executed successfully");
				return readLineFromStream(p.getInputStream());
			} else {
				String error = readLineFromStream(p.getErrorStream());
				log.error(String.format("Error in executing command, code: %d, details: %s", responseCode, error));
			}
		} catch (IOException | InterruptedException e) {
			log.error(e.getMessage(), e);
		} finally {
			if(p != null) {
				p.destroy();
			}
		}
		return null;
	}

	private String readLineFromStream(InputStream in) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		return reader.readLine();
	}
}
