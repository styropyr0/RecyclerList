package com.matrix.recycler_list.interfaces;

import androidx.recyclerview.widget.RecyclerView;

/**
 * A method which runs when the adapter is attached to the RecyclerView.
 */
public interface AttachedToRecyclerView {
    void onAttachedToRecyclerView(RecyclerView recyclerView);
}
