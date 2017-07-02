package cim.enterprise.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableHystrix
@EnableKafka
public class KafkaService {

	Logger log = Logger.getLogger(KafkaService.class);
	
	@Autowired
	KafkaProducerConfig producer;
	
	
	@KafkaListener(topics = "mes-topic", group="mesgroup")
    public void process(String message/* , Acknowledgment ack */) {
        //Gson gson = new Gson();
        //Record record = gson.fromJson(message, Record.class);

        //log.info(record.getId() + " " + record.getName());
        // ack.acknowledge();
		System.out.println("got KAFKA Message : " + message);
    }	


	@RequestMapping(value="/cimmessage", produces = "application/json")
    public @ResponseBody CimTxnResult dummyService(@RequestParam(value="user") String user, @RequestParam(value="data") String data) {
    	System.out.println("dummyService() ENTRY : UserContext : " + user);
    	
    	CimTxnResult retObj = new CimTxnResult();
    	
    	producer.kafkaTemplate().send("mes-topic", data);
    	try
    	{
    		retObj.setCimObject("Kakfa");
    		retObj.setTxnResult(0,"SUCCESS", null);
    		return retObj;
    	}
    	catch(Exception err)
    	{
    		log.error(err.getMessage());
    		err.printStackTrace();
    		retObj.setTxnResult(-9999,err.getMessage(), null);
    		return retObj;
    	}
    	finally
    	{
    		log.info("dummyService() EXIT : UserContext : " + user);
    	}
    }	
	
  
}
