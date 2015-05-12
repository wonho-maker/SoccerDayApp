package soccerday.media.ssu.ac.kr.soccerdayapp;

import android.app.Activity;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewGroup;

import soccerday.media.ssu.ac.kr.soccerdayapp.drawer.NavigationDrawerFragment;
import soccerday.media.ssu.ac.kr.soccerdayapp.fragments.LigeaueListAdapter;
import soccerday.media.ssu.ac.kr.soccerdayapp.schedule.MatchListData;
import soccerday.media.ssu.ac.kr.soccerdayapp.schedule.ScheduleDetailFragment;
import soccerday.media.ssu.ac.kr.soccerdayapp.schedule.ScheduleFragment;



public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        LigeaueListAdapter.LeagueAndMatchItemClickListener{

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();


        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onBackPressed() {

        if(mOnKeyBackPressedListener == null) {
            super.onBackPressed();
        } else {
            if(!mOnKeyBackPressedListener.onBack()) {
                super.onBackPressed();
            }
        }
    }

    public interface onKeyBackPressedListener {
        public boolean onBack();
    }
    private onKeyBackPressedListener mOnKeyBackPressedListener;

    public void setOnKeyBackPressedListener(onKeyBackPressedListener listener) {
        mOnKeyBackPressedListener = listener;
    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(position == 0) {
            fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            fragmentManager.beginTransaction()
                    .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                    .commit();
        }
        else if(position == 1) {
            /*ScheduleFragment scheduleFragment = (ScheduleFragment) fragmentManager.findFragmentByTag("scheduleFragment");
            //Log.i("fragments", fragmentManager.getFragments().toString());

            if (scheduleFragment == null) {
                Log.i("test", "position");
                scheduleFragment = ScheduleFragment.newInstance(position);

                fragmentManager.beginTransaction()
                        .replace(R.id.container, scheduleFragment, "scheduleFragment")
                        .commit();
            }*/
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            ScheduleFragment scheduleFragment = ScheduleFragment.newInstance(position);

            fragmentManager.beginTransaction()
                    .replace(R.id.container, scheduleFragment, "scheduleFragment")
                    .commit();
        }
    }

    @Override
    public void leagueAndMatchItemClick(int leaguePosition, int matchPosition) {
        Log.i("main", ""+leaguePosition+", "+matchPosition);

    }

    @Override
    public void leagueAndMatchItemClick(int leaguePosition, int matchPosition, MatchListData matchData) {
        Log.i("main", ""+leaguePosition+", "+matchPosition);


        FragmentManager fragmentManager = getSupportFragmentManager();

        ScheduleDetailFragment scheduleDetailFragment = (ScheduleDetailFragment) fragmentManager.findFragmentByTag("scheduleDetailFragment");

        if (scheduleDetailFragment == null) {

            scheduleDetailFragment = scheduleDetailFragment.newInstance(matchData);

            fragmentManager.beginTransaction()
                    .addToBackStack("scheduleFragment")
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.container, scheduleDetailFragment, "scheduleDetailFragment")
                    .commit();
        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 0:
                mTitle = "0";
                break;
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
