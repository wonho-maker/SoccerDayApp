package soccerday.media.ssu.ac.kr.soccerdayapp.schedule;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import soccerday.media.ssu.ac.kr.soccerdayapp.MainActivity;
import soccerday.media.ssu.ac.kr.soccerdayapp.fragments.LeagueListFragment;

/**
 * Created by Wonho Lee on 2015-05-01.
 */
public class ScheduleViewPagerAdapter extends FragmentStatePagerAdapter{

    Fragment[] fragments = new Fragment[1];

    public ScheduleViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);

        fragments[0] = new LeagueListFragment();
        //fragments[1] = new LeagueListFragment();
    }

    public Fragment[] getFragments() {
        return fragments;
    }

    public void setFragments(Fragment[] fragments) {
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
