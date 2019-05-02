package home.mesurement.dispatcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MesurementDispatcherApplication {

	public static void main(String[] args) {
		SpringApplication.run(MesurementDispatcherApplication.class, args);
	}

}
