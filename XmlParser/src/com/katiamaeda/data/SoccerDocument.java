package com.katiamaeda.data;

public class SoccerDocument {
	private String type;
	private String uID;
	private Competition competition;
	private MatchData matchData;
	private Team team1;
	private Team team2;
	private Venue venue;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getuID() {
		return uID;
	}
	public void setuID(String uID) {
		this.uID = uID;
	}
	public Competition getCompetition() {
		return competition;
	}
	public void setCompetition(Competition competition) {
		this.competition = competition;
	}
	public MatchData getMatchData() {
		return matchData;
	}
	public void setMatchData(MatchData matchData) {
		this.matchData = matchData;
	}
	public Team getTeam1() {
		return team1;
	}
	public void setTeam1(Team team1) {
		this.team1 = team1;
	}
	public Team getTeam2() {
		return team2;
	}
	public void setTeam2(Team team2) {
		this.team2 = team2;
	}
	public Venue getVenue() {
		return venue;
	}
	public void setVenue(Venue venue) {
		this.venue = venue;
	}
}
