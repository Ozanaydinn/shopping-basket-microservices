package com.springbank.user.query.api;

import com.springbank.user.core.configuration.AxonConfig;
import com.springbank.user.core.configuration.RabbitMQConfig;
import com.springbank.user.core.configuration.RabbitMQConsumerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@Import({ AxonConfig.class, RabbitMQConfig.class, RabbitMQConsumerConfig.class})
@EnableScheduling
public class UserQueryApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserQueryApplication.class, args);
	}

}
