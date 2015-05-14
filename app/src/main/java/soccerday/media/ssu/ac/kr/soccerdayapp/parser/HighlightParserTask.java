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


import soccerday.media.ssu.ac.kr.soccerdayapp.schedule.HighlightList;

/**
 * Created by Wonho Lee on 2015-05-13.
 */
public class HighlightParserTask extends AsyncTask<Calendar, Integer, List<HighlightList>> {

    List<String> hasHighLightTeamNameList;
    List<HighlightList> highlightList;

    @Override
    protected List<HighlightList> doInBackground(Calendar... params) {

        Calendar date = params[0];

        String dateString = convertDate(date);


        Log.i("task date", dateString);
        URL url = ParserData.getScheduleAndResultMobileURL(dateString);

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

        highlightList = new ArrayList<>();
        hasHighLightTeamNameList = new ArrayList<>();

        Element element = source.getAllElements(HTMLElementName.SECTION).get(2);
        //Log.i("teamName1",element.toString());

        List<Element> scheduleElement = element.getChildElements();


        for(Element tElement : scheduleElement) {

            if(tElement.getAttributeValue("class").equals("h2_area")) {
                HighlightList hList = new HighlightList(tElement.getFirstElement("span").getTextExtractor().toString());
                highlightList.add(hList);
            }

        }

        int i = 0;
        for(Element tElement : scheduleElement) {

            if(tElement.getAttributeValue("class").equals("ct_wrp")) {

                List<Boolean> tempList = new ArrayList<>();

                for(Element hElement : tElement.getAllElements(HTMLElementName.LI)) {

                    List<Element> dElement = hElement.getAllElements(HTMLElementName.DIV);

                    if(dElement.get(dElement.size()-1).
                            getTextExtractor().toString().trim().equals("영상")) {
                        tempList.add(true);
                    } else {
                        tempList.add(false);
                    }
                }
                highlightList.get(i).setHasHighlight(tempList);
                i++;
            }
        }

        Log.i("hlist",highlightList.toString());

        return highlightList;
    }

    private String convertDate(Calendar date) {
        String year = ""+date.get(Calendar.YEAR);
        String month = ""+(date.get(Calendar.MONTH) + 1);
        String day = ""+date.get(Calendar.DAY_OF_MONTH);

        if(Integer.parseInt(month) < 10) {
            month = "0"+month;
        }
        if(Integer.parseInt(day) < 10) {
            day = "0"+day;
        }

        return (year+month+day);
    }


}
