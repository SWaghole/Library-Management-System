package com.example.LibraryProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;

@SpringBootApplication
@EntityScan("com.example.LibraryProject.models")
public class LibraryProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryProjectApplication.class, args);
	}

}
