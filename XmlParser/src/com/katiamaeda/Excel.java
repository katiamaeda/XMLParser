package com.katiamaeda;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.katiamaeda.data.Competition;
import com.katiamaeda.data.MatchData;
import com.katiamaeda.data.MatchInfo;
import com.katiamaeda.data.MatchPlayer;
import com.katiamaeda.data.Player;
import com.katiamaeda.data.SoccerDocument;
import com.katiamaeda.data.SoccerFeed;
import com.katiamaeda.data.Stat;
import com.katiamaeda.data.Team;
import com.katiamaeda.data.TeamData;
import com.katiamaeda.data.TeamOfficial;
import com.katiamaeda.data.Venue;

public class Excel {
	private String inputFile;
	private ArrayList<SoccerFeed> feed;
	private Sheet excelSheet;
	private SXSSFWorkbook workbook;
	private Map<String, Integer> statsDictionary;
	private int rowNumber;
	private String returnMessage;

	public void setOutputFile(String inputFile, ArrayList<SoccerFeed> feed) throws Exception {
		this.inputFile = inputFile;
		this.feed = feed;
		
		statsDictionary = createStatsDictionary();
	}

	public String write() throws Exception {
		workbook = new SXSSFWorkbook();
		excelSheet = workbook.createSheet("Sample sheet");

		rowNumber = 1;
		returnMessage = "";
//		createFile1();
		createFile2();

		try {
            FileOutputStream out = new FileOutputStream(new File(inputFile));
            workbook.write(out);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return returnMessage;
	}
	
	private void createFile1()  throws Exception {
		createLabel();

		for (SoccerFeed soccerFeed : feed) {
			createContent(soccerFeed);
		}
	}
	
	private void createFile2() throws Exception {
		createTitle2();

		for (SoccerFeed soccerFeed : feed) {
			createContent2(soccerFeed);
		}
	}

	private void createLabel() throws Exception {
		// Write a few headers
		Row row0 = excelSheet.createRow(0);

		int column = 0;
		addLabel(column++, row0, "matchid");
		addLabel(column++, row0, "compid");
		addLabel(column++, row0, "country");
		addLabel(column++, row0, "league");
		addLabel(column++, row0, "season");
		addLabel(column++, row0, "matchday");
		addLabel(column++, row0, "matchtype");
		addLabel(column++, row0, "period");
		addLabel(column++, row0, "wheather");
		addLabel(column++, row0, "attendance");
		addLabel(column++, row0, "date");
		addLabel(column++, row0, "winner");
		addLabel(column++, row0, "officialid");
		addLabel(column++, row0, "official_first");
		addLabel(column++, row0, "official_last");

		for (int i = 0; i < 3; i++) {
			addLabel(column++, row0, "assist1_first");
			addLabel(column++, row0, "assist1_last");
			addLabel(column++, row0, "assist1_type");
			addLabel(column++, row0, "assist1id");
		}
		
		addLabel(column++, row0, "match_time");
		addLabel(column++, row0, "second_half_time");
		addLabel(column++, row0, "first_half_time");
		addLabel(column++, row0, "score");
		addLabel(column++, row0, "side");
		addLabel(column++, row0, "teamid");
		addLabel(column++, row0, "playerid");
		addLabel(column++, row0, "position");
		addLabel(column++, row0, "shirtnumber");
		addLabel(column++, row0, "status");
		addLabel(column++, row0, "subposition");
		addLabel(column++, row0, "captain");
		
		for (int i = 0; i < statsDictionary.keySet().size(); i++) {
			addLabel(column++, row0, getKeyByValue(new Integer(i)));
		}

		addLabel(column++, row0, "team");
		addLabel(column++, row0, "player_first");
		addLabel(column++, row0, "player_last");
		addLabel(column++, row0, "stadiumid");
		addLabel(column++, row0, "stadium");
	}
	
	private void createTitle2() throws Exception {
		// Write a few headers
		Row row0 = excelSheet.createRow(0);

		int column = 0;
		addLabel(column++, row0, "matchid");
		addLabel(column++, row0, "compid");
		addLabel(column++, row0, "country");
		addLabel(column++, row0, "league");
		addLabel(column++, row0, "season");
		addLabel(column++, row0, "matchday");
		addLabel(column++, row0, "matchtype");
		addLabel(column++, row0, "period");
		addLabel(column++, row0, "wheather");
		addLabel(column++, row0, "attendance");
		addLabel(column++, row0, "date");
		addLabel(column++, row0, "winner");
		addLabel(column++, row0, "officialid");
		addLabel(column++, row0, "official_first");
		addLabel(column++, row0, "official_last");

		for (int i = 0; i < 3; i++) {
			addLabel(column++, row0, "assist1_first");
			addLabel(column++, row0, "assist1_last");
			addLabel(column++, row0, "assist1_type");
			addLabel(column++, row0, "assist1id");
		}
		
		addLabel(column++, row0, "match_time");
		addLabel(column++, row0, "second_half_time");
		addLabel(column++, row0, "first_half_time");
		addLabel(column++, row0, "score");
		addLabel(column++, row0, "side");
		addLabel(column++, row0, "teamid");

		addLabel(column++, row0, "team");
		addLabel(column++, row0, "stadiumid");
		addLabel(column++, row0, "stadium");
		
		for (int i = 0; i < statsDictionary.keySet().size(); i++) {
			addLabel(column++, row0, getKeyByValue(new Integer(i)));
		}
	}
	
	public String getKeyByValue(Integer value) throws Exception {
	    for (Entry<String, Integer> entry : statsDictionary.entrySet()) {
	        if (entry.getValue().equals(value)) {
	            return entry.getKey();
	        }
	    }
	    return "";
	}

	private void createContent(SoccerFeed soccerFeed) throws Exception {
		SoccerDocument soccerDocument = soccerFeed.getSoccerDocument();
		MatchData matchData = soccerDocument.getMatchData();
		TeamData teamData1 = matchData.getTeam1();
		TeamData teamData2 = matchData.getTeam2();
		Team team1 = soccerDocument.getTeam1();
		Team team2 = soccerDocument.getTeam2();

		addInfo(soccerFeed, teamData1, team1);
		addInfo(soccerFeed, teamData2, team2);
	}
	
	private void createContent2(SoccerFeed soccerFeed) throws Exception {
		SoccerDocument soccerDocument = soccerFeed.getSoccerDocument();
		MatchData matchData = soccerDocument.getMatchData();
		TeamData teamData1 = matchData.getTeam1();
		TeamData teamData2 = matchData.getTeam2();
		Team team1 = soccerDocument.getTeam1();
		Team team2 = soccerDocument.getTeam2();

		addInfo2(soccerFeed, teamData1, team1);
		addInfo2(soccerFeed, teamData2, team2);
	}
	
	private void addInfo(SoccerFeed soccerFeed, TeamData teamData1, Team team1) throws Exception {
		SoccerDocument soccerDocument = soccerFeed.getSoccerDocument();
		Competition competition = soccerDocument.getCompetition();
		MatchData matchData = soccerDocument.getMatchData();
		MatchInfo matchInfo = matchData.getMatchInfo();
		TeamOfficial matchOfficial = matchInfo.getMatchOfficial();
		Venue venue = soccerDocument.getVenue();

		for (MatchPlayer player : teamData1.getPlayerLineUp()) {
			int column = 0;
			Row row = excelSheet.createRow(rowNumber);
			
			addLabel(column++, row, soccerDocument.getuID());
			addLabel(column++, row, competition.getuID());
			addLabel(column++, row, competition.getCountry());
			addLabel(column++, row, competition.getName());
			addLabel(column++, row, competition.getStat("season_id"));
			addLabel(column++, row, competition.getStat("matchday"));
			
			addLabel(column++, row, matchInfo.getType());
			addLabel(column++, row, matchInfo.getPeriod());
			addLabel(column++, row, matchInfo.getWeather());
			addLabel(column++, row, matchInfo.getAttendance());
			addLabel(column++, row, matchInfo.getTimeStamp());
			addLabel(column++, row, matchInfo.getWinner());
			if (matchOfficial != null) {
				addLabel(column++, row, matchOfficial.getuID());
				addLabel(column++, row, matchOfficial.getFirstName());
				addLabel(column++, row, matchOfficial.getLastName());
			} else {
				column += 3;
			}

			if (matchInfo.getAssistantOfficials() != null) {
				for (int i = 0; i < 3; i++) {
					TeamOfficial official = null;
					if (i < matchInfo.getAssistantOfficials().size()) {
						official = matchInfo.getAssistantOfficials().get(i);
					}
							
					if (official != null) {
						addLabel(column++, row, official.getFirstName());
						addLabel(column++, row, official.getLastName());
						addLabel(column++, row, official.getType());
						addLabel(column++, row, official.getuID());
					} else {
						column += 4;
					}
				}
			} else {
				column += 12;
			}

			addLabel(column++, row, matchInfo.getStat("match_time"));
			addLabel(column++, row, matchInfo.getStat("second_half_time"));
			addLabel(column++, row, matchInfo.getStat("first_half_time"));
			
			addLabel(column++, row, teamData1.getScore());
			addLabel(column++, row, teamData1.getSide());
			addLabel(column++, row, teamData1.getTeamRef());
			
			addLabel(column++, row, player.getPlayerRef());
			addLabel(column++, row, player.getPosition());
			addLabel(column++, row, player.getShirtNumber());
			addLabel(column++, row, player.getStatus());
			addLabel(column++, row, player.getSubPosition());
			addLabel(column++, row, player.getCaptain());
			
			ArrayList<Stat> playerStats = player.getStats();
			for (Stat stat : playerStats) {
				
				try {
					Integer columnIndex = column + statsDictionary.get(stat.getType());
					addLabel(columnIndex, row, stat.getValue());
				} catch (Exception e) {
//					e.printStackTrace();
					if (returnMessage.equals("")) {
						returnMessage = "New stats found:";
					}
					
					returnMessage += stat.getType() + ", ";
				}
			}
			column += statsDictionary.size();
			
			addLabel(column++, row, team1.getName());
			Player playerInfo = team1.getPlayer(player.getPlayerRef());
			addLabel(column++, row, playerInfo.getFirstName());
			addLabel(column++, row, playerInfo.getLastName());
			
			addLabel(column++, row, venue.getuID());
			addLabel(column++, row, venue.getName());
			
			rowNumber++;
		}
	}
	
	private void addInfo2(SoccerFeed soccerFeed, TeamData teamData1, Team team1) throws Exception {
		SoccerDocument soccerDocument = soccerFeed.getSoccerDocument();
		Competition competition = soccerDocument.getCompetition();
		MatchData matchData = soccerDocument.getMatchData();
		MatchInfo matchInfo = matchData.getMatchInfo();
		TeamOfficial matchOfficial = matchInfo.getMatchOfficial();
		Venue venue = soccerDocument.getVenue();

		int column = 0;
		Row row = excelSheet.createRow(rowNumber);
		
		addLabel(column++, row, soccerDocument.getuID());
		addLabel(column++, row, competition.getuID());
		addLabel(column++, row, competition.getCountry());
		addLabel(column++, row, competition.getName());
		addLabel(column++, row, competition.getStat("season_id"));
		addLabel(column++, row, competition.getStat("matchday"));
		
		addLabel(column++, row, matchInfo.getType());
		addLabel(column++, row, matchInfo.getPeriod());
		addLabel(column++, row, matchInfo.getWeather());
		addLabel(column++, row, matchInfo.getAttendance());
		addLabel(column++, row, matchInfo.getTimeStamp());
		addLabel(column++, row, matchInfo.getWinner());
		if (matchOfficial != null) {
			addLabel(column++, row, matchOfficial.getuID());
			addLabel(column++, row, matchOfficial.getFirstName());
			addLabel(column++, row, matchOfficial.getLastName());
		} else {
			column += 3;
		}

		if (matchInfo.getAssistantOfficials() != null) {
			ArrayList<TeamOfficial> officials = matchInfo.getAssistantOfficials();
			for (int i = 0; i < 3; i++) {
				TeamOfficial official = null;
				if (i < officials.size()) {
					official = officials.get(i);
				}

				if (official != null) {
					addLabel(column++, row, official.getFirstName());
					addLabel(column++, row, official.getLastName());
					addLabel(column++, row, official.getType());
					addLabel(column++, row, official.getuID());
				} else {
					column += 4;
				}
			}
		} else {
			column += 12;
		}

		addLabel(column++, row, matchInfo.getStat("match_time"));
		addLabel(column++, row, matchInfo.getStat("second_half_time"));
		addLabel(column++, row, matchInfo.getStat("first_half_time"));
		
		addLabel(column++, row, teamData1.getScore());
		addLabel(column++, row, teamData1.getSide());
		addLabel(column++, row, teamData1.getTeamRef());
		
		addLabel(column++, row, team1.getName());
		addLabel(column++, row, venue.getuID());
		addLabel(column++, row, venue.getName());
		
		ArrayList<Stat> stats = teamData1.getStats();
		for (Stat stat : stats) {
			try {
				Integer columnIndex = column + statsDictionary.get(stat.getType());
				addLabel(columnIndex, row, stat.getValue());
			} catch (Exception e) {
				e.printStackTrace();
				if (returnMessage.equals("")) {
					returnMessage = "New stats found:";
				}
				
				returnMessage += stat.getType() + ", ";
			}
		}
		column += statsDictionary.size();
		
		rowNumber++;
	}

	private void addLabel(int column, Row row, String s) throws Exception {
		
		Cell cell = row.createCell(column);
		cell.setCellValue(s);
	}
	
	private Map<String, Integer> createStatsDictionary() throws Exception {
		Map<String, Integer> statsDictionary = new HashMap<String, Integer>();
		int index = 0;
		statsDictionary.put("accurate_back_zone_pass", index++);
		statsDictionary.put("accurate_corners_intobox", index++);
		statsDictionary.put("accurate_cross", index++);
		statsDictionary.put("accurate_cross_nocorner", index++);
		statsDictionary.put("accurate_fwd_zone_pass", index++);
		statsDictionary.put("accurate_goal_kicks", index++);
		statsDictionary.put("accurate_keeper_throws", index++);
		statsDictionary.put("accurate_launches", index++);
		statsDictionary.put("accurate_layoffs", index++);
		statsDictionary.put("accurate_long_balls", index++);
		statsDictionary.put("accurate_pass", index++);
		statsDictionary.put("accurate_through_ball", index++);
		statsDictionary.put("accurate_throws", index++);
		statsDictionary.put("aerial_lost", index++);
		statsDictionary.put("aerial_won", index++);
		statsDictionary.put("att_bx_centre", index++);
		statsDictionary.put("att_obx_centre", index++);
		statsDictionary.put("att_corner", index++);
		statsDictionary.put("att_fastbreak", index++);
		statsDictionary.put("att_freekick_goal", index++);
		statsDictionary.put("att_freekick_target", index++);
		statsDictionary.put("att_freekick_total", index++);
		statsDictionary.put("att_goal_high_centre", index++);
		statsDictionary.put("att_goal_high_left", index++);
		statsDictionary.put("att_goal_high_right", index++);
		statsDictionary.put("att_goal_low_centre", index++);
		statsDictionary.put("att_goal_low_left", index++);
		statsDictionary.put("att_goal_low_right", index++);
		statsDictionary.put("att_hd_goal", index++);
		statsDictionary.put("att_hd_miss", index++);
		statsDictionary.put("att_hd_post", index++);
		statsDictionary.put("att_hd_target", index++);
		statsDictionary.put("att_hd_total", index++);
		statsDictionary.put("att_ibox_blocked", index++);
		statsDictionary.put("att_ibox_goal", index++);
		statsDictionary.put("att_ibox_miss", index++);
		statsDictionary.put("att_ibox_post", index++);
		statsDictionary.put("att_ibox_target", index++);
		statsDictionary.put("att_ibox_own_goal", index++);
		statsDictionary.put("att_obox_own_goal", index++);
		statsDictionary.put("att_lf_goal", index++);
		statsDictionary.put("att_lf_target", index++);
		statsDictionary.put("att_lf_total", index++);
		statsDictionary.put("att_miss_high", index++);
		statsDictionary.put("att_miss_high_left", index++);
		statsDictionary.put("att_miss_high_right", index++);
		statsDictionary.put("att_miss_left", index++);
		statsDictionary.put("att_miss_right", index++);
		statsDictionary.put("att_obox_blocked", index++);
		statsDictionary.put("att_obox_goal", index++);
		statsDictionary.put("att_obox_miss", index++);
		statsDictionary.put("att_obox_post", index++);
		statsDictionary.put("att_obox_target", index++);
		statsDictionary.put("att_obp_goal", index++);
		statsDictionary.put("att_one_on_one", index++);
		statsDictionary.put("att_openplay", index++);
		statsDictionary.put("att_pen_goal", index++);
		statsDictionary.put("att_pen_miss", index++);
		statsDictionary.put("att_pen_post", index++);
		statsDictionary.put("att_pen_target", index++);
		statsDictionary.put("att_post_high", index++);
		statsDictionary.put("att_post_left", index++);
		statsDictionary.put("att_post_right", index++);
		statsDictionary.put("att_rf_goal", index++);
		statsDictionary.put("att_rf_target", index++);
		statsDictionary.put("att_rf_total", index++);
		statsDictionary.put("att_setpiece", index++);
		statsDictionary.put("att_sv_high_centre", index++);
		statsDictionary.put("att_sv_high_left", index++);
		statsDictionary.put("att_sv_high_right", index++);
		statsDictionary.put("att_sv_low_centre", index++);
		statsDictionary.put("att_sv_low_left", index++);
		statsDictionary.put("att_sv_low_right", index++);
		statsDictionary.put("attempts_conceded_ibox", index++);
		statsDictionary.put("attempts_conceded_obox", index++);
		statsDictionary.put("back_pass", index++);
		statsDictionary.put("ball_recovery", index++);
		statsDictionary.put("blocked_scoring_att", index++);
		statsDictionary.put("challenge_lost", index++);
		statsDictionary.put("clearance_off_line", index++);
		statsDictionary.put("contentious_decision", index++);
		statsDictionary.put("corner_taken", index++);
		statsDictionary.put("cross_not_claimed", index++);
		statsDictionary.put("crosses_18yard", index++);
		statsDictionary.put("crosses_18yardplus", index++);
		statsDictionary.put("dangerous_play", index++);
		statsDictionary.put("defender_goals", index++);
		statsDictionary.put("dive_catch", index++);
		statsDictionary.put("dive_save", index++);
		statsDictionary.put("duel_lost", index++);
		statsDictionary.put("duel_won", index++);
		statsDictionary.put("effective_clearance", index++);
		statsDictionary.put("effective_head_clearance", index++);
		statsDictionary.put("error_lead_to_goal", index++);
		statsDictionary.put("error_lead_to_shot", index++);
		statsDictionary.put("final_third_entries", index++);
		statsDictionary.put("fk_foul_won", index++);
		statsDictionary.put("fk_foul_lost", index++);
		statsDictionary.put("forward_goals", index++);
		statsDictionary.put("fouled_final_third", index++);
		statsDictionary.put("game_started", index++);
		statsDictionary.put("gk_smother", index++);
		statsDictionary.put("goal_assist", index++);
		statsDictionary.put("goal_assist_intentional", index++);
		statsDictionary.put("goal_kicks", index++);
		statsDictionary.put("goals", index++);
		statsDictionary.put("goals_conceded", index++);
		statsDictionary.put("goals_conceded_ibox", index++);
		statsDictionary.put("goals_conceded_obox", index++);
		statsDictionary.put("good_high_claim", index++);
		statsDictionary.put("goals_openplay", index++);
		statsDictionary.put("good_one_on_one", index++);
		statsDictionary.put("hand_ball", index++);
		statsDictionary.put("head_clearance", index++);
		statsDictionary.put("head_pass", index++);
		statsDictionary.put("interception", index++);
		statsDictionary.put("interceptions_in_box", index++);
		statsDictionary.put("keeper_pick_up", index++);
		statsDictionary.put("keeper_throws", index++);
		statsDictionary.put("last_man_contest", index++);
		statsDictionary.put("last_man_tackle", index++);
		statsDictionary.put("long_pass_own_to_opp", index++);
		statsDictionary.put("long_pass_own_to_opp_success", index++);
		statsDictionary.put("lost_corners", index++);
		statsDictionary.put("mins_played", index++);
		statsDictionary.put("midfielder_goals", index++);
		statsDictionary.put("offside_provoked", index++);
		statsDictionary.put("offtarget_att_assist", index++);
		statsDictionary.put("ontarget_att_assist", index++);
		statsDictionary.put("ontarget_scoring_att", index++);
		statsDictionary.put("outfielder_block", index++);
		statsDictionary.put("own_goals", index++);
		statsDictionary.put("passes_left", index++);
		statsDictionary.put("passes_right", index++);
		statsDictionary.put("pen_goals_conceded", index++);
		statsDictionary.put("penalty_conceded", index++);
		statsDictionary.put("penalty_save", index++);
		statsDictionary.put("penalty_won", index++);
		statsDictionary.put("possession_percentage", index++);
		statsDictionary.put("post_scoring_att", index++);
		statsDictionary.put("punches", index++);
		statsDictionary.put("red_card", index++);
		statsDictionary.put("saved_ibox", index++);
		statsDictionary.put("saved_obox", index++);
		statsDictionary.put("saved_setpiece", index++);
		statsDictionary.put("saves", index++);
		statsDictionary.put("second_yellow", index++);
		statsDictionary.put("second_goal_assist", index++);
		statsDictionary.put("shot_off_target", index++);
		statsDictionary.put("six_second_violation", index++);
		statsDictionary.put("six_yard_block", index++);
		statsDictionary.put("stand_catch", index++);
		statsDictionary.put("stand_save", index++);
		statsDictionary.put("subs_made", index++);
		statsDictionary.put("total_att_assist", index++);
		statsDictionary.put("total_attacking_pass", index++);
		statsDictionary.put("total_back_zone_pass", index++);
		statsDictionary.put("total_clearance", index++);
		statsDictionary.put("total_contest", index++);
		statsDictionary.put("total_corners_intobox", index++);
		statsDictionary.put("total_cross", index++);
		statsDictionary.put("total_cross_nocorner", index++);
		statsDictionary.put("total_fastbreak", index++);
		statsDictionary.put("total_fwd_zone_pass", index++);
		statsDictionary.put("total_high_claim", index++);
		statsDictionary.put("total_launches", index++);
		statsDictionary.put("total_layoffs", index++);
		statsDictionary.put("total_long_balls", index++);
		statsDictionary.put("total_offside", index++);
		statsDictionary.put("total_one_on_one", index++);
		statsDictionary.put("total_pass", index++);
		statsDictionary.put("total_scoring_att", index++);
		statsDictionary.put("total_sub_off", index++);
		statsDictionary.put("total_sub_on", index++);
		statsDictionary.put("total_tackle", index++);
		statsDictionary.put("total_through_ball", index++);
		statsDictionary.put("total_throws", index++);
		statsDictionary.put("total_yel_card", index++);
		statsDictionary.put("total_red_card", index++);
		statsDictionary.put("was_fouled", index++);
		statsDictionary.put("won_contest", index++);
		statsDictionary.put("won_corners", index++);
		statsDictionary.put("won_tackle", index++);
		statsDictionary.put("yellow_card", index++);
		statsDictionary.put("total_flick_on", index++);
		statsDictionary.put("accurate_flick_on", index++);
		statsDictionary.put("total_chipped_pass", index++);
		statsDictionary.put("accurate_chipped_pass", index++);
		statsDictionary.put("blocked_cross", index++);
		statsDictionary.put("shield_ball_oop", index++);
		statsDictionary.put("foul_throw_in", index++);
		statsDictionary.put("effective_blocked_cross", index++);
		statsDictionary.put("penalty_faced", index++);
		statsDictionary.put("total_pull_back", index++);
		statsDictionary.put("accurate_pull_back", index++);
		statsDictionary.put("total_keeper_sweeper", index++);
		statsDictionary.put("accurate_keeper_sweeper", index++);
		statsDictionary.put("goal_assist_openplay", index++);
		statsDictionary.put("goal_assist_setplay", index++);
		statsDictionary.put("att_assist_openplay", index++);
		statsDictionary.put("att_assist_setplay", index++);
		statsDictionary.put("overrun", index++);
		statsDictionary.put("interception_won", index++);
		statsDictionary.put("big_chance_created", index++);
		statsDictionary.put("big_chance_missed", index++);
		statsDictionary.put("big_chance_scored", index++);
		statsDictionary.put("unsuccessful_touch", index++);
		statsDictionary.put("fwd_pass", index++);
		statsDictionary.put("backward_pass", index++);
		statsDictionary.put("leftside_pass", index++);
		statsDictionary.put("rightside_pass", index++);
		statsDictionary.put("successful_final_third_passes", index++);
		statsDictionary.put("total_final_third_passes", index++);
		statsDictionary.put("rescinded_red_card", index++);
		statsDictionary.put("diving_save", index++);
		statsDictionary.put("poss_won_def_3rd", index++);
		statsDictionary.put("poss_won_mid_3rd", index++);
		statsDictionary.put("poss_won_att_3rd", index++);
		statsDictionary.put("poss_lost_all", index++);
		statsDictionary.put("poss_lost_ctrl", index++);
		statsDictionary.put("goal_fastbreak", index++);
		statsDictionary.put("shot_fastbreak", index++);
		statsDictionary.put("pen_area_entries", index++);
		statsDictionary.put("final_third_entry", index++);
		statsDictionary.put("hit_woodwork", index++);
		statsDictionary.put("goal_assist_deadball", index++);
		statsDictionary.put("freekick_cross", index++);
		statsDictionary.put("accurate_freekick_cross", index++);
		statsDictionary.put("open_play_pass", index++);
		statsDictionary.put("successful_open_play_pass", index++);
		statsDictionary.put("attempted_tackle_foul", index++);
		statsDictionary.put("fifty_fifty", index++);
		statsDictionary.put("successful_fifty_fifty", index++);
		statsDictionary.put("blocked_pass", index++);
		statsDictionary.put("failed_to_block", index++);
		statsDictionary.put("put_through", index++);
		statsDictionary.put("successful_put_through", index++);
		statsDictionary.put("assist_pass_lost", index++);
		statsDictionary.put("assist_blocked_shot", index++);
		statsDictionary.put("assist_attempt_saved", index++);
		statsDictionary.put("assist_post", index++);
		statsDictionary.put("assist_free_kick_won", index++);
		statsDictionary.put("assist_handball_won", index++);
		statsDictionary.put("assist_own_goal", index++);
		statsDictionary.put("shots_conc_onfield", index++);
		statsDictionary.put("goals_conc_onfield", index++);
		
		statsDictionary.put("touches", index++);
		statsDictionary.put("formation_place", index++);
		statsDictionary.put("turnover", index++);
		statsDictionary.put("fouls", index++);
		statsDictionary.put("dispossessed", index++);
		statsDictionary.put("att_cmiss_high", index++);
		statsDictionary.put("att_bx_right", index++);
		statsDictionary.put("att_cmiss_left", index++);
		statsDictionary.put("att_bx_left", index++);
		statsDictionary.put("clean_sheet", index++);
		statsDictionary.put("att_cmiss_right", index++);
		statsDictionary.put("att_cmiss_high_left", index++);
		statsDictionary.put("att_obx_left", index++);
		statsDictionary.put("att_lg_centre", index++);

		statsDictionary.put("first_half_goals", index++);
		statsDictionary.put("formation_used", index++);
		statsDictionary.put("own_goal_accrued", index++);
		
		return statsDictionary;
	}
}
