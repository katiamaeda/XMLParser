package com.katiamaeda.data;

import java.util.ArrayList;

public class Competition {
	private String uID;
	private String country;
	private String name;
	private ArrayList<Stat> stats;
	
	public String getuID() {
		return uID;
	}
	public void setuID(String uID) {
		this.uID = uID;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Stat> getStats() {
		return stats;
	}
	public void setStats(ArrayList<Stat> stats) {
		this.stats = stats;
	}
	
	public String getStat(String name) {
		Stat founded = null;
		for (Stat stat : stats) {
			if (stat.getType().equals(name)) {
				founded = stat;
				break;
			}
		}
		if (founded == null) return "";
		return founded.getValue();
	}
}
