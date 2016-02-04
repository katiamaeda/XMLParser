package com.katiamaeda;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.katiamaeda.data.Competition;
import com.katiamaeda.data.Event;
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

public class Parser {
	public static final String SOCCER_FEED = "SoccerFeed";
	public static final String TIME_STAMP = "TimeStamp";
	public static final String SOCCER_DOCUMENT = "SoccerDocument";
	public static final String TYPE = "Type";
	public static final String UID = "uID";
	public static final String COMPETITION = "Competition";
	public static final String COUNTRY = "Country";
	public static final String NAME = "Name";
	public static final String STAT = "Stat";
	public static final String MATCH_DATA = "MatchData";
	public static final String MATCH_INFO = "MatchInfo";
	public static final String MATCH_TYPE = "MatchType";
	public static final String PERIOD = "Period";
	public static final String WEATHER = "Weather";
	public static final String ATTENDANCE = "Attendance";
	public static final String RESULT = "Result";
	public static final String WINNER = "Winner";
	public static final String MATCH_OFFICIAL = "MatchOfficial";
	public static final String OFFICIAL_NAME = "OfficialName";
	public static final String OFFICIAL_REF = "OfficialRef";
	public static final String ASSISTANT_OFFICIALS = "AssistantOfficials";
	public static final String ASSISTANT_OFFICIAL = "AssistantOfficial";
	public static final String FIRST_NAME = "FirstName";
	public static final String LAST_NAME = "LastName";
	public static final String TEAM_DATA = "TeamData";
	public static final String SCORE = "Score";
	public static final String SIDE = "Side";
	public static final String TEAM_REF = "TeamRef";
	public static final String SUBSTITUTION = "Substitution";
	public static final String BOOKING = "Booking";
	public static final String GOAL = "Goal";
	public static final String MISSED_PENALTY = "MissedPenalty";
	public static final String CARD = "Card";
	public static final String CARD_TYPE = "CardType";
	public static final String EVENT_ID = "EventID";
	public static final String EVENT_NUMBER = "EventNumber";
	public static final String PLAYER_REF = "PlayerRef";
	public static final String REASON = "Reason";
	public static final String TIME = "Time";
	public static final String SUB_OFF = "SubOff";
	public static final String SUB_ON = "SubOn";
	public static final String SUBSTITUTE_POSITION = "SubstitutePosition";
	public static final String PLAYER_LINEUP = "PlayerLineUp";
	public static final String MATCH_PLAYER = "MatchPlayer";
	public static final String POSITION = "Position";
	public static final String SHIRT_NUMBER = "ShirtNumber";
	public static final String STATUS = "Status";
	public static final String CAPTAIN = "Captain";
	public static final String SUB_POSITION = "SubPosition";
	public static final String FH = "FH";
	public static final String SH = "SH";
	public static final String TEAM = "Team";
	public static final String PLAYER = "Player";
	public static final String PERSON_NAME = "PersonName";
	public static final String FIRST = "First";
	public static final String LAST = "Last";
	public static final String KNOWN = "Known";
	public static final String TEAM_OFFICIAL = "TeamOfficial";
	public static final String VENUE = "Venue";

	private Document dom;
	private String fileFolder;
	
	private String message;
	
	public String parseXmlFile(File[] files, String outputFile) {
		//get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		message = "File created";
		try {
			ArrayList<SoccerFeed> feed = new ArrayList<SoccerFeed>();
			for (File file : files) {
				message += "File:" + file.getName() + "\n";
				System.out.println("File:" + file.getName());
				
				DocumentBuilder db = dbf.newDocumentBuilder();
				try {
					dom = db.parse(file);
				} catch(SAXException se) {
					se.printStackTrace();
					message += se.toString() + "\n";
				}
				
				Element soccerFeedElement = dom.getDocumentElement();
				SoccerFeed soccerFeed = getSoccerFeed(soccerFeedElement);
				
				feed.add(soccerFeed);
				
				fileFolder = file.getParent();
				
				System.gc();
			}

			Excel test = new Excel();
			
			if (outputFile == null || outputFile.equals("")) {
				outputFile = "OutputFile.xls";
			}
			if (!outputFile.endsWith(".xls")) {
				outputFile = outputFile + ".xls";
			}
			
		    test.setOutputFile(fileFolder+"/"+outputFile, feed);
		    message += test.write();

		} catch(ParserConfigurationException pce) {
			pce.printStackTrace();
			message += pce.toString();
			return message;
		} catch(SAXException se) {
			se.printStackTrace();
			message += se.toString();
			return message;
		} catch(IOException ioe) {
			ioe.printStackTrace();
			message += ioe.toString();
			return message;
		} catch(Exception ex) {
			ex.printStackTrace();
			message += ex.toString();
			return message;
		}
		
		return message + "\nFile created";
	}
	
	private String getTagValue(Element element, String tagName) throws Exception {
		String textVal = null;
		ArrayList<Element> list = getElementsByTagName(element, tagName);
		if(list != null && list.size() > 0) {
			Element el = list.get(0);
			textVal = el.getFirstChild().getNodeValue();
		}

		return textVal;
	}
	
	private String getAttributeValue(Element element, String attributeName) {
		NamedNodeMap nodes = element.getAttributes();
		if (nodes == null) {
			return null;
		}
		
		Node attribute = nodes.getNamedItem(attributeName);
		if (attribute == null) {
			return null;
		}

		return attribute.getNodeValue();
	}
	
	private SoccerFeed getSoccerFeed(Element soccerFeedEl) throws Exception {
		
		SoccerDocument soccerDocument = new SoccerDocument();
		ArrayList<Element> soccerDocumentList = getElementsByTagName(soccerFeedEl, SOCCER_DOCUMENT);
		if (soccerDocumentList.size() > 0) {
			Element soccerDocumentElement = soccerDocumentList.get(0);
			soccerDocument.setType(getAttributeValue(soccerDocumentElement, TYPE));
			soccerDocument.setuID(getAttributeValue(soccerDocumentElement, UID));
			
			Competition competition = new Competition();
			ArrayList<Element> competitionList = getElementsByTagName(soccerDocumentElement, COMPETITION);
			if (competitionList.size() <= 0) {
				getError(COMPETITION);
			} else {
				Element competitionElement = competitionList.get(0);
				competition.setuID(getAttributeValue(competitionElement, UID));
				competition.setCountry(getTagValue(competitionElement, COUNTRY));
				competition.setName(getTagValue(competitionElement, NAME));
				competition.setStats(getStats(competitionElement));
				soccerDocument.setCompetition(competition);
			}

			ArrayList<Element> matchDataList = getElementsByTagName(soccerDocumentElement, MATCH_DATA);
			if (matchDataList.size() <= 0) {
				getError(MATCH_DATA);
			} else {
				Element matchDataElement = matchDataList.get(0);
				MatchData matchData = new MatchData();
				
				MatchInfo matchInfo = new MatchInfo();
				ArrayList<Element> matchInfoList = getElementsByTagName(matchDataElement, MATCH_INFO);
				if (matchInfoList.size() <= 0) {
					getError(MATCH_INFO);
				} else {
					Element matchInfoElement = matchInfoList.get(0);
					matchInfo.setType(getAttributeValue(matchInfoElement, MATCH_TYPE));
					matchInfo.setPeriod(getAttributeValue(matchInfoElement, PERIOD));
					matchInfo.setTimeStamp(getAttributeValue(matchInfoElement, TIME_STAMP));
					matchInfo.setWeather(getAttributeValue(matchInfoElement, WEATHER));
					matchInfo.setAttendance(getTagValue(matchInfoElement, ATTENDANCE));
					
					ArrayList<Element> resultList = getElementsByTagName(matchInfoElement, RESULT);
					if (resultList.size() <= 0) {
						getError(RESULT);
					} else {
						Element resultElement = resultList.get(0);
						matchInfo.setWinner(getAttributeValue(resultElement, WINNER));
					}
				}
				
				TeamOfficial teamOfficial = new TeamOfficial();
				ArrayList<Element> matchOfficialList = getElementsByTagName(matchDataElement, MATCH_OFFICIAL);
				if (matchOfficialList.size() <= 0) {
					getError(MATCH_OFFICIAL);
				} else {
					Element matchOfficialElement = matchOfficialList.get(0);
					teamOfficial.setuID(getAttributeValue(matchOfficialElement, UID));
					ArrayList<Element> officialNameList = getElementsByTagName(matchOfficialElement, OFFICIAL_NAME);
					Element officialNameElement = officialNameList.get(0);
					teamOfficial.setFirstName(getTagValue(officialNameElement, FIRST));
					teamOfficial.setLastName(getTagValue(officialNameElement, LAST));
					matchInfo.setMatchOfficial(teamOfficial);
				}
				
				ArrayList<Element> assistantOfficialsList = getElementsByTagName(matchDataElement, ASSISTANT_OFFICIALS);
				if (assistantOfficialsList.size() <= 0) {
					getError(ASSISTANT_OFFICIALS);
				} else {
					Element assistantOfficialsElement = assistantOfficialsList.get(0);
					matchInfo.setAssistantOfficials(getOfficials(assistantOfficialsElement));
				}
				
				matchInfo.setStats(getStats(matchDataElement));
				matchData.setMatchInfo(matchInfo);
				
				ArrayList<Element> teamDataList = getElementsByTagName(matchDataElement, TEAM_DATA);
				Element teamDataElement1 = teamDataList.get(0);
				matchData.setTeam1(getTeamData(teamDataElement1));
				Element teamDataElement2 = teamDataList.get(1);
				matchData.setTeam2(getTeamData(teamDataElement2));
				soccerDocument.setMatchData(matchData);
			}

			ArrayList<Element> teamList = getElementsByTagName(soccerDocumentElement, TEAM);
			Element teamElement1 = teamList.get(0);
			soccerDocument.setTeam1(getTeam(teamElement1));
			Element teamElement2 = teamList.get(1);
			soccerDocument.setTeam2(getTeam(teamElement2));
			
			ArrayList<Element> venueList = getElementsByTagName(soccerDocumentElement, VENUE);
			if (venueList.size() <= 0) {
				getError(VENUE);
			} else {
				Element venueElement = venueList.get(0);
				Venue venue = new Venue();
				venue.setuID(getAttributeValue(venueElement, UID));
				venue.setCountry(getTagValue(venueElement, COUNTRY));
				venue.setName(getTagValue(venueElement, NAME));
				soccerDocument.setVenue(venue);
			}
		} else {
			getError(SOCCER_DOCUMENT);
		}

		SoccerFeed soccerFeed = new SoccerFeed();
		soccerFeed.setTimeStamp(getAttributeValue(soccerFeedEl, TIME_STAMP));
		soccerFeed.setSoccerDocument(soccerDocument);
		
		return soccerFeed;
	}
	
	private void getError(String field) {
		message += "Missing " + field + "\n";
	}
	
	private ArrayList<Stat> getStats(Element element) throws Exception {
		ArrayList<Stat> stats = new ArrayList<Stat>();

		ArrayList<Element> statList = getElementsByTagName(element, STAT);
		if (statList.size() <= 0) {
			getError(STAT);
		} else {
			for(Element statElement : statList) {
				Stat stat = new Stat();
				stat.setValue(statElement.getFirstChild().getNodeValue());
				stat.setType(getAttributeValue(statElement, TYPE));
				stat.setFH(getAttributeValue(statElement, FH));
				stat.setSH(getAttributeValue(statElement, SH));
				
				stats.add(stat);
			}
		}

		return stats;
	}
	
	private ArrayList<TeamOfficial> getOfficials(Element element) throws Exception {
		ArrayList<TeamOfficial> array = new ArrayList<TeamOfficial>();

		ArrayList<Element> list = getElementsByTagName(element, ASSISTANT_OFFICIAL);
		if (list.size() <= 0) {
			getError(ASSISTANT_OFFICIAL);
		} else {
			for(Element innerElement : list) {
				TeamOfficial teamOfficial = new TeamOfficial();
				teamOfficial.setFirstName(getAttributeValue(innerElement, FIRST_NAME));
				teamOfficial.setLastName(getAttributeValue(innerElement, LAST_NAME));
				teamOfficial.setType(getAttributeValue(innerElement, TYPE));
				teamOfficial.setuID(getAttributeValue(innerElement, UID));
				
				array.add(teamOfficial);
			}
		}

		return array;
	}
	
	private TeamData getTeamData(Element element) throws Exception {
		TeamData teamData = new TeamData();
		teamData.setScore(getAttributeValue(element, SCORE));
		teamData.setSide(getAttributeValue(element, SIDE));
		teamData.setTeamRef(getAttributeValue(element, TEAM_REF));
		
		ArrayList<Event> events = new ArrayList<Event>();
		ArrayList<Element> bookingList = getElementsByTagName(element, BOOKING);
		getEvents(bookingList, events, BOOKING);
		ArrayList<Element> goalList = getElementsByTagName(element, GOAL);
		getEvents(goalList, events, GOAL);
		ArrayList<Element> missedPenaltyList = getElementsByTagName(element, MISSED_PENALTY);
		getEvents(missedPenaltyList, events, MISSED_PENALTY);
		ArrayList<Element> substitutionList = getElementsByTagName(element, SUBSTITUTION);
		getEvents(substitutionList, events, SUBSTITUTION);
		teamData.setEvents(events);
		
		NodeList playerLineUpList = element.getElementsByTagName(PLAYER_LINEUP);
		Element playerLineUpElement = (Element)playerLineUpList.item(0);
		teamData.setPlayerLineUp(getMatchPlayers(playerLineUpElement));
		
		teamData.setStats(getStats(element));

		return teamData;
	}
	
	private void getEvents(ArrayList<Element> list, ArrayList<Event> events, String eventType) throws Exception {
		if(list != null && list.size() > 0) {
			for(Element innerElement : list) {
				Event event = new Event();
				event.setEventType(eventType);
				event.setCard(getAttributeValue(innerElement, CARD));
				event.setCardType(getAttributeValue(innerElement, CARD_TYPE));
				event.setEventID(getAttributeValue(innerElement, EVENT_ID));
				event.setEventNumber(getAttributeValue(innerElement, EVENT_NUMBER));
				event.setPeriod(getAttributeValue(innerElement, PERIOD));
				event.setPlayerRef(getAttributeValue(innerElement, PLAYER_REF));
				event.setReason(getAttributeValue(innerElement, REASON));
				event.setTime(getAttributeValue(innerElement, TIME));
				event.setTimeStamp(getAttributeValue(innerElement, TIME_STAMP));
				event.setType(getAttributeValue(innerElement, TYPE));
				event.setuID(getAttributeValue(innerElement, UID));
				event.setSubOff(getAttributeValue(innerElement, SUB_OFF));
				event.setSubOn(getAttributeValue(innerElement, SUB_ON));
				event.setSubstitutePosition(getAttributeValue(innerElement, SUBSTITUTE_POSITION));
				events.add(event);
			}
		}
	}
	
	private ArrayList<MatchPlayer> getMatchPlayers(Element element) throws Exception {
		ArrayList<MatchPlayer> array = new ArrayList<MatchPlayer>();

		ArrayList<Element> list = getElementsByTagName(element, MATCH_PLAYER);
		if (list.size() <= 0) {
			getError(MATCH_PLAYER);
		} else {
			for(Element innerElement : list) {
				MatchPlayer matchPlayer = new MatchPlayer();
				matchPlayer.setPlayerRef(getAttributeValue(innerElement, PLAYER_REF));
				matchPlayer.setPosition(getAttributeValue(innerElement, POSITION));
				matchPlayer.setShirtNumber(getAttributeValue(innerElement, SHIRT_NUMBER));
				matchPlayer.setStatus(getAttributeValue(innerElement, STATUS));
				matchPlayer.setSubPosition(getAttributeValue(innerElement, SUB_POSITION));
				matchPlayer.setCaptain(getAttributeValue(innerElement, CAPTAIN));
				
				matchPlayer.setStats(getStats(innerElement));
				
				array.add(matchPlayer);
			}
		}

		return array;
	}
	
	private Team getTeam(Element element) throws Exception {
		Team team = new Team();
		team.setuID(getAttributeValue(element, UID));
		team.setCountry(getTagValue(element, COUNTRY));
		team.setName(getTagValue(element, NAME));
		
		team.setPlayers(getPlayers(element));
		
		ArrayList<Element> teamOfficialList = getElementsByTagName(element, TEAM_OFFICIAL);
		if (teamOfficialList.size() <= 0) {
			getError(TEAM_OFFICIAL);
		} else {
			Element teamOfficialElement = teamOfficialList.get(0);
			TeamOfficial teamOfficial = new TeamOfficial();
			teamOfficial.setType(getAttributeValue(teamOfficialElement, TYPE));
			teamOfficial.setuID(getAttributeValue(teamOfficialElement, UID));
			
			ArrayList<Element> personNameList = getElementsByTagName(teamOfficialElement, PERSON_NAME);
			if (personNameList.size() <= 0) {
				getError(PERSON_NAME);
			} else {
				Element personNameElement = personNameList.get(0);
				teamOfficial.setFirstName(getTagValue(personNameElement, FIRST));
				teamOfficial.setLastName(getTagValue(personNameElement, LAST));
				team.setTeamOfficial(teamOfficial);
			}
		}

		return team;
	}
	
	private ArrayList<Player> getPlayers(Element element) throws Exception {
		ArrayList<Player> array = new ArrayList<Player>();

		ArrayList<Element> list = getElementsByTagName(element, PLAYER);
		if (list.size() <= 0) {
			getError(PLAYER);
		} else {
			for(Element innerElement : list) {
				Player player = new Player();
				player.setPosition(getAttributeValue(innerElement, POSITION));
				player.setuID(getAttributeValue(innerElement, UID));
				
				ArrayList<Element> personNameList = getElementsByTagName(innerElement, PERSON_NAME);
				if (personNameList.size() <= 0) {
					getError(PERSON_NAME);
				} else {
					Element personNameElement = personNameList.get(0);
					player.setFirstName(getTagValue(personNameElement, FIRST));
					player.setLastName(getTagValue(personNameElement, LAST));
					player.setKnown(getTagValue(personNameElement, KNOWN));
				}
				
				array.add(player);
			}
		}

		return array;
	}
	
	private ArrayList<Element> getElementsByTagName(Element element, String tagName) throws Exception {
		ArrayList<Element> list = new ArrayList<Element>();
		
		NodeList children = element.getElementsByTagName(tagName);
		for(int i = 0; i < children.getLength(); i++) {
		   Element childElement = (Element)children.item(i);
		   if(childElement.getParentNode().isSameNode(element)) {
			   list.add(childElement);
		    }
		}
		
		return list;
	}
}
