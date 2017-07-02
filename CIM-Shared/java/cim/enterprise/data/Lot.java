package cim.enterprise.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity(name = "LOT")
public class Lot extends CimObject {
	
	@Column(name = "LOT_ID")
	@Id
	private String lotId;
	
	@Column(name = "LOT_NAME")
	private String lotName;
	
	@Column(name = "LOT_FAMILY")
	private String lotFamily;
	
	@Column(name = "FOUP_ID")
	private String foupId;

	@Column(name = "WAFER_COUNT")
	private int waferCount;	
	
	@Column(name = "CLAIM_TIME", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date claimTime;
	
	
	@Transient
	private LotProcessJob lotJobProcess;
	

	public String getLotId() {
		return lotId;
	}

	public void setLotId(String lotId) {
		this.lotId = lotId;
	}

	public String getLotName() {
		return lotName;
	}

	public void setLotName(String lotName) {
		this.lotName = lotName;
	}

	public String getLotFamily() {
		return lotFamily;
	}

	public void setLotFamily(String lotFamily) {
		this.lotFamily = lotFamily;
	}

	public String getFoupId() {
		return foupId;
	}

	public void setFoupId(String foupId) {
		this.foupId = foupId;
	}

	public int getWaferCount() {
		return waferCount;
	}

	public void setWaferCount(int waferCount) {
		this.waferCount = waferCount;
	}

	public Date getClaimTime() {
		return claimTime;
	}

	public void setClaimTime(Date claimTime) {
		this.claimTime = claimTime;
	}

	public LotProcessJob getLotJobProcess() {
		return lotJobProcess;
	}

	public void setLotJobProcess(LotProcessJob lotJobProcess) {
		this.lotJobProcess = lotJobProcess;
	}
	
	
	
	

}
