package com.medallia.imgprocessor.configuration;

import com.medallia.imgprocessor.handler.RestTemplateResponseErrorHandler;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.Queue;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;


/**
 * Main application configurer class to manage and create basic beans.
 * @author: dheeraj.suthar
 */
@Configuration
@EnableScheduling
@EnableJms
@Slf4j
public class ApplicationConfiguration {

	@Value("${local.activemq.name:localqueue}")
	private String queueName;

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
		return restTemplateBuilder
				.errorHandler(new RestTemplateResponseErrorHandler())
				.build();
	}

	@Bean
	public Queue createQueue(){
		return new ActiveMQQueue(queueName);
	}

	@Bean
	public JmsListenerContainerFactory<?> defaultFactory(ConnectionFactory connectionFactory,
														 DefaultJmsListenerContainerFactoryConfigurer configurer) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		configurer.configure(factory, connectionFactory);
		return factory;
	}

	@Bean
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}
}
