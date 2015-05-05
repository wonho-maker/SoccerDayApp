package soccerday.media.ssu.ac.kr.soccerdayapp.schedule;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;
import soccerday.media.ssu.ac.kr.soccerdayapp.R;
import soccerday.media.ssu.ac.kr.soccerdayapp.fragments.LeagueListData;
import soccerday.media.ssu.ac.kr.soccerdayapp.parser.ScheduleParserTask;

/**
 * Created by Wonho Lee on 2015-05-01.
 */
public class ScheduleFragment extends Fragment implements MaterialTabListener {



    MaterialTabHost mScheduleTabHost;
    ViewPager mScheduleViewPager;
    ScheduleViewPagerAdapter mScheduleViewPagerAdapter;

    List<MaterialTab> testTab;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View scheduleFragmentView = (View)inflater.inflate(R.layout.fragment_schedule, container, false);

        mScheduleTabHost = (MaterialTabHost) scheduleFragmentView.findViewById(R.id.schedule_fragment_materialTabHost);
        mScheduleViewPager = (ViewPager) scheduleFragmentView.findViewById(R.id.schedule_fragment_pager);

        testTab = new ArrayList<>();

        Calendar today = Calendar.getInstance();

        mScheduleViewPagerAdapter = new ScheduleViewPagerAdapter(getChildFragmentManager(), today);

        mScheduleViewPager.setAdapter(mScheduleViewPagerAdapter);
        //mScheduleViewPager.setOffscreenPageLimit(1);
        //ViewPager.Li
        mScheduleViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                swipePage(position);
                //mScheduleTabHost.setSelectedNavigationItem(position);
            }

        });

        for (int i = 0; i < mScheduleViewPagerAdapter.getCount(); i++) {

            MaterialTab test = mScheduleTabHost.newTab().setText(mScheduleViewPagerAdapter.getViewTitle(i)).setTabListener(this);
            testTab.add(test);

            mScheduleTabHost.addTab(test);
        }
        mScheduleTabHost.post(new Runnable() {
            @Override
            public void run() {
                mScheduleTabHost.requestLayout();
            }
        });

        mScheduleViewPager.setCurrentItem(1);
        mScheduleTabHost.setSelectedNavigationItem(1);

        return scheduleFragmentView;
    }

    private void swipePage(int position) {


        if(position == 2) {
            mScheduleViewPagerAdapter.setScheduleToNextDay();

            for (int i = 0; i < mScheduleViewPagerAdapter.getCount(); i++) {
                testTab.get(i).setText(mScheduleViewPagerAdapter.getViewTitle(i));
            }

            mScheduleViewPager.setCurrentItem(1);
            mScheduleTabHost.setSelectedNavigationItem(1);


            mScheduleViewPager.getAdapter().notifyDataSetChanged();
        }
    }

    public static ScheduleFragment newInstance(int position) {

        ScheduleFragment scheduleFragment = new ScheduleFragment();

        return scheduleFragment;
    }

    @Override
    public void onTabSelected(MaterialTab materialTab) {
        //mScheduleViewPager.setCurrentItem(materialTab.getPosition());
        int position = materialTab.getPosition();
        if(position != 1) {
            swipePage(position);
        }

    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {

    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {

    }




}
