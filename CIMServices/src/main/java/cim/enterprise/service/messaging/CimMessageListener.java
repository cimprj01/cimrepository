package cim.enterprise.service.messaging;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.log4j.Logger;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import cim.enterprise.service.CimTxnInput;

@Service
@EnableKafka
public class CimMessageListener {
	
	Logger log = Logger.getLogger(CimMessageListener.class);

	//public final CountDownLatch countDownLatch1 = new CountDownLatch(1);
	
	/*
	@KafkaListener(
			  topics = "mes-topic", 
			  containerFactory = "cimKafkaListenerContainerFactory")
	*/
	
	@KafkaListener(topics = "mes-topic", group = "mesgroup")
	//public void getCimMessage(CimTxnInput cimMessage)
	public void getCimMessage(ConsumerRecord<?, ?> record)
	{
		Object cimMessage = record.value();
		
		log.info("Got CIM Mesage : " + cimMessage + " at offset : " + record.offset());
		
	}
	

}