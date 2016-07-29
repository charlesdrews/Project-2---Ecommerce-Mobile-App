package com.charlesdrews.superherostore.team.dialog.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.charlesdrews.superherostore.R;
import com.charlesdrews.superherostore.characters.model.CharacterModel;

import java.util.List;

/**
 * Created by charlie on 7/28/16.
 */
public class TeamRvAdapter extends RecyclerView.Adapter<TeamItemViewHolder> {
    private List<CharacterModel> mCharacters;

    public TeamRvAdapter(List<CharacterModel> characters) {
        mCharacters = characters;
    }

    @Override
    public TeamItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.team_list_item, parent, false);
        return new TeamItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TeamItemViewHolder holder, int position) {
        holder.bindData(mCharacters.get(position));
    }

    @Override
    public int getItemCount() {
        return mCharacters.size();
    }
}
