package com.charlesdrews.superherostore.characters.listscreen.presenter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;


/**
 * Created by charlie on 7/28/16.
 */
public abstract class CharacterRvOnScrollListener extends RecyclerView.OnScrollListener {
    private static final String TAG = CharacterRvOnScrollListener.class.getSimpleName();

    private static final int VISIBLE_THRESHOLD = 5;

    private int mPreviousTotal = 0;
    private boolean mLoading = true;
    private int mCurrentPage = 1;
    private LinearLayoutManager mLayoutManager;

    public CharacterRvOnScrollListener(LinearLayoutManager layoutManager) {
        mLayoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = recyclerView.getChildCount();
        int totalItemCount = mLayoutManager.getItemCount();
        int firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();

        if (mLoading) {
            Log.d(TAG, "onScrolled: loading (" + totalItemCount + " loaded)");
            if (totalItemCount > mPreviousTotal) {
                mLoading = false;
                mPreviousTotal = totalItemCount;
            }
        }

        if (!mLoading &&
                (totalItemCount < firstVisibleItem + visibleItemCount + VISIBLE_THRESHOLD)) {
            Log.d(TAG, "onScrolled: need more");
            mCurrentPage++;
            onLoadMore(mCurrentPage);
            mLoading = true;
        }
    }

    public abstract void onLoadMore(int currentPage);
}
