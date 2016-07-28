package com.charlesdrews.superherostore.characters.data.contracts;

import android.provider.BaseColumns;

/**
 * Created by charlie on 7/27/16.
 */
public class CharactersDatabaseContract implements BaseColumns {
    public static final String TABLE_NAME = "characters";
    public static final String NAME_COLUMN = "name";
    public static final String DESCRIPTION_COLUMN = "description";
    public static final String COMIC_COUNT_COLUMN = "comic_count";
    public static final String IMAGE_URL_COLUMN = "image_url";

    public static String getCreateTableStatement() {
        return "CREATE TABLE " + TABLE_NAME + " ("
                + _ID + " INTEGER NOT NULL PRIMARY KEY, "
                + NAME_COLUMN + " TEXT NOT NULL, "
                + DESCRIPTION_COLUMN + " TEXT NOT NULL, "
                + COMIC_COUNT_COLUMN + " INTEGER NOT NULL, "
                + IMAGE_URL_COLUMN + " TEXT NOT NULL)";
    }
}
