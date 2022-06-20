package br.com.joshua.baseproject.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

	@Value("${app-config.rabbit.exchange.baseproject}")
	private String personTopicExchange;

	@Value("${app-config.rabbit.routingKey.person-create}")
	private String personCreateKey;

	@Value("${app-config.rabbit.routingKey.person-update}")
	private String personUpdateKey;

	@Value("${app-config.rabbit.routingKey.person-delete}")
	private String personDeleteKey;

	@Value("${app-config.rabbit.queue.person-create}")
	private String personCreateMq;

	@Value("${app-config.rabbit.queue.person-update}")
	private String personUpdateMq;

	@Value("${app-config.rabbit.queue.person-delete}")
	private String personDeleteMq;

	@Bean
	public TopicExchange personTopicExchange() {
		return new TopicExchange(personTopicExchange);
	}

	@Bean
	public Queue personCreateMq() {
		return new Queue(personCreateMq, true);
	}

	@Bean
	public Queue personUpdateMq() {
		return new Queue(personUpdateMq, true);
	}

	@Bean
	public Queue personDeleteMq() {
		return new Queue(personDeleteMq, true);
	}

	@Bean
	public Binding personCreatekMqBinding(TopicExchange topicExchange) {
		return BindingBuilder.bind(personCreateMq()).to(topicExchange).with(personCreateKey);
	}

	@Bean
	public Binding personUpdateMqBinding(TopicExchange topicExchange) {
		return BindingBuilder.bind(personUpdateMq()).to(topicExchange).with(personUpdateKey);
	}

	@Bean
	public Binding personDeleteMqBinding(TopicExchange topicExchange) {
		return BindingBuilder.bind(personDeleteMq()).to(topicExchange).with(personDeleteKey);
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
}
