package com.matrix.recycler_list.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.matrix.recycler_list.interfaces.AttachedToRecyclerView
import com.matrix.recycler_list.interfaces.ViewAttachedToWindow
import com.matrix.recycler_list.interfaces.ViewDetachedFromWindow
import com.matrix.recycler_list.interfaces.ViewRecycled

/**
 * A generic RecyclerView Adapter that supports data binding and handling view lifecycle events.
 *
 * @param layoutResId The layout resource ID to be inflated for each item in the RecyclerView.
 * @author Saurav Sajeev
 */

@SuppressLint("NotifyDataSetChanged")
internal class RecyclerListAdapter<T>(internal var layoutResId: Int) :
    RecyclerView.Adapter<RecyclerListAdapter<T>.ViewHolder>() {

    private var items: MutableList<T> = mutableListOf()

    private var binder1: ((View, T, Int) -> Unit) = { _, _, _ -> }
    private var binder2: ((View, T) -> Unit) = { _, _ -> }

    private var viewRecycled: ViewRecycled? = null
    private var viewAttachedToWindow: ViewAttachedToWindow? = null
    private var viewDetachedFromWindow: ViewDetachedFromWindow? = null
    private var attachedToRecyclerView: AttachedToRecyclerView? = null

    /**
     * ViewHolder class that holds the view for each item in the RecyclerView.
     * @param view The view to be held by the ViewHolder.
     */
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
        return ViewHolder(view)
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     */
    override fun onBindViewHolder(holder: ViewHolder, index: Int) {
        binder1.invoke(holder.itemView, items[index], index)
        binder2.invoke(holder.itemView, items[index])
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     */
    override fun getItemCount(): Int = items.size

    /**
     * Sets new data for the adapter with a layout resource ID and a binder function with index.
     * @param newItems The new data to be displayed.
     * @param newLayoutResId The new layout resource ID.
     * @param newBinder The new binder function with index.
     */
    fun setData(newItems: List<T>, newLayoutResId: Int, newBinder: (View, T, Int) -> Unit) {
        layoutResId = newLayoutResId
        binder1 = newBinder
        binder2 = { _, _ -> }
        items = newItems.toMutableList()
        notifyDataSetChanged()
    }

    /**
     * Sets new data for the adapter with a layout resource ID and a binder function without index.
     * @param newItems The new data to be displayed.
     * @param newLayoutResId The new layout resource ID.
     * @param newBinder The new binder function without index.
     */
    fun setData(newItems: List<T>, newLayoutResId: Int, newBinder: (View, T) -> Unit) {
        layoutResId = newLayoutResId
        binder2 = newBinder
        binder1 = { _, _, _ -> }
        items = newItems.toMutableList()
        notifyDataSetChanged()
    }

    /**
     * Removes an item from the data set at the specified index.
     * @param index The index of the item to be removed.
     */
    fun removeItem(index: Int) {
        if (index >= 0 && index < items.size) {
            items.removeAt(index)
            notifyItemRemoved(index)
            notifyItemRangeChanged(index, items.size - index)
        }
    }

    /**
     * Sets a listener for when a view is recycled.
     * @param viewRecycled The listener for when a view is recycled.
     */
    fun triggerWhenViewRecycled(viewRecycled: ViewRecycled) {
        this.viewRecycled = viewRecycled
    }

    /**
     * Called when a view created by this adapter has been recycled.
     */
    override fun onViewRecycled(holder: RecyclerListAdapter<T>.ViewHolder) {
        super.onViewRecycled(holder)
        viewRecycled?.onViewRecycled()
    }

    /**
     * Sets a listener for when a view is attached to the window.
     * @param viewAttachedToWindow The listener for when a view is attached to the window.
     */
    fun triggerWhenViewAttachedToWindow(viewAttachedToWindow: ViewAttachedToWindow) {
        this.viewAttachedToWindow = viewAttachedToWindow
    }

    /**
     * Sets a listener for when a view is detached from the window.
     * @param viewDetachedFromWindow The listener for when a view is detached from the window.
     */
    fun triggerWhenViewDetachedFromWindow(viewDetachedFromWindow: ViewDetachedFromWindow) {
        this.viewDetachedFromWindow = viewDetachedFromWindow
    }

    /**
     * Called when a view created by this adapter has been attached to the window.
     */
    override fun onViewAttachedToWindow(holder: RecyclerListAdapter<T>.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        viewAttachedToWindow?.onViewAttachedToWindow()
    }

    /**
     * Called when a view created by this adapter has been detached from the window.
     */
    override fun onViewDetachedFromWindow(holder: RecyclerListAdapter<T>.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        viewDetachedFromWindow?.onViewDetachedFromWindow()
    }

    /**
     * Sets a listener for when the adapter is attached to the RecyclerView.
     * @param attachedToRecyclerView The listener for when the adapter is attached to the RecyclerView.
     */
    fun triggerOnAttachedToRecyclerView(attachedToRecyclerView: AttachedToRecyclerView) {
        this.attachedToRecyclerView = attachedToRecyclerView
    }

    /**
     * Called when the adapter is attached to the RecyclerView.
     */
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        attachedToRecyclerView?.onAttachedToRecyclerView(recyclerView)
    }

    /**
     * Returns the layout resource ID being used by the adapter.
     */
    fun getLayoutResId() = layoutResId
}
