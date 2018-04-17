package com.hooyee.view.foldview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.support.annotation.FloatRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author Hooyee on 2018/3/19.
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

public abstract class ItemView<T extends View> extends LinearLayout {
    protected TextView mTitleTv;
    protected RelativeLayout mContentFl;

    protected int originBottomOnScreen;

    protected float mCollapsingTopOnScreen;
    protected float mCollapsingBottomOnScreen;

    public ItemView(Context context) {
        this(context, null);
    }

    public ItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context.obtainStyledAttributes(attrs, R.styleable.ItemView));
    }

    private void init(TypedArray typedArray) {
        LayoutInflater.from(getContext()).inflate(R.layout.fold_itemview, this);
        mTitleTv = findViewById(R.id.fold_tv_title);
        mContentFl = findViewById(R.id.fold_fl_content);

        int type = typedArray.getInt(R.styleable.ItemView_fold_type, 0);
        String typeStr = null;
        if (type == 0) {
            typeStr = getResources().getString(R.string.fold_item_type_grand);
            String title = typedArray.getString(R.styleable.ItemView_fold_title);
            mTitleTv.setText(typeStr + ":" + title);
            mTitleTv.setCompoundDrawables(getResources().getDrawable(R.drawable.ic_launcher_background), null, null, null);
        } else if (type == 1) {
            typeStr = getResources().getString(R.string.fold_item_type_notice);
            mTitleTv.setText(typeStr);
            mTitleTv.setCompoundDrawables(getResources().getDrawable(R.drawable.ic_launcher_background), null, null, null);
        } else {
            typeStr = getResources().getString(R.string.fold_item_type_discounts);
            mTitleTv.setText(typeStr);
            mTitleTv.setCompoundDrawables(getResources().getDrawable(R.drawable.ic_launcher_background), null, null, null);
        }

        post(new Runnable() {
            @Override
            public void run() {
                int[] p = new int[2];
                getLocationOnScreen(p);
                originBottomOnScreen = p[1] + getHeight();
            }
        });

        typedArray.recycle();
    }

    public abstract void fillContent(final T view);

    public void fillContent(@LayoutRes int res) {
        View view = LayoutInflater.from(getContext()).inflate(res, null);
        fillContent((T) view);
    }
    LinearLayout mPlaceholderLl;

    public abstract void setProgress(float baseLine, @FloatRange float progress);

    /**
     * 计算当前位置
     * @return
     */
    protected abstract float calculateCollapsingBottomOnScreen();

    protected abstract float calculateCollapsingTopOnScreen();

    private float getCharacterWidth(TextView tv) {
        Paint p = new Paint();
        p.setTextSize(tv.getTextSize() * tv.getScaleX());
        String text = (String) tv.getText();
        return p.measureText(text) / text.length();
    }

    protected float getLineHeight(TextView tv) {
        Paint p = tv.getPaint();
        return p.getFontMetrics().bottom - p.getFontMetrics().top;
    }

    public float getCollapsingTopOnScreen() {
        if (mCollapsingTopOnScreen == 0) {
            mCollapsingTopOnScreen = calculateCollapsingTopOnScreen();
        }
        return mCollapsingTopOnScreen;
    }

    public float getCollapsingBottomOnScreen() {
        if (mCollapsingBottomOnScreen == 0) {
            mCollapsingBottomOnScreen = calculateCollapsingTopOnScreen();
        }
        return mCollapsingBottomOnScreen;
    }

    /**
     * @param v
     * @param baseLine 用以作为标注线
     * @return 当前透明度
     */
    protected float needHide(View v, float baseLine) {
        int[] position = new int[2];
        v.getLocationOnScreen(position);
        if (position[1] >= baseLine) {
            v.setAlpha(1);
            return 1;
        } else if (position[1] + v.getHeight() > baseLine) {
            float alpha = (position[1] + v.getHeight() - baseLine) / v.getHeight();
            v.setAlpha(alpha);
            return alpha;
        } else {
            v.setAlpha(0);
            return 0;
        }
    }

    public int getOriginBottomOnScreen() {
        return originBottomOnScreen;
    }
}
