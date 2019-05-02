package home.mesurement.dispatcher.service;

public interface DispatcherService {
	
	public String notifyTemperature(float value);
	public String notifyPressure(float value);
	public String notifyHumidity(float value);
}
