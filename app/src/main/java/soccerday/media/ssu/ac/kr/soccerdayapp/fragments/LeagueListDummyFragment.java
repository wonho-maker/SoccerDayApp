package soccerday.media.ssu.ac.kr.soccerdayapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import soccerday.media.ssu.ac.kr.soccerdayapp.R;

/**
 * Created by Wonho Lee on 2015-05-05.
 */
public class LeagueListDummyFragment extends Fragment {

    public LeagueListDummyFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View leagueListFragmentDummyView = (View)inflater.inflate(R.layout.fragment_league_list_dummy, container, false);

        return leagueListFragmentDummyView;
    }
}
