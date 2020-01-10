package com.bibleinayear

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Can't get it working just yet. Suppose to make floating button go away as you scroll.
 */
class HideOnScrollFAB(context: Context, attrs: AttributeSet) :
    FloatingActionButton.Behavior() {

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: FloatingActionButton,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int
    ) {
        super.onNestedScroll(
            coordinatorLayout!!,
            child,
            target,
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed
        )
        //child -> Floating Action Button
        if (child.visibility == View.VISIBLE && dyConsumed > 0) {
            child.hide()
        } else if (child.visibility == View.GONE && dyConsumed < 0) {
            child.show()
        }
    }

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: FloatingActionButton,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }
//    override fun onStartNestedScroll(
//        coordinatorLayout: CoordinatorLayout?,
//        child: FloatingActionButton?,
//        directTargetChild: View?,
//        target: View?,
//        nestedScrollAxes: Int
//    ): Boolean {
//        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
//    }
}