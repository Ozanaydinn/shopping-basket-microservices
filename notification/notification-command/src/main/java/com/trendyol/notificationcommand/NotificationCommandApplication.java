package com.trendyol.notificationcommand;

import com.trendyol.notificationcore.configuration.AxonConfig;
import com.trendyol.notificationcore.configuration.RabbitMQConfig;
import com.trendyol.notificationcore.configuration.RabbitMQProducerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import({AxonConfig.class, RabbitMQConfig.class})
@SpringBootApplication
public class NotificationCommandApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationCommandApplication.class, args);
	}

}
