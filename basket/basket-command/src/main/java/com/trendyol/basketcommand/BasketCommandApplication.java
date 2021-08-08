package com.trendyol.basketcommand;

import com.trendyol.basketcore.config.AxonConfig;
import com.trendyol.basketcore.config.RabbitMQConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ AxonConfig.class, RabbitMQConfig.class})
public class BasketCommandApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasketCommandApplication.class, args);
	}

}
