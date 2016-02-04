package com.katiamaeda.data;

import java.util.ArrayList;

public class MatchPlayer {
	private String playerRef;
	private String position;
	private String shirtNumber;
	private String status;
	private String subPosition;
	private String captain;
	
	private ArrayList<Stat> stats;

	public String getPlayerRef() {
		return playerRef;
	}

	public void setPlayerRef(String playerRef) {
		this.playerRef = playerRef;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getShirtNumber() {
		return shirtNumber;
	}

	public void setShirtNumber(String shirtNumber) {
		this.shirtNumber = shirtNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubPosition() {
		return subPosition;
	}

	public void setSubPosition(String subPosition) {
		this.subPosition = subPosition;
	}

	public ArrayList<Stat> getStats() {
		return stats;
	}

	public void setStats(ArrayList<Stat> stats) {
		this.stats = stats;
	}

	public String getCaptain() {
		return captain;
	}

	public void setCaptain(String captain) {
		this.captain = captain;
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
