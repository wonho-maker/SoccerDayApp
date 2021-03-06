package soccerday.media.ssu.ac.kr.soccerdayapp.schedule.detail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.OrientationHelper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.VideoView;

/**
 * Created by Wonho Lee on 2015-05-11.
 */
public class CustomChromeClient extends WebChromeClient {

    private View mCustomView;
    private Activity mActivity;

    public CustomChromeClient(Activity activity) {
        this.mActivity = activity;


    }

    public View getmCustomView() {
        return mCustomView;
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        result.confirm();
        return super.onJsAlert(view, url, message, result);
    }

    private int mOriginalOrientation;
    private FullscreenHolder mFullscreenContainer;
    private CustomViewCallback customViewCallback;

    private int mOriginalSystemUiVisibility;

    @Override
    public void onShowCustomView(View view, CustomViewCallback callback) {

        super.onShowCustomView(view,callback);

        if (mCustomView != null) {
            callback.onCustomViewHidden();
            return;
        }
        mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        mOriginalSystemUiVisibility = mActivity.getWindow().getDecorView().getSystemUiVisibility();

/*
        mOriginalOrientation = mActivity.getRequestedOrientation();
        //mOriginalOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

        FrameLayout decor = (FrameLayout) mActivity.getWindow().getDecorView();

        mFullscreenContainer = new FullscreenHolder(mActivity);
        mFullscreenContainer.addView(view, ViewGroup.LayoutParams.MATCH_PARENT);
        decor.addView(mFullscreenContainer, ViewGroup.LayoutParams.MATCH_PARENT);
        mCustomView = view;
        customViewCallback = callback;
        mActivity.setRequestedOrientation(mOriginalOrientation);


        if (view instanceof FrameLayout){
            FrameLayout frame = (FrameLayout) view;
            Log.i("webview1", ""+frame.getChildCount());

            for(int i = 0; i < frame.getChildCount(); i++) {
                Log.i("webview", frame.getChildAt(i).toString());
                if (frame.getChildAt(i) instanceof WebView){

                }
            }

        }*/




        final int API_VERSION = Build.VERSION.SDK_INT;

        if(API_VERSION < Build.VERSION_CODES.KITKAT) {
            android.net.Uri mUri = null;
            mCustomView = view;
            customViewCallback = callback;
            try
            {
                @SuppressWarnings("rawtypes")
                Class _VideoSurfaceView_Class_ = Class.forName("android.webkit.HTML5VideoFullScreen$VideoSurfaceView");

                java.lang.reflect.Field _HTML5VideoFullScreen_Field_ = _VideoSurfaceView_Class_.getDeclaredField("this$0");

                _HTML5VideoFullScreen_Field_.setAccessible(true);

                Object _HTML5VideoFullScreen_Instance_ = _HTML5VideoFullScreen_Field_.get(((FrameLayout) view).getFocusedChild());

                @SuppressWarnings("rawtypes")
                Class _HTML5VideoView_Class_ = _HTML5VideoFullScreen_Field_.getType().getSuperclass();

                java.lang.reflect.Field _mUri_Field_ = _HTML5VideoView_Class_.getDeclaredField("mUri");

                _mUri_Field_.setAccessible(true);

                mUri =  (Uri) _mUri_Field_.get(_HTML5VideoFullScreen_Instance_);
            }
            catch (Exception ex)
            {
            }

            if (mUri != null)
            {
                Intent i = new Intent(Intent.ACTION_VIEW);

                i.setDataAndType(mUri, "video/mp4");

                mActivity.startActivity(i);
            }
        } else {
            mOriginalOrientation = mActivity.getRequestedOrientation();

            mActivity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_IMMERSIVE);

            FrameLayout decor = (FrameLayout) mActivity.getWindow().getDecorView();
            //decor.setRotation(90);
            mFullscreenContainer = new FullscreenHolder(mActivity);
            //mFullscreenContainer.setRotation(90);
            //view.setRotation(90);
            mFullscreenContainer.addView(view, ViewGroup.LayoutParams.MATCH_PARENT);
            decor.addView(mFullscreenContainer, ViewGroup.LayoutParams.MATCH_PARENT);
            mCustomView = view;
            customViewCallback = callback;


            //mActivity.setRequestedOrientation(mOriginalOrientation);
        }


        /*WebView test = (WebView) view;


        String origin_url = test.getUrl();;


            Intent i = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.parse(origin_url);
            i.setDataAndType(uri, "video/mp4");
            mActivity.startActivity(i);*/
    }


    @Override
    public void onHideCustomView() {
        if (mCustomView == null) {

            return;
        }

        FrameLayout decor = (FrameLayout) mActivity.getWindow().getDecorView();
        decor.removeView(mFullscreenContainer);

        mFullscreenContainer = null;
        mCustomView = null;

        mActivity.getWindow().getDecorView()
                .setSystemUiVisibility(mOriginalSystemUiVisibility);
        customViewCallback.onCustomViewHidden();
        customViewCallback = null;

        //mActivity.setRequestedOrientation(mOriginalOrientation);
        mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public boolean isShowingFullScreen() {

        if(mCustomView != null && mFullscreenContainer != null) {
            return true;
        }
        else {
            return false;
        }
    }

    static class FullscreenHolder extends FrameLayout {

        public FullscreenHolder(Context ctx) {
            super(ctx);
            setBackgroundColor(ctx.getResources().getColor(android.R.color.black));
        }

        @Override
        public boolean onTouchEvent(MotionEvent evt) {
            return true;
        }
    }


}
