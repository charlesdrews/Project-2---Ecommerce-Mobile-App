package com.charlesdrews.superherostore.characters.listscreen.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.charlesdrews.superherostore.R;
import com.charlesdrews.superherostore.characters.interfaces.CharacterRvOnItemClickListener;
import com.charlesdrews.superherostore.characters.interfaces.DataBinder;
import com.charlesdrews.superherostore.characters.model.CharacterModel;

import java.util.List;

/**
 * Created by charlie on 7/28/16.
 */
public class CharacterRvAdapter extends RecyclerView.Adapter<DataBinder<CharacterModel>> {
    private List<CharacterModel> mCharacters;
    private CharacterRvOnItemClickListener mListener;

    public CharacterRvAdapter(List<CharacterModel> characters,
                              CharacterRvOnItemClickListener listener) {
        mCharacters = characters;
        mListener = listener;
    }

    @Override
    public DataBinder<CharacterModel> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.character_list_item, parent, false);
        return new CharacterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataBinder<CharacterModel> holder, final int position) {
        holder.bindData(mCharacters.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onCharacterSelected(view, mCharacters.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCharacters.size();
    }
}
