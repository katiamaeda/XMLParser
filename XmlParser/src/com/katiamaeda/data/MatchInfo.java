package com.katiamaeda.data;

import java.util.ArrayList;

public class MatchInfo {
	private String type;
	private String period;
	private String timeStamp;
	private String weather;
	private String attendance;
	private String winner;
	
	private TeamOfficial matchOfficial;
	private ArrayList<TeamOfficial> assistantOfficials;
	
	private ArrayList<Stat> stats;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getAttendance() {
		return attendance;
	}

	public void setAttendance(String attendance) {
		this.attendance = attendance;
	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}

	public TeamOfficial getMatchOfficial() {
		return matchOfficial;
	}

	public void setMatchOfficial(TeamOfficial matchOfficial) {
		this.matchOfficial = matchOfficial;
	}

	public ArrayList<TeamOfficial> getAssistantOfficials() {
		return assistantOfficials;
	}

	public void setAssistantOfficials(ArrayList<TeamOfficial> assistantOfficials) {
		this.assistantOfficials = assistantOfficials;
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
