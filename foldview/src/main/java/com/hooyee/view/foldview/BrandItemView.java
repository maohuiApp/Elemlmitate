package com.hooyee.view.foldview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
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

public class BrandItemView extends ItemView<LinearLayout> {
    public BrandItemView(Context context) {
        super(context);
    }

    public BrandItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BrandItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void fillContent(LinearLayout view) {
        mContentFl.addView(view);
    }

    @Override
    public void setProgress(float baseLine, float progress) {
        setTranslationY(-baseLine * progress);
    }

    @Override
    public float calculateCollapsingBottomOnScreen() {
        return 0;
    }

    @Override
    public float calculateCollapsingTopOnScreen() {
        return 0;
    }
}
