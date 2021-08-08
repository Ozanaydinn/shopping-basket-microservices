package com.trendyol.basketquery;

import com.trendyol.basketcore.config.AxonConfig;
import com.trendyol.basketcore.config.RabbitMQConfig;
import com.trendyol.basketcore.config.RabbitMQProducerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ AxonConfig.class, RabbitMQConfig.class, RabbitMQProducerConfig.class})
@EntityScan(basePackages = "com.trendyol.basketcore.models")
public class BasketQueryApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasketQueryApplication.class, args);
	}

}
