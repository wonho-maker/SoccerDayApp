package soccerday.media.ssu.ac.kr.soccerdayapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import soccerday.media.ssu.ac.kr.soccerdayapp.LeagueData;
import soccerday.media.ssu.ac.kr.soccerdayapp.R;
import soccerday.media.ssu.ac.kr.soccerdayapp.drawer.DrawerListData;
import soccerday.media.ssu.ac.kr.soccerdayapp.drawer.DrawerMenuAdapter;

/**
 * Created by Wonho Lee on 2015-05-01.
 */
public class LeagueListFragment extends Fragment {

    RecyclerView mRecyclerView;
    LigeaueListAdapter mLigeaueListAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    List<LeagueListData> mLeagueListData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View leagueListFragmentView = (View)inflater.inflate(R.layout.fragment_league_list, container, false);

        mRecyclerView = (RecyclerView)leagueListFragmentView.findViewById(R.id.league_list_container_recycler);

        mRecyclerView.setHasFixedSize(true);

        //mRecyclerView.set
        //test
        mLeagueListData = new ArrayList<>();

        String leagueTitles[] = getResources().getStringArray(R.array.league_titles);

        for(int i =0; i < leagueTitles.length; i++) {
            LeagueListData temp = new LeagueListData(leagueTitles[i], LeagueData.leageEmblemURL(leagueTitles[i]));
            mLeagueListData.add(temp);
        }

        ////

        mLigeaueListAdapter = new LigeaueListAdapter(mLeagueListData, R.drawable.ic_indicator_up, R.drawable.ic_indicator
        , getActivity().getApplicationContext());

        mRecyclerView.setAdapter(mLigeaueListAdapter);

        mLayoutManager = new LinearLayoutManager(this.getActivity().getApplicationContext());



        mRecyclerView.setLayoutManager(mLayoutManager);

        return leagueListFragmentView;
    }

}
