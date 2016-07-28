package com.charlesdrews.superherostore.characters.list.presenter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import com.charlesdrews.superherostore.characters.interfaces.CharacterRvLayoutManager;

/**
 * Created by charlie on 7/28/16.
 */
public class CharacterRvLinearLayoutManager extends LinearLayoutManager
        implements CharacterRvLayoutManager {

    public CharacterRvLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    @Override
    public int getFirstVisibleItemPosition() {
        return findFirstVisibleItemPosition();
    }
}
