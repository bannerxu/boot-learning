package top.banner.mongo.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.UUID;

/**
 * @author XGL
 */
@Data
@Document(collection = "messages")
public class Message {
    /**
     * 消息类型<br/>
     * Notice_AddGroup 加入群聊通知
     * Notice_QuitGroup 离开群聊
     * Transfer 转账消息
     */
    @Indexed
    private String msgType;

    /**
     * 消息内容 的json字符串
     */
    private String msgContent;

    /**
     * 发消息的人
     */
    private Long fromId;

    /**
     * 收消息的人
     */
    private Long toId;

    /**
     * 收消息的群
     */
    private Long groupId;

    /**
     * 消息时间
     */
    private Long sendTime;

    /**
     * 消息id
     */
    @Id
    private String messageId = UUID.randomUUID().toString();

    /**
     * 销毁时间 单位 s
     */
    private int destroy = 0;

    /**
     * 平台
     */
    private String platform;

    /**
     * 是否是一个持久化消息
     */
    private Boolean endurance = true;

    public String getMsgContent() {
        if (msgContent == null) {
            return null;
        }
        try {
            return URLDecoder.decode(msgContent, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return msgContent;
        } catch (IllegalArgumentException e) {//Illegal hex characters in escape (%) pattern
            // 如果携带百分号会出错。要先转换过渡
            msgContent = msgContent.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
            msgContent = msgContent.replaceAll("\\+", "%2B");
            try {
                return URLDecoder.decode(msgContent, "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
                return msgContent;
            }
        }
    }

    @Override
    public String toString() {
        return "Message{" +
                "msgType='" + msgType + '\'' +
                ", msgContent='" + msgContent + '\'' +
                ", fromId=" + fromId +
                ", toId=" + toId +
                ", groupId=" + groupId +
                ", sendTime=" + sendTime +
                ", messageId='" + messageId + '\'' +
                ", destroy=" + destroy +
                ", platform='" + platform + '\'' +
                ", endurance=" + endurance +
                '}';
    }

    public String toJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException ignored) {
        }
        throw new RuntimeException("Json异常");
    }
}
