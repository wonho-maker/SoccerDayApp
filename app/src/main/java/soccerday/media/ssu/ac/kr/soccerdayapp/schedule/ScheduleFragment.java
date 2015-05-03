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

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View scheduleFragmentView = (View)inflater.inflate(R.layout.fragment_schedule, container, false);

        mScheduleTabHost = (MaterialTabHost) scheduleFragmentView.findViewById(R.id.schedule_fragment_materialTabHost);
        mScheduleViewPager = (ViewPager) scheduleFragmentView.findViewById(R.id.schedule_fragment_pager);

        mScheduleViewPagerAdapter = new ScheduleViewPagerAdapter(getChildFragmentManager());

        mScheduleViewPager.setAdapter(mScheduleViewPagerAdapter);
        mScheduleViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                mScheduleTabHost.setSelectedNavigationItem(position);
            }
        });

        for (int i = 0; i < mScheduleViewPagerAdapter.getCount(); i++) {
            Log.i("tab", " "+i);
            mScheduleTabHost.addTab(
                    mScheduleTabHost.newTab().setText("test" + i).setTabListener(this));
        }
        mScheduleTabHost.post(new Runnable() {
            @Override
            public void run() {
                mScheduleTabHost.requestLayout();
            }
        });



        return scheduleFragmentView;
    }

    public static ScheduleFragment newInstance(int position) {

        ScheduleFragment scheduleFragment = new ScheduleFragment();

        return scheduleFragment;
    }

    @Override
    public void onTabSelected(MaterialTab materialTab) {
        mScheduleViewPager.setCurrentItem(materialTab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {

    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {

    }




}
