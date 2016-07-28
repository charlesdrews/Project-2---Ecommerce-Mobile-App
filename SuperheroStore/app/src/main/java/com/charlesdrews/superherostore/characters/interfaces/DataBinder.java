package com.charlesdrews.superherostore.characters.interfaces;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by charlie on 7/28/16.
 */
public abstract class DataBinder<T> extends RecyclerView.ViewHolder {
    public DataBinder(View itemView) {
        super(itemView);
    }

    public abstract void bindData(T data);
}
