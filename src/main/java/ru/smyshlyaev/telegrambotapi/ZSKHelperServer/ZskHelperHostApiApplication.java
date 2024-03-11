package ru.smyshlyaev.telegrambotapi.ZSKHelperServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.modelmapper.ModelMapper;

@SpringBootApplication
public class ZskHelperHostApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZskHelperHostApiApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}

