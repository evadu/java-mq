package org.dudenglan.mq.receiver;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ServiceReceiver {
    @RabbitListener(queues = {"service-queue"}, containerFactory = "serviceFactory")
    public void onMessage(Message message, Channel channel) {
        try {
            System.out.println("Service-Receiver  : " + message.getBody().toString());
        } catch (Exception e) {

        }
    }
}
