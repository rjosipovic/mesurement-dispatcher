package home.mesurement.dispatcher.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import home.mesurement.dispatcher.config.ApplicationConfig.Mesurement;
import home.mesurement.dispatcher.config.Constants;
import home.mesurement.dispatcher.service.DispatcherService;
import home.mesurement.dispatcher.service.MesurementService;

@Component
public class MesurementScheduler {
	
	private static final Logger log = LoggerFactory.getLogger(MesurementScheduler.class);
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	
	private final DispatcherService dispatcher;
	private final MesurementService mesurement;
	
	public MesurementScheduler(DispatcherService dispatcher, MesurementService mesurement) {
		this.dispatcher = dispatcher;
		this.mesurement = mesurement;
	}
	
	@Scheduled(fixedDelay = Constants.MESUREMENT_DELAY)
	public void scheduleMesurement() {
		log.debug("Starting mesurement read");
		Map<Mesurement, String> mesurements = mesurement.readMesurements();
		notifyMesurements(mesurements);
	}
	
	private void notifyMesurements(Map<Mesurement, String> mesurements) {
		
		for(Mesurement mesurement : mesurements.keySet()) {

			String mesurementName = mesurement.getName();
			String mesurementValue = mesurements.get(mesurement);
			
			log.info("At {} {} is {}", sdf.format(new Date()), mesurementName, mesurementValue);
			
			switch (mesurementName) {
			case "temperature":
				dispatcher.notifyTemperature(Float.parseFloat(mesurementValue));				
				break;
			case "pressure":
				dispatcher.notifyPressure(Float.parseFloat(mesurementValue));
				break;
			case "humidity":
				dispatcher.notifyHumidity(Float.parseFloat(mesurementValue));
				break;
			default:
				log.warn("Unknown mesurement: {} with value {}", mesurementName, mesurementValue);
				break;
			}
		}		
	}
}
