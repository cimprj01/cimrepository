package cim.enterprise.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "TOOL_LOT")
public class ToolLot extends CimObject {
	
	@EmbeddedId
    private ToolLotIdKey key;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "CLAIM_TIME", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date claimTime;
	

	public ToolLotIdKey getKey() {
		return key;
	}

	public void setKey(ToolLotIdKey key) {
		this.key = key;
	}

	public String getLotId() {
		return getKey().getLotId();
	}

	public void setLotId(String lotId) {
		getKey().setLotId(lotId); 
	}

	public String getToolId() {
		return getKey().getToolId();
	}

	public void setToolId(String toolId) {
		getKey().setToolId(toolId);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getClaimTime() {
		return claimTime;
	}

	public void setClaimTime(Date claimTime) {
		this.claimTime = claimTime;
	}

	
	
}
