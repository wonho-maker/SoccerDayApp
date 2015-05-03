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
}
