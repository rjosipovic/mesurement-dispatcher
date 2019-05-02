package home.mesurement.dispatcher.service;

import java.util.Map;

import home.mesurement.dispatcher.config.ApplicationConfig.Mesurement;

public interface MesurementService {
	
	public Map<Mesurement, String> readMesurements();	
}
