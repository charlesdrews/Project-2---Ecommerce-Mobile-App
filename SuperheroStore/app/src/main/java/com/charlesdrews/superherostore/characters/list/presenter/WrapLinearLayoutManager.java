package com.charlesdrews.superherostore.characters.list.presenter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.charlesdrews.superherostore.characters.interfaces.CharacterRvLayoutManager;

/**
 * Created by charlie on 7/28/16.
 */
public class WrapLinearLayoutManager extends LinearLayoutManager {
    private static final String TAG = WrapLinearLayoutManager.class.getSimpleName();

    public WrapLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
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
