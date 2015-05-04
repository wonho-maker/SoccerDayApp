package soccerday.media.ssu.ac.kr.soccerdayapp.schedule;

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
import soccerday.media.ssu.ac.kr.soccerdayapp.fragments.LeagueListData;

/**
 * Created by Wonho Lee on 2015-05-03.
 */
public class MatchListAdapter extends RecyclerView.Adapter<MatchListAdapter.ViewHolder>{

    private List<MatchListData> mMatchListData;

    private final Context context;

    public MatchListAdapter(Context context, List<MatchListData> matchListData) {
        this.context = context;
        this.mMatchListData = matchListData;
    }

    @Override
    public MatchListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_league_list_item_expand, parent, false);


        ViewHolder vhItem = new ViewHolder(v);

        return vhItem;
    }

    public void showAndHide(boolean show, List<MatchListData> temp) {
        if(show) {
            for (int i = 0; i < temp.size(); i++) {
                mMatchListData.add(temp.get(i));
            }
        }
        else {
            mMatchListData.removeAll(temp);
        }

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        MatchListData item = mMatchListData.get(position);

        viewHolder.homeTeamNameTextView.setText(item.getHomeTeamTitle());


        DrawableTypeRequest<String> drawable = Glide.with(context).load(item.getHomeTeamEmblemURL());
        drawable.into(viewHolder.homeTeamIconImageView);

        viewHolder.timeOrScoreTextView.setText(item.getTime());
        //viewHolder.matchStateTextView;

        DrawableTypeRequest<String> drawable2 = Glide.with(context).load(item.getAwayTeamEmblemURL());
        drawable2.into(viewHolder.awayTeamIconImageView);

        viewHolder.awayTeamNameTextView.setText(item.getAwayTeamTitle());
    }

    @Override
    public int getItemCount() {
        return mMatchListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView homeTeamNameTextView;
        ImageView homeTeamIconImageView;

        TextView timeOrScoreTextView;
        TextView matchStateTextView;

        TextView awayTeamNameTextView;
        ImageView awayTeamIconImageView;

        public ViewHolder(View itemView) {
            super(itemView);

            homeTeamNameTextView = (TextView) itemView.findViewById(R.id.league_list_expand_homeTeam_name);
            homeTeamIconImageView = (ImageView) itemView.findViewById(R.id.league_list_expand_homeTeam_icon);

            timeOrScoreTextView = (TextView) itemView.findViewById(R.id.league_list_expand_time_or_score);
            matchStateTextView = (TextView) itemView.findViewById(R.id.league_list_expand_match_state);

            awayTeamNameTextView = (TextView) itemView.findViewById(R.id.league_list_expand_awayTeam_name);
            awayTeamIconImageView = (ImageView) itemView.findViewById(R.id.league_list_expand_awayTeam_icon);
        }
    }

    public List<MatchListData> getmMatchListData() {
        return mMatchListData;
    }

    public void setmMatchListData(List<MatchListData> mMatchListData) {
        this.mMatchListData = mMatchListData;
    }
}
