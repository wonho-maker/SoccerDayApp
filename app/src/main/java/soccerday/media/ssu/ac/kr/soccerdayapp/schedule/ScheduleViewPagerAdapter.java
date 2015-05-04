package soccerday.media.ssu.ac.kr.soccerdayapp.schedule;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.Calendar;

import soccerday.media.ssu.ac.kr.soccerdayapp.MainActivity;
import soccerday.media.ssu.ac.kr.soccerdayapp.fragments.LeagueListFragment;

/**
 * Created by Wonho Lee on 2015-05-01.
 */
public class ScheduleViewPagerAdapter extends FragmentStatePagerAdapter{

    LeagueListFragment[] fragments = new LeagueListFragment[1];
    String[] viewTitle = new String[3];

    public ScheduleViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);

        fragments[0] = new LeagueListFragment();
        //fragments[1] = new LeagueListFragment();
    }

    public ScheduleViewPagerAdapter(FragmentManager childFragmentManager, Calendar date) {
        super(childFragmentManager);

        fragments[0] = new LeagueListFragment();

        viewTitle[1] = (date.get(Calendar.MONTH) + 1) + "월 " + + date.get(Calendar.DAY_OF_MONTH)+ "일";

        date.add(Calendar.DAY_OF_WEEK, -1);
        viewTitle[0] = (date.get(Calendar.MONTH) + 1) + "월 " + + date.get(Calendar.DAY_OF_MONTH)+ "일";

        date.add(Calendar.DAY_OF_WEEK, 2);
        viewTitle[2] = (date.get(Calendar.MONTH) + 1) + "월 " + + date.get(Calendar.DAY_OF_MONTH)+ "일";


    }

    public String getViewTitle(int index) {
        return viewTitle[index];
    }

    public Fragment[] getFragments() {
        return fragments;
    }

    public void setFragments(LeagueListFragment[] fragments) {
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}
