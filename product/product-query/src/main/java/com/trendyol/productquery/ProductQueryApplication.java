package com.trendyol.productquery;

import com.trendyol.productcore.config.AxonConfig;
import com.trendyol.productcore.config.RabbitMQConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ AxonConfig.class, RabbitMQConfig.class})
@EntityScan(basePackages = "com.trendyol.productcore.models")
public class ProductQueryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductQueryApplication.class, args);
	}

}
