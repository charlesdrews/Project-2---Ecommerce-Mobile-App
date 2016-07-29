package com.charlesdrews.superherostore.team.dialog;


import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.charlesdrews.superherostore.R;
import com.charlesdrews.superherostore.characters.listscreen.presenter.GridDividerItemDecoration;
import com.charlesdrews.superherostore.characters.listscreen.presenter.LinearDividerItemDecoration;
import com.charlesdrews.superherostore.characters.model.CharacterModel;
import com.charlesdrews.superherostore.team.data.TeamDataStore;
import com.charlesdrews.superherostore.team.dialog.presenter.TeamItemViewHolder;
import com.charlesdrews.superherostore.team.dialog.presenter.TeamRvAdapter;

import java.util.List;

public class TeamDialogFragment extends DialogFragment {
    private static final Double PERCENTAGE_OF_SCREEN_SIZE = 0.95;
    private static final int NUMBER_OF_COLUMNS = 2;

    private ImageButton mCloseButton;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mDoneButton;
    private TeamRvAdapter mAdapter;
    private TeamDataStore mTeamDataStore;
    private List<CharacterModel> mCharactersInTeam;

    public TeamDialogFragment() {}

    public static TeamDialogFragment newInstance(Bundle args) {
        TeamDialogFragment fragment = new TeamDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team_dialog, container, false);

        mCloseButton = (ImageButton) view.findViewById(R.id.team_close_button);
        mDoneButton = (FloatingActionButton) view.findViewById(R.id.team_fab);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.team_recycler_view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView.setBackgroundResource(R.color.colorPrimaryDark);

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), NUMBER_OF_COLUMNS));
            mRecyclerView.addItemDecoration(new GridDividerItemDecoration(NUMBER_OF_COLUMNS));
        } else {
            mRecyclerView.setLayoutManager(
                    new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            mRecyclerView.addItemDecoration(new LinearDividerItemDecoration());
        }

        mTeamDataStore = TeamDataStore.getInstance();
        mCharactersInTeam = mTeamDataStore.getCharacters();
        mAdapter = new TeamRvAdapter(mCharactersInTeam);
        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(getSimpleCallback());
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        mCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        mDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO - add team to database
                mTeamDataStore.removeAllCharacters();
                getDialog().dismiss();
                Toast.makeText(getActivity(), "Team Created!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        Point size = new Point();
        getDialog().getWindow().getWindowManager().getDefaultDisplay().getSize(size);
        int desiredWidth = (int) ((double) size.x * PERCENTAGE_OF_SCREEN_SIZE);
        int desiredHeight = (int) ((double) size.y * PERCENTAGE_OF_SCREEN_SIZE);

        getDialog().getWindow().setLayout(desiredWidth, desiredHeight);
    }

    private ItemTouchHelper.SimpleCallback getSimpleCallback() {
        return new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                CharacterModel character = ((TeamItemViewHolder) viewHolder).getCharacter();
                mTeamDataStore.removeCharacter(character);
                mCharactersInTeam.remove(character);
                mAdapter.notifyDataSetChanged();

                //TODO set up snackbars for removing characters from team
                /*
                // prep snackbar to show if character is removed (user clicks "UNDO")
                final Snackbar removeSnackBar = Snackbar.make(view, removeCharacterMessage,
                        Snackbar.LENGTH_LONG);
                removeSnackBar.getView().setBackgroundResource(R.color.colorAccent);
                ((TextView) removeSnackBar.getView()
                        .findViewById(android.support.design.R.id.snackbar_text))
                        .setTextColor(Color.BLACK);

                // prep snackbar to show character was added and offer chance to UNDO
                final Snackbar addSnackBar = Snackbar
                        .make(view, addCharacterMessage, Snackbar.LENGTH_INDEFINITE);
                addSnackBar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // for UNDO action, remove character & show remove snackbar
                        teamDataStore.removeCharacter(mSelectedCharacter);
                        removeSnackBar.show();
                        addSnackBar.dismiss();
                    }
                }).show();
                */
            }
        };
    }
}
