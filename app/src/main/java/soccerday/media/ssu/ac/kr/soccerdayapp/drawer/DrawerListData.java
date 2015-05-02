package soccerday.media.ssu.ac.kr.soccerdayapp.drawer;

import com.bumptech.glide.DrawableTypeRequest;

import java.util.List;

/**
 * Created by Wonho Lee on 2015-04-30.
 */
public class DrawerListData {

    private String title;
    private int icon;
    private String iconURL;
    private int itemType;

    private boolean isHasDivider;

    private boolean isExpandList;
    private boolean isExpandListOpened;
    private List<DrawerListData> expandChildItemData;
    private boolean isFavorite;
    DrawableTypeRequest<String> loadedIconFromURL;

    private boolean hasIndicator;
    private int openIndicatorIcon;
    private int closeIndicatorIcon;


    public DrawerListData(String title, int icon) {
        this.title = title;
        this.icon = icon;

        this.loadedIconFromURL = null;
        this.isExpandList = false;

        this.hasIndicator = false;
        this.isHasDivider = false;
    }

    public DrawerListData(String title, int icon, int itemType) {
        this.title = title;
        this.icon = icon;

        this.loadedIconFromURL = null;
        this.isExpandList = false;

        this.hasIndicator = false;
        this.isHasDivider = false;

        this.itemType = itemType;
    }

    public DrawerListData(String title, String imageURL, int itemType) {
        this.title = title;
        this.icon = -1;
        this.iconURL = imageURL;
        this.loadedIconFromURL = null;

        this.isExpandList = false;
        this.hasIndicator = false;
        this.isHasDivider = false;

        this.itemType = itemType;
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

    public boolean isHasDivider() {
        return isHasDivider;
    }

    public void setHasDivider(boolean isHasDivider) {
        this.isHasDivider = isHasDivider;
    }

    public boolean isExpandList() {
        return isExpandList;
    }

    public void setExpandList(boolean isExpandList) {
        this.isExpandList = isExpandList;
    }

    public boolean isExpandListOpened() {
        return isExpandListOpened;
    }

    public void setExpandListOpened(boolean isExpandListOpened) {
        this.isExpandListOpened = isExpandListOpened;
    }

    public List<DrawerListData> getExpandChildItemData() {
        return expandChildItemData;
    }

    public void setExpandChildItemData(List<DrawerListData> expandChildItemData) {
        this.expandChildItemData = expandChildItemData;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
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

    public int getOpenIndicatorIcon() {
        return openIndicatorIcon;
    }

    public void setOpenIndicatorIcon(int openIndicatorIcon) {
        this.openIndicatorIcon = openIndicatorIcon;
    }

    public int getCloseIndicatorIcon() {
        return closeIndicatorIcon;
    }

    public void setCloseIndicatorIcon(int closeIndicatorIcon) {
        this.closeIndicatorIcon = closeIndicatorIcon;
    }
}
