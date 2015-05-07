package soccerday.media.ssu.ac.kr.soccerdayapp.schedule;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

    String leagueTitle;
    MatchListData matchData;

    WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static ScheduleDetailFragment newInstance(String leagueTitle, MatchListData matchData){

        ScheduleDetailFragment scheduleDetailFragment = new ScheduleDetailFragment();

        Bundle args = new Bundle();
        args.putString("leagueTitle", leagueTitle);
        args.putSerializable("match", (java.io.Serializable) matchData);
        scheduleDetailFragment.setArguments(args);

        return scheduleDetailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = (View) inflater.inflate(R.layout.fragment_schedule_detail, container, false);

        ((TextView)v.findViewById(R.id.schedule_detail_league_title)).setText(leagueTitle);

        ((TextView)v.findViewById(R.id.schedule_detail_home_title)).setText(matchData.getHomeTeamTitle());
        Glide.with(getActivity()).load(matchData.getHomeTeamEmblemURL()).into((ImageView)v.findViewById(R.id.schedule_detail_home_image));

        ((TextView) v.findViewById(R.id.schedule_detail_time_or_score)).setText(leagueTitle);
        ((TextView)v.findViewById(R.id.schedule_detail_match_state)).setText(leagueTitle);

        Glide.with(getActivity()).load(matchData.getAwayTeamEmblemURL()).into((ImageView)v.findViewById(R.id.schedule_detail_away_image));
        ((TextView)v.findViewById(R.id.schedule_detail_away_title)).setText(leagueTitle);

        webView = ((WebView)v.findViewById(R.id.schedule_detail_web_view));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(ParserData.getScheduleDetailURL(leagueTitle, matchData.matchId));

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.leagueTitle = getArguments().getString("leagueTitle");
        this.matchData = (MatchListData) getArguments().getSerializable("match");
    }
}
