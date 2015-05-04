package soccerday.media.ssu.ac.kr.soccerdayapp.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import soccerday.media.ssu.ac.kr.soccerdayapp.R;
import soccerday.media.ssu.ac.kr.soccerdayapp.schedule.MatchLinearLayoutManager;
import soccerday.media.ssu.ac.kr.soccerdayapp.schedule.MatchListAdapter;
import soccerday.media.ssu.ac.kr.soccerdayapp.schedule.MatchListData;

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

        viewHolder.matchListData = item.getExpandChildItemData();

        viewHolder.expandRecyclerView.setHasFixedSize(true);

        viewHolder.mLayoutManager = new MatchLinearLayoutManager(context, MatchLinearLayoutManager.VERTICAL, false);

        viewHolder.expandRecyclerView.setLayoutManager(viewHolder.mLayoutManager);
        //viewHolder.expandRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        viewHolder.expandRecyclerView.setAdapter(new MatchListAdapter(context, viewHolder.matchListData));

        if(!viewHolder.testLinearLayoutView.hasOnClickListeners()) {
            viewHolder.testLinearLayoutView.setOnClickListener(new TestCardViewClick(viewHolder.matchListData,
                    viewHolder.expandRecyclerView, viewHolder.cardView ,position));
        }

        //viewHolder.expandRecyclerView.getAdapter().notifyItemRangeInserted(0, item.getExpandChildItemData().size());

        if(position == 0) {
            viewHolder.topMargin.setVisibility(View.VISIBLE);
        } else if(position == getItemCount() - 1) {
            viewHolder.bottomMargin.setVisibility(View.VISIBLE);
        }

        //viewHolder.testCardView.se
    }

    @Override
    public int getItemCount() {
        return mLeagueListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;

        TextView titleTextView;
        ImageView iconImageView;

        ImageView expandIndicatorImageView;

        LinearLayout testLinearLayoutView;
        RecyclerView expandRecyclerView;

        View topMargin;
        View bottomMargin;

        List<MatchListData> matchListData;
        MatchLinearLayoutManager mLayoutManager;

        public ViewHolder(View itemView, int ViewType) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.league_list_card_view);
            titleTextView = (TextView) itemView.findViewById(R.id.league_list_leagueTitle);
            iconImageView = (ImageView) itemView.findViewById(R.id.league_list_leagueIcon);

            expandIndicatorImageView = (ImageView) itemView.findViewById(R.id.league_list_leagueExpandIcon);

            testLinearLayoutView = (LinearLayout) itemView.findViewById(R.id.league_list_top);
            expandRecyclerView = (RecyclerView) itemView.findViewById(R.id.league_list_expand_schedule_recycler);

            topMargin = (View) itemView.findViewById(R.id.league_list_top_margin);
            bottomMargin = (View) itemView.findViewById(R.id.league_list_bottom_margin);

            matchListData = new ArrayList<>();
        }
    }

    public void setmLeagueListData(List<LeagueListData> mLeagueListData) {
        this.mLeagueListData = mLeagueListData;
    }

    private class TestCardViewClick implements View.OnClickListener {
        CardView cardView;
        List<MatchListData> matchListData;
        RecyclerView tempRecyclerView;
        int position;
        boolean hide;
        public TestCardViewClick(int position) {
            this.hide = true;
        }

        public TestCardViewClick(RecyclerView expandRecyclerView, int position) {
            tempRecyclerView = expandRecyclerView;
            this.position = position;
            this.hide = true;
        }

        public TestCardViewClick(List<MatchListData> matchListData, RecyclerView expandRecyclerView, int position) {
            tempRecyclerView = expandRecyclerView;
            this.position = position;
            this.hide = true;
            this.matchListData = matchListData;
        }

        public TestCardViewClick(List<MatchListData> matchListData, RecyclerView expandRecyclerView, CardView cardView, int position) {
            tempRecyclerView = expandRecyclerView;
            this.position = position;
            this.hide = true;
            this.matchListData = matchListData;
            this.cardView = cardView;
        }

        @Override
        public void onClick(View v) {


            List<MatchListData> tempMData = mLeagueListData.get(position).getExpandChildItemData();

            if(tempMData.size() > 0) {
                if(hide) {
                    Log.i("test", tempMData.toString());
                    //matchListData.addAll(tempMData);

                    //tempRecyclerView.getAdapter().notifyItemRangeInserted(0, tempMData.size());

                    //tempRecyclerView.getAdapter().notifyDataSetChanged();

                    cardView.setMaxCardElevation(2);
                    cardView.setCardElevation(3);


                    Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_down);

                    tempRecyclerView.setVisibility(View.VISIBLE);


                    tempRecyclerView.startAnimation(animation);


                    hide = false;

                } else {
                    //matchListData.removeAll(tempMData);

                    //tempRecyclerView.getAdapter().notifyDataSetChanged();

                    cardView.setCardElevation(0);



                    Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_up);
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            tempRecyclerView.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    tempRecyclerView.startAnimation(animation);


                    hide = true;
                }

            } else {

            }

            /*
            //Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
                //tempCardView.startAnimation(animation);

                tempRecyclerView.animate()
                        .translationY(0)
                        .alpha(0.0f)
                        .alphaBy(1.0f)
                        .setInterpolator(new TimeInterpolator() {
                            @Override
                            public float getInterpolation(float input) {
                                return 1000;
                            }
                        })
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                tempCardView.animate()
                                        .setInterpolator(new TimeInterpolator() {
                                            @Override
                                            public float getInterpolation(float input) {
                                                return 1000;
                                            }
                                        });

                                tempRecyclerView.setVisibility(View.GONE);


                            }
                        });
                //tempRecyclerView.setVisibility(View.GONE);
                //tempRecyclerView.getAdapter().notifyItemRemoved(position);
             */
        }
    }


}
