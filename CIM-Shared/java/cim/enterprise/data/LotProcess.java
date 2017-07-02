package cim.enterprise.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "LOT_PROCESS")
public class LotProcess extends CimObject {
	
	@Column(name = "LOT_ID")
	@Id
	private String lotId;
	
	@Column(name = "PROCESS_ID")
	private String processId;

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
	
	

}
