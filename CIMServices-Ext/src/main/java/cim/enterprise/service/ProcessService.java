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
import cim.enterprise.data.repository.ProcessRepository;
import cim.enterprise.data.repository.ProcessRouteRepository;

@RestController
@EnableHystrix
public class ProcessService {

	Logger log = Logger.getLogger(ProcessService.class);
	
	@Autowired
	ProcessRepository processRepository;

	@Autowired
	ProcessRouteRepository processRouteRepository;
	
	
	@RequestMapping(value="/processservice/getAllProcesses", produces = "application/json")
    public @ResponseBody CimTxnResult getAllProcesses(@RequestParam(value="user") String user) {
    	log.info("getAllProcesses() ENTRY : UserContext : " + user);
    	CimTxnResult retObj = new CimTxnResult();
    	try
    	{
    		Iterable<cim.enterprise.data.Process> processList =  processRepository.findAll();
    		
    		processList.forEach( 
    							(process) -> process.setProcessRoutes( processRouteRepository.findByProcessId(process.getProcessId()) ) 
    							);
       		retObj.setCimObject(processList);
    		retObj.setTxnResult(0,"SUCCESS", null);
    	}
    	catch(Exception err)
    	{
    		log.error(err.getMessage());
    		err.printStackTrace();
    		retObj.setTxnResult(-9999,err.getMessage(), null);
    	}
    	finally
    	{
    		log.info("getAllProcesses() EXIT : UserContext : " + user);
    	}
		return retObj;   	
    }	
	
  	@RequestMapping(value="/processservice/getProcess", produces = "application/json")
    public @ResponseBody CimTxnResult getProcess(@RequestParam(value="user") String user, @RequestParam(value="processId") String processId) {
    	log.info("getProcess() ENTRY : UserContext : " + user);
    	CimTxnResult retObj = new CimTxnResult();
    	try
    	{
    		cim.enterprise.data.Process process =  processRepository.findOne(processId);
    		
    		process.setProcessRoutes( processRouteRepository.findByProcessId(process.getProcessId()) );
    		
       		retObj.setCimObject(process);
    		retObj.setTxnResult(0,"SUCCESS", null);
    	}
    	catch(Exception exp)
    	{
    		exp.printStackTrace();
 			retObj.setTxnResult(-9999, exp.getMessage(), null);
    	}
    	finally
    	{
    		log.info("getProcess() EXIT : UserContext : " + user);
    	}
    	
		return retObj;   	
    }	
}
