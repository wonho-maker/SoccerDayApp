package soccerday.media.ssu.ac.kr.soccerdayapp.schedule;

import java.io.Serializable;

/**
 * Created by Wonho Lee on 2015-05-02.
 */
public class MatchListData implements Serializable {

    public String time;
    public String league;

    public String homeTeamTitle;
    public String homeTeamEmblemURL;

    public String score;

    public String awayTeamTitle;
    public String awayTeamEmblemURL;

    public MatchState matchState;
    public static enum MatchState { BEFORE, ING, AFTER };

    public String place;

    public String linkURL;

    public String matchId;


    public String getLinkURL() {
        return linkURL;
    }

    public void setLinkURL(String linkURL) {
        this.linkURL = linkURL;

        matchId = linkURL.split("gameId=")[1];
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public String getHomeTeamTitle() {
        return homeTeamTitle;
    }

    public void setHomeTeamTitle(String homeTeamTitle) {
        this.homeTeamTitle = homeTeamTitle;
    }

    public String getHomeTeamEmblemURL() {
        return homeTeamEmblemURL;
    }

    public void setHomeTeamEmblemURL(String homeTeamEmblemURL) {
        this.homeTeamEmblemURL = homeTeamEmblemURL;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getAwayTeamTitle() {
        return awayTeamTitle;
    }

    public void setAwayTeamTitle(String awayTeamTitle) {
        this.awayTeamTitle = awayTeamTitle;
    }

    public String getAwayTeamEmblemURL() {
        return awayTeamEmblemURL;
    }

    public void setAwayTeamEmblemURL(String awayTeamEmblemURL) {
        this.awayTeamEmblemURL = awayTeamEmblemURL;
    }

    public MatchState getMatchState() {
        return matchState;
    }

    public void setMatchState(MatchState matchState) {
        this.matchState = matchState;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    @Override
    public String toString() {
        return "MatchListData{" +
                "time='" + time + '\'' +
                ", league='" + league + '\'' +
                ", homeTeamTitle='" + homeTeamTitle + '\'' +
                ", score='" + score + '\'' +
                ", awayTeamTitle='" + awayTeamTitle + '\'' +
                ", matchState=" + matchState +
                ", place='" + place + '\'' +
                ", linkURL='" + linkURL + '\'' +
                ", matchId='" + matchId + '\'' +
                '}';
    }
}
