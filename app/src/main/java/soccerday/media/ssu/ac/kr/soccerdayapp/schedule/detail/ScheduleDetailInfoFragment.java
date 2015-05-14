package soccerday.media.ssu.ac.kr.soccerdayapp.schedule.detail;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.pnikosis.materialishprogress.ProgressWheel;

import java.net.URISyntaxException;

import soccerday.media.ssu.ac.kr.soccerdayapp.MainActivity;
import soccerday.media.ssu.ac.kr.soccerdayapp.R;
import soccerday.media.ssu.ac.kr.soccerdayapp.parser.ParserData;

/**
 * Created by Wonho Lee on 2015-05-14.
 */
public class ScheduleDetailInfoFragment extends Fragment {

    ProgressWheel progressWheel;
    WebView webView;

    CustomChromeClient customChromeClient;

    String loadURL;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = (View) inflater.inflate(R.layout.fragment_schedule_detail_info, container, false);

        progressWheel = (ProgressWheel) v.findViewById(R.id.schedule_detail_info_progress_wheel);



        webView = ((WebView)v.findViewById(R.id.schedule_detail_info_web_view));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);

        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setSupportMultipleWindows(true);

        webView.setWebViewClient(setWebViewClient());
        customChromeClient = new CustomChromeClient(getActivity());
        webView.setWebChromeClient(customChromeClient);

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

        //Log.i("url", ParserData.getScheduleDetailURL(matchData.getLeague(), matchData.matchId));
        webView.loadUrl(loadURL);

        return v;
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

    public static ScheduleDetailInfoFragment newInstance(String matchURL){

        ScheduleDetailInfoFragment scheduleDetailInfoFragment = new ScheduleDetailInfoFragment();

        Bundle args = new Bundle();

        args.putString("match", matchURL);
        scheduleDetailInfoFragment.setArguments(args);

        return scheduleDetailInfoFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.loadURL = getArguments().getString("match");
    }

    @Override
    public void onDetach() {
        super.onDetach();

        webView.destroy();
        webView = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(customChromeClient != null)
            customChromeClient.onHideCustomView();
    }
}
