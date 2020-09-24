package amqp.rabbitmq.publish.controller;

import amqp.rabbitmq.publish.service.PublishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/rabbit")
public class PublishController {

    @Autowired
    PublishService publishService;

    @GetMapping("/pub/{exchange}/{routingKey}/{message}")
    public String pubStr(@PathVariable(name = "exchange") String exchange, @PathVariable(name = "routingKey") String routingKey, @PathVariable(name = "message") String message) {
        publishService.sendQueue(exchange, routingKey, message);
        return "ok";
    }

}
