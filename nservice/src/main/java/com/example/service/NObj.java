package com.example.service;

public class NObj {
   String id;
   String name;
   
    public NObj(long id, String name)
    {
    	this.id = "" + id;
    	this.name = name;
    }
   
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	   
   
	
}
