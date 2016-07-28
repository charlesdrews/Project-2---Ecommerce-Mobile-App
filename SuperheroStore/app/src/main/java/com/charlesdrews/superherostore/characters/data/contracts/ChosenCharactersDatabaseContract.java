package com.charlesdrews.superherostore.characters.data.contracts;

import android.provider.BaseColumns;

/**
 * Created by charlie on 7/27/16.
 */
public class ChosenCharactersDatabaseContract implements BaseColumns {
    public static final String TABLE_NAME = "chosen_characters";
    public static final String TEAM_ID_COLUMN = "team_id";
    public static final String CHARACTER_ID_COLUMN = "character_id";

    public static String getCreateTableStatement() {
        return "CREATE TABLE " + TABLE_NAME + " ("
                + _ID + " INTEGER NOT NULL PRIMARY KEY, "
                + TEAM_ID_COLUMN + " INTEGER NOT NULL, "
                + CHARACTER_ID_COLUMN + " INTEGER NOT NULL, "
                + "FOREIGN KEY (" + TEAM_ID_COLUMN + ") REFERENCES "
                    + TeamsDatabaseContract.TABLE_NAME + "(" + TeamsDatabaseContract._ID + "), "
                + "FOREIGN KEY (" + CHARACTER_ID_COLUMN + ") REFERENCES "
                    + CharactersDatabaseContract.TABLE_NAME + "(" + CharactersDatabaseContract._ID + ")"
                +")";
    }
}
