package cim.enterprise.service.messaging;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import cim.enterprise.service.CimTxnInput;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {
	
	@Value("${spring.kafka.consumer.bootstrap.servers}")
	private String bootstrapServers;
	@Value("${spring.kafka.consumer.group.id}")
	private String groupId;
	@Value("${spring.kafka.consumer.enable.auto.commit}")
	private String enableAutoCommit;
	@Value("${spring.kafka.consumer.auto.commit.interval.ms}")
	private String autoCommit;
	@Value("${spring.kafka.consumer.key.deserializer}")
	private String keyDeserializer;
	@Value("${spring.kafka.consumer.value.deserializer}")
	private String valueDeserializer;
	@Value("${spring.kafka.consumer.session.timeout.ms}")
	private String sessionTimeout;
	@Value("${spring.kafka.consumer.request.timeout.ms}")
	private String requestTimeout;	
	
	@Bean
	KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		factory.setConcurrency(3);
		factory.getContainerProperties().setPollTimeout(3000);
		return factory;
	}

	@Bean
	public ConsumerFactory<String, String> consumerFactory() {
		System.out.println("Consumer configs called.");
		return new DefaultKafkaConsumerFactory<>(consumerConfigs());
	}

	@Bean
	public Map<String, Object> consumerConfigs() {
		Map<String, Object> properties = new HashMap<>();
		
		/*
		propsMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9093");
		//propsMap.put(ConsumerConfig., "localhost:9093");
		propsMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
		propsMap.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
		propsMap.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
		propsMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		propsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		propsMap.put(ConsumerConfig.GROUP_ID_CONFIG, "mesgroup");
		propsMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		*/
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,groupId);
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,enableAutoCommit);
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,autoCommit);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,keyDeserializer);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,valueDeserializer);
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG,sessionTimeout);
        properties.put("request.timeout.ms",requestTimeout);        
        
		return properties;
	}

	@Bean
	public CimMessageListener listener() {
		System.out.println("getListener(); called");
		return new CimMessageListener();
	}
	
	
	@Bean
	public ConsumerFactory<String, CimTxnInput> cimConsumerFactory() {
	    // ...
	    return new DefaultKafkaConsumerFactory<>(
	    		consumerConfigs(),
	      new StringDeserializer(), 
	      new JsonDeserializer<>(CimTxnInput.class));
	}
	 
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, CimTxnInput> cimKafkaListenerContainerFactory() {
	 
	    ConcurrentKafkaListenerContainerFactory<String, CimTxnInput> factory
	      = new ConcurrentKafkaListenerContainerFactory<>();
	    factory.setConsumerFactory(cimConsumerFactory());
	    return factory;
	}
}
