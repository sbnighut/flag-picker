package com.practice.flagpicker;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.flagpicker.service.CountryService;
import com.practice.flagpicker.model.Continent;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@SpringBootApplication(scanBasePackages = "com.practice.flagpicker")
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	/**
	 * Read the text file during service boot up
	 * @param countryService
	 * @return
	 */
	@Bean
	CommandLineRunner runner(CountryService countryService) {
		return args -> {
			// read text and write to mongodb document
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<List<Continent>> typeReference = new TypeReference<List<Continent>>(){};
			InputStream inputStream = TypeReference.class.getResourceAsStream("/static/continents.txt");
			try {
				List<Continent> continents = mapper.readValue(inputStream, typeReference);
				countryService.saveContinents(continents);
				System.out.println("Users Saved!");
			} catch (IOException e){
				System.out.println("Unable to save users: " + e.getMessage());
			}
		};
	}

}
