package cim.enterprise.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cim.enterprise.common.CIMConstants;
import cim.enterprise.common.CIMUtils;
import cim.enterprise.service.CimTxnResult;
import cim.enterprise.data.CimObject;
import cim.enterprise.data.Lot;
import cim.enterprise.data.LotProcessJob;
import cim.enterprise.data.ProcessHistory;
import cim.enterprise.data.ProcessRoute;
import cim.enterprise.data.Tool;
import cim.enterprise.data.repository.LotProcessRepository;
import cim.enterprise.data.repository.LotRepository;
import cim.enterprise.data.repository.ProcessHistoryRepository;
import cim.enterprise.data.repository.ProcessRepository;
import cim.enterprise.data.repository.ProcessRouteRepository;
import cim.enterprise.data.repository.ToolRepository;

@RestController
@EnableHystrix
public class CimService {
	Logger log = Logger.getLogger(CimService.class);
	// Get Process for Lot
	// Start Lot in Process
	// Process Lot in equipment (step)
	// Get Process History
	
	@Autowired
	ProcessRepository processRepository;
	
	@Autowired
	ProcessHistoryRepository processHistoryRepository;
		
	
	@Autowired
	ToolRepository toolRepository;	

	@Autowired
	ProcessRouteRepository processRouteRepository;
	
	@Autowired
	LotRepository lotRepository;	
	
	@Autowired
	LotProcessRepository lotProcessRepository;
	
	//Start Lot
	@RequestMapping(value = "/cimservice/startLot", produces = "application/json")
    public @ResponseBody CimTxnResult startLot(@RequestParam(value="user") String user, 
    								@RequestParam(value="lotId") String lotId,
    								@RequestParam(value="processId") String processId
    								) {
		log.info("startLot() ENTRY user : " + user);
		CimTxnResult retObj = new CimTxnResult();
		try
		{
			if(lotProcessRepository.isLotInProcess(lotId, processId))
			{
				log.info("Lot <" + lotId + "> is already in process.");
				retObj.setTxnResult(-100, "Lot Already Started", null);
				return retObj;
			}
			
			log.info("Starting the lot <" + lotId +"> with process <" + processId + ">.");
			
			Date now = new Date();
			
			lotProcessRepository.startLot(lotId, processId);
			
			cim.enterprise.data.Process process = getProcess(processId);
			
			ProcessRoute firstRoute = process.getProcessRoutes().get(0);
			
			lotProcessRepository.addProcessJob(lotId, processId, CIMConstants.LOT_EQUIP_INIT, 
											firstRoute.getRouteId(), firstRoute.getSeqNo(), null, now, null);
			
			addHistory(lotId, processId, null, null, "LOT_START", now);
			
			retObj.setTxnResult(0, "SUCCESS", null);
			retObj.setCimObject(firstRoute);
			return retObj;
		}
		catch(Exception exp)
		{
			retObj.setTxnResult(-9999, exp.getMessage(), null);
			return retObj;
		}
		finally
		{
			log.info("startLot() EXIT user : " + user);
		}
	}
	
	
	@RequestMapping(value="/cimservice/getNextEquipmentsForLot", produces = "application/json")
    public @ResponseBody CimTxnResult getNextEquipmentsForLot(@RequestParam(value="user") String user, 
    								@RequestParam(value="lotId") String lotId
    								) {
		log.info("getNextEquipmentsForLot() ENTRY user : " + user);
		CimTxnResult retObj = new CimTxnResult();		
		try
		{
			String processId = lotProcessRepository.getLotProcessId(lotId);
			
			if(processId==null || !lotProcessRepository.isLotInProcess(lotId, processId))
			{
				log.info("Lot <" + lotId + "> is not in any process.");
				retObj.setTxnResult(-100, "Lot is not in process.", null);
				return retObj;
			}
			
			Lot lot = lotRepository.findOne(lotId);
			Iterable<Tool> toolList = toolRepository.findAll();
			
			ArrayList<Tool> availbleToolList = new ArrayList<Tool>();
			ProcessRoute nextRoute = null;
			
			
			//Get the next step for the process if any			
			LotProcessJob lastStep = lotProcessRepository.findLastProcessJob(lotId);
			
			cim.enterprise.data.Process process = getProcess(processId);
			nextRoute = process.getProcessRoutes().get(0);//Assign first route by default
			for(ProcessRoute processRoute : process.getProcessRoutes())
			{
				if(lastStep!=null)
				{
					log.info("Checking with process step : " + processRoute.getRouteId() + " and seq : " + processRoute.getSeqNo());
					if(processRoute.getSeqNo() == (lastStep.getSeqNo()+1))
					{
						nextRoute = processRoute;
						break;
					}
				}
			}
			
			if(nextRoute == null)
			{
				retObj.setTxnResult(-100, "No route found for lot", null);
				return retObj;
			}
			
			
			//Get available equipments
			final ProcessRoute tmpNextRoute = nextRoute;
			toolList.forEach( (tool) -> 
								{
									if(tool.getLotFamily().equalsIgnoreCase(lot.getLotFamily()) && tool.getLotCount()==0)
									{
										//Next check if this equipment can process current route step
										if(tool.getEquipmentFamily().equalsIgnoreCase(tmpNextRoute.getEquipmentFamily()))
											availbleToolList.add(tool);
									}
								}
							);
			
			
			retObj.setTxnResult(0, "SUCCESS", null);
			retObj.setCimObject(availbleToolList);
			return retObj;			
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
			log.info("getNextEquipmentsForLot() EXIT user : " + user);
		}
	}
	
	
	@RequestMapping(value="/cimservice/getLotNextProcessStep", produces = "application/json")
    public @ResponseBody CimTxnResult getLotNextProcessStep(@RequestParam(value="user") String user, 
    								@RequestParam(value="lotId") String lotId
    								) {
		log.info("getLotProcessStep() ENTRY user : " + user);
		CimTxnResult retObj = new CimTxnResult();
		try
		{
			String processId = lotProcessRepository.getLotProcessId(lotId);
			if(processId == null || !lotProcessRepository.isLotInProcess(lotId, processId))
			{
				log.info("Lot <" + lotId + "> is not in any process.");
				retObj.setTxnResult(-100, "Lot is not in any process.", null);
				return retObj;
			}

			ProcessRoute nextRoute = getLotNextRoute(lotId, processId);
			
			if(nextRoute==null)
			{
				retObj.setTxnResult(-200, "No Process Route",null );
			}
			else
			{
				retObj.setTxnResult(0, "SUCCESS",null );
				retObj.setCimObject(nextRoute);
			}
			
			
			return retObj;
		}
		catch(Exception exp)
		{
			retObj.setTxnResult(-9999, exp.getMessage(), null);
			return retObj;
		}
		finally
		{
			log.info("getLotProcessStep() EXIT user : " + user);
		}
	}
	
	@Transactional
	@RequestMapping(value="/cimservice/processLotStep", produces = "application/json")
    public @ResponseBody CimTxnResult processLotStep(@RequestParam(value="user") String user, 
    								@RequestParam(value="lotId") String lotId,
    								@RequestParam(value="processId") String processId,
    								@RequestParam(value="routeId") String routeId,
    								@RequestParam(value="equipmentId") String equipmentId
    								) {
		log.info("processLotStep() ENTRY user : " + user);
		CimTxnResult retObj = new CimTxnResult();
		
		//TODO - Check if the equipment is eligible
		try
		{
			//Step 0 - Check the lot status
			if(!lotProcessRepository.isLotInProcess(lotId, processId))
			{
				log.info("Lot <" + lotId + "> is not in any process.");
				retObj.setTxnResult(-100, "Lot is not in process.", null);
				return retObj;
			}			
			
			if(!lotProcessRepository.isLotAtCurrentRoute(lotId, processId, routeId))
			{
				retObj.setTxnResult(-300,"Lot<" + lotId +"> is not at the route<" + routeId + ">.", null);
				return retObj;
			}
			
			//Step 0 - Check if the route is at current step
			ProcessRoute currentRoute = getLotNextRoute(lotId, processId);
			if(currentRoute!=null && !currentRoute.getRouteId().equalsIgnoreCase(routeId))
			{
				retObj.setTxnResult(-300,"Lot <" + lotId + "> is not at the route <" + routeId + ">.", null);
				return retObj;
			}
			
			//Step 0 - Check if the equipment can process this lot
			if(!isFreeToProcess(equipmentId))
			{
				retObj.setTxnResult(-300,"Equpiment <" + equipmentId + "> not free to process lot <" + lotId + ">.", null);
				return retObj;
			}
			
			if(!ableToProcess(equipmentId, lotId, currentRoute.getEquipmentFamily()))
			{
				retObj.setTxnResult(-300,"Equpiment <" + equipmentId + "> will not be able to process lot <" + lotId + ">.", null);
				return retObj;
			}			
			
			Date now = new Date();
			
			//Step 1 - Update the current step to END
			lotProcessRepository.updateProcessJob(lotId, processId, CIMConstants.LOT_EQUIP_END, routeId, equipmentId, now, now);
			
			//Step 2 - Add to History
			addHistory(lotId, processId, equipmentId, routeId, "PROCESSED", now);
			
			//Step 3 - Check for the next route
			ProcessRoute nextRoute = null;
					
			nextRoute = getLotNextRoute(lotId, processId);
			
			//Step 4 : Initialize next step if available
			if(nextRoute!=null)
			{
				lotProcessRepository.addProcessJob(lotId, processId, CIMConstants.LOT_EQUIP_INIT, 
						nextRoute.getRouteId(), nextRoute.getSeqNo(), null, now, null);
			}
			
			//Step 5 : Check if all processing is completed, if yes set the Lot Process to complete
			if(nextRoute==null)
			{
				//Process Completed
				lotProcessRepository.updateLotProcess(lotId, processId, "COMPLETED");
				//Add to History
				addHistory(lotId, processId, equipmentId, null, "LOT_COMPLETE", now);
				
				retObj.setTxnResult(0, "SUCCESS",null );
				retObj.setTxnResult(0,"Lot Process Completed", null);
			}
			else
			{
				retObj.setTxnResult(0, "SUCCESS",null );
				retObj.setCimObject(nextRoute);
			}
			
			return retObj;
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
			retObj.setTxnResult(-9999, exp.getMessage(), null);
			return retObj;
		}
		finally
		{
			log.info("processLotStep() EXIT user : " + user);
		}
	}	
	
	
	
   public cim.enterprise.data.Process getProcess(String processId) {
    	log.info("getProcess() ENTRY ");
    	try
    	{
    		cim.enterprise.data.Process process =  processRepository.findOne(processId);
    		
    		process.setProcessRoutes( processRouteRepository.findByProcessId(process.getProcessId()) );
    		
    		return process;
    	}
    	catch(Exception err)
    	{
    		err.printStackTrace();
    		//return new Error("" , err.getMessage());
    		throw err;
    	}
    	finally
    	{
    		log.info("getProcess() EXIT ");
    	}
    }		
	
	
  public void addHistory(String lotId, String processId, String equipmentId, String routeId, String actionCode, Date claimTime)
  {
	  log.info("addHistory() ENTRY ");

	  Lot lot = lotRepository.findOne(lotId);

	  ProcessHistory history = new ProcessHistory();
	  history.setLotId(lotId);
	  history.setFoupId(lot.getFoupId());
	  history.setProcessId(processId);
	  history.setRouteId(routeId);
	  history.setToolId(equipmentId);
	  history.setActionCode(actionCode);
	  history.setClaimTime(claimTime);
	  
	  processHistoryRepository.save(history);
	  
	  log.info("addHistory() EXIT ");
  }
  
  
  ProcessRoute getLotCurrentRoute(String lotId, String processId)
  {
	  log.info("getLotCurrentOrCompletedRoute() ENTRY ");
	  
	  ProcessRoute nextRoute = null;
	  
	  //Step 1 Get the last completed or Initiated step for the process if any	
	  LotProcessJob lastStep = lotProcessRepository.findLastProcessJob(lotId);
	
	  //Step 2 Get the routes in process
	  cim.enterprise.data.Process process = getProcess(processId);
	  
	  //Step 3 Assume first route if no last completed
	  if(lastStep == null)
		  nextRoute = process.getProcessRoutes().get(0);
	  else
	  {  
		  //Step 4 Browse throught process to check current route
		  for(ProcessRoute processRoute : process.getProcessRoutes())
		  {
			if(lastStep!=null)
			{
				log.info("Checking with process step : " + processRoute.getRouteId() + " and seq : " + processRoute.getSeqNo());
				if(processRoute.getSeqNo() == (lastStep.getSeqNo()))
				{
					nextRoute = processRoute;
					break;
				}
			}
		  }	
	  }
		
	  log.info("getLotCurrentOrCompletedRoute() EXIT ");
	  
	  return nextRoute;
  }
  
  ProcessRoute getLotNextRoute(String lotId, String processId)
  {
	  log.info("getLotNextRoute() ENTRY ");
	  ProcessRoute nextRoute = null;
	  
	  //Step 1 Get the last completed step for the process if any	
	  LotProcessJob lastStep = lotProcessRepository.findLastProcessJob(lotId);
	
	  //Step 2 Get the routes in process
	  cim.enterprise.data.Process process = getProcess(processId);
	  
	  //Step 3 Assume first route if no last completed
	  if(lastStep == null)
		  nextRoute = process.getProcessRoutes().get(0);
	  else
	  {  
		  //Step 4 Browse through process route to check for next route
		  for(ProcessRoute processRoute : process.getProcessRoutes())
		  {
			if(lastStep!=null)
			{
				log.info("Checking with process step : " + processRoute.getRouteId() + " and seq : " + processRoute.getSeqNo());
				if(processRoute.getSeqNo() == (lastStep.getSeqNo() + 1))
				{
					nextRoute = processRoute;
					break;
				}
			}
		  }	
	  }
	  
	  log.info("getLotNextRoute() ENTRY ");
	  return nextRoute;
  }
  
  
   boolean isFreeToProcess(String equipmentId) {
  	
  	log.info("isFreeToProcess() ENTRY ");
  	try
  	{
  		Tool tool =  toolRepository.findOne(equipmentId);

   		return tool.getLotCount() < tool.getMaxCapacity();
  	}
  	catch(Exception err)
  	{
  		throw err;
  	}
  	finally
  	{
  		log.info("isFreeToProcess() EXIT ");
  	}

  }
  
  public boolean ableToProcess(String equipmentId, String lotId, String equipmentFamily) {
  	
  	log.info("ableToProcess() ENTRY ");
  	try
  	{
  		Lot lot = lotRepository.findOne(lotId);
  		Tool tool = toolRepository.findOne(equipmentId);
  		
  		log.debug("Lot : " + lot.getLotName());
  		log.debug("Tool : " + tool.getToolName());
  		
  		if(tool!=null && tool.getLotFamily()!=null 
  			&& lot!=null && lot.getLotFamily()!=null)
  		{
      		return lot.getLotFamily().equalsIgnoreCase(tool.getLotFamily())
      				&& tool.getEquipmentFamily().equalsIgnoreCase(equipmentFamily);
  		}
 
  		return false;
  	}
  	catch(Exception err)
  	{
  		log.error(err.getMessage());
  		err.printStackTrace();
  		throw err;
  	}
  	finally
  	{
  		log.info("ableToProcess() EXIT ");
  	}
  }      
  
  
}
