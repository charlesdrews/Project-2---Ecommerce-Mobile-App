package com.charlesdrews.superherostore.team.dialog;

import android.app.Dialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by charlie on 7/28/16.
 */
public class TeamDialogFragment extends DialogFragment {
    private static final String CHARACTER_IDS_KEY = "character_ids_key";

    public TeamDialogFragment() {}

    public static TeamDialogFragment newInstance(ArrayList<Integer> characterIds) {
        TeamDialogFragment fragment = new TeamDialogFragment();
        Bundle args = new Bundle();
        args.putIntegerArrayList(CHARACTER_IDS_KEY, characterIds);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        RecyclerView recyclerView = new RecyclerView(getContext());
        builder.setView(recyclerView);

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2);
        } else {
            recyclerView.setLayoutManager(
                    new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        }

        return builder.create();
    }
}
