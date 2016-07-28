package com.charlesdrews.superherostore.characters.list.presenter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.charlesdrews.superherostore.R;

/**
 * Created by charlie on 7/28/16.
 */
public class LinearDividerItemDecoration extends RecyclerView.ItemDecoration {
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (parent.getChildAdapterPosition(view) > 0) {
            outRect.top = (int) view.getResources()
                    .getDimension(R.dimen.recycler_view_divider_width);
        }
    }
}
