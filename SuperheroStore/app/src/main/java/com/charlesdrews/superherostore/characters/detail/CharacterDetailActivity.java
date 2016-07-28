package com.charlesdrews.superherostore.characters.detail;

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
import com.charlesdrews.superherostore.characters.list.CharacterListActivity;
import com.charlesdrews.superherostore.characters.model.CharacterModel;

public class CharacterDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int characterId = getIntent().getIntExtra(CharacterListActivity.CHARACTER_ID_KEY, -1);
        if (characterId >= 0) {
            ImageView imageView = (ImageView) findViewById(R.id.character_detail_image);
            TextView description = (TextView) findViewById(R.id.character_detail_description);
            TextView comicCount = (TextView) findViewById(R.id.character_detail_comic_count);

            DatabaseHelper databaseHelper = DatabaseHelper.getInstance(this);
            CharacterModel character = databaseHelper.getCharacterById(characterId);

            if (character == null) {
                description.setText("Character not found");
            } else {
                toolbar.setTitle(character.getName());
                if (character.getDescription().isEmpty()) {
                    description.setText("Character description unavailable");
                } else {
                    description.setText(character.getDescription());
                }
                comicCount.setText("Featured in " + character.getComicCount() + " available comics");

                Glide.with(this)
                        .load(character.getImageUrl())
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .into(imageView);

            }
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
