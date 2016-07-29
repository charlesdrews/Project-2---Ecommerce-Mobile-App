package com.charlesdrews.superherostore.team.dialog.presenter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.charlesdrews.superherostore.R;
import com.charlesdrews.superherostore.characters.interfaces.DataBinder;
import com.charlesdrews.superherostore.characters.model.CharacterModel;
import com.squareup.picasso.Picasso;

/**
 * Created by charlie on 7/28/16.
 */
public class TeamItemViewHolder extends DataBinder<CharacterModel> {
    private ImageView mImage;
    private TextView mName;

    public TeamItemViewHolder(View itemView) {
        super(itemView);

        mImage = (ImageView) itemView.findViewById(R.id.team_list_item_image);
        mName = (TextView) itemView.findViewById(R.id.team_list_item_name);
    }

    @Override
    public void bindData(CharacterModel data) {
        mName.setText(data.getName());

        //TODO - look into picasso's caching strategies
        Picasso.with(itemView.getContext())
                .load(data.getImageUrl())
                .into(mImage);
    }
}
