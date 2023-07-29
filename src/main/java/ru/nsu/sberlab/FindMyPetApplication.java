package ru.nsu.sberlab;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class FindMyPetApplication {
	public static void main(String[] args) {
		SpringApplication.run(FindMyPetApplication.class, args);
	}
}
