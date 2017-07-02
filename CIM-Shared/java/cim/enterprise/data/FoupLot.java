package cim.enterprise.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "FOUP_LOT")
public class FoupLot extends CimObject {

	@Column(name = "LOT_ID")
	private String lotId;
	
	@Column(name = "FOUP_ID")
	@Id
	private String foupId;
	
	@Column(name = "CLAIM_TIME", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date claimTime;

	public String getLotId() {
		return lotId;
	}

	public void setLotId(String lotId) {
		this.lotId = lotId;
	}

	public String getFoupId() {
		return foupId;
	}

	public void setFoupId(String foupId) {
		this.foupId = foupId;
	}

	public Date getClaimTime() {
		return claimTime;
	}

	public void setClaimTime(Date claimTime) {
		this.claimTime = claimTime;
	}
	
	
}
