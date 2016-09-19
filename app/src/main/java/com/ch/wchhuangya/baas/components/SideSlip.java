package com.ch.wchhuangya.baas.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.ch.wchhuangya.baas.R;
import com.ch.wchhuangya.baas.util.LogHelper;
import com.ch.wchhuangya.baas.util.ScreenHelper;

/**
 * 仿 QQ 侧滑菜单控件
 * Created by wchya on 16/9/16.
 */
public class SideSlip extends HorizontalScrollView {
    /** 左边控件离屏幕右边的边距，类型：int，默认值为 50dp */
    private int rightmMargin;
    /** 屏幕宽度,单位: 像素 */
    private int mScreenWidth;
    /** 是否是首次显示 */
    private boolean isFirst;
    /** 菜单显示状态: true —— 显示; false —— 不显示; */
    private boolean isShowMenu;

    public SideSlip(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SideSlip);

        rightmMargin = (int) typedArray.getDimension(R.styleable.SideSlip_rightMargin, 50);

        typedArray.recycle();

        rightmMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightmMargin, context.getResources().getDisplayMetrics());
        mScreenWidth = ScreenHelper.getScreenWidth(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!isFirst) {
            LinearLayout wrapper = (LinearLayout) getChildAt(0);
            ViewGroup leftMenu = (ViewGroup) wrapper.getChildAt(0);
            ViewGroup midContent = (ViewGroup) wrapper.getChildAt(1);
            leftMenu.getLayoutParams().width = mScreenWidth - rightmMargin;
            midContent.getLayoutParams().width = mScreenWidth;
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (!isFirst) {
            this.scrollTo(mScreenWidth - rightmMargin, 0);

            isFirst = true;
        }
    }

    public void leftToggleShow(boolean show) {
        if (isShowMenu) {
            if (!show) {
                this.smoothScrollTo(mScreenWidth - rightmMargin, 0);
                isShowMenu = !isShowMenu;
            }
        } else {
            if (show) {
                this.smoothScrollTo(0, 0);
                isShowMenu = !isShowMenu;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                int scrollWidth = getScrollX();
                LogHelper.i("滚动距离: " + scrollWidth + ", 屏幕宽度1/3: " + (mScreenWidth / 3));
                if (scrollWidth > mScreenWidth / 2 || (isShowMenu && ev.getX() > mScreenWidth - rightmMargin)) {
                    this.smoothScrollTo(mScreenWidth - rightmMargin, 0);
                    isShowMenu = false;
                } else {
                    this.smoothScrollTo(0, 0);
                    isShowMenu = true;
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isShowMenu) {
            if (ev.getX() > mScreenWidth - rightmMargin)
                return true;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
