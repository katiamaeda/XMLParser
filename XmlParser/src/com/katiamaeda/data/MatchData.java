package com.katiamaeda.data;


public class MatchData {
	private MatchInfo matchInfo;
	private TeamData team1;
	private TeamData team2;
	
	public MatchInfo getMatchInfo() {
		return matchInfo;
	}
	public void setMatchInfo(MatchInfo matchInfo) {
		this.matchInfo = matchInfo;
	}
	public TeamData getTeam1() {
		return team1;
	}
	public void setTeam1(TeamData team1) {
		this.team1 = team1;
	}
	public TeamData getTeam2() {
		return team2;
	}
	public void setTeam2(TeamData team2) {
		this.team2 = team2;
	}
}
