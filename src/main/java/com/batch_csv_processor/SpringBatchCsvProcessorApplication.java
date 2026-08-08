package com.batch_csv_processor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class SpringBatchCsvProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchCsvProcessorApplication.class, args);
		System.out.println("App started");
	}

}
