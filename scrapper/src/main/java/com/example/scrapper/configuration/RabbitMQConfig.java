package com.example.scrapper.configuration;

import com.example.scrapper.service.queue.ScrapperQueueProducer;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "true")
public class RabbitMQConfig {
    private final String exchangeName;
    private final String queueName;
    private final String routingKey;

    private final static String DLQ_SUFFIX = ".dlq";

    public RabbitMQConfig(ApplicationConfig applicationConfig) {
        this.exchangeName = applicationConfig.rabbitQueue().exchangeName();
        this.queueName = applicationConfig.rabbitQueue().queueName();
        this.routingKey = applicationConfig.rabbitQueue().routingKey();
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public DirectExchange exchange(){
        return new DirectExchange(exchangeName, true, false);
    }

    @Bean
    public Queue queue(){
        return QueueBuilder
                .durable(queueName)
                .withArgument("x-dead-letter-exchange", exchangeName + DLQ_SUFFIX)
                .withArgument("x-dead-letter-routing-key", routingKey + DLQ_SUFFIX)
                .build();
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange directExchange){
        return BindingBuilder
                .bind(queue)
                .to(directExchange)
                .with(routingKey);
    }

    @Bean
    public ScrapperQueueProducer scrapperQueueProducer(RabbitTemplate rabbitTemplate,
                                                       ApplicationConfig applicationConfig){
        return  new ScrapperQueueProducer(rabbitTemplate, applicationConfig);

    }

}
