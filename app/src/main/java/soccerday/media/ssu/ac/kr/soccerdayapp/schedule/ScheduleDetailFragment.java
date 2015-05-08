package soccerday.media.ssu.ac.kr.soccerdayapp.schedule;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import soccerday.media.ssu.ac.kr.soccerdayapp.R;
import soccerday.media.ssu.ac.kr.soccerdayapp.parser.ParserData;

/**
 * Created by Wonho Lee on 2015-05-07.
 */
public class ScheduleDetailFragment extends Fragment {


    MatchListData matchData;

    WebView webView;

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
            ((TextView) v.findViewById(R.id.schedule_detail_time_or_score)).setText(matchData.getScore());
            ((TextView)v.findViewById(R.id.schedule_detail_match_state)).setText("경기 종료");
        }



        Glide.with(getActivity()).load(matchData.getAwayTeamEmblemURL()).into((ImageView)v.findViewById(R.id.schedule_detail_away_image));
        ((TextView)v.findViewById(R.id.schedule_detail_away_title)).setText(matchData.getAwayTeamTitle());

        webView = ((WebView)v.findViewById(R.id.schedule_detail_web_view));
        webView.getSettings().setJavaScriptEnabled(true);

        Log.i("url", ParserData.getScheduleDetailURL(matchData.getLeague(), matchData.matchId));
        webView.loadUrl(ParserData.getScheduleDetailURL(matchData.getLeague(), matchData.matchId));

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);


        this.matchData = (MatchListData) getArguments().getSerializable("match");
    }
}
