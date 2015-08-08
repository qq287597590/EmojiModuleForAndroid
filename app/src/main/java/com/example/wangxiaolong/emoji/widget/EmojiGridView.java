package com.example.wangxiaolong.emoji.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.example.wangxiaolong.emoji.R;
import com.example.wangxiaolong.emoji.interfaces.OnInsertEmojiListener;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * User: Wang Xiao Long.
 * Email: wangxiaolong@meizu.com
 * Date: 2015-08-05
 * Time: 14:39
 * Description: 表情板控件类
 */
public class EmojiGridView extends GridView implements AdapterView.OnItemClickListener{
    private int[] emotions = {R.drawable.biggrin, R.drawable.call, R.drawable.cry, R.drawable.curse, R.drawable.dizzy,
            R.drawable.funk, R.drawable.handshake, R.drawable.huffy, R.drawable.hug, R.drawable.kiss, R.drawable.lol,
            R.drawable.loveliness, R.drawable.mad, R.drawable.sad, R.drawable.shocked, R.drawable.shutup, R.drawable.shy,
            R.drawable.sleepy, R.drawable.smile, R.drawable.sweat, R.drawable.time, R.drawable.titter, R.drawable.tongue,
            R.drawable.victory
    };
    private String[] emotion_escapes = {":D", ":call:", ":'(", ":curse:", ":dizzy:",
            ":funk:", ":handshake", ":@", ":hug:", ":kiss:",
            ":loveliness:", ":Q", ":(", ":o", ":shutup:",
            ":sleepy:", ":)", ":L", ":time:", ";P",
            " :victory:"
    };
    Context mContext;
    SimpleAdapter mAdapter;
    OnInsertEmojiListener mOnInsertEmojiListener;
    public EmojiGridView(Context context) {
        super(context);
        init(context);
    }

    public EmojiGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EmojiGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    public void setOnInsertEmojiListener(OnInsertEmojiListener onInsertEmojiListener){
        mOnInsertEmojiListener = onInsertEmojiListener;
    }
    /**
     * 初始化表情板
     * @param context Activity上下文
     */
    public void init(Context context){
        mContext =context;
        ArrayList<HashMap<String, Object>> listImageItem = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < emotion_escapes.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", emotions[i]);
            map.put("ItemEscape", emotion_escapes[i]);
            listImageItem.add(map);
        }
        mAdapter = new SimpleAdapter(mContext,
                listImageItem,
                R.layout.emoj_item,
                new String[]{"ItemImage"},
                new int[]{R.id.ItemImage});
        setAdapter(mAdapter);
        setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        HashMap<String, Object> map = (HashMap<String, Object>) mAdapter.getItem(position);
        String emojiEscape = (String) map.get("ItemEscape");
        int emojiSource = (int) map.get("ItemImage");
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), emojiSource);
        ImageSpan imageSpan = new ImageSpan(mContext, bitmap);
        SpannableString spannableString = new SpannableString(emojiEscape);   //“image”是图片名称的前缀
        spannableString.setSpan(imageSpan, 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mOnInsertEmojiListener.onInsertEmoji(spannableString);
    }

}
