package cim.enterprise.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cim.enterprise.common.CIMUtils;
import cim.enterprise.service.CimTxnResult;
import cim.enterprise.data.Lot;
import cim.enterprise.data.Tool;
import cim.enterprise.data.repository.LotRepository;
import cim.enterprise.data.repository.ToolRepository;

@RestController
@EnableHystrix
public class EquipmentService {
	
	Logger log = Logger.getLogger(EquipmentService.class);

	@Autowired
	private ToolRepository toolRepository;

	@Autowired
	private LotRepository lotRepository;
	
    @RequestMapping(value="/equipmentservice/getAllEquipments", produces = "application/json")
    public @ResponseBody CimTxnResult getAllEquipments(@RequestParam(value="user") String user) {
    	log.info("getAllEquipments() ENTRY : UserContext : " + user);
    	
    	CimTxnResult retObj = new CimTxnResult();
    	try
    	{
       		retObj.setCimObject(toolRepository.findAll());
    		retObj.setTxnResult(0,"SUCCESS", null);
    	}
    	catch(Exception err)
    	{
    		err.printStackTrace();
    		log.error(err.getMessage());
       		retObj.setTxnResult(-9999,err.getMessage(), null);
    	}
    	finally
    	{
    		log.info("getAllEquipments() EXIT : UserContext : " + user);
    	}
		return retObj;   	
    }
    
    
    @RequestMapping(value="/equipmentservice/isFreeToProcess", produces = "application/json")
    public @ResponseBody CimTxnResult isFreeToProcess(@RequestParam(value="user") String user, @RequestParam(value="equipmentId") String equipmentId) {
    	
    	log.info("isFreeToProcess() ENTRY : UserContext : " + user);
    	CimTxnResult retObj = new CimTxnResult();
    	try
    	{
    		Tool tool =  toolRepository.findOne(equipmentId);

     		retObj.setCimObject(tool.getLotCount() < tool.getMaxCapacity());
    		retObj.setTxnResult(0,"SUCCESS", null);
     		
    	}
    	catch(Exception err)
    	{
    		log.error(err.getMessage());
    		err.printStackTrace();
    		retObj.setCimObject(false);
    	}
    	finally
    	{
    		log.info("isFreeToProcess() EXIT : UserContext : " + user);
    	}
    	return retObj;
    }
    
    
    
    @RequestMapping(value="/equipmentservice/ableToProcess", produces = "application/json")
    public @ResponseBody CimTxnResult ableToProcess(@RequestParam(value="user") String user, @RequestParam(value="equipmentId") String equipmentId, @RequestParam(value="lotId") String lotId) {
    	
    	log.info("ableToProcess() ENTRY : UserContext : " + user);
    	CimTxnResult retObj = new CimTxnResult();
    	try
    	{
    		Lot lot = lotRepository.findOne(lotId);
    		Tool tool = toolRepository.findOne(equipmentId);
    		
    		log.debug("Lot : " + lot.getLotName());
    		log.debug("Tool : " + tool.getToolName());
    		
    		if(tool!=null && tool.getLotFamily()!=null 
    			&& lot!=null && lot.getLotFamily()!=null)
    		{
        		retObj.setCimObject(lot.getLotFamily().equalsIgnoreCase(tool.getLotFamily()));
        		retObj.setTxnResult(0,"SUCCESS", null);
       			
    		}
    		else
    			retObj.setCimObject(false);
    	}
    	catch(Exception err)
    	{
    		log.error(err.getMessage());
    		err.printStackTrace();
    		retObj.setCimObject(false);
    	}
    	finally
    	{
    		log.info("ableToProcess() EXIT : UserContext : " + user);
    	}
    	
    	return retObj;
    }    
    
}


