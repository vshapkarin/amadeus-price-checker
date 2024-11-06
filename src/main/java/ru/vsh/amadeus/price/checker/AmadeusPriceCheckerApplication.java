package ru.vsh.amadeus.price.checker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AmadeusPriceCheckerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmadeusPriceCheckerApplication.class, args);
	}

}
