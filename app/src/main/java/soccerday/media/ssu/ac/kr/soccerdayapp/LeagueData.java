package soccerday.media.ssu.ac.kr.soccerdayapp;

/**
 * Created by Wonho Lee on 2015-04-30.
 */
public class LeagueData {

    public static String leageEmblemURL(String league) {

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
}
