package cim.enterprise.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "PROCESS_ROUTE")
public class ProcessRoute extends CimObject {
	
	@Column(name = "ROUTE_ID")
	@Id
	private String routeId;
	
	@Column(name = "ROUTE_NAME")
	private String routeName;
	
	@Column(name = "EQUIPMENT_FAMILY")
	private String equipmentFamily;
	
	@Column(name = "PROCESS_ID")
	private String processId;	
	
	@Column(name = "RECIPE")
	private String recipe;
	
	@Column(name = "SEQ_NO")
	private int seqNo;

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public String getEquipmentFamily() {
		return equipmentFamily;
	}

	public void setEquipmentFamily(String equipmentFamily) {
		this.equipmentFamily = equipmentFamily;
	}

	public String getRecipe() {
		return recipe;
	}

	public void setRecipe(String recipe) {
		this.recipe = recipe;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public int getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}

	
}
