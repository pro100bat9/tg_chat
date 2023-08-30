package com.example.bot.configuration;

import com.example.bot.dto.request.LinkUpdateRequest;
import com.example.bot.listener.QueueListener;
import com.example.bot.telegram.BotDispatcher;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "use-queue")
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
    public ClassMapper classMapper(){
        Map<String, Class<?>> mappings = new HashMap<>();
        mappings.put("com.example.scrapper.dto.request.LinkUpdateRequest", LinkUpdateRequest.class);

        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setTrustedPackages("com.example.scrapper.dto.*");
        classMapper.setIdClassMapping(mappings);
        return classMapper;
    }

    @Bean
    public MessageConverter jsonMessageConverter(ClassMapper classMapper){
        Jackson2JsonMessageConverter jsonConverter=new Jackson2JsonMessageConverter();
        jsonConverter.setClassMapper(classMapper);
        return jsonConverter;
    }

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(exchangeName + DLQ_SUFFIX);
    }

    @Bean
    public Queue deadLetterQueue(){
        return QueueBuilder
                .durable(queueName + DLQ_SUFFIX)
                .build();
    }

    @Bean
    public Binding deadLetterBinding(Queue queue, DirectExchange directExchange){
        return BindingBuilder
                .bind(queue)
                .to(directExchange)
                .with(routingKey + DLQ_SUFFIX);
    }

    @Bean
    public QueueListener queueListener(BotDispatcher botDispatcher){
        return new QueueListener(botDispatcher);
    }

}
