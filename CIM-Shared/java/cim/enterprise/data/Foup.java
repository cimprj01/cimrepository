package cim.enterprise.data;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "FOUP")
public class Foup extends CimObject {
	
	@Column(name = "FOUP_ID")
	@Id
	private String foupId;
	
	@Column(name = "FOUP_NAME")
	private String foupName;
	
	@Column(name = "MAX_CAPACITY")
	private int maxCapacity;
	
	@Column(name = "LOT_COUNT")
	private int lotCount;
		
	@Column(name = "CLAIM_TIME", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date claimTime;

	public String getFoupId() {
		return foupId;
	}

	public void setFoupId(String foupId) {
		this.foupId = foupId;
	}

	public String getFoupName() {
		return foupName;
	}

	public void setFoupName(String foupName) {
		this.foupName = foupName;
	}

	public int getMaxCapacity() {
		return maxCapacity;
	}

	public void setMaxCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	public int getLotCount() {
		return lotCount;
	}

	public void setLotCount(int lotCount) {
		this.lotCount = lotCount;
	}

	public Date getClaimTime() {
		return claimTime;
	}

	public void setClaimTime(Date claimTime) {
		this.claimTime = claimTime;
	}
	
}