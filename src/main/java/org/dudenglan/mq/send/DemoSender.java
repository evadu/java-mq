package org.dudenglan.mq.send;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class DemoSender {
    @Resource(name = "rabbitTemplate")
    private RabbitTemplate rabbitTemplate;

    @Resource(name = "serviceAmqTemplate")
    private RabbitTemplate serviceAmqTemplate;

    public void send(String data) {
        this.rabbitTemplate.convertAndSend("demo-queue", data);
    }

    public void sendService(String data) {
        this.serviceAmqTemplate.convertAndSend("service-queue", data);
    }
}
