package cim.enterprise.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity(name = "LOT_PROCESS_JOB")
public class LotProcessJob extends CimObject {

	@Column(name = "LOT_ID")
	@Id
	private String lotId;
	
	@Column(name = "PROCESS_ID")
	private String processId;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "ROUTE_ID")
	private String routeId;
	
	@Column(name = "SEQ_NO")
	private int seqNo;	
	
	@Column(name = "EQUIPMENT_ID")
	private String equipmentId;
	
	
	@Column(name = "START_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startTime;	
	
	@Column(name = "END_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endTime;
	
	@Transient
	private String processName;

	
	public LotProcessJob(String lotId, String processId, String status, String routeId, int seqNo, 
			String equipmentId, Date startTime, Date endTime, String processName)
	{
		this.lotId = lotId;
		this.processId = processId;
		this.status = status;
		this.routeId = routeId;
		this.seqNo = seqNo;
		this.equipmentId = equipmentId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.processName = processName;
		
	}
	
	public String getLotId() {
		return lotId;
	}

	public void setLotId(String lotId) {
		this.lotId = lotId;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public String getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public int getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}	

	
	
}
