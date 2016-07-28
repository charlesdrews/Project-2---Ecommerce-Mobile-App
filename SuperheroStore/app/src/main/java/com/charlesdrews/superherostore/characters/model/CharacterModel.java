package com.charlesdrews.superherostore.characters.model;

/**
 * Created by charlie on 7/28/16.
 */
public class CharacterModel {
    private int mId, mComicCount;
    private String mName, mDescription, mImageUrl;

    public CharacterModel() {
        mId = -1;
        mName = "Default Name";
        mDescription = "Default Description";
        mComicCount = 0;
        mImageUrl = "http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg";
    }

    public CharacterModel(int id, String name, String description,
                          int comicCount, String imageUrl) {
        mId = id;
        mName = name;
        mDescription = description;
        mComicCount = comicCount;
        mImageUrl = imageUrl;
    }

    public int getId() {
        return mId;
    }

    public int getComicCount() {
        return mComicCount;
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getImageUrl() {
        return mImageUrl;
    }
}
