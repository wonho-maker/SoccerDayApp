package soccerday.media.ssu.ac.kr.soccerdayapp.parser;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by wonho on 2015-05-03.
 */
public class ParserData {

    public enum ParseType { SCHEDULE, NEWS, RANK };

    private static String naverSearchURL = "http://search.naver.com/search.naver?query=";

    private static String queryForSchedule = "해외축구일정";

    private static String scheduleDetailURL = "http://m.sports.naver.com/worldfootball/gamecenter/worldfootball/index.nhn?tab=cheer&gameId=";

    private static URL scheduleURL;

    public static String getNaverSearchURL() {
        return naverSearchURL;
    }

    public static String getQueryForSchedule() {
        return queryForSchedule;
    }

    public static URL getScheduleURL(String date) {
        try {
            scheduleURL = new URL(naverSearchURL +URLEncoder.encode(date + queryForSchedule, "UTF-8"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return scheduleURL;
    }

    public static String getScheduleDetailURL(String leagueTitle, String matchId) {

        String detailURL = scheduleDetailURL + matchId;

        String parameter = "&league=";

        if(leagueTitle.contains("세리에")) {
            detailURL += parameter + "seria";
        }
        else if(detailURL.contains("분데스")) {
            detailURL += parameter + "bundesliga";
        }
        else if(detailURL.contains("프리미어")) {
            detailURL += parameter + "epl";
        }
        else if(detailURL.contains("프리메라")) {
            detailURL += parameter + "primera";
        }
        else if(detailURL.contains("리그앙")) {
            detailURL += parameter + "ligue1";
        }
        else if(detailURL.contains("샹피오나")) {
            detailURL += parameter + "ligue1";
        }
        else if(detailURL.contains("에레디비지")) {
            detailURL += parameter + "eredivisie";
        }
        else if(detailURL.contains("챔피언스")) {
            detailURL += parameter + "champs";
        }
        else if(detailURL.contains("유로파")) {
            detailURL += parameter + "europa";
        }
        else if(detailURL.contains("잉글랜드")) {
            detailURL += parameter + "facup";
        }
        else {

        }

        return detailURL;
    }
}
