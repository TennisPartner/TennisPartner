package com.tennisPartner.tennisP;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TennisPApplication {

	public static void main(String[] args) {
		SpringApplication.run(TennisPApplication.class, args);
	}

}
