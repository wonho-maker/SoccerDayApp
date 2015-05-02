package soccerday.media.ssu.ac.kr.soccerdayapp.fragments;

import com.bumptech.glide.DrawableTypeRequest;

import java.util.List;

import soccerday.media.ssu.ac.kr.soccerdayapp.schedule.MatchListData;

/**
 * Created by Wonho Lee on 2015-05-02.
 */
public class LeagueListData {

    private String title;
    private int icon;
    private String iconURL;
    private int itemType;

    private List<MatchListData> expandChildItemData;
    private boolean isExpandListOpened;
    DrawableTypeRequest<String> loadedIconFromURL;

    private boolean hasIndicator;
    private int openIndicatorIcon;
    private int closeIndicatorIcon;

    public LeagueListData(String title, String imageURL) {
        this.title = title;
        this.icon = -1;
        this.iconURL = imageURL;
        this.loadedIconFromURL = null;

        this.hasIndicator = true;

    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getIconURL() {
        return iconURL;
    }

    public void setIconURL(String iconURL) {
        this.iconURL = iconURL;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public List<MatchListData> getExpandChildItemData() {
        return expandChildItemData;
    }

    public void setExpandChildItemData(List<MatchListData> expandChildItemData) {
        this.expandChildItemData = expandChildItemData;
    }

    public DrawableTypeRequest<String> getLoadedIconFromURL() {
        return loadedIconFromURL;
    }

    public void setLoadedIconFromURL(DrawableTypeRequest<String> loadedIconFromURL) {
        this.loadedIconFromURL = loadedIconFromURL;
    }

    public boolean hasIndicator() {
        return hasIndicator;
    }

    public void setHasIndicator(boolean hasIndicator) {
        this.hasIndicator = hasIndicator;
    }

    public boolean isExpandListOpened() {
        return isExpandListOpened;
    }

    public void setExpandListOpened(boolean isExpandListOpened) {
        this.isExpandListOpened = isExpandListOpened;
    }
}
