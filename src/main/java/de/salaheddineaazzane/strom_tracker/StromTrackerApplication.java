package de.salaheddineaazzane.strom_tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StromTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(StromTrackerApplication.class, args);
	}

}
