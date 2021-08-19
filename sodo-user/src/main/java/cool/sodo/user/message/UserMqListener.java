package cool.sodo.user.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import cool.sodo.common.domain.User;
import cool.sodo.common.entity.Constants;
import cool.sodo.common.entity.Notification;
import cool.sodo.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 监听异步消息，微信平台新用户登录注册
 *
 * @author TimeChaser
 * @date 2021/7/5 9:53
 */
@Component
@Slf4j
public class UserMqListener {

    @Resource
    private ObjectMapper mapper;
    @Resource
    private UserMqProperty userMqProperty;
    @Resource
    private UserService userService;

    /**
     * 订阅MQ信息，并对比数据库 1. 当消息类型为create，如果该ID在数据库存在，不作任何处理；否则新增一条用户记录 2.
     * 当消息类型为update，如果该ID在数据库存在，更新记录；否则新增一条记录
     */
    @RabbitListener(queues = "${user-message.queue-name}")
    public void listen(byte[] message) throws IOException {

        String msg = new String(message);
        Notification notification = mapper.readValue(msg, Notification.class);
        Object data = notification.getData();
        log.info("接收到一条通告信息... ");
        log.info("通告消息类型为：[{}]", notification.getEventType());
        log.info("消息来源：[{}]", notification.getOrigin());
        log.info("消息实体：[{}]", mapper.writeValueAsString(data));
        log.info(msg);

        // 只监听 SODO_AUTH 的消息
        if (notification.getOrigin().equals(Constants.SODO_AUTH)) {
            // 有新增用户时
            if (notification.getEventType().equalsIgnoreCase(userMqProperty.getCreateType())) {
                User user = mapper.readValue(mapper.writeValueAsString(data), User.class);
                userService.insertUserMq(user);
            }
        }
    }
}