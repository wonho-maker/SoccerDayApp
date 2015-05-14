package soccerday.media.ssu.ac.kr.soccerdayapp.schedule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wonho Lee on 2015-05-14.
 */
public class HighlightList {

    String title;
    List<Boolean> hasHighlight;

    public HighlightList(String title) {
        this.title = title;

        hasHighlight = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Boolean> getHasHighlight() {
        return hasHighlight;
    }

    public void setHasHighlight(List<Boolean> hasHighlight) {
        this.hasHighlight = hasHighlight;
    }

    @Override
    public String toString() {
        return "HighlightList{" +
                "title='" + title + '\'' +
                ", hasHighlight=" + hasHighlight +
                '}';
    }
}
