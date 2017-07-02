package cim.enterprise.data.repository;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cim.enterprise.data.LotProcessJob;

@Repository
public class LotProcessRepository {
	Logger log = Logger.getLogger(LotProcessRepository.class);
	
		@Autowired
	    private JdbcTemplate jdbcTemplate;
	    		
	    public List<LotProcessJob> findLotProcessJob(String lotId) {

	        List<LotProcessJob> result = jdbcTemplate.query(
	                "SELECT lot_id, process_id, status, route_id, seq_no, equipment_id,start_time, end_time  "
	        		+ " FROM LOT_PROCESS_JOB WHERE lot_id = '" + lotId + "' AND status in ('END','SKIP') ORDER BY seq_no",
	                (rs, rowNum) -> new LotProcessJob(rs.getString("lot_id"),
	                        rs.getString("process_id"), rs.getString("status"), rs.getString("route_id"), 
	                        rs.getInt("seq_no"), rs.getString("equipment_id"), rs.getDate("start_time"),
	                        rs.getDate("end_time"), rs.getString("process_name")
	                        )
	        		);

	        return result;

	    }   
	    
	    public LotProcessJob findLastProcessJob(String lotId) {

	        List<LotProcessJob> result = jdbcTemplate.query(
	                "SELECT lot_id, process_id, status, route_id, seq_no, equipment_id,start_time, end_time  "
	        		+ " FROM LOT_PROCESS_JOB WHERE lot_id = '" + lotId + "' AND status in ('END','SKIP') ORDER BY SEQ_NO",
	                (rs, rowNum) -> new LotProcessJob(rs.getString("lot_id"),
	                        rs.getString("process_id"), rs.getString("status"), rs.getString("route_id"), 
	                        rs.getInt("seq_no"), rs.getString("equipment_id"), rs.getDate("start_time"),
	                        rs.getDate("end_time"), null
	                        )
	        		);

	        return result!=null && result.size()>0?result.get(result.size()-1):null;
	    }
	    
	    public boolean isLotAtCurrentRoute(String lotId, String processId, String routeId)
	    {

	       int count = (int) jdbcTemplate.query(
	                "SELECT count(*) "
	        		+ " FROM LOT_PROCESS_JOB WHERE process_id='" + processId  + "' AND lot_id = '" + lotId + "' "
	        		+ " AND route_id = '" + routeId  + "' AND status NOT IN ('END','SKIP') ",
	                (rs, rowNum) -> rs.getInt(1)
	        		).get(0);

	        return count == 1;	    	
	    }
	    
	    @Transactional
	    public void startLot(String lotId, String processId) {
	    	log.info("startLot() ENTRY ");
	    	
	        jdbcTemplate.update("INSERT INTO LOT_PROCESS(lot_id, process_id,status) "
	        		             + "VALUES (?,?,'START')",
	                lotId, processId);
	        
	        log.info("startLot() EXIT ");
	    }
	    
	    
	    public boolean isLotInProcess(String lotId, String processId) {
	    
		       List<String> processIdList = jdbcTemplate.query(
		                "SELECT process_id "
		        		+ " FROM LOT_PROCESS WHERE lot_id = '" + lotId + "' "
		        		+ " AND status NOT IN ('COMPLETED') ",
		                (rs, rowNum) -> rs.getString("process_id")
		        		);

		        return processIdList.size()>0;		    	
	    }
	    
	    
	    public String getLotProcessId(String lotId) {
		    
		       List<String> processIdList = jdbcTemplate.query(
		                "SELECT process_id "
		        		+ " FROM LOT_PROCESS WHERE lot_id = '" + lotId + "' "
		        		+ " AND status NOT IN ('COMPLETED') ",
		                (rs, rowNum) -> rs.getString("process_id")
		        		);

		        return processIdList.get(0);		    	
	    }	    
	    

	    @Transactional
	    public void addProcessJob(String lotId, String processId, String status, String routeId, int seqNo, 
				String equipmentId, Date startTime, Date endTime) {

	        jdbcTemplate.update("INSERT INTO LOT_PROCESS_JOB(lot_id, process_id, status, route_id, seq_no, equipment_id,start_time, end_time) "
	        		             + "VALUES (?,?,?,?,?,?,?,?)",
	                lotId, processId, status, routeId, seqNo, equipmentId, startTime, endTime);

	    }
	    
	    @Transactional
	    public void updateProcessJob(String lotId, String processId, String status, String routeId, 
				String equipmentId, Date startTime, Date endTime) {

	        jdbcTemplate.update("UPDATE LOT_PROCESS_JOB SET status = ? , equipment_id=?, start_time = ?, end_time = ? "
	        		             + "WHERE lot_id = ? AND route_id = ?",
	        		             status, equipmentId, startTime, endTime, lotId, routeId);

	    }
	    
	    @Transactional
	    public void updateLotProcess(String lotId, String processId, String status) {

	        jdbcTemplate.update("UPDATE LOT_PROCESS SET status = ?  WHERE lot_id = ? AND process_id = ?",
	        		             status, lotId, processId);

	    }
	  	    
}
