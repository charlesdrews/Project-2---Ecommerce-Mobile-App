package com.charlesdrews.superherostore.team.dialog;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.charlesdrews.superherostore.R;

public class TeamDialogFragment extends DialogFragment {


    public TeamDialogFragment() {}

    public static TeamDialogFragment newInstance(Bundle args) {
        TeamDialogFragment fragment = new TeamDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team_dialog, container, false);
    }

}
