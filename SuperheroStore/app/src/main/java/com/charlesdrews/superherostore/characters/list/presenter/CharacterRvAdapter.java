package com.charlesdrews.superherostore.characters.list.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.charlesdrews.superherostore.R;
import com.charlesdrews.superherostore.characters.interfaces.DataBinder;
import com.charlesdrews.superherostore.characters.model.CharacterModel;

import java.util.List;

/**
 * Created by charlie on 7/28/16.
 */
public class CharacterRvAdapter extends RecyclerView.Adapter<DataBinder<CharacterModel>> {
    private List<CharacterModel> mCharacters;

    public CharacterRvAdapter(List<CharacterModel> characters) {
        mCharacters = characters;
    }

    @Override
    public DataBinder<CharacterModel> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.character_list_item, parent, false);
        return new CharacterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataBinder<CharacterModel> holder, int position) {
        holder.bindData(mCharacters.get(position));
    }

    @Override
    public int getItemCount() {
        return mCharacters.size();
    }
}
