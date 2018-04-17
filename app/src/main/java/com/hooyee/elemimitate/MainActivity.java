package com.hooyee.elemimitate;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.TextView;

import com.hooyee.view.foldview.FoldView;
import com.hooyee.view.foldview.ItemView;

public class MainActivity extends AppCompatActivity {
    private float mX;
    private float mY;

    private TextView mText;
    private ItemView mItemView;
    private FoldView mFoldView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mText = findViewById(R.id.tv_test);
        mItemView = findViewById(R.id.app_item_view);
        mFoldView = findViewById(R.id.fold_foldview);
        mItemView.fillContent(R.layout.fold_textview);
        findViewById(R.id.fl_content).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int eventCode = event.getActionMasked();
                switch (eventCode) {
                    case MotionEvent.ACTION_DOWN:
                        mFoldView.restore();
                        mX = event.getX();
                        mY = event.getY();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        float dy = mY - event.getY();
                        if (dy > ViewConfiguration.get(v.getContext()).getScaledTouchSlop()) {
                            mFoldView.moveY(dy);
                        }

                        break;
                    case MotionEvent.ACTION_UP:
                        dy = mY - event.getY();
                        if (dy > FoldView.MAX_MOVE_Y / 2) {
                            ObjectAnimator o = ObjectAnimator.ofFloat(mFoldView, "moveY", dy, FoldView.MAX_MOVE_Y * 2);
                            o.setDuration(500);
                            o.start();
                        } else if (dy > 0){
                            ObjectAnimator o = ObjectAnimator.ofFloat(mFoldView, "moveY", dy, 0);
                            o.setDuration(500);

                            o.start();
                        } else {
                            mFoldView.restore();
                        }
                        break;
                }
                return false;
            }
        });
    }
}
