package com.charlesdrews.superherostore.characters.list;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.charlesdrews.superherostore.R;
import com.charlesdrews.superherostore.characters.data.DatabaseHelper;
import com.charlesdrews.superherostore.characters.detail.CharacterDetailActivity;
import com.charlesdrews.superherostore.characters.interfaces.CharacterRvLayoutManager;
import com.charlesdrews.superherostore.characters.interfaces.CharacterRvOnItemClickListener;
import com.charlesdrews.superherostore.characters.list.presenter.CharacterRvAdapter;
import com.charlesdrews.superherostore.characters.list.presenter.CharacterRvLinearLayoutManager;
import com.charlesdrews.superherostore.characters.list.presenter.CharacterRvOnScrollListener;
import com.charlesdrews.superherostore.characters.model.CharacterModel;

import java.util.ArrayList;
import java.util.List;

public class CharacterListActivity extends AppCompatActivity
        implements CharacterRvOnItemClickListener {

    private static final String TAG = CharacterListActivity.class.getSimpleName();
    private static final String LIST_STATE_KEY = "list_state_key";
    private static final String CURRENT_PAGE_KEY = "current_page_key";

    public static final String CHARACTER_ID_KEY = "character_id_key";

    protected DatabaseHelper mDatabaseHelper;
    protected RecyclerView mRecyclerView;
    protected CharacterRvLinearLayoutManager mLayoutManager;
    protected CharacterRvOnScrollListener mOnScrollListener;
    protected CharacterRvAdapter mAdapter;
    protected List<CharacterModel> mCharacters;
    protected Parcelable mListState;
    protected int mCurrentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDatabaseHelper = DatabaseHelper.getInstance(this);
        mCharacters = new ArrayList<>();

        mRecyclerView = (RecyclerView) findViewById(R.id.character_list_recycler_view);

        mLayoutManager = new CharacterRvLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        if (savedInstanceState != null) {
            mListState = savedInstanceState.getParcelable(LIST_STATE_KEY);
            mCurrentPage = savedInstanceState.getInt(CURRENT_PAGE_KEY, 0);
        }

        mOnScrollListener = new CharacterRvOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                Log.d(TAG, "onLoadMore: loading page " + currentPage);
                mCurrentPage = currentPage;
                mCharacters.addAll(mDatabaseHelper.getAllCharacters(currentPage));
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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");

        for (int i = 0; i <= mCurrentPage; i++) {
            mCharacters.addAll(mDatabaseHelper.getAllCharacters(i));
        }

        mAdapter = new CharacterRvAdapter(mCharacters, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(mOnScrollListener);

        if (mListState != null) {
            Log.d(TAG, "onResume: restore list state");
            mLayoutManager.onRestoreInstanceState(mListState);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
        mRecyclerView.removeOnScrollListener(mOnScrollListener);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: ");

        mListState = mLayoutManager.onSaveInstanceState();
        outState.putParcelable(LIST_STATE_KEY, mListState);

        outState.putInt(CURRENT_PAGE_KEY, mCurrentPage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_character_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
