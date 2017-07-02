package cim.enterprise.service.messaging;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;


@Configuration
@EnableKafka
public class KafkaProducerConfig {
	
	@Value("${spring.kafka.producer.bootstrap.servers}")
	private String bootstrapServers;
	@Value("${spring.kafka.producer.enable.auto.commit}")
	private String enableAutoCommit;
	@Value("${spring.kafka.producer.auto.commit.interval.ms}")
	private String autoCommit;
	@Value("${spring.kafka.producer.key.deserializer}")
	private String keyDeserializer;
	@Value("${spring.kafka.producer.value.deserializer}")
	private String valueDeserializer;
	@Value("${spring.kafka.producer.session.timeout.ms}")
	private String sessionTimeout;
	@Value("${spring.kafka.producer.request.timeout.ms}")
	private String requestTimeout;		
	
	@Bean
	public ProducerFactory<String, String> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfigs());
	}

	@Bean
	public Map<String, Object> producerConfigs() {
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
        //properties.put("group.id",groupId);
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,enableAutoCommit);
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,autoCommit);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,keyDeserializer);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,valueDeserializer);
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG,sessionTimeout);
        properties.put("request.timeout.ms",requestTimeout);
		return properties;
	}

	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {
		return new KafkaTemplate<String, String>(producerFactory());
	}
}
