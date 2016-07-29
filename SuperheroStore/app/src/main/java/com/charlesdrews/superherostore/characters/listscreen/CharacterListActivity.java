package com.charlesdrews.superherostore.characters.listscreen;

import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.charlesdrews.superherostore.R;
import com.charlesdrews.superherostore.characters.data.DatabaseHelper;
import com.charlesdrews.superherostore.characters.detailscreen.CharacterDetailActivity;
import com.charlesdrews.superherostore.characters.interfaces.CharacterRvOnItemClickListener;
import com.charlesdrews.superherostore.characters.listscreen.presenter.CharacterRvAdapter;
import com.charlesdrews.superherostore.characters.listscreen.presenter.CharacterRvOnScrollListener;
import com.charlesdrews.superherostore.characters.listscreen.presenter.GridDividerItemDecoration;
import com.charlesdrews.superherostore.characters.listscreen.presenter.LinearDividerItemDecoration;
import com.charlesdrews.superherostore.characters.listscreen.presenter.WrapGridLayoutManager;
import com.charlesdrews.superherostore.characters.listscreen.presenter.WrapLinearLayoutManager;
import com.charlesdrews.superherostore.characters.model.CharacterModel;
import com.charlesdrews.superherostore.team.dialog.TeamDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class CharacterListActivity extends AppCompatActivity
        implements CharacterRvOnItemClickListener {

    private static final String TAG = CharacterListActivity.class.getSimpleName();
    private static final String LIST_STATE_KEY = "list_state_key";
    private static final String CURRENT_PAGE_KEY = "current_page_key";
    private static final String TEAM_DIALOG_TAG = "team_dialog_tag";
    private static final int NUMBER_OF_COLUMNS = 2;

    public static final String CHARACTER_ID_KEY = "character_id_key";

    protected DatabaseHelper mDatabaseHelper;
    protected RecyclerView mRecyclerView;
    protected LinearLayoutManager mLayoutManager;
    protected CharacterRvOnScrollListener mOnScrollListener;
    protected CharacterRvAdapter mAdapter;
    protected List<CharacterModel> mCharacters;
    protected Parcelable mListState;
    protected int mCurrentPage = 0;
    protected boolean mDisplayingSearchResults = false;
    protected String mCurrentQuery = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDatabaseHelper = DatabaseHelper.getInstance(this);
        mCharacters = new ArrayList<>();

        mRecyclerView = (RecyclerView) findViewById(R.id.character_list_recycler_view);

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mLayoutManager = new WrapGridLayoutManager(this, NUMBER_OF_COLUMNS);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.addItemDecoration(new GridDividerItemDecoration(NUMBER_OF_COLUMNS));
        } else {
            mLayoutManager = new WrapLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.addItemDecoration(new LinearDividerItemDecoration());
        }

        if (savedInstanceState != null) {
            mListState = savedInstanceState.getParcelable(LIST_STATE_KEY);
            mCurrentPage = savedInstanceState.getInt(CURRENT_PAGE_KEY, 0);
        }

        mOnScrollListener = new CharacterRvOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                Log.d(TAG, "onLoadMore: loading page " + currentPage);
                mCurrentPage = currentPage;

                if (mDisplayingSearchResults) {
                    mCharacters.addAll(mDatabaseHelper.searchCharacters(mCurrentQuery, currentPage));
                } else {
                    mCharacters.addAll(mDatabaseHelper.getAllCharacters(currentPage));
                }

                mAdapter.notifyItemRangeInserted(
                        currentPage * DatabaseHelper.PAGE_SIZE,
                        DatabaseHelper.PAGE_SIZE
                );
            }
        };

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                TeamDialogFragment teamDialog = TeamDialogFragment.newInstance(new Bundle());
                teamDialog.show(fragmentManager, TEAM_DIALOG_TAG);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        for (int i = 0; i <= mCurrentPage; i++) {
            mCharacters.addAll(mDatabaseHelper.getAllCharacters(i));
        }

        mAdapter = new CharacterRvAdapter(mCharacters, this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(mOnScrollListener);

        if (mListState != null) {
            mLayoutManager.onRestoreInstanceState(mListState);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        mRecyclerView.removeOnScrollListener(mOnScrollListener);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        mListState = mLayoutManager.onSaveInstanceState();
        outState.putParcelable(LIST_STATE_KEY, mListState);

        outState.putInt(CURRENT_PAGE_KEY, mCurrentPage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_character_list, menu);

        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                doSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 2) {
                    doSearch(newText);
                    return true;
                }
                return false;
            }
        });

        MenuItemCompat.setOnActionExpandListener(menu.findItem(R.id.action_search),
                new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                mDisplayingSearchResults = false;
                mCurrentQuery = "";
                mCurrentPage = 0;

                mCharacters.clear();
                mCharacters.addAll(mDatabaseHelper.getAllCharacters());
                mAdapter.notifyDataSetChanged();

                return true;
            }
        });

        return true;
    }

    private void doSearch(String query) {
        Log.d(TAG, "doSearch: searching for " + query);

        mCharacters.clear();
        mCharacters.addAll(mDatabaseHelper.searchCharacters(query));
        mAdapter.notifyDataSetChanged();

        mCurrentPage = 0;
        mDisplayingSearchResults = true;
        mCurrentQuery = query;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCharacterSelected(View v, int characterId) {
        Intent intent = new Intent(this, CharacterDetailActivity.class);
        intent.putExtra(CHARACTER_ID_KEY, characterId);

        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(
                        this,
                        v.findViewById(R.id.character_list_item_image),
                        getString(R.string.character_list_to_detail_shared_element_name)
                );

        startActivity(intent, options.toBundle());
    }
}
