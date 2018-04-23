package org.dudenglan.mq.receiver;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DemoReceiver {
    @RabbitListener(queues = {"demo-queue"})
    public void onMessage(Message message, Channel channel) {
        try {
            System.out.println("Demo-Receiver  : " + message.getBody().toString());
        } catch (Exception e) {

        }
    }
}
