package com.charlesdrews.superherostore.characters.list.presenter;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.charlesdrews.superherostore.R;
import com.charlesdrews.superherostore.characters.interfaces.DataBinder;
import com.charlesdrews.superherostore.characters.model.CharacterModel;

/**
 * Created by charlie on 7/28/16.
 */
public class CharacterViewHolder extends DataBinder<CharacterModel> {

    private ImageView mImage;
    private ProgressBar mProgressBar;
    private TextView mName;

    public CharacterViewHolder(View itemView) {
        super(itemView);

        mImage = (ImageView) itemView.findViewById(R.id.character_list_item_image);
        mProgressBar = (ProgressBar) itemView.findViewById(R.id.character_list_item_progress_bar);
        mName = (TextView) itemView.findViewById(R.id.character_list_item_name);
    }

    @Override
    public void bindData(CharacterModel data) {
        mName.setText(data.getName());

        mProgressBar.setVisibility(View.VISIBLE);

        Glide.with(itemView.getContext())
                .load(data.getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        mProgressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(mImage);
    }
}
