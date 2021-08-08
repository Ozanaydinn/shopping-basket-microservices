package com.trendyol.productcommand;

import com.trendyol.productcore.config.AxonConfig;
import com.trendyol.productcore.config.RabbitMQConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ AxonConfig.class, RabbitMQConfig.class})
public class ProductCommandApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductCommandApplication.class, args);
	}

}
