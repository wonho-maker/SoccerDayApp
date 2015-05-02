package soccerday.media.ssu.ac.kr.soccerdayapp.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;

import java.util.List;

import soccerday.media.ssu.ac.kr.soccerdayapp.R;

/**
 * Created by Wonho Lee on 2015-05-02.
 */
public class LigeaueListAdapter extends RecyclerView.Adapter<LigeaueListAdapter.ViewHolder> {

    private List<LeagueListData> mLeagueListData;
    private final int icon_Indicator_open;
    private final int icon_Indicator_close;
    private final Context context;

    public LigeaueListAdapter(List<LeagueListData> mLeagueListData, int icon_indicator_open, int icon_indicator_close, Context context) {
        this.mLeagueListData = mLeagueListData;
        icon_Indicator_open = icon_indicator_open;
        icon_Indicator_close = icon_indicator_close;
        this.context = context;
    }

    @Override
    public LigeaueListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_league_list_item, parent, false);


        ViewHolder vhItem = new ViewHolder(v, viewType);

        return vhItem;
    }

    @Override
    public void onBindViewHolder(LigeaueListAdapter.ViewHolder viewHolder, int position) {

        LeagueListData item = mLeagueListData.get(position);

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

        if(item.hasIndicator()) {
            if(item.isExpandListOpened()) {
                viewHolder.expandIndicatorImageView.setImageResource(icon_Indicator_open);
            }
            else {
                viewHolder.expandIndicatorImageView.setImageResource(icon_Indicator_close);
            }
        }

        if(position == 0) {
            viewHolder.topMargin.setVisibility(View.VISIBLE);
        } else if(position == getItemCount() - 1) {
            viewHolder.bottomMargin.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mLeagueListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView titleTextView;
        ImageView iconImageView;

        ImageView expandIndicatorImageView;

        View topMargin;
        View bottomMargin;

        public ViewHolder(View itemView, int ViewType) {
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(R.id.league_list_leagueTitle);
            iconImageView = (ImageView) itemView.findViewById(R.id.league_list_leagueIcon);

            expandIndicatorImageView = (ImageView) itemView.findViewById(R.id.league_list_leagueExpandIcon);

            topMargin = (View) itemView.findViewById(R.id.league_list_top_margin);
            bottomMargin = (View) itemView.findViewById(R.id.league_list_bottom_margin);
        }
    }
}
