package com.charlesdrews.superherostore.characters.interfaces;

/**
 * Created by charlie on 7/28/16.
 */
public interface CharacterRvLayoutManager {

    // if extending LinearLayoutManager or GridLayoutManager,
    // simply return findFirstVisibleItemPosition()

    // if extending StaggeredGridLayoutManager, return the lowest of the ints
    // returned by findFirstVisibleItemPositions()

    int getFirstVisibleItemPosition();
    int getItemCount();
}
