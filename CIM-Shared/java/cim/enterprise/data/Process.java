package cim.enterprise.data;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity(name = "PROCESS")
public class Process extends CimObject {
	
	@Column(name = "PROCESS_ID")
	@Id
	private String processId;
	
	@Column(name = "PROCESS_NAME")
	private String processName;

	@Column(name = "SEQ_NO")
	private int seqNo;
	
	@Transient
	private List<ProcessRoute> routes;

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public int getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}
	
    public List<ProcessRoute> getProcessRoutes() {
        return routes;
    }

    public void setProcessRoutes(List<ProcessRoute> routes) {
        this.routes = routes;
    }	
	
}
