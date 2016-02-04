package com.katiamaeda.data;

import java.util.ArrayList;

public class Team {
	private String uID;
	private String country;
	private String name;
	private ArrayList<Player> players;
	private TeamOfficial teamOfficial;
	
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
	public ArrayList<Player> getPlayers() {
		return players;
	}
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	public TeamOfficial getTeamOfficial() {
		return teamOfficial;
	}
	public void setTeamOfficial(TeamOfficial teamOfficial) {
		this.teamOfficial = teamOfficial;
	}
	
	public Player getPlayer(String uID) {
		Player founded = new Player();
		for (Player player : players) {
			if (player.getuID().equals(uID)) {
				founded = player;
				break;
			}
		}
		return founded;
	}
}
