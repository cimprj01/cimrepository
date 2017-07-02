package cim.enterprise.service.messaging;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import cim.enterprise.data.CimMessageProcessLotStep;
import cim.enterprise.service.CimService;
import cim.enterprise.service.CimTxnResult;

@Service
@EnableKafka
public class CimMessageListener {
	
	Logger log = Logger.getLogger(CimMessageListener.class);
	
	@Autowired
	CimService cimService;
	
	@Autowired
	KafkaProducerConfig produceConfig;

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
		
		try
		{
			ObjectMapper mapper = new ObjectMapper();
			CimMessageProcessLotStep input = mapper.readValue(cimMessage.toString(), CimMessageProcessLotStep.class);
			
			log.info("Called Txn for Lot : " + input.getLotId());
			
			CimTxnResult result = cimService.processLotStep(input.getUserToken(), input.getLotId(), input.getProcessId(), input.getRouteId(), input.getEquipmentId());
			
			//Send Reply
			String resultJson = mapper.writeValueAsString(result);
			produceConfig.kafkaTemplate().send("mes-topic-response", resultJson);
		}
		catch(Exception exp)
		{
			log.error("Unable to parse CIM message");
			exp.printStackTrace();
		}
		
		
		
	}
	

}