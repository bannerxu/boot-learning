package top.banner.mongo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

/**
 * 离线消息
 */
@Data
@Document(collection = "offlineMessage")
public class OfflineMessage {

    @Id
    private String id = UUID.randomUUID().toString().replace("-", "");

    /**
     * 收件人
     */
    private Long user;

    /**
     * 发件人
     */
    private Long fromUser;

    /**
     * 所属群组
     */
    private Long groupId;

    /**
     * 消息id
     */
    private String messageId;

    /**
     * 创建时间
     */
    private Long createTime;
}
