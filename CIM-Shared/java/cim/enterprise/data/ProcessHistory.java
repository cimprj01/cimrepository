package cim.enterprise.data;

import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "PROCESS_HISTORY")
public class ProcessHistory extends CimObject {
	
	@Column(name="ID")
	@Id
	@GeneratedValue
	private int id;
	
	@Column(name = "LOT_ID")
	private String lotId;
	
	@Column(name = "TOOL_ID")
	private String toolId;
	
	@Column(name = "FOUP_ID")
	private String foupId;
	
	@Column(name = "ROUTE_ID")
	private String routeId;
	
	@Column(name = "PROCESS_ID")
	private String processId;	
	
	@Column(name = "ACTION_CODE")
	private String actionCode;	

	@Column(name = "CLAIM_TIME")
	@Temporal(TemporalType.TIMESTAMP)	
	private Date claimTime;
	
	public ProcessHistory(String lotId, String toolId, String foupId, String actionCode, Date claimTime)
	{
		this.lotId = lotId;
		this.toolId = toolId;
		this.foupId = foupId;
		this.actionCode = actionCode;
		this.claimTime = claimTime;
		
	}

	public ProcessHistory() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLotId() {
		return lotId;
	}

	public void setLotId(String lotId) {
		this.lotId = lotId;
	}

	public String getToolId() {
		return toolId;
	}

	public void setToolId(String toolId) {
		this.toolId = toolId;
	}

	public String getFoupId() {
		return foupId;
	}

	public void setFoupId(String foupId) {
		this.foupId = foupId;
	}

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	public Date getClaimTime() {
		return claimTime;
	}

	public void setClaimTime(Date claimTime) {
		this.claimTime = claimTime;
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}
	
	
}
