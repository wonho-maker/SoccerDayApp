package soccerday.media.ssu.ac.kr.soccerdayapp.schedule.detail;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.net.URISyntaxException;

import soccerday.media.ssu.ac.kr.soccerdayapp.LeagueData;
import soccerday.media.ssu.ac.kr.soccerdayapp.MainActivity;
import soccerday.media.ssu.ac.kr.soccerdayapp.R;
import soccerday.media.ssu.ac.kr.soccerdayapp.parser.ParserData;
import soccerday.media.ssu.ac.kr.soccerdayapp.schedule.MatchListData;

/**
 * Created by Wonho Lee on 2015-05-07.
 */
public class ScheduleDetailFragment extends Fragment {


    MatchListData matchData;



    TextView scoreTextView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public static ScheduleDetailFragment newInstance(MatchListData matchData){

        ScheduleDetailFragment scheduleDetailFragment = new ScheduleDetailFragment();

        Bundle args = new Bundle();

        args.putSerializable("match",  matchData);
        scheduleDetailFragment.setArguments(args);

        return scheduleDetailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = (View) inflater.inflate(R.layout.fragment_schedule_detail, container, false);



        ((TextView)v.findViewById(R.id.schedule_detail_league_title)).setText(matchData.getLeague());

        ((TextView)v.findViewById(R.id.schedule_detail_home_title)).setText(matchData.getHomeTeamTitle());
        Glide.with(getActivity()).load(matchData.getHomeTeamEmblemURL()).into((ImageView)v.findViewById(R.id.schedule_detail_home_image));


        if(matchData.getMatchState() == MatchListData.MatchState.BEFORE) {
            ((TextView) v.findViewById(R.id.schedule_detail_time_or_score)).setText(matchData.getTime());
            ((TextView)v.findViewById(R.id.schedule_detail_match_state)).setText("경기 전");
        } else if(matchData.getMatchState() == MatchListData.MatchState.ING) {
            ((TextView) v.findViewById(R.id.schedule_detail_time_or_score)).setText(matchData.getScore());
            ((TextView)v.findViewById(R.id.schedule_detail_match_state)).setText("경기 중");
        } else { //after
            scoreTextView = ((TextView) v.findViewById(R.id.schedule_detail_time_or_score));
            if(matchData.hasHighlight()) {
                scoreTextView.setBackgroundColor(Color.parseColor("#ff2d363f"));
                scoreTextView.setText("?");
            } else {
                scoreTextView = (TextView) v.findViewById(R.id.schedule_detail_time_or_score);
                scoreTextView.setText(matchData.getScore());
            }
            /*
            * viewHolder.timeOrScoreTextView.setTextColor(Color.WHITE);
            viewHolder.timeOrScoreTextView.setBackgroundColor(Color.parseColor("#EE4D6674"));
            viewHolder.timeOrScoreTextView.setText("?");*/
            ((TextView)v.findViewById(R.id.schedule_detail_match_state)).setText("경기 종료");
        }



        Glide.with(getActivity()).load(matchData.getAwayTeamEmblemURL()).into((ImageView)v.findViewById(R.id.schedule_detail_away_image));
        ((TextView)v.findViewById(R.id.schedule_detail_away_title)).setText(matchData.getAwayTeamTitle());

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
               .replace(R.id.schedule_detail_container, ScheduleDetailInfoFragment.newInstance(ParserData.getScheduleDetailURL(matchData.getLeague(), matchData.getMatchId())))
               .commit();




        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("fr","pause");
        //customChromeClient.onHideCustomView();
}

    @Override
    public void onResume() {
        super.onResume();
        Log.i("fr","resume");

    }





    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.matchData = (MatchListData) getArguments().getSerializable("match");
    }

    @Override
    public void onDetach() {



        super.onDetach();
    }
}
