package soccerday.media.ssu.ac.kr.soccerdayapp.schedule;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
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

import soccerday.media.ssu.ac.kr.soccerdayapp.MainActivity;
import soccerday.media.ssu.ac.kr.soccerdayapp.R;
import soccerday.media.ssu.ac.kr.soccerdayapp.parser.ParserData;

/**
 * Created by Wonho Lee on 2015-05-07.
 */
public class ScheduleDetailFragment extends Fragment {


    MatchListData matchData;

    ProgressWheel progressWheel;
    WebView webView;

    TextView scoreTextView;

    CustomChromeClient customChromeClient;
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

        progressWheel = (ProgressWheel) v.findViewById(R.id.schedule_detail_progress_wheel);

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
            scoreTextView.setBackgroundColor(Color.parseColor("#ff2d363f"));
            scoreTextView.setText("?");
            /*
            * viewHolder.timeOrScoreTextView.setTextColor(Color.WHITE);
            viewHolder.timeOrScoreTextView.setBackgroundColor(Color.parseColor("#EE4D6674"));
            viewHolder.timeOrScoreTextView.setText("?");*/
            ((TextView)v.findViewById(R.id.schedule_detail_match_state)).setText("경기 종료");
        }



        Glide.with(getActivity()).load(matchData.getAwayTeamEmblemURL()).into((ImageView)v.findViewById(R.id.schedule_detail_away_image));
        ((TextView)v.findViewById(R.id.schedule_detail_away_title)).setText(matchData.getAwayTeamTitle());

        webView = ((WebView)v.findViewById(R.id.schedule_detail_web_view));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);

        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setSupportMultipleWindows(true);

        webView.setWebViewClient(setWebViewClient());
        customChromeClient = new CustomChromeClient(getActivity());
        webView.setWebChromeClient(customChromeClient);

        /*webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (KeyEvent.ACTION_UP == event.getAction()) {
                        if (webView.canGoBack()) {
                            webView.goBack();
                        }else{
                            getActivity().onBackPressed();
                            return true;
                        }
                    }
                    return true;
                } else {
                    getActivity().onKeyDown(keyCode,event);
                    return true;
                }

            }
        });*/
        ((MainActivity)getActivity()).setOnKeyBackPressedListener(new MainActivity.onKeyBackPressedListener() {
            @Override
            public boolean onBack() {
                if(customChromeClient.isShowingFullScreen()) {
                    customChromeClient.onHideCustomView();
                    return true;
                } else if (webView != null && webView.canGoBack()) {
                    webView.goBack();
                    return true;
                }else{

                    return false;
                }
            }
        });

        webView.addJavascriptInterface(new myJavaScriptInterface(), "CallAndroidFunction");

        Log.i("url", ParserData.getScheduleDetailURL(matchData.getLeague(), matchData.matchId));
        webView.loadUrl(ParserData.getScheduleDetailURL(matchData.getLeague(), matchData.matchId));

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
        customChromeClient.onHideCustomView();
    }

    private WebViewClient setWebViewClient() {
        return new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                if(progressWheel != null) {
                    progressWheel.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                view.loadUrl("javascript:(function() { " +
                        "document.getElementsByClassName('end_head')[0].style.display = 'none'; " +
                        "document.getElementsByClassName('mtch_bx_wrp')[0].style.display = 'none'; " +
                        "document.getElementsByClassName('gs_wrp typ4')[0].style.display = 'none'; " +
                        "})()");

                view.loadUrl("javascript: window.CallAndroidFunction.setVisible()");
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("intent://")) {
                    Intent intent;
                    try {
                        intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                    } catch (URISyntaxException ex) {
                        return false;
                    }
                    if (intent != null) {
                        try {
                            getActivity().startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            Log.e("test3", "ActivityNotFoundException");
                        }
                        return true;
                    }
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        };
    }

    public class myJavaScriptInterface {
        Handler mHandler = new Handler();
        @JavascriptInterface
        public void setVisible(){
            mHandler.post(new Runnable() {

                @Override
                public void run() {
                    if(progressWheel != null) {
                        progressWheel.setVisibility(View.GONE);
                    }
                }
            });
        }



    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);



        this.matchData = (MatchListData) getArguments().getSerializable("match");
    }

    @Override
    public void onDetach() {
        webView.destroy();
        webView = null;


        super.onDetach();
    }
}
