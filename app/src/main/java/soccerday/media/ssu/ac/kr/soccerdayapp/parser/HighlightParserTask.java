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

import soccerday.media.ssu.ac.kr.soccerdayapp.fragments.LeagueListData;

/**
 * Created by Wonho Lee on 2015-05-13.
 */
public class HighlightParserTask extends AsyncTask<Calendar, Integer, List<String>> {

    List<String> hasHighLightTeamNameList;

    @Override
    protected List<String> doInBackground(Calendar... params) {

        Calendar date = params[0];

        String dateString = date.get(Calendar.YEAR) +"년 " + (date.get(Calendar.MONTH) + 1)+"월 "
                + date.get(Calendar.DAY_OF_MONTH)+ "일";

        //Log.i("task date", dateString);
        URL url = ParserData.getScheduleMobileURL(dateString);

        //Log.i("url", url.toString());
        Source source = null;

        try {
            URLConnection urlConnection = url.openConnection();
            source = new Source(urlConnection);

        } catch (IOException e) {
            e.printStackTrace();
            //Log.i("taskBackground", e.toString());
        }

        if (source != null) {

            //Log.i("source2", source.getPreliminaryEncodingInfo());
            //Log.i("source3", source.getRenderer().toString());

        } else {
            //Log.i("source", "null");
        }

        //Log.i("teamName1",source.toString());


            hasHighLightTeamNameList = new ArrayList<>();

            Element element = source.getAllElements(HTMLElementName.TABLE).get(0);
            //Log.i("teamName1",element.toString());
            List<Element> scheduleTable = element.getAllElements(HTMLElementName.TABLE);
            scheduleTable.remove(0);

            for(Element schedule : scheduleTable) {

                List<Element> aElement = schedule.getAllElements(HTMLElementName.A);
                Log.i("teamName3",schedule.toString());
                if(aElement.size() >= 3) {
                    String teamName = aElement.get(0).getTextExtractor().toString();

                    hasHighLightTeamNameList.add(teamName);
                }
            }


        Log.i("teamName",hasHighLightTeamNameList.toString());

        return hasHighLightTeamNameList;
    }
}
