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
import android.widget.Button;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;

import java.net.URISyntaxException;

import soccerday.media.ssu.ac.kr.soccerdayapp.R;


/**
 * Created by wonho on 2015-05-15.
 */
public class ScheduleDetailCoverFragment extends Fragment implements View.OnClickListener {

    ProgressWheel progressWheel;
    TextView noUploadTextView;
    WebView webView;

    CustomChromeClient customChromeClient;

    String loadURL;

    Button scoreButton;

    boolean hasHighlightVOD = false;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = (View) inflater.inflate(R.layout.fragment_schedule_detail_highlight_cover, container, false);

        progressWheel = (ProgressWheel) v.findViewById(R.id.schedule_detail_highlight_cover_progress_wheel);

        noUploadTextView = (TextView) v.findViewById(R.id.schedule_detail_highlight_cover_noUpload);

        scoreButton = (Button) v.findViewById(R.id.schedule_detail_highlight_cover_button);
        scoreButton.setOnClickListener(this);
        webView = ((WebView)v.findViewById(R.id.schedule_detail_highlight_cover_webView));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);

        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setSupportMultipleWindows(true);

        webView.setWebViewClient(setWebViewClient());
        customChromeClient = new CustomChromeClient(getActivity());
        webView.setWebChromeClient(customChromeClient);

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

                if(!hasHighlightVOD) {

                    view.loadUrl("javascript:(function() { " +
                            "var test = document.getElementsByClassName('hl')[0]; " +
                            "var test2 = test.getElementsByTagName('a')[0];" +
                            "var test3 = test2.getAttribute('href');" +
                            "var test4 = test.getElementsByTagName('em')[0].innerHTML;" +
                            "window.CallAndroidFunction.checkHighlight(test3, test4);" +
                            "})()");

                } else {

                    view.loadUrl("javascript:(function() { " +
                            "document.getElementsByClassName('end_head')[0].style.display = 'none'; " +
                            "document.getElementsByClassName('info_wrp')[0].style.display = 'none'; " +
                            "document.getElementById('view').style.display = 'none';"+
                            "document.getElementById('comment_module').style.display = 'none';"+
                            "})()");

                    view.loadUrl("javascript: window.CallAndroidFunction.setVisible()");
                }
                //view.loadUrl("javascript: ");
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

    @Override
    public void onClick(View v) {
        
    }

    public class myJavaScriptInterface {
        Handler mHandler = new Handler();
        @JavascriptInterface
        public void checkHighlight(final String test, final String test2){

            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {

                    if(test2.trim().equals("하이라이트")) {
                        if (progressWheel != null) {
                            Log.i("test", test + ", "+test2);
                            progressWheel.setVisibility(View.GONE);
                            noUploadTextView.setVisibility(View.GONE);

                            hasHighlightVOD = true;

                            webView.loadUrl(test);
                        }
                    } else {
                        progressWheel.setVisibility(View.GONE);
                    }


                }
            }, 400);
        }


        @JavascriptInterface
        public void setVisible(){
            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    if(progressWheel != null) {
                        progressWheel.setVisibility(View.GONE);
                        noUploadTextView.setVisibility(View.GONE);
                    }
                }
            }, 400);
        }

    }

    public static ScheduleDetailCoverFragment newInstance(String matchURL) {

        ScheduleDetailCoverFragment scheduleDetailCoverFragment = new ScheduleDetailCoverFragment();

        Bundle args = new Bundle();

        args.putString("match", matchURL);
        scheduleDetailCoverFragment.setArguments(args);

        return scheduleDetailCoverFragment;
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
