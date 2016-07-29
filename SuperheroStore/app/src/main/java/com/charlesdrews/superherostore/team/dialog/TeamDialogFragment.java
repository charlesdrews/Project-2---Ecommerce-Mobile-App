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

import com.charlesdrews.superherostore.R;
import com.charlesdrews.superherostore.characters.listscreen.presenter.GridDividerItemDecoration;
import com.charlesdrews.superherostore.characters.listscreen.presenter.LinearDividerItemDecoration;
import com.charlesdrews.superherostore.characters.model.CharacterModel;
import com.charlesdrews.superherostore.team.data.TeamDataStore;
import com.charlesdrews.superherostore.team.dialog.presenter.TeamRvAdapter;

import java.util.List;

/**
 * Created by charlie on 7/28/16.
 */
public class TeamDialogFragment extends DialogFragment {
    private static final int NUMBER_OF_COLUMNS = 2;
    private List<CharacterModel> mCharactersInTeam;

    public TeamDialogFragment() {}

    public static TeamDialogFragment newInstance(Bundle args) {
        TeamDialogFragment fragment = new TeamDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle(getString(R.string.team_dialog_title));

        RecyclerView recyclerView = new RecyclerView(getContext());
        recyclerView.setBackgroundResource(R.color.colorPrimaryDark);

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), NUMBER_OF_COLUMNS));
            recyclerView.addItemDecoration(new GridDividerItemDecoration(NUMBER_OF_COLUMNS));
        } else {
            recyclerView.setLayoutManager(
                    new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            recyclerView.addItemDecoration(new LinearDividerItemDecoration());
        }

        TeamDataStore dataStore = TeamDataStore.getInstance();
        recyclerView.setAdapter(new TeamRvAdapter(dataStore.getCharacters()));

        builder.setView(recyclerView);

        return builder.create();
    }
}
