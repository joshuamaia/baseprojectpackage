package br.com.joshua.baseprojectexpense;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@EnableRabbit
@SpringBootApplication
@EnableEurekaClient
@OpenAPIDefinition(info = @Info(title = "Base Project API", version = "1.0", description = "Base Project Information"))
public class BaseprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaseprojectApplication.class, args);
	}

}
