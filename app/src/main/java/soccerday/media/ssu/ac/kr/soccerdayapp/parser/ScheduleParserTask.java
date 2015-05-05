package soccerday.media.ssu.ac.kr.soccerdayapp.parser;

import android.os.AsyncTask;
import android.util.Log;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import soccerday.media.ssu.ac.kr.soccerdayapp.LeagueData;
import soccerday.media.ssu.ac.kr.soccerdayapp.fragments.LeagueListData;
import soccerday.media.ssu.ac.kr.soccerdayapp.schedule.MatchListData;

/**
 * Created by wonho on 2015-05-03.
 */
public class ScheduleParserTask extends AsyncTask<Calendar, Integer, List<LeagueListData>> {



    @Override
    protected List<LeagueListData> doInBackground(Calendar... params) {

        Calendar date = params[0];

        String dateString = date.get(Calendar.YEAR) +"년 " + (date.get(Calendar.MONTH) + 1)+"월 "
                    + date.get(Calendar.DAY_OF_MONTH)+ "일";
        Log.i("task date", dateString);
        URL url = ParserData.getScheduleURL(dateString);

        Log.i("url", url.toString());
        Source source = null;

        try {
            URLConnection urlConnection = url.openConnection();
            urlConnection.setRequestProperty("User-Agent", "Mozilla");
            source = new Source(urlConnection);

        } catch (IOException e) {
            e.printStackTrace();
            Log.i("taskBackground", "1");
        }

        if (source != null) {

            Log.i("source2", source.getPreliminaryEncodingInfo());
            //Log.i("source3", source.getRenderer().toString());

        } else {
            Log.i("source", "null");
        }

        List<LeagueListData> leagueData = new ArrayList<>();

        //second table is shedule table.
        Element element = source.getAllElements(HTMLElementName.TABLE).get(1);

        List<Element> scheduleTable = element.getAllElements(HTMLElementName.TR);
        scheduleTable.remove(0);


        for (Element match : scheduleTable) {
            MatchListData schedule = new MatchListData();

            for (Element tableData : match.getAllElements(HTMLElementName.TD)) {

                //match time and league data    example)<td class=time> ... </td>
                if (tableData.getAttributeValue("class").equals("time")) {

                    //parse match time    example) <span class="bg_none">01:00</span>
                    schedule.time = tableData.getFirstElement("span").getTextExtractor().toString();

                    //parse league   example) <p title="프리메라리가">
                    schedule.league = tableData.getFirstElement("p").getAttributeValue("title");
                }

                //home team data example) <td class="l_team"> ... </td>
                if (tableData.getAttributeValue("class").equals("l_team")) {

                    // example)  <img src="image adress" width="36" height="36" alt="말라가 CF">
                    //team name - alt attribute value
                    schedule.homeTeamTitle = tableData.getFirstElement("img").getAttributeValue("alt");

                    //team emblem - img src
                    schedule.homeTeamEmblemURL = tableData.getFirstElement("img").getAttributeValue("src").split("src=")[1];
                }

            /*   <td class="score"> ... </td>
                 <td class="score"> VS </td>    - before start match
                 <td class="score"> 2:2 </td>   - after match
             */
                if (tableData.getAttributeValue("class").equals("score")) {
                    schedule.score = tableData.getTextExtractor().toString();
                }

                //away team data example) <td class="l_team"> ... </td>
                if (tableData.getAttributeValue("class").equals("r_team")) {

                    // example)  <img src="image adress" width="36" height="36" alt="레알 마드리드">
                    //team name - alt attribute value
                    schedule.awayTeamTitle = tableData.getFirstElement("img").getAttributeValue("alt");

                    //team emblem - img src
                    schedule.awayTeamEmblemURL = tableData.getFirstElement("img").getAttributeValue("src").split("src=")[1];
                }

                //place -  example)  <td class="place" title="La Rosaleda">La Rosaleda</td>
                if (tableData.getAttributeValue("class").equals("place")) {
                    schedule.place = tableData.getAttributeValue("title");
                }
            }

            //check league and add schedule
            boolean leagueExist = false;
            for (LeagueListData leagueGroup : leagueData) {
                if (leagueGroup.getTitle().equals(schedule.league)) {
                    leagueGroup.getExpandChildItemData().add(schedule);
                    leagueExist = true;
                }
            }

            //if league is not exist, make a new Group and add schedule
            if (!leagueExist) {
                // Log.e("le", schedule.league);
                LeagueListData newLeague = new LeagueListData(schedule.league, LeagueData.getLeageEmblemURL(schedule.league));
                newLeague.getExpandChildItemData().add(schedule);
                leagueData.add(newLeague);
            }
        }

        return leagueData;
    }


}
