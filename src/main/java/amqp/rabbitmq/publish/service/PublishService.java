package amqp.rabbitmq.publish.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PublishService {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void sendQueue(String exchange, String routingKey, String message) {
        try {
            log.info("{} : {} : {}", exchange, routingKey, message);
            rabbitTemplate.convertAndSend(exchange, routingKey, message);
        } catch (Exception e) {
            log.error("", e);
        }
    }

}
