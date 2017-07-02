package cim.enterprise.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "TOOL")
public class Tool extends CimObject {

	@Column(name = "TOOL_ID")
	@Id
	private String toolId;
	
	@Column(name = "TOOL_NAME")
	private String toolName;

	@Column(name = "EQUIPMENT_FAMILY")
	private String equipmentFamily;
	
	@Column(name = "LOT_FAMILY")
	private String lotFamily;
	
	@Column(name = "MAX_CAPACITY")
	private int maxCapacity;
	
	@Column(name = "LOT_COUNT")
	private int lotCount;	
	
	@Column(name = "CLAIM_TIME", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date claimTime;

	public String getToolId() {
		return toolId;
	}

	public void setToolId(String toolId) {
		this.toolId = toolId;
	}

	public String getToolName() {
		return toolName;
	}

	public void setToolName(String toolName) {
		this.toolName = toolName;
	}

	public int getMaxCapacity() {
		return maxCapacity;
	}

	public void setMaxCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	public Date getClaimTime() {
		return claimTime;
	}

	public void setClaimTime(Date claimTime) {
		this.claimTime = claimTime;
	}

	public String getLotFamily() {
		return lotFamily;
	}

	public void setLotFamily(String lotFamily) {
		this.lotFamily = lotFamily;
	}

	public String getEquipmentFamily() {
		return equipmentFamily;
	}

	public void setEquipmentFamily(String equipmentFamily) {
		this.equipmentFamily = equipmentFamily;
	}

	public int getLotCount() {
		return lotCount;
	}

	public void setLotCount(int lotCount) {
		this.lotCount = lotCount;
	}
	
	
	
	
}
