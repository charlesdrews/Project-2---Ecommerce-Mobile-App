package com.charlesdrews.superherostore.team.data;

import com.charlesdrews.superherostore.characters.model.CharacterModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by charlie on 7/28/16.
 */
public class TeamDataStore {
    private static TeamDataStore sInstance = new TeamDataStore();

    private String mTeamName;
    private List<CharacterModel> mCharacters;

    public static TeamDataStore getInstance() {
        if (sInstance == null) {
            sInstance = new TeamDataStore();
        }
        return sInstance;
    }

    private TeamDataStore() {
        mTeamName = "Default Team Name";
        mCharacters = new ArrayList<>();
    }

    public String getTeamName() {
        return mTeamName;
    }

    public void setTeamName(String teamName) {
        mTeamName = teamName;
    }

    public List<CharacterModel> getCharacters() {
        return mCharacters;
    }

    public List<Integer> getCharacterIds() {
        List<Integer> ids = new ArrayList<>(mCharacters.size());
        for (CharacterModel character : mCharacters) {
            ids.add(character.getId());
        }
        return ids;
    }

    public boolean addCharacter(CharacterModel character) {
        return mCharacters.add(character);
    }

    public boolean removeCharacter(CharacterModel character) {
        return mCharacters.remove(character);
    }
}
