package com.katiamaeda.data;

import java.util.ArrayList;

public class TeamData {
	private String score;
	private String side;
	private String teamRef;
	
	private ArrayList<Event> events;
	private ArrayList<MatchPlayer> playerLineUp;
	
	private ArrayList<Stat> stats;

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public String getTeamRef() {
		return teamRef;
	}

	public void setTeamRef(String teamRef) {
		this.teamRef = teamRef;
	}

	public ArrayList<Event> getEvents() {
		return events;
	}

	public void setEvents(ArrayList<Event> events) {
		this.events = events;
	}

	public ArrayList<MatchPlayer> getPlayerLineUp() {
		return playerLineUp;
	}

	public void setPlayerLineUp(ArrayList<MatchPlayer> playerLineUp) {
		this.playerLineUp = playerLineUp;
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
