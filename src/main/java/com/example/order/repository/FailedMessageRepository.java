package com.example.order.repository;

import com.example.order.model.FailedMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FailedMessageRepository extends MongoRepository<FailedMessage, String> {
}
