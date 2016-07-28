package com.charlesdrews.superherostore.characters.list.presenter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

import com.charlesdrews.superherostore.characters.interfaces.CharacterRvLayoutManager;

/**
 * Created by charlie on 7/28/16.
 */
public class CharacterRvGridLayoutManager extends GridLayoutManager
        implements CharacterRvLayoutManager {

    public CharacterRvGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    public int getFirstVisibleItemPosition() {
        return findFirstVisibleItemPosition();
    }
}
