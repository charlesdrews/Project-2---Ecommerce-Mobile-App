package com.charlesdrews.superherostore.characters.list.presenter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by charlie on 7/28/16.
 */
public class WrapGridLayoutManager extends GridLayoutManager {
    private static final String TAG = WrapGridLayoutManager.class.getSimpleName();

    public WrapGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    // likely bug in RecyclerView per http://stackoverflow.com/a/38611931
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException e) {
            Log.e(TAG, "onLayoutChildren: IOOBE bug", e);
        }
    }
}
