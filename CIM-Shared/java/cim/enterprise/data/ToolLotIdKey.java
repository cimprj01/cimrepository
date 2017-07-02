package cim.enterprise.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ToolLotIdKey implements Serializable{

	@Column(name = "LOT_ID")
	private String lotId;
	
	@Column(name = "TOOL_ID")
	private String toolId;

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
	
	
	
}
