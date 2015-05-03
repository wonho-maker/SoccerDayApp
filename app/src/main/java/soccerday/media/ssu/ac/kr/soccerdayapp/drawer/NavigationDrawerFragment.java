package soccerday.media.ssu.ac.kr.soccerdayapp.drawer;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import soccerday.media.ssu.ac.kr.soccerdayapp.LeagueData;
import soccerday.media.ssu.ac.kr.soccerdayapp.R;

/**
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
 * design guidelines</a> for a complete explanation of the behaviors implemented here.
 */
public class NavigationDrawerFragment extends Fragment implements DrawerMenuAdapter.DrawerItemClickListener {

    /**
     * Remember the position of the selected item.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    /**
     * A pointer to the current callbacks instance (the Activity).
     */
    private NavigationDrawerCallbacks mCallbacks;

    /**
     * Helper component that ties the action bar to the navigation drawer.
     */
    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;
    private View mFragmentContainerView;

    private int mCurrentSelectedPosition = 0;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;

    //menu for drawer
    RecyclerView mRecyclerView;
    DrawerMenuAdapter mDrawerMenuAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    List<DrawerListData> drawerListData;



    public NavigationDrawerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Read in the flag indicating whether or not the user has demonstrated awareness of the
        // drawer. See PREF_USER_LEARNED_DRAWER for details.
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }

        // Select either the default item (0) or the last selected item.
        selectItem(mCurrentSelectedPosition);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of actions in the action bar.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View drawerView = (LinearLayout)inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        //menu////
        mRecyclerView = (RecyclerView) drawerView.findViewById(R.id.drawer_menu_container_recycler);


        mRecyclerView.setHasFixedSize(true);

        initializeDrawerMenuListData();

        //create adapter
        mDrawerMenuAdapter = new DrawerMenuAdapter(drawerListData,
                R.drawable.ic_indicator_up, R.drawable.ic_indicator,
                R.drawable.ic_favorite_star_empty, R.drawable.ic_favorite_star_fill,
                getActivity().getApplicationContext());


        mDrawerMenuAdapter.setDrawerItemClickListener(this);

        mRecyclerView.setAdapter(mDrawerMenuAdapter);

        //set layout manager
        mLayoutManager = new LinearLayoutManager(this.getActivity().getApplicationContext());

        mRecyclerView.setLayoutManager(mLayoutManager);


        //touch event listener


        //mRecyclerView.addOnItemTouchListener(new DrawerMenuTouchListener());
        //mRecyclerView.listion


        return drawerView;
    }

    private void initializeDrawerMenuListData() {
        String drawerMenuTitles[] = getResources().getStringArray(R.array.drawer_menu);

        int drawerMenuIcons[] = {R.drawable.ic_schedule, R.drawable.ic_news, R.drawable.ic_rank, R.drawable.ic_favorite,
                R.drawable.ic_setting};

        String itemsType[] = getResources().getStringArray(R.array.drawer_item_list_types);

        String isHasDivider[] = getResources().getStringArray(R.array.drawer_item_list_divider);

        drawerListData = new ArrayList();

        //insert Header
        DrawerListData header = new DrawerListData("header", 0, DrawerMenuAdapter.TYPE_HEADER);

        drawerListData.add(header);

        //insert menu items
        for(int i = 0; i < drawerMenuTitles.length; i++) {
            DrawerListData tempData = new DrawerListData(drawerMenuTitles[i], drawerMenuIcons[i]);

            if(itemsType[i].equals("normal")) {
                tempData.setItemType(DrawerMenuAdapter.TYPE_ITEM);


            }
            else if(itemsType[i].equals("expand")) {
                tempData.setItemType(DrawerMenuAdapter.TYPE_ITEM_EXPAND_GROUP);
                tempData.setHasIndicator(true);
            }

            if(isHasDivider[i].equals("divider")) {
                tempData.setHasDivider(true);
            }

            drawerListData.add(tempData);
        }


        //expand list's item for favorite
        String leagueTitles[] = getResources().getStringArray(R.array.league_titles);

        List<DrawerListData> expandChildItems = new ArrayList<>();



        for(int i = 0; i < leagueTitles.length; i++) {
            DrawerListData tempChildData = new DrawerListData(leagueTitles[i], LeagueData.getLeageEmblemURL(leagueTitles[i]), DrawerMenuAdapter.TYPE_ITEM_EXPAND_ITEM);

            expandChildItems.add(tempChildData);
        }

        for(DrawerListData listData : drawerListData) {
            if(listData.getItemType() == DrawerMenuAdapter.TYPE_ITEM_EXPAND_GROUP) {
                listData.setExpandChildItemData(expandChildItems);

            }
        }
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),                    /* host Activity */
                mDrawerLayout,                    /* DrawerLayout object */

                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                if (!mUserLearnedDrawer) {
                    // The user manually opened the drawer; store this flag to prevent auto-showing
                    // the navigation drawer automatically in the future.
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };

        // If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
        // per the navigation drawer design guidelines.
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }

        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void selectItem(int position) {
        mCurrentSelectedPosition = position;
        if (mRecyclerView != null) {
            //mDrawerListView.setItemChecked(position, true);
        }
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // If the drawer is open, show the global app actions in the action bar. See also
        // showGlobalContextActionBar, which controls the top-left area of the action bar.
        if (mDrawerLayout != null && isDrawerOpen()) {
            inflater.inflate(R.menu.global, menu);
            showGlobalContextActionBar();
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        if (item.getItemId() == R.id.action_example) {
            Toast.makeText(getActivity(), "Example action.", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Per the navigation drawer design guidelines, updates the action bar to show the global app
     * 'context', rather than just what's in the current screen.
     */
    private void showGlobalContextActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(R.string.app_name);
    }

    private ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }

    /**
     * Callbacks interface that all activities using this fragment must implement.
     */
    public static interface NavigationDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onNavigationDrawerItemSelected(int position);
    }

    @Override
    public void drawerItemClick(int position) {
        if (position == 0) {  //header
            selectItem(position);
            // mDrawerLayout.closeDrawers();

        } else if (position == 1) {    //schedule
            //mDrawerLayout.closeDrawers();
            selectItem(position);
            mDrawerLayout.closeDrawers();

        } else if (position == 2) {    //news
            mDrawerLayout.closeDrawers();

        } else if (position == 3) {    //ranks
            mDrawerLayout.closeDrawers();

        } else if (position == 4) {   //favorite
            DrawerListData listData = drawerListData.get(position);

            if (listData.isExpandListOpened()) {
                listData.setExpandListOpened(false);

                drawerListData.removeAll(listData.getExpandChildItemData());
                mDrawerMenuAdapter.notifyItemChanged(position);
                mDrawerMenuAdapter.notifyItemRangeRemoved(position + 1, listData.getExpandChildItemData().size());
                mDrawerMenuAdapter.settingIndex -= listData.getExpandChildItemData().size();
            } else {
                listData.setExpandListOpened(true);

                drawerListData.addAll(position + 1, listData.getExpandChildItemData());
                mDrawerMenuAdapter.notifyItemChanged(position);
                mDrawerMenuAdapter.notifyItemRangeInserted(position + 1, listData.getExpandChildItemData().size());
                mDrawerMenuAdapter.settingIndex += listData.getExpandChildItemData().size();
            }

            //drawerMenuAdapter.add

        } else if (position >= 5) {
            if(mDrawerMenuAdapter.settingIndex == position) {
                mDrawerLayout.closeDrawers();
            }
            else {
                DrawerListData listData = drawerListData.get(position);
                if(listData.isFavorite()) {
                    listData.setFavorite(false);
                    mDrawerMenuAdapter.notifyItemChanged(position);
                }
                else {
                    listData.setFavorite((true));



                    mDrawerMenuAdapter.notifyItemChanged(position);
                }

            }

        }
    }

    private class DrawerMenuTouchListener implements RecyclerView.OnItemTouchListener {
        final GestureDetector gestureDetector = new GestureDetector(getActivity().getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onDown(MotionEvent e) {
                return super.onDown(e);
            }
        });



        @Override
        public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            View child = recyclerView.findChildViewUnder(motionEvent.getX(),motionEvent.getY());
            //motionEvent.get


            //MaterialRippleLayout testView = (MaterialRippleLayout) child.findViewById(R.id.ripple);


            if(child != null && gestureDetector.onTouchEvent(motionEvent)){

                //Toast.makeText(getActivity().getApplicationContext(),"The Item Clicked is: "+recyclerView.getChildAdapterPosition(child),Toast.LENGTH_SHORT).show();


                int itemPosition = recyclerView.getChildLayoutPosition(child);

                //Log.e("test", " " + itemPosition + ", " + mDrawerMenuAdapter.settingIndex);

                if (itemPosition == 0) {  //header
                    selectItem(0);
                   // mDrawerLayout.closeDrawers();

                } else if (itemPosition == 1) {    //schedule
                    //mDrawerLayout.closeDrawers();

                } else if (itemPosition == 2) {    //news
                    mDrawerLayout.closeDrawers();

                } else if (itemPosition == 3) {    //ranks
                    mDrawerLayout.closeDrawers();

                } else if (itemPosition == 4) {   //favorite
                    DrawerListData listData = drawerListData.get(itemPosition);

                    if (listData.isExpandListOpened()) {
                        listData.setExpandListOpened(false);

                        drawerListData.removeAll(listData.getExpandChildItemData());
                        mDrawerMenuAdapter.notifyItemChanged(itemPosition);
                        mDrawerMenuAdapter.notifyItemRangeRemoved(itemPosition + 1, listData.getExpandChildItemData().size());
                        mDrawerMenuAdapter.settingIndex -= listData.getExpandChildItemData().size();
                    } else {
                        listData.setExpandListOpened(true);

                        drawerListData.addAll(itemPosition + 1, listData.getExpandChildItemData());
                        mDrawerMenuAdapter.notifyItemChanged(itemPosition);
                        mDrawerMenuAdapter.notifyItemRangeInserted(itemPosition + 1, listData.getExpandChildItemData().size());
                        mDrawerMenuAdapter.settingIndex += listData.getExpandChildItemData().size();
                    }

                    //drawerMenuAdapter.add

                } else if (itemPosition >= 5) {
                    if(mDrawerMenuAdapter.settingIndex == itemPosition) {
                        mDrawerLayout.closeDrawers();
                    }
                    else {
                        DrawerListData listData = drawerListData.get(itemPosition);
                        if(listData.isFavorite()) {
                            listData.setFavorite(false);
                            mDrawerMenuAdapter.notifyItemChanged(itemPosition);
                        }
                        else {
                            listData.setFavorite((true));



                            mDrawerMenuAdapter.notifyItemChanged(itemPosition);
                        }

                    }

                }
                return true;

            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

        }

    }
}
