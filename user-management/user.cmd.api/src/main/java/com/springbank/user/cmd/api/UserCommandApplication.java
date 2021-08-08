package com.springbank.user.cmd.api;

import com.springbank.user.core.configuration.AxonConfig;
import com.springbank.user.core.configuration.RabbitMQConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ AxonConfig.class, RabbitMQConfig.class})
public class UserCommandApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserCommandApplication.class, args);
	}

}
