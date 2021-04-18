package com.paulocorrea.desafiopagseguro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableMongoAuditing
public class DesafioPagseguroApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioPagseguroApplication.class, args);
	}

}
