package com.trendyol.notificationquery.repositories;

import com.trendyol.notificationcore.models.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification, String> {
}
