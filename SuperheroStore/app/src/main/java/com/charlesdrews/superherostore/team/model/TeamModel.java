package com.charlesdrews.superherostore.team.model;

import com.charlesdrews.superherostore.characters.model.CharacterModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by charlie on 7/28/16.
 */
public class TeamModel {
    private String mName;
    private long mDateCreated;
    private List<CharacterModel> mCharacters;

    public TeamModel() {
        this("Default Team Name");
    }

    public TeamModel(String name) {
        mName = name;
        mDateCreated = System.currentTimeMillis();
        mCharacters = new ArrayList<>();
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public long getDateCreated() {
        return mDateCreated;
    }

    public List<CharacterModel> getCharacters() {
        return mCharacters;
    }

    public void setCharacters(List<CharacterModel> characters) {
        mCharacters = characters;
    }

    public boolean addCharacter(CharacterModel character) {
        return mCharacters.add(character);
    }

    public boolean removeCharacter(CharacterModel character) {
        return mCharacters.remove(character);
    }
}
