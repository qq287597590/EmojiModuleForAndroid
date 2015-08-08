package com.example.wangxiaolong.emoji.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.example.wangxiaolong.emoji.R;
import com.example.wangxiaolong.emoji.interfaces.OnInsertEmojiListener;

/**
 * User: Wang Xiao Long.
 * Email: wangxiaolong@meizu.com
 * Date: 2015-08-05
 * Time: 16:10
 * Description: 表情EditText
 */
public class EmojiEditText extends EditText implements OnInsertEmojiListener{
    private Drawable mSmileDrawable;
    private Paint mPaint;
    @Override
    public void onInsertEmoji(SpannableString spannableString) {
        int start = getSelectionStart();
        int end = getSelectionEnd();
        getText().replace(Math.min(start, end), Math.max(start, end), spannableString, 0, spannableString.length());
        setSelection(start+spannableString.length());
    }

    public EmojiEditText(Context context) {
        super(context);
    }

    public EmojiEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmojiEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private void init() {
        mSmileDrawable = getCompoundDrawables()[2];
        if (mSmileDrawable == null) {
            mSmileDrawable = getResources().getDrawable(R.drawable.smile_button);
        }
        mSmileDrawable.setBounds(0, 0, mSmileDrawable.getIntrinsicWidth(),
                mSmileDrawable.getIntrinsicHeight());

        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], mSmileDrawable,
                getCompoundDrawables()[3]);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(3);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        if (getCompoundDrawables()[2] != null) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                boolean touchable = event.getX() > (getWidth() - getPaddingRight() - mSmileDrawable
                        .getIntrinsicWidth())
                        && (event.getX() < ((getWidth() - getPaddingRight())));

            }
        }
        return true;
    }
}


