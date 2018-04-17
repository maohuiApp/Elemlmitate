package com.hooyee.view.foldview;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * @author Hooyee on 2018/3/21.
 *         　　　┏┓　　　┏┓
 *         　　┏┛┻━━━┛┻┓
 *         　　┃　　　　　　　┃
 *         　　┃　　　━　　　┃
 *         　　┃　┳┛　┗┳　┃
 *         　　┃　　　　　　　┃
 *         　　┃　　　┻　　　┃
 *         　　┃　　　　　　　┃
 *         　　┗━┓　　　┏━┛
 *         　　　　┃　　　┃神兽保佑
 *         　　　　┃　　　┃代码无BUG！
 *         　　　　┃　　　┗━━━┓
 *         　　　　┃　　　　　　　┣┓
 *         　　　　┃　　　　　　　┏┛
 *         　　　　┗┓┓┏━┳┓┏┛
 *         　　　　　┃┫┫　┃┫┫
 *         　　　　　┗┻┛　┗┻┛
 *         ━━━━━━神兽出没━━━━━━
 *         玄冥守护，铁剑无敌！
 *         千剑藏锋数十载，未曾出鞘！
 *         待回头，流星赶月，瞬息十九州！
 *         斩妖魔！亦斩神佛！
 *         千世为泽，巨剑弥合！
 *         偏安一隅任逍遥，红尘莫扰！
 *         今转身！剑气纵横，寒光三万里！
 *         只问天下——
 *         谁人能匹！
 */

public class BrandView extends FrameLayout {
    private View mCollapsingView;
    private View mUnwindView;

    public BrandView(Context context) {
        this(context, null);
    }

    public BrandView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BrandView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.fold_brandview, this);
    }

    public void fillCollapsingContent(View view) {
        addView(view, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.setAlpha(0);
        mCollapsingView = view;
    }

    public void fillUnwindContent(View view) {
        addView(view, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mUnwindView = view;
    }

    public void fillCollapsingContent(@LayoutRes int resId) {
        fillCollapsingContent(LayoutInflater.from(getContext()).inflate(resId, null));
    }

    public void fillUnwindContent(@LayoutRes int resId) {
        fillUnwindContent(LayoutInflater.from(getContext()).inflate(resId, null));
    }

    public float getCanCollapsingHeight() {
        return getHeight() - mCollapsingView.getHeight();
    }

    public void changShowState(float baseLine) {
        View v = mUnwindView;
        int[] position = new int[2];
        v.getLocationInWindow(position);

        if (position[1] >= baseLine) {
            v.setAlpha(0);
            mCollapsingView.setAlpha(1);
        } else if (position[1] + v.getHeight() > baseLine) {
            float alpha = (position[1] + v.getHeight() - baseLine) / getCanCollapsingHeight();
            Log.i("ttt", alpha + "");
            v.setAlpha(1 - alpha);
            mCollapsingView.setAlpha(alpha);
        } else {
            v.setAlpha(1);
            mCollapsingView.setAlpha(0);
        }
    }

    public float getCollapsingTopOnScreen() {
        int[] p = new int[2];
        mCollapsingView.getLocationInWindow(p);
        return p[1];
    }

    public float getCollapsingBottomOnScreen() {
        int[] p = new int[2];
        mCollapsingView.getLocationOnScreen(p);
        return p[1] + mCollapsingView.getHeight();
    }

    public float getUnwindowBottomOnScreen() {
        int[] p = new int[2];
        mUnwindView.getLocationOnScreen(p);
        return p[1] + mUnwindView.getHeight();
    }
}
