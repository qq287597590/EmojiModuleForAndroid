package com.meizu.flyme.flymebbs.widget;

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
import com.meizu.flyme.flymebbs.R;
import com.meizu.flyme.flymebbs.interfaces.OnInsertEmojiListener;
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
        ArrayList<HashMap<String, Object>> listImageItem = new ArrayList<>();
        for (int i = 0; i < EmojiconHandler.emotion_escapes.length; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("ItemImage", EmojiconHandler.emo_news[i]);
            map.put("ItemEscape", EmojiconHandler.emo_news_escapes[i]);
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
