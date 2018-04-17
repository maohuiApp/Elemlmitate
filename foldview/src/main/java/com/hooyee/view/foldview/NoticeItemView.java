package com.hooyee.view.foldview;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

public class NoticeItemView extends ItemView<TextView> {
    protected float mMinKeepHeight;

    public NoticeItemView(Context context) {
        this(context, null);
    }

    public NoticeItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NoticeItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void fillContent(final TextView view) {
        view.post(new Runnable() {
            @Override
            public void run() {
                TextView tv = view;
                mMinKeepHeight = getLineHeight(tv);

                LinearLayout ll = new LinearLayout(getContext());
                ll.setOrientation(LinearLayout.VERTICAL);
                for (int i = 0; i < tv.getLineCount(); i++) {
                    View v = new View(getContext());
                    v.setAlpha(0);
                    ll.addView(v, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) mMinKeepHeight));
                    setViewBackgroundBySuperView(v);
                }
                mContentFl.addView(ll, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                mPlaceholderLl = ll;
            }
        });

        decrement = view.getPaint().measureText("AA");
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        mContentFl.addView(view, params);

        textView = view;
    }

    float decrement;
    int decrementCount;
    int count;
    TextView textView;

    private void setViewBackgroundBySuperView(View v) {
        ViewGroup parent = (ViewGroup) v.getParent();
        if (parent == null) {
            return;
        }
        while (parent != null) {
            if (parent.getBackground() == null) {
                parent = (ViewGroup) parent.getParent();
                continue;
            }
            if (parent.getBackground() instanceof ColorDrawable) {
                ColorDrawable drawable = (ColorDrawable) parent.getBackground();
                v.setBackgroundColor(drawable.getColor());
                return;
            } else {
                // 若为图片等背景，则没必要设置颜色
                return;
            }
        }

        // 若父类也没背景颜色，则设置为rootView的背景颜色
        if (getRootView().getBackground() instanceof ColorDrawable) {
            ColorDrawable colorDrawable = (ColorDrawable) getRootView().getBackground();
            v.setBackgroundColor(colorDrawable.getColor());
        }
    }

    @Override
    public void setProgress(float baseLine, float progress) {
        setTranslationY((baseLine - getCollapsingTopOnScreen()) * progress);
        if ((count == mPlaceholderLl.getChildCount() - 1)) {
            if (decrementCount < 2) {
                decrementCount++;
                textView.setMaxLines(1);
                textView.setWidth((int) (textView.getWidth() - decrement));
            }
        } else if (decrementCount > 0) {
            textView.setWidth((int) (textView.getWidth() + decrement));
            decrementCount--;
            if (decrementCount == 0) {
                textView.setMaxLines(Integer.MAX_VALUE);
            }
        }
    }

    protected float getLineHeight(TextView tv) {
        Paint p = tv.getPaint();
        return p.getFontMetrics().bottom - p.getFontMetrics().top;
    }

    /**
     * @param baseLine 如超过该值，则hide item
     */
    public void hideItem(float baseLine) {
        ViewGroup group = mPlaceholderLl;
        int count = 0;
        for (int i = 0; i < group.getChildCount(); i++) {
            float alpha = needHide(group.getChildAt(i), baseLine);
            if (alpha == 1) {
                count++;
            }
        }
        this.count = count;
    }

    public void hideTitle(float baseLine) {
        needHide(mTitleTv, baseLine);
    }

    protected float calculateCollapsingTopOnScreen() {
        int[] p = new int[2];
        mPlaceholderLl.getLocationInWindow(p);
        return p[1];
    }

    protected float calculateCollapsingBottomOnScreen() {
        int[] p = new int[2];
        if (mPlaceholderLl.getChildCount() > 0) {
            View v = mPlaceholderLl.getChildAt(0);
            v.getLocationOnScreen(p);
            return p[1] + v.getHeight();
        }
        return 0;
    }
}
