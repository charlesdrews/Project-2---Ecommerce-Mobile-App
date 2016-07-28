package com.charlesdrews.superherostore.characters.listscreen.presenter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.charlesdrews.superherostore.R;

/**
 * Created by charlie on 7/28/16.
 */
public class GridDividerItemDecoration extends RecyclerView.ItemDecoration {
    private int mNumberOfColumns;

    public GridDividerItemDecoration(int numberOfColumns) {
        mNumberOfColumns = numberOfColumns;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        boolean childIsInFirstColumn = (parent.getChildAdapterPosition(view) % mNumberOfColumns) == 0;
        if (!childIsInFirstColumn) {
            outRect.left = (int) view.getResources()
                    .getDimension(R.dimen.recycler_view_divider_width);
        }

        boolean childIsInFirstRow = (parent.getChildAdapterPosition(view)) < mNumberOfColumns;
        if (!childIsInFirstRow) {
            outRect.top = (int) view.getResources()
                    .getDimension(R.dimen.recycler_view_divider_width);
        }
    }
}
