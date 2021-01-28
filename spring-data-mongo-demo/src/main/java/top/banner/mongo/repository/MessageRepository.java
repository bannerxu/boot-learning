package top.banner.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import top.banner.mongo.entity.Message;

public interface MessageRepository extends MongoRepository<Message, String> {
}