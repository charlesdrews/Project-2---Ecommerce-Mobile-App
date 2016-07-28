package com.charlesdrews.superherostore.characters.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.charlesdrews.superherostore.R;
import com.charlesdrews.superherostore.characters.data.contracts.CharactersDatabaseContract;
import com.charlesdrews.superherostore.characters.data.contracts.ChosenCharactersDatabaseContract;
import com.charlesdrews.superherostore.characters.data.contracts.TeamsDatabaseContract;
import com.charlesdrews.superherostore.characters.model.CharacterModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by charlie on 7/27/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = DatabaseHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "data.db";
    private static final int DATABASE_VERSION = 1;

    public static final int PAGE_SIZE = 10;

    private static DatabaseHelper sInstance;

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        loadCharacters(context);
    }

    public static DatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public List<CharacterModel> getAllCharacters() {
        return getAllCharacters(0);
    }

    public List<CharacterModel> getAllCharacters(int pageNumber) {
        List<CharacterModel> characters = new ArrayList<>(PAGE_SIZE);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                CharactersDatabaseContract.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                CharactersDatabaseContract._ID,             // order by id
                (pageNumber * PAGE_SIZE) + "," + PAGE_SIZE  // offset,limit
        );

        if (cursor.moveToFirst()) {
            int idIdx = cursor.getColumnIndex(CharactersDatabaseContract._ID);
            int nameIdx = cursor.getColumnIndex(CharactersDatabaseContract.NAME_COLUMN);
            int descIdx = cursor.getColumnIndex(CharactersDatabaseContract.DESCRIPTION_COLUMN);
            int countIdx = cursor.getColumnIndex(CharactersDatabaseContract.COMIC_COUNT_COLUMN);
            int imgUrlIdx = cursor.getColumnIndex(CharactersDatabaseContract.IMAGE_URL_COLUMN);

            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(idIdx);
                String name = cursor.getString(nameIdx);
                String description = cursor.getString(descIdx);
                int comicCount = cursor.getInt(countIdx);
                String imageUrl = cursor.getString(imgUrlIdx);

                characters.add(new CharacterModel(id, name, description, comicCount, imageUrl));
                Log.d(TAG, "getAllCharacters: adding " + id + " " + name);
                cursor.moveToNext();
            }
        }

        cursor.close();
        return characters;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CharactersDatabaseContract.getCreateTableStatement());
        db.execSQL(TeamsDatabaseContract.getCreateTableStatement());
        db.execSQL(ChosenCharactersDatabaseContract.getCreateTableStatement());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CharactersDatabaseContract.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TeamsDatabaseContract.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ChosenCharactersDatabaseContract.TABLE_NAME);
        onCreate(db);
    }

    private void loadCharacters(Context context) {
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.query(CharactersDatabaseContract.TABLE_NAME, null, null, null, null, null, null);
        if (cursor == null || !cursor.moveToFirst()) {
            BufferedReader inputStream = new BufferedReader(
                    new InputStreamReader(
                            context.getResources().openRawResource(R.raw.insert_characters_sql)
                    )
            );

            db.beginTransaction();

            try {
                while (inputStream.ready()) {
                    db.execSQL(inputStream.readLine());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        }

        db.close();
    }
}
