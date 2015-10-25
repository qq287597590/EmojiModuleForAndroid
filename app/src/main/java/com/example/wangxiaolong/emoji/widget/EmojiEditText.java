package com.meizu.flyme.flymebbs.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.SpannableString;
import android.text.style.DynamicDrawableSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.meizu.flyme.flymebbs.R;
import com.meizu.flyme.flymebbs.interfaces.OnInsertEmojiListener;
import com.meizu.flyme.flymebbs.interfaces.OnSmileFaceClickedListener;

/**
 * User: Wang Xiao Long.
 * Email: wangxiaolong@meizu.com
 * Date: 2015-08-05
 * Time: 16:10
 * Description: 表情EditText
 */
public class EmojiEditText extends EditText implements OnInsertEmojiListener {

    private OnSmileFaceClickedListener onSmileFaceClickedListener;
    private int mEmojiconSize;
    private int mEmojiconAlignment;
    private int mEmojiconTextSize;
    private boolean mUseSystemDefault = false;
    private int mTextMaxSize;

    @Override
    public void onInsertEmoji(SpannableString spannableString) {
        int start = getSelectionStart();
        int end = getSelectionEnd();
        if (getText().length() + spannableString.length() < mTextMaxSize) {
            getText().replace(Math.min(start, end), Math.max(start, end), spannableString, 0, spannableString.length());
            setSelection(start + spannableString.length());
        }
    }

    public EmojiEditText(Context context) {
        super(context);
        mEmojiconSize = (int) getTextSize();
        mEmojiconTextSize = (int) getTextSize();
    }

    public EmojiEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public EmojiEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.Emojicon);
        mEmojiconSize = (int) typedArray.getDimension(R.styleable.Emojicon_emojiconSize, getTextSize());
        mEmojiconAlignment = typedArray.getInt(R.styleable.Emojicon_emojiconAlignment, DynamicDrawableSpan.ALIGN_BASELINE);
        mUseSystemDefault = typedArray.getBoolean(R.styleable.Emojicon_emojiconUseSystemDefault, false);
        typedArray.recycle();
        mEmojiconTextSize = (int) getTextSize();

//        mSmileDrawable = getCompoundDrawables()[2];
//        if (mSmileDrawable == null) {
//            mSmileDrawable = getResources().getDrawable(R.drawable.flymebbs_postdetail_biaoqing);
//        }
//        mSmileDrawable.setBounds(0, 0, mSmileDrawable.getIntrinsicWidth(),
//                mSmileDrawable.getIntrinsicHeight());
//
//        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], mSmileDrawable,
//                getCompoundDrawables()[3]);
        Paint mPaint = new Paint();
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
                boolean touchable = event.getX() > (getWidth() - getPaddingRight())
                        && (event.getX() < ((getWidth() - getPaddingRight())));
                if (touchable) {
                    if (onSmileFaceClickedListener != null) {
                        onSmileFaceClickedListener.onSmileFaceClicked();
                    }
                }
            }
        }
        return true;
    }


    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        updateText();
    }

    private void updateText() {
        EmojiconHandler.addEmojis(getContext(), getText(), mEmojiconSize, mEmojiconAlignment, mEmojiconTextSize, mUseSystemDefault);
    }

    public void setOnSmileFaceClickedListener(OnSmileFaceClickedListener onSmileFaceClickedListener) {
        this.onSmileFaceClickedListener = onSmileFaceClickedListener;
    }

    public void setTextMaxSize(int size){
        mTextMaxSize = size;
    }
}


