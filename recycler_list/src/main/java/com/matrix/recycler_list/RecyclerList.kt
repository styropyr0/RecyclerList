package com.matrix.recycler_list

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.matrix.recycler_list.adapter.RecyclerListAdapter
import com.matrix.recycler_list.interfaces.AttachedToRecyclerView
import com.matrix.recycler_list.interfaces.ViewAttachedToWindow
import com.matrix.recycler_list.interfaces.ViewDetachedFromWindow
import com.matrix.recycler_list.interfaces.ViewRecycled

/**
 * A customizable RecyclerView subclass that simplifies the implementation of a list with a LinearLayoutManager.
 * This class provides convenience methods to set items, handle view recycling, and manage various layout configurations.
 *
 * @param T The type of items in the list.
 * @constructor Creates a RecyclerList with optional XML attributes and style.
 * @param context The context of the application environment.
 * @param attrs The attribute set from XML (optional).
 * @param defStyleAttr An attribute in the current theme that contains a reference to a style resource (optional).
 *
 * @author Saurav Sajeev
 */

@SuppressLint("NotifyDataSetChanged")
class RecyclerList<T> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : RecyclerView(context, attrs) {

    private val layoutResId: Int = 0

    private val adapter = RecyclerListAdapter<T>(layoutResId)

    init {
        layoutManager = LinearLayoutManager(context)
        setAdapter(adapter)
    }

    /**
     * Sets a callback to be triggered when a view is recycled.
     * @param viewRecycled The callback to trigger.
     */
    fun onViewRecycled(viewRecycled: ViewRecycled) {
        adapter.triggerWhenViewRecycled(viewRecycled)
    }

    /**
     * Sets a callback to be triggered when a view is attached to the window.
     * @param viewAttachedToWindow The callback to trigger.
     */
    fun onViewAttachedToWindow(viewAttachedToWindow: ViewAttachedToWindow) {
        adapter.triggerWhenViewAttachedToWindow(viewAttachedToWindow)
    }

    /**
     * Sets a callback to be triggered when a view is detached from the window.
     * @param viewDetachedFromWindow The callback to trigger.
     */
    fun onViewDetachedFromWindow(viewDetachedFromWindow: ViewDetachedFromWindow) {
        adapter.triggerWhenViewDetachedFromWindow(viewDetachedFromWindow)
    }

    /**
     * Sets a callback to be triggered when the RecyclerView is attached to the RecyclerView.
     * @param attachedToRecyclerView The callback to trigger.
     */
    fun onAttachedToRecyclerView(attachedToRecyclerView: AttachedToRecyclerView) {
        adapter.triggerOnAttachedToRecyclerView(attachedToRecyclerView)
    }

    /**
     * Sets whether the adapter has stable IDs.
     * @param hasStableIds Whether the adapter has stable IDs.
     */
    fun setHasStableIds(hasStableIds: Boolean) {
        adapter.setHasStableIds(hasStableIds)
    }

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)
    }

    /**
     * Sets the list of items to be displayed and binds each item to the view.
     * @param items The list of items to display.
     * @param newLayoutResId Optional new layout resource ID for the items.
     * @param binder The function to bind each item to the view, with item position.
     */
    fun setItems(items: List<T>, newLayoutResId: Int? = null, binder: (View, T, Int) -> Unit) {
        adapter.setData(items, newLayoutResId ?: adapter.layoutResId, binder)
    }

    /**
     * Sets the list of items to be displayed and binds each item to the view.
     * This function uses a different binder signature.
     * @param items The list of items to display.
     * @param newLayoutResId Optional new layout resource ID for the items.
     * @param binder The function to bind each item to the view.
     */
    fun setItems(items: List<T>, newLayoutResId: Int? = null, binder: (View, T) -> Unit) {
        adapter.setData(items, newLayoutResId ?: adapter.layoutResId, binder)
    }

    /**
     * Gets the view at a specific position.
     * @param position The position of the view.
     * @return The view at the specified position.
     */
    fun getViewAt(position: Int): View? {
        return findViewHolderForAdapterPosition(position)?.itemView
    }

    /**
     * Removes an item from the list at a specific position.
     * @param position The position of the item to remove.
     */
    fun removeItem(position: Int) {
        adapter.removeItem(position)
    }

    /**
     * Sets whether the layout should be reversed.
     * @param boolean Whether to reverse the layout.
     */
    fun reverseLayout(boolean: Boolean) {
        (layoutManager as LinearLayoutManager).reverseLayout = boolean
        adapter.notifyDataSetChanged()
    }

    /**
     * Sets whether the layout should stack from the end.
     * @param boolean Whether to stack from the end.
     */
    fun stackFromEnd(boolean: Boolean) {
        (layoutManager as LinearLayoutManager).stackFromEnd = boolean
        adapter.notifyDataSetChanged()
    }

    /**
     * Sets the orientation of the layout.
     * @param orientation The orientation to set.
     */
    fun setOrientation(orientation: Int) {
        (layoutManager as LinearLayoutManager).orientation = orientation
        adapter.notifyDataSetChanged()
    }

    /**
     * Sets whether the smooth scrollbar is enabled.
     * @param boolean Whether the smooth scrollbar is enabled.
     */
    fun setSmoothScrollBarEnabled(boolean: Boolean) {
        (layoutManager as LinearLayoutManager).isSmoothScrollbarEnabled = boolean
        adapter.notifyDataSetChanged()
    }

    /**
     * Finds the position of the first visible item.
     * @return The position of the first visible item.
     */
    fun findFirstVisibleItemPosition() =
        (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

    /**
     * Finds the position of the last visible item.
     * @return The position of the last visible item.
     */
    fun findLastVisibleItemPosition() =
        (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

    /**
     * Finds the position of the first completely visible item.
     * @return The position of the first completely visible item.
     */
    fun findFirstCompletelyVisibleItemPosition() =
        (layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()

    /**
     * Finds the position of the last completely visible item.
     * @return The position of the last completely visible item.
     */
    fun findLastCompletelyVisibleItemPosition() =
        (layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()

    /**
     * Scrolls to a position with an offset.
     * @param position The position to scroll to.
     * @param offset The offset to apply.
     */
    fun scrollToPositionWithOffset(position: Int, offset: Int) =
        (layoutManager as LinearLayoutManager).scrollToPositionWithOffset(position, offset)

    /**
     * Gets the orientation of the layout.
     * @return The orientation of the layout.
     */
    fun getOrientation() = (layoutManager as LinearLayoutManager).orientation

    /**
     * Checks whether predictive item animations are supported.
     * @return Whether predictive item animations are supported.
     */
    fun supportsPredictiveItemAnimations() =
        (layoutManager as LinearLayoutManager).supportsPredictiveItemAnimations()

    /**
     * Computes the horizontal scroll extent.
     * @param state The RecyclerView state.
     * @return The horizontal scroll extent.
     */
    fun computeHorizontalScrollExtent(state: RecyclerView.State) =
        (layoutManager as LinearLayoutManager).computeHorizontalScrollExtent(state)

    /**
     * Computes the horizontal scroll offset.
     * @param state The RecyclerView state.
     * @return The horizontal scroll offset.
     */
    fun computeHorizontalScrollOffset(state: RecyclerView.State) =
        (layoutManager as LinearLayoutManager).computeHorizontalScrollOffset(state)
}
