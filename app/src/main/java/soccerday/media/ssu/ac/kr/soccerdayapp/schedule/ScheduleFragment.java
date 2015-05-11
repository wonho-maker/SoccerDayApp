package soccerday.media.ssu.ac.kr.soccerdayapp.schedule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import it.neokree.materialtabs.MaterialTab;
import soccerday.media.ssu.ac.kr.soccerdayapp.MainActivity;
import soccerday.media.ssu.ac.kr.soccerdayapp.R;
import soccerday.media.ssu.ac.kr.soccerdayapp.fragments.LigeaueListAdapter;

/**
 * Created by Wonho Lee on 2015-05-06.
 */
public class ScheduleFragment extends Fragment implements View.OnClickListener {

    ViewPager mScheduleViewPager;
    ScheduleViewPagerAdapter mScheduleViewPagerAdapter;

    List<TextView> testTab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View scheduleFragmentView = (View) inflater.inflate(R.layout.fragment_schedule, container, false);

        mScheduleViewPager = (ViewPager) scheduleFragmentView.findViewById(R.id.schedule_fragment_pager);

        testTab = new ArrayList<>();

        testTab.add((TextView) scheduleFragmentView.findViewById(R.id.schedule_tab_1));
        testTab.add((TextView) scheduleFragmentView.findViewById(R.id.schedule_tab_2));
        testTab.add((TextView) scheduleFragmentView.findViewById(R.id.schedule_tab_3));

        Calendar today = Calendar.getInstance();

        Bundle stateData = getArguments();

        if(stateData == null) {
            mScheduleViewPagerAdapter = new ScheduleViewPagerAdapter(getChildFragmentManager(), today);
        } else {
            mScheduleViewPagerAdapter = (ScheduleViewPagerAdapter)savedInstanceState.getSerializable("test");
            Log.i("save", "save2");
        }
        mScheduleViewPager.setAdapter(mScheduleViewPagerAdapter);
        //mScheduleViewPager.setOffscreenPageLimit(1);
        //ViewPager.Li
        mScheduleViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                swipePage(position);

                Log.i("page", "" + position);
            }

        });

        for (int i = 0; i < mScheduleViewPagerAdapter.getCount(); i++) {

            testTab.get(i).setText(mScheduleViewPagerAdapter.getViewTitle(i));
            testTab.get(i).setOnClickListener(this);

        }

        mScheduleViewPager.setCurrentItem(1);

        return scheduleFragmentView;

    }



    private void swipePage(int position) {

        if(position==1) {
            return;
        }
        else if(position == 2) {
            mScheduleViewPagerAdapter.setScheduleToNextDay();

        } else if(position == 0) {
            mScheduleViewPagerAdapter.setScheduleToYesterday();

        }

        for (int i = 0; i < mScheduleViewPagerAdapter.getCount(); i++) {
            testTab.get(i).setText(mScheduleViewPagerAdapter.getViewTitle(i));
        }

        mScheduleViewPager.setCurrentItem(1);


        mScheduleViewPager.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        swipePage(testTab.indexOf(((TextView)v)));
    }

    public static ScheduleFragment newInstance(int position) {
        return new ScheduleFragment();
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();

        /*Bundle args = new Bundle();
        args.putSerializable("test", mScheduleViewPagerAdapter);
        //this.setArguments(args);
        getArguments().putSerializable("test", mScheduleViewPagerAdapter);
        Log.i("save", "save");*/
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putSerializable("test", mScheduleViewPagerAdapter);

    }
}
