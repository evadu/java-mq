package org.dudenglan.mq;

import org.dudenglan.mq.send.DemoSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMqHelloTest {

    @Autowired
    private DemoSender demoSender;

    @Test
    public void demo() throws Exception {
        demoSender.send("this is my demo test");
    }

    @Test
    public void service(){
        demoSender.sendService("this is my service test");
    }

}