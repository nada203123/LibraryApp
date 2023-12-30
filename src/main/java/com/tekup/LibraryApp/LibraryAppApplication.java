package com.tekup.LibraryApp;

import com.tekup.LibraryApp.service.utils.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryAppApplication implements CommandLineRunner {
	@Autowired
	private FileStorageService fileStorageService;


	public static void main(String[] args) {
		SpringApplication.run(LibraryAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		fileStorageService.init();
	}
}
