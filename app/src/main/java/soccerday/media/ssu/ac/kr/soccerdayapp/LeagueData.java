package soccerday.media.ssu.ac.kr.soccerdayapp;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Wonho Lee on 2015-04-30.
 */
public class LeagueData {

    public static String getLeageEmblemURL(String league) {

        if(league.contains("세리에")) {
            return SerieA_URL;
        }
        else if(league.contains("분데스")) {
            return Bundesliga_URL;
        }
        else if(league.contains("프리미어")) {
            return PremierLeague_URL;
        }
        else if(league.contains("프리메라")) {
            return LaLiga_URL;
        }
        else if(league.contains("리그앙")) {
            return France_Ligue1_URL;
        }
        else if(league.contains("샹피오나")) {
            return France_Ligue1_URL;
        }
        else if(league.contains("에레디비지")) {
            return Eredivisie_URL;
        }
        else if(league.contains("챔피언스")) {
            return ChampionsLeague_URL;
        }
        else if(league.contains("유로파")) {
            return EuropaLeague_URL;
        }
        else {
            return "no matched Leage URL";
        }

    }

    public static final String SerieA_URL = "http://i632.photobucket.com/albums/uu49/kremmen/serie-a-logo-336x336.png";

    public static final String Bundesliga_URL = "http://upload.wikimedia.org/wikipedia/de/thumb/c/ce/Bundesliga-Logo-2010-SVG.svg/200px-Bundesliga-Logo-2010-SVG.svg.png";

    public static final String PremierLeague_URL = "http://img2.wikia.nocookie.net/__cb20100529000224/inciclopedia/images/7/72/Premier-league-logo.png";

    public static final String LaLiga_URL = "http://upload.wikimedia.org/wikipedia/fr/2/23/Logo_La_Liga.png";

    public static final String France_Ligue1_URL = "http://upload.wikimedia.org/wikipedia/en/thumb/9/9b/Logo_de_la_Ligue_1_(2008).svg/364px-Logo_de_la_Ligue_1_(2008).svg.png";

    public static final String Eredivisie_URL = "http://vignette1.wikia.nocookie.net/the-football-database/images/5/5a/Eredivisie_Logo.png";

    public static final String ChampionsLeague_URL = "http://vignette3.wikia.nocookie.net/the-football-database/images/f/f4/UEFA_Champions_League_Logo.png";

    public static final String EuropaLeague_URL ="http://upload.wikimedia.org/wikipedia/it/archive/5/52/20090717151009!UEFA_Europa_League_logo.png";

    public static final int LEAGUE_TITLE = 0;
    public static final int TEAM_TITLE = 1;

    public static String checkTitle(String title, int flag) {
        if(flag == LEAGUE_TITLE) {
            if(title.contains("샹피오나")) {
                return "리그 앙";
            }

        }
        else if(flag == TEAM_TITLE) {
            title = checkAlphabetAndNumberWord(title);

            if(title.contains("셀타 데")) {
                return "셀타 비고";
            }
            else if(title.contains("아인트라흐")) {
                return "프랑크푸르트";
            }
            else if(title.contains("묀헨글라드")) {
                return "묀헨글라드바흐";
            }
            else if(title.contains("바이에른")) {
                return "바이에른 뮌헨";
            }

        }

        return title;

    }

    public static String checkAlphabetAndNumberWord(String title){
        String regPice = "\\^[a-zA-Z0-9]*$";
        String subsPice= "\\?";

        String samplePattern = "";


        title = title.replaceAll("[a-zA-Z0-9]", "");
        title = title.trim();
        //Log.i("title", title);
        //Pattern.compile(regPice).matcher(this).

       /* String[] sampleWords=new String[]{"변?태","변***태","경기도 변()태님", "변///\\태짓을"};

        Pattern ptrn = Pattern.compile(checkRegex);
        for(String word: sampleWords){
            Matcher matcher = ptrn.matcher(word);
            boolean check = matcher.find();
            if (check){
                System.out.println(word+" check:"+check+", 체크 단어:"+matcher.group());
            }
        }*/

        return title;
    }

}
