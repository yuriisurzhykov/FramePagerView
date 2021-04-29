package com.yuriysurzhikov.framepager

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView

class FramePagerView : ConstraintLayout {

    companion object {
        private const val DEFAULT_MENU_VALUE = -1
    }

    private val mBottomView: BottomNavigationView = BottomNavigationView(context)
    private val mPagerLayout: ViewPager2 = ViewPager2(context)

    private val mBottomViewId = View.generateViewId()
    private val mPagerViewId = View.generateViewId()

    constructor(context: Context) : super(context) {
        initByDefault(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initByParams(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    private fun initByDefault(context: Context) {
        initBottomView()
        initViewPager()
    }

    private fun initByParams(context: Context, attrs: AttributeSet?) {
        initByDefault(context)
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.FramePagerView,
            0, 0
        ).apply {
            try {
                val menu = getResourceId(R.styleable.FramePagerView_menu, DEFAULT_MENU_VALUE)
                obtainBottomMenu(menu)
            } finally {
                recycle()
            }
        }
    }

    private fun initBottomView() {
        mBottomView.id = mBottomViewId
        val layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        val constraintSet = ConstraintSet()
        constraintSet.clone(this)
        constraintSet.connect(mBottomView.id, ConstraintSet.BOTTOM, this.id, ConstraintSet.BOTTOM)
        constraintSet.connect(mBottomView.id, ConstraintSet.START, this.id, ConstraintSet.START)
        constraintSet.connect(mBottomView.id, ConstraintSet.END, this.id, ConstraintSet.END)
        constraintSet.applyTo(this)
        mBottomView.layoutParams = layoutParams
    }

    private fun initViewPager() {
        mPagerLayout.id = mPagerViewId
        val layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, 0)
        val constraintSet = ConstraintSet()
        constraintSet.clone(this)
        constraintSet.connect(mPagerViewId, ConstraintSet.TOP, this.id, ConstraintSet.TOP)
        constraintSet.connect(mPagerViewId, ConstraintSet.START, this.id, ConstraintSet.START)
        constraintSet.connect(mPagerViewId, ConstraintSet.END, this.id, ConstraintSet.END)
        constraintSet.connect(mPagerViewId, ConstraintSet.BOTTOM, mBottomViewId, ConstraintSet.TOP)
        constraintSet.applyTo(this)
        mBottomView.layoutParams = layoutParams
    }

    private fun obtainBottomMenu(menuId: Int) {
        if (menuId != DEFAULT_MENU_VALUE) {
            mBottomView.inflateMenu(menuId)
        }
    }
}