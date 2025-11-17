package br.com.iagovsuite.api;

import org.springframework.boot.SpringApplication;

public class TestIaGovSuiteApiApplication {

	public static void main(String[] args) {
		SpringApplication.from(iApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
