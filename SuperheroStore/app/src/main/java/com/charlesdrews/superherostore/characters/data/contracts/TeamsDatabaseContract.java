package com.charlesdrews.superherostore.characters.data.contracts;

import android.provider.BaseColumns;

/**
 * Created by charlie on 7/27/16.
 */
public class TeamsDatabaseContract implements BaseColumns {
    public static final String TABLE_NAME = "teams";
    public static final String NAME_COLUMN = "name";
    public static final String DATE_COLUMN = "date";

    public static String getCreateTableStatement() {
        return "CREATE TABLE " + TABLE_NAME + " ("
                + _ID + " INTEGER NOT NULL PRIMARY KEY, "
                + NAME_COLUMN + " TEXT NOT NULL, "
                + DATE_COLUMN + " INTEGER NOT NULL)";
    }
}
