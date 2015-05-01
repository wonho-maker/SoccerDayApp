package soccerday.media.ssu.ac.kr.soccerdayapp.drawer;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;

import java.util.List;

import soccerday.media.ssu.ac.kr.soccerdayapp.R;

/**
 * Created by Wonho Lee on 2015-04-30.
 */
public class DrawerMenuAdapter extends RecyclerView.Adapter<DrawerMenuAdapter.ViewHolder> {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_ITEM = 1;
    public static final int TYPE_ITEM_EXPAND_GROUP = 2;
    public static final int TYPE_ITEM_EXPAND_ITEM = 3;
    private final List<DrawerListData> drawerListData;
    private final int icon_Indicator_open;
    private final int icon_Indicator_close;
    private final int icon_favorite_empty;
    private final int icon_favorite_fill;
    private final Context context;

    public int settingIndex = 5;

    public DrawerItemClickListener drawerItemClickListener;

    public DrawerMenuAdapter(List<DrawerListData> drawerListData, int openIndicatorIcon, int closeIndicatorIcon,
                             int icon_favorite_empty, int icon_favorite_fill, Context applicationContext) {

        this.drawerListData = drawerListData;

        this.icon_Indicator_open = openIndicatorIcon;
        this.icon_Indicator_close = closeIndicatorIcon;

        this.icon_favorite_empty = icon_favorite_empty;
        this.icon_favorite_fill = icon_favorite_fill;

        this.context = applicationContext;

    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        int itemType;

        //basic item
        TextView titleTextView;
        ImageView iconImageView;
        View itemDividerView;

        //for expand item
        ImageView expandIndicatorImageView;
        ImageView expandItemFavoriteImageView;

        MaterialRippleLayout testLayout;
        //LinearLayout testLayoutForExpandChild;

        public ViewHolder(View itemView, int ViewType) {
            super(itemView);

            if(ViewType == TYPE_HEADER) {
                testLayout = (MaterialRippleLayout) itemView.findViewById(R.id.ripple);
                itemType = TYPE_HEADER;
            }
            else { //TYPE_HEADER
                if(ViewType == TYPE_ITEM) {
                    testLayout = (MaterialRippleLayout) itemView.findViewById(R.id.ripple);

                    titleTextView = (TextView) itemView.findViewById(R.id.drawer_menu_rowText);
                    iconImageView = (ImageView) itemView.findViewById(R.id.drawer_menu_rowIcon);

                    itemDividerView = (View) itemView.findViewById(R.id.drawer_menu_item_divider);
                    itemType = TYPE_ITEM;
                }
                else if(ViewType == TYPE_ITEM_EXPAND_GROUP){ //expand List group
                    testLayout = (MaterialRippleLayout) itemView.findViewById(R.id.ripple);
                    titleTextView = (TextView) itemView.findViewById(R.id.drawer_menu_expandable_groupText);
                    iconImageView = (ImageView) itemView.findViewById(R.id.drawer_menu_expandable_groupIcon);

                    expandIndicatorImageView = (ImageView) itemView.findViewById(R.id.drawer_menu_expandable_groupExpandIcon);
                    itemDividerView = (View) itemView.findViewById(R.id.drawer_menu_expandable_item_divider);

                    itemType = TYPE_ITEM_EXPAND_GROUP;
                }
                else { //expand List child item
                    testLayout = (MaterialRippleLayout) itemView.findViewById(R.id.ripple);

                    titleTextView = (TextView) itemView.findViewById(R.id.drawer_menu_expandable_childText);
                    iconImageView = (ImageView) itemView.findViewById(R.id.drawer_menu_expandable_childIcon);

                    expandItemFavoriteImageView = (ImageView) itemView.findViewById(R.id.drawer_menu_expandable_childFavorite);
                    itemDividerView = (View) itemView.findViewById(R.id.drawer_menu_expandable_item_divider);

                    itemType = TYPE_ITEM_EXPAND_ITEM;
                }

            }

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_menu_item_row,parent,false); //Inflating the layout
            //MaterialRippleLayout.on(v).rippleColor(Color.BLACK).create();
            //Typeface typeface = Typeface.createFromAsset(parent.getContext().getAssets(), "fonts/NanumGothicBold.ttf");
            ViewHolder vhItem = new ViewHolder(v,viewType);
            //ViewHolder vhItem = new ViewHolder(v,viewType, typeface); //Creating ViewHolder and passing the object of type view

            return vhItem; // Returning the created object

        }
        else if (viewType == TYPE_ITEM_EXPAND_GROUP){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_menu_item_row_expandable,parent,false); //Inflating the layout

            ViewHolder vhItem = new ViewHolder(v,viewType);

            return vhItem;

        }
        else if (viewType == TYPE_ITEM_EXPAND_ITEM){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_menu_item_row_expandable_item,parent,false);
            ViewHolder vhItem = new ViewHolder(v,viewType);

            return vhItem;


        }
        else if (viewType == TYPE_HEADER) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_header,parent,false);
            ViewHolder vhHeader = new ViewHolder(v,viewType);

            return vhHeader;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        if(viewHolder.itemType == TYPE_ITEM) {                              // as the list view is going to be called after the header view so we decrement the

            //viewHolder.testLayout.setClickable(true);
            if(!viewHolder.testLayout.hasOnClickListeners()) {
                viewHolder.testLayout.setOnClickListener(new TestLayoutClick(position));
            }

            DrawerListData item = drawerListData.get(position);

            viewHolder.titleTextView.setText(item.getTitle()); // Setting the Text with the array of our Titles
            viewHolder.iconImageView.setImageResource(item.getIcon());// Settimg the image with array of our icons

            if(item.isHasDivider()) {
                viewHolder.itemDividerView.setVisibility(View.VISIBLE);
            }

        }
        else if(viewHolder.itemType == TYPE_ITEM_EXPAND_GROUP) {

            if(!viewHolder.testLayout.hasOnClickListeners()) {
                viewHolder.testLayout.setOnClickListener(new TestLayoutClick(position));
            }

            DrawerListData item = drawerListData.get(position);

            viewHolder.titleTextView.setText(item.getTitle()); // Setting the Text with the array of our Titles
            viewHolder.iconImageView.setImageResource(item.getIcon());// Settimg the image with array of our icons

            //viewHolder.expandIndicatorImageView

            if(item.isHasDivider()) {
                viewHolder.itemDividerView.setVisibility(View.VISIBLE);
            }

            if(item.isHasIndicator()) {
                if(item.isExpandListOpened()) {
                    viewHolder.expandIndicatorImageView.setImageResource(icon_Indicator_open);
                }
                else {
                    viewHolder.expandIndicatorImageView.setImageResource(icon_Indicator_close);
                }
            }

        }
        else if(viewHolder.itemType == TYPE_ITEM_EXPAND_ITEM) {

            if(!viewHolder.testLayout.hasOnClickListeners()) {
                viewHolder.testLayout.setOnClickListener(new TestLayoutClick(position));
            }

            DrawerListData item = drawerListData.get(position);

            viewHolder.titleTextView.setText(item.getTitle()); // Setting the Text with the array of our Titles

            int checkIcon = item.getIcon();
            if(checkIcon != -1) {
                viewHolder.iconImageView.setImageResource(item.getIcon());// Settimg the image with array of our icons
            }
            else {

                DrawableTypeRequest<String> loadDrawable = item.getLoadedIconFromURL();
                if(loadDrawable == null) {
                    DrawableTypeRequest<String> drawable = Glide.with(context).load(item.getIconURL());
                    item.setLoadedIconFromURL(drawable);

                    drawable.into(viewHolder.iconImageView);
                }
                else {
                    loadDrawable.into(viewHolder.iconImageView);
                }

            }

            if(item.isHasDivider()) {
                viewHolder.itemDividerView.setVisibility(View.VISIBLE);
            }

            if(item.isFavorite()) {
                viewHolder.expandItemFavoriteImageView.setImageResource(icon_favorite_fill);
            }
            else {
                viewHolder.expandItemFavoriteImageView.setImageResource(icon_favorite_empty);
            }
        }
        else  {  //header

            if(!viewHolder.testLayout.hasOnClickListeners()) {
                viewHolder.testLayout.setOnClickListener(new TestLayoutClick(position));
            }
        }
    }

    @Override
    public int getItemCount() {
        return drawerListData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return drawerListData.get(position).getItemType();
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    public void setDrawerItemClickListener(DrawerItemClickListener drawerItemClickListener) {
        this.drawerItemClickListener = drawerItemClickListener;
    }

    private class TestLayoutClick implements View.OnClickListener {
        int position;

        public TestLayoutClick(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            drawerItemClickListener.drawerItemClick(position);
            Log.e("position", " "+position);
        }
    }

    public interface DrawerItemClickListener {

        public void drawerItemClick(int position);
    }
}
