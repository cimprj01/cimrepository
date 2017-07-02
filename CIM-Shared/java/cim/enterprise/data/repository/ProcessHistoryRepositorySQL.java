package cim.enterprise.data.repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cim.enterprise.data.ProcessHistory;

@Repository
public class ProcessHistoryRepositorySQL {
	
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    
 // Find all customers, thanks Java 8, you can create a custom RowMapper like this :
    public List<ProcessHistory> findAll() {

        List<ProcessHistory> result = jdbcTemplate.query(
                "SELECT lot_id, tool_id, foup_id, action_code, claim_time FROM PROCESS_HISTORY",
                (rs, rowNum) -> new ProcessHistory(rs.getString("lot_id"),
                        rs.getString("tool_id"), rs.getString("foup_id"), rs.getString("action_code"), rs.getDate("claim_time")
                        )
        		);

        return result;

    }

	// Add History
    public void addHistory(String lotId, String toolId, String foupId, String actionCode, Date claimTime) {

        jdbcTemplate.update("INSERT INTO PROCESS_HISTORY(lot_id, tool_id, foup_id, action_code, claim_time) VALUES (?,?,?,?,?)",
                lotId, toolId, foupId, actionCode, claimTime);

    }
   

}
