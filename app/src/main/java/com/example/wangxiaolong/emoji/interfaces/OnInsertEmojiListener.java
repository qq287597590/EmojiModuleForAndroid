package com.example.wangxiaolong.emoji.interfaces;

import android.text.SpannableString;

/**
 * User: Wang Xiao Long.
 * Email: wangxiaolong@meizu.com
 * Date: 2015-08-05
 * Time: 16:11
 * Description: EmojiEditTExt provide to EmojiGrid for inserting emoji callback
 */
public interface OnInsertEmojiListener {
   void onInsertEmoji(SpannableString spannableString);
}