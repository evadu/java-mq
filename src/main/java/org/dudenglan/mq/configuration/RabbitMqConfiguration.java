package org.dudenglan.mq.configuration;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RabbitMqConfiguration {
    @Value("${spring.rabbitmq.host}")
    private String rpbaddress;

    @Value("${spring.rabbitmq.username}")
    private String rpbusername;

    @Value("${spring.rabbitmq.password}")
    private String rpbpassword;

    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualhost;

    @Bean
    public String demoQueue(@Qualifier("mainAmq") ConnectionFactory connectionFactory) {
        try {
            connectionFactory.createConnection().createChannel(false).queueDeclare("demo-queue", false, false, false, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "demo-queue";

    }

    @Bean
    public String serviceQueue(@Qualifier("serviceAmq") ConnectionFactory connectionFactory) {
        try {
            connectionFactory.createConnection().createChannel(false).queueDeclare("service-queue", false, false, false, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "service-queue";
    }


    @Bean(name = "mainAmq")
    @Primary
    ConnectionFactory mainAmq() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(rpbaddress);
        connectionFactory.setUsername(rpbusername);
        connectionFactory.setPassword(rpbpassword);
        connectionFactory.setVirtualHost(virtualhost);
        return connectionFactory;
    }

    @Bean(name = "rabbitTemplate")
    @Primary
    public RabbitTemplate rabbitTemplate(@Qualifier("mainAmq") ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }

    @Bean("serviceAmq")
    ConnectionFactory serviceAmq(@Value("${rabbitmq.vhost.service}") String vhost) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(rpbaddress);
        connectionFactory.setUsername(rpbusername);
        connectionFactory.setPassword(rpbpassword);
        connectionFactory.setVirtualHost(vhost);
        return connectionFactory;
    }

    @Bean("serviceAmqTemplate")
    public RabbitTemplate serviceAmqTemplate(@Qualifier("serviceAmq") ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }

    @Bean(name = "mainFactory")
    @Primary
    public SimpleRabbitListenerContainerFactory firstFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            @Qualifier("mainAmq") ConnectionFactory connectionFactory
    ) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    @Bean(name = "serviceFactory")
    public SimpleRabbitListenerContainerFactory secondFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            @Qualifier("serviceAmq") ConnectionFactory connectionFactory
    ) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }
}
