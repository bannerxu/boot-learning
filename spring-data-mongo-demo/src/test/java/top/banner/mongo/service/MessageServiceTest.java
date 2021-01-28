package top.banner.mongo.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import top.banner.mongo.entity.Message;
import top.banner.mongo.entity.OfflineMessage;
import top.banner.mongo.repository.MessageRepository;
import top.banner.mongo.repository.OfflineMessageRepository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MessageServiceTest {
    @Resource
    private MessageRepository messageRepository;
    @Resource
    private OfflineMessageRepository offlineMessageRepository;

    public static <T> List<T> toList(final Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Test
    public void test() {
        long l = System.currentTimeMillis();
        Message message = new Message();
        message.setMsgContent("2020-11-05 17:51:23.578  INFO 9212 --- [http-nio-9902-exec-6] com.ican.shiro.StatelessAuthFilter       : 这个有可能就是401出现的原因36a9ee0456474649925633b40d45a582Realm [com.ican.shiro.RedisRealm@795fd838] was unable to find account data for the submitted AuthenticationToken [com.ican.shiro.Token@40e429af].");
        message.setSendTime(System.currentTimeMillis());
        messageRepository.save(message);


        List<OfflineMessage> offlineMessages = new ArrayList<>();
        for (int i = 0; i < 3000; i++) {
            OfflineMessage offlineMessage = new OfflineMessage();
            offlineMessage.setUser((long) i);
            offlineMessage.setMessageId(message.getMessageId());
            offlineMessage.setFromUser(message.getFromId());
            offlineMessage.setGroupId(message.getGroupId());
            offlineMessage.setCreateTime(message.getSendTime());
            offlineMessages.add(offlineMessage);
        }
        offlineMessageRepository.saveAll(offlineMessages);

        System.out.println("耗时 " + (System.currentTimeMillis() - l));

    }

    @Test
    public void select1() {
        List<String> ids = new ArrayList<>();

        for (int i = 0; i < 10; i++) {

            ids.addAll(messageRepository.findAll(PageRequest.of((i + 1) * 2, 100)).map(Message::getMessageId).stream().collect(Collectors.toList()));

        }


        long l = System.currentTimeMillis();

        Iterable<Message> allById = messageRepository.findAllById(ids);
        List<Message> messages = toList(allById);
        System.out.println(messages.size());

        long l1 = System.currentTimeMillis();
        System.out.println("耗时 " + (l1 - l));
    }

}