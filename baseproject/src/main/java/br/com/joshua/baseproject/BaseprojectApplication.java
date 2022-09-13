package br.com.joshua.baseproject;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableRabbit
@SpringBootApplication
@EnableEurekaClient
@OpenAPIDefinition(info = @Info(title = "Base Project API", version = "1.0", description = "Base Project Information"))
public class BaseprojectApplication implements CommandLineRunner {

	@Value("${app-config.rabbit.exchange.baseproject}")
	private String personTopicExchange;

	@Value("${app-config.rabbit.routingKey.start}")
	private String startKey;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public static void main(String[] args) {
		SpringApplication.run(BaseprojectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Entrndo no método RUN!");
		rabbitTemplate.convertAndSend(personTopicExchange, startKey, "Teste");
		log.info("Entrndo no método RUN!");
	}

}
