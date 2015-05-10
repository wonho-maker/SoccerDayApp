package soccerday.media.ssu.ac.kr.soccerdayapp.schedule;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.io.Serializable;
import java.util.Calendar;

import soccerday.media.ssu.ac.kr.soccerdayapp.MainActivity;
import soccerday.media.ssu.ac.kr.soccerdayapp.fragments.LeagueListDummyFragment;
import soccerday.media.ssu.ac.kr.soccerdayapp.fragments.LeagueListFragment;
import soccerday.media.ssu.ac.kr.soccerdayapp.fragments.LigeaueListAdapter;

/**
 * Created by Wonho Lee on 2015-05-01.
 */
public class ScheduleViewPagerAdapter extends FragmentStatePagerAdapter implements Serializable {

    private  LigeaueListAdapter.LeagueAndMatchItemClickListener leagueAndMatchItemClickListener;
    Fragment[] fragments = new Fragment[3];
    String[] viewTitle = new String[3];
    Calendar date;

    LeagueListFragment leagueListFragment;

    public ScheduleViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);

        fragments[0] = new LeagueListFragment();
        //fragments[1] = new LeagueListFragment();
    }



    public ScheduleViewPagerAdapter(FragmentManager childFragmentManager, Calendar date) {
        super(childFragmentManager);

        this.date = date;
        fragments[0] = new LeagueListDummyFragment();

        leagueListFragment = LeagueListFragment.newInstance(date);
        leagueListFragment.setLeagueAndMatchItemClickListener(leagueAndMatchItemClickListener);

        fragments[1] = leagueListFragment;
        fragments[2] = new LeagueListDummyFragment();




        date.add(Calendar.DAY_OF_WEEK, -1);
        viewTitle[0] = (date.get(Calendar.MONTH) + 1) + "월 " + + date.get(Calendar.DAY_OF_MONTH)+ "일";

        date.add(Calendar.DAY_OF_WEEK, 2);
        viewTitle[2] = (date.get(Calendar.MONTH) + 1) + "월 " + + date.get(Calendar.DAY_OF_MONTH)+ "일";

        date.add(Calendar.DAY_OF_WEEK, -1);
        viewTitle[1] = (date.get(Calendar.MONTH) + 1) + "월 " + + date.get(Calendar.DAY_OF_MONTH)+ "일";

        //Log.i("loading"," logdijf");
    }

    public void setLeagueAndMatchItemClickListener(LigeaueListAdapter.LeagueAndMatchItemClickListener leagueAndMatchItemClickListener) {
        this.leagueAndMatchItemClickListener = leagueAndMatchItemClickListener;
    }

    public void setViewTitle(String[] viewTitle) {
        this.viewTitle = viewTitle;
    }

    public String getViewTitle(int index) {
        return viewTitle[index];
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

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public Calendar getDate() {
        return date;
    }

    public void setScheduleToNextDay() {

        //date.add(Calendar.DAY_OF_WEEK, -1);
        viewTitle[0] = (date.get(Calendar.MONTH) + 1) + "월 " + + date.get(Calendar.DAY_OF_MONTH)+ "일";

        date.add(Calendar.DAY_OF_WEEK, 2);
        viewTitle[2] = (date.get(Calendar.MONTH) + 1) + "월 " + + date.get(Calendar.DAY_OF_MONTH)+ "일";

        date.add(Calendar.DAY_OF_WEEK, -1);
        viewTitle[1] = (date.get(Calendar.MONTH) + 1) + "월 " + + date.get(Calendar.DAY_OF_MONTH)+ "일";

        fragments[1] = LeagueListFragment.newInstance(date);


    }

    public void setScheduleToYesterday() {

        date.add(Calendar.DAY_OF_WEEK, -2);
        viewTitle[0] = (date.get(Calendar.MONTH) + 1) + "월 " + + date.get(Calendar.DAY_OF_MONTH)+ "일";

        date.add(Calendar.DAY_OF_WEEK, 2);
        viewTitle[2] = (date.get(Calendar.MONTH) + 1) + "월 " + + date.get(Calendar.DAY_OF_MONTH)+ "일";

        date.add(Calendar.DAY_OF_WEEK, -1);
        viewTitle[1] = (date.get(Calendar.MONTH) + 1) + "월 " + + date.get(Calendar.DAY_OF_MONTH)+ "일";

        fragments[1] = LeagueListFragment.newInstance(date);
    }
}
