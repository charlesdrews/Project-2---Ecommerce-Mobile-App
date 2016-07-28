package com.charlesdrews.superherostore.characters.detailscreen;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.charlesdrews.superherostore.R;
import com.charlesdrews.superherostore.characters.data.DatabaseHelper;
import com.charlesdrews.superherostore.characters.listscreen.CharacterListActivity;
import com.charlesdrews.superherostore.characters.model.CharacterModel;
import com.charlesdrews.superherostore.team.data.TeamDataStore;

public class CharacterDetailActivity extends AppCompatActivity {
    CharacterModel mSelectedCharacter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int characterId = getIntent().getIntExtra(CharacterListActivity.CHARACTER_ID_KEY, -1);
        if (characterId >= 0) {
            // get references to views
            ImageView imageView = (ImageView) findViewById(R.id.character_detail_image);
            TextView description = (TextView) findViewById(R.id.character_detail_description);
            TextView comicCount = (TextView) findViewById(R.id.character_detail_comic_count);

            // get reference to selected character
            DatabaseHelper databaseHelper = DatabaseHelper.getInstance(this);
            mSelectedCharacter = databaseHelper.getCharacterById(characterId);

            if (mSelectedCharacter == null) {
                description.setText(getString(R.string.character_not_found));
            } else {

                // put character name in toolbar
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(mSelectedCharacter.getName());
                }

                // update text & image views
                if (mSelectedCharacter.getDescription().isEmpty()) {
                    description.setText(getString(R.string.character_desc_unavailable));
                } else {
                    description.setText(mSelectedCharacter.getDescription());
                }

                comicCount.setText(
                        String.format(
                                getString(R.string.character_comic_count),
                                mSelectedCharacter.getComicCount()
                        )
                );

                Glide.with(this)
                        .load(mSelectedCharacter.getImageUrl())
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .into(imageView);
            }
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // set up message strings for snackbars
                String addCharacterMessage = String.format(
                        getString(R.string.add_to_team_message), mSelectedCharacter.getName());
                final String removeCharacterMessage = String.format(
                        getString(R.string.remove_from_team_message), mSelectedCharacter.getName());

                // add character to team
                final TeamDataStore teamDataStore = TeamDataStore.getInstance();
                teamDataStore.addCharacter(mSelectedCharacter);

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

            }
        });
    }
}
