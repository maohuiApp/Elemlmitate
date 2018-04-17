package com.hooyee.view.foldview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

public class FoldView extends LinearLayout {
    private NoticeItemView mNoticeItem;
    private BrandView mBrandItem;
    private DiscountsItemView mDiscountsItem;

    public static final int MAX_MOVE_Y = 200;

    public FoldView(Context context) {
        this(context, null);
    }

    public FoldView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FoldView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.fold_foldview, this);
        mNoticeItem = findViewById(R.id.fold_item_notice);
        mBrandItem = findViewById(R.id.fold_item_brand);
        mDiscountsItem = findViewById(R.id.fold_item_discounts);
        mNoticeItem.fillContent(R.layout.fold_textview);

        mBrandItem.fillUnwindContent(R.layout.fold_brand_fill_vertical_view);
        mBrandItem.fillCollapsingContent(R.layout.fold_brand_fill_horizontal_view);


        TextView v = new TextView(getContext());
        v.setText("你好啊sdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffwerererererererererererererererererererererererererererererererererererererererererererererererer");
        mDiscountsItem.fillContent(v);
    }

//    private float lastP;
    
    private float mLevelOne;
    private float mLevelTwo;
    private float mOriginLeft;
    private float mOriginRight;

    public void moveY(float moveY) {
        float progress = moveY / MAX_MOVE_Y >= 1 ? 1 : moveY / MAX_MOVE_Y;
//        if (Math.abs(progress - lastP) < 0.02 && progress != 1 && progress != 0) return;
        NoticeItemView noticeItem = mNoticeItem;
        DiscountsItemView discountsItem = mDiscountsItem;

        discountsItem.setProgress(noticeItem.calculateCollapsingBottomOnScreen(), progress);
        float alpha = discountsItem.hideTitle(noticeItem.getOriginBottomOnScreen());
        if (alpha != 0) {
            return;
        }
        if (mLevelOne == 0) {
            mLevelOne = moveY;
        }
        progress = (moveY - mLevelOne) / MAX_MOVE_Y >= 1 ? 1 : (moveY - mLevelOne) / MAX_MOVE_Y;
        noticeItem.hideItem(discountsItem.calculateCollapsingTopOnScreen());
        noticeItem.setProgress(mBrandItem.getCollapsingBottomOnScreen(), progress);
        noticeItem.hideTitle(mBrandItem.getUnwindowBottomOnScreen());
        mBrandItem.changShowState(noticeItem.calculateCollapsingTopOnScreen());
//        lastP = progress;

        if (progress == 1 && mLevelTwo == 0) {
            mLevelTwo = moveY;
        }

        if (mLevelTwo == 0) return;
        progress = (moveY - mLevelTwo) / MAX_MOVE_Y >= 1 ? 1 : (moveY - mLevelTwo) / MAX_MOVE_Y;
        if (progress < 0) progress = 0;
        float scale = 0.2f;

        discountsItem.setLeft((int) (discountsItem.getOriginLeft() + (discountsItem.getWidth() / 10 * progress)));
        discountsItem.setRight((int) (discountsItem.getOriginRight() - discountsItem.getWidth() / 10 * progress));
//        discountsItem.requestLayout();
//        params.width = (int) (mParams.width * (1 - scale * progress));
//        discountsItem.setScaleX(1 - scale * progress);

    }

    public void setMoveY(float moveY) {
        moveY(moveY);
    }

    public void restore() {
        mLevelTwo = 0;
        mLevelOne = 0;
        mDiscountsItem.setLeft(mDiscountsItem.getOriginLeft());
        mDiscountsItem.setRight(mDiscountsItem.getOriginRight());
//        mDiscountsItem.setScaleX(1);

    }
}
