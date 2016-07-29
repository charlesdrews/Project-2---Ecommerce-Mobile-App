package com.charlesdrews.superherostore.team.dialog.presenter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.charlesdrews.superherostore.R;
import com.charlesdrews.superherostore.characters.interfaces.DataBinder;
import com.charlesdrews.superherostore.characters.model.CharacterModel;

/**
 * Created by charlie on 7/28/16.
 */
public class TeamItemViewHolder extends DataBinder<CharacterModel> {
    private CharacterModel mCharacter;
    private ImageView mImage;
    private TextView mName;

    public TeamItemViewHolder(View itemView) {
        super(itemView);

        mImage = (ImageView) itemView.findViewById(R.id.team_list_item_image);
        mName = (TextView) itemView.findViewById(R.id.team_list_item_name);
    }

    @Override
    public void bindData(CharacterModel data) {
        mCharacter = data;

        mName.setText(data.getName());

        Glide.with(itemView.getContext())
                .load(data.getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(mImage);
    }

    public CharacterModel getCharacter() {
        return mCharacter;
    }
}
