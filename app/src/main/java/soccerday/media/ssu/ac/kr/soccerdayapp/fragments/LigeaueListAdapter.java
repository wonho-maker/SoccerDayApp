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
public class LigeaueListAdapter extends RecyclerView.Adapter<LigeaueListAdapter.ViewHolder>  {

    private List<LeagueListData> mLeagueListData;
    private final int icon_Indicator_open;
    private final int icon_Indicator_close;
    private final Context context;

    LeagueAndMatchItemClickListener leagueAndMatchItemClickListener;

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
    public void onBindViewHolder(LigeaueListAdapter.ViewHolder viewHolder, final int position) {

        final LeagueListData item = mLeagueListData.get(position);

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
        viewHolder.matchListAdapter = new MatchListAdapter((context), viewHolder.matchListData);

        viewHolder.matchListAdapter.setMatchListClickListener(new MatchListAdapter.MatchListClickListener() {
            @Override
            public void matchItemClick(int matchItemPosition) {
                //Log.i("lladap", " p"+matchItemPosition);
                //Log.i("lladap", " p"+position);
                if(leagueAndMatchItemClickListener != null) {
                    //leagueAndMatchItemClickListener.leagueAndMatchItemClick(position, matchItemPosition);
                    leagueAndMatchItemClickListener.leagueAndMatchItemClick(position, matchItemPosition, item.getExpandChildItemData().get(matchItemPosition));
                }
            }
        });
        viewHolder.expandRecyclerView.setAdapter(viewHolder.matchListAdapter);

        viewHolder.expandRecyclerView.setVisibility(View.GONE);

        if(!viewHolder.testLinearLayoutView.hasOnClickListeners()) {
            viewHolder.testLinearLayoutView.setOnClickListener(new TestCardViewClick(viewHolder.matchListData,
                    viewHolder.expandRecyclerView, viewHolder.cardView , position));
        }

        //viewHolder.expandRecyclerView.getAdapter().notifyItemRangeInserted(0, item.getExpandChildItemData().size());

        if(position == 0) {
            viewHolder.topMargin.setVisibility(View.VISIBLE);
        } else if(position == getItemCount() - 1) {
            viewHolder.bottomMargin.setVisibility(View.VISIBLE);
        }

        //viewHolder.testCardView.se
    }

    public void setLeagueAndMatchItemClickListener(LeagueAndMatchItemClickListener listener) {

        this.leagueAndMatchItemClickListener = listener;
    }

    public interface LeagueAndMatchItemClickListener {

        public void leagueAndMatchItemClick(int leaguePosition, int matchPosition);

        public void leagueAndMatchItemClick(int leaguePosition, int matchPosition, MatchListData matchData);
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
        MatchListAdapter matchListAdapter;


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
        int childExpandHeight;
        int start;

        public TestCardViewClick(int position) {
            this.hide = true;
        }

        public TestCardViewClick(RecyclerView expandRecyclerView, int position) {
            tempRecyclerView = expandRecyclerView;
            this.position = position;
            this.hide = true;
            childExpandHeight = -1;
            int start = 0;
        }

        public TestCardViewClick(List<MatchListData> matchListData, RecyclerView expandRecyclerView, int position) {
            tempRecyclerView = expandRecyclerView;
            this.position = position;
            this.hide = true;
            this.matchListData = matchListData;
            childExpandHeight = -1;
            int start = 0;
        }

        public TestCardViewClick(List<MatchListData> matchListData, RecyclerView expandRecyclerView, CardView cardView, int position) {
            tempRecyclerView = expandRecyclerView;
            this.position = position;
            this.hide = true;
            this.matchListData = matchListData;
            this.cardView = cardView;
            childExpandHeight = -1;
            int start = 0;
        }



        @Override
        public void onClick(View v) {


            List<MatchListData> tempMData = mLeagueListData.get(position).getExpandChildItemData();

            if(tempMData.size() > 0) {
                if(hide) {
                    //Log.i("test", tempMData.toString());
                    //matchListData.addAll(tempMData);

                    //tempRecyclerView.getAdapter().notifyItemRangeInserted(0, tempMData.size());

                    //tempRecyclerView.getAdapter().notifyDataSetChanged();

                    //cardView.setMaxCardElevation(3);
                    //cardView.setCardElevation(5);



                    tempRecyclerView.setVisibility(View.VISIBLE);

                    if(childExpandHeight == -1) {
                        tempRecyclerView.measure(cardView.getMeasuredWidth(), cardView.getMeasuredHeight());

                        childExpandHeight = tempRecyclerView.getMeasuredHeight() + 10;

                    }

                    ValueAnimator animator = null;

                    if(start == 0) {

                        //cardView.measure(cardView.getMeasuredWidth(), cardView.getMeasuredHeight());
                        animator = ValueAnimator.ofInt(cardView.getMeasuredHeight(), cardView.getMeasuredHeight() + tempRecyclerView.getMeasuredHeight() + 10);
                        start = cardView.getMeasuredHeight();

                    } else {
                        animator =  ValueAnimator.ofInt( start, start + tempRecyclerView.getMeasuredHeight() + 10);
                    }


                    Log.i("test", " "+start+", "+cardView.getMeasuredHeight()+", "+childExpandHeight+", "+tempRecyclerView.getMeasuredHeight() );

                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                        @Override
                        public void onAnimationUpdate(final ValueAnimator valueAnimator) {
                            int value = (Integer) valueAnimator.getAnimatedValue();

                            ViewGroup.LayoutParams layoutParams = cardView.getLayoutParams();
                            layoutParams.height = value;

                            cardView.setLayoutParams(layoutParams);
                        }
                    });

                    animator.start();
                    //tempRecyclerView.animate().alpha(1.0f).alphaBy(0.0f).start();


                    hide = false;

                } else {
                    //matchListData.removeAll(tempMData);

                    //tempRecyclerView.getAdapter().notifyDataSetChanged();

                    //cardView.setMaxCardElevation(2);
                    //cardView.setCardElevation(4);

                    ValueAnimator animator =  ValueAnimator.ofInt( cardView.getMeasuredHeight(), start);
                    //Log.i("test", " "+start+", "+cardView.getMeasuredHeight()+", "+childExpandHeight+", "+tempRecyclerView.getMeasuredHeight() );
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                        @Override
                        public void onAnimationUpdate(final ValueAnimator valueAnimator) {
                            int value = (Integer) valueAnimator.getAnimatedValue();

                            ViewGroup.LayoutParams layoutParams = cardView.getLayoutParams();
                            layoutParams.height = value;

                            cardView.setLayoutParams(layoutParams);
                        }
                    });

                    animator.start();
                    tempRecyclerView.setVisibility(View.GONE);
                    //tempRecyclerView.animate().alpha(1.0f).alphaBy(0.0f).start();


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
