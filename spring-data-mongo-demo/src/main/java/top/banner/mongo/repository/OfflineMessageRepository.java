package top.banner.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import top.banner.mongo.entity.OfflineMessage;

public interface OfflineMessageRepository extends MongoRepository<OfflineMessage, String> {
}