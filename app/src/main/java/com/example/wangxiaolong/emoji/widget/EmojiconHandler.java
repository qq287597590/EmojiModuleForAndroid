/*
 * Copyright 2014 Hieu Rocker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.meizu.flyme.flymebbs.widget;

import android.content.Context;
import android.text.Spannable;

import com.meizu.flyme.flymebbs.R;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Wang Xiao Long.
 * Email: wangxiaolong@meizu.com
 * Date: 2015-08-10
 * Time: 16:37
 * Description: 表情处理类
 */
public final class EmojiconHandler {

    private EmojiconHandler() {
    }

    private static final HashMap<String, Integer> sEmojiMap = new HashMap<>();
    private static final Map<String, Boolean> sEmojiPendingTree = new HashMap<>();
    private static int sMaxEscapeLength = 0;
    //兼容pc端表情
    public static int[] emotions = {
            R.drawable.biggrin,
            R.drawable.call,
            R.drawable.cry,
            R.drawable.curse,
            R.drawable.dizzy,
            R.drawable.funk,
            R.drawable.handshake,
            R.drawable.huffy,
            R.drawable.hug,
            R.drawable.kiss,
            R.drawable.lol,
            R.drawable.loveliness,
            R.drawable.mad,
            R.drawable.sad,
            R.drawable.shocked,
            R.drawable.shutup,
            R.drawable.shy,
            R.drawable.sleepy,
            R.drawable.smile,
            R.drawable.sweat,
            R.drawable.time,
            R.drawable.titter,
            R.drawable.tongue,
            R.drawable.victory
    };


    public static String[] emotion_escapes = {
            ":D",
            ":call:",
            ":'(",
            ":curse:",
            ":dizzy:",
            ":funk:",
            ":handshake",
            ":@",
            ":hug:",
            ":kiss:",
            ":lol",
            ":loveliness:",
            ":Q",
            ":(",
            ":o",
            ":shutup:",
            ":$",
            ":sleepy:",
            ":)",
            ":L",
            ":time:",
            ";P",
            ":P",
            ":victory:"
    };
    //客户端表情
    public static int[] emo_news = {
            R.drawable.emo_angry,
            R.drawable.emo_apathy,
            R.drawable.emo_blank,
            R.drawable.emo_cool,
            R.drawable.emo_cry,
            R.drawable.emo_despise,
            R.drawable.emo_dizzy,
            R.drawable.emo_gosh,
            R.drawable.emo_greedy,
            R.drawable.emo_injury,
            R.drawable.emo_jiong,
            R.drawable.emo_laughing,
            R.drawable.emo_like,
            R.drawable.emo_naughty,
            R.drawable.emo_piggy,
            R.drawable.emo_sad,
            R.drawable.emo_shut_up,
            R.drawable.emo_sleep,
            R.drawable.emo_smile,
            R.drawable.emo_sorrow,
            R.drawable.emo_surprise,
            R.drawable.emo_sweety,
            R.drawable.emo_undecided,
            R.drawable.emo_winking
    };
    public static String[] emo_news_escapes = {
            "{:emo_angry}",
            "{:emo_apathy}",
            "{:emo_vacant}",
            "{:emo_cool}",
            "{:emo_cry}",
            "{:emo_despise}",
            "{:emo_dizzy}",
            "{:emo_gosh}",
            "{:emo_greedy}",
            "{:emo_injury}",
            "{:emo_jiong}",
            "{:emo_laughing}",
            "{:emo_like}",
            "{:emo_naughty}",
            "{:emo_piggy}",
            "{:emo_sad}",
            "{:emo_shut_up}",
            "{:emo_sleep}",
            "{:emo_smile}",
            "{:emo_sorrow}",
            "{:emo_surprise}",
            "{:emo_sweet}",
            "{:emo_undecided}",
            "{:emo_winking}"
    };

    static {
        // PC端表情
        for (int i = 0; i < emotion_escapes.length; i++) {
            sEmojiMap.put(emotion_escapes[i], emotions[i]);
            sEmojiPendingTree.put(emotion_escapes[i], true);
            if (emotion_escapes[i].length() > sMaxEscapeLength)
                sMaxEscapeLength = emotion_escapes[i].length();
        }

        //客户端定义表情
        for(int i=0;i<emo_news_escapes.length;i++){
            sEmojiMap.put(emo_news_escapes[i],emo_news[i]);
            sEmojiPendingTree.put(emo_news_escapes[i],true);
            if (emo_news_escapes[i].length() > sMaxEscapeLength)
                sMaxEscapeLength = emo_news_escapes[i].length();
        }
    }

    private static boolean isSoftBankEmoji(char c) {
        return c == '{';
    }

    private static int getEmojiResource(Context context, int codePoint) {
        return sEmojiMap.get(codePoint);
    }

    /**
     * Convert emoji characters of the given Spannable to the according emojicon.
     *
     * @param context
     * @param text
     * @param emojiSize
     * @param emojiAlignment
     * @param textSize
     */
    public static void addEmojis(Context context, Spannable text, int emojiSize, int emojiAlignment, int textSize) {
        addEmojis(context, text, emojiSize, emojiAlignment, textSize, 0, -1, false);
    }

    /**
     * Convert emoji characters of the given Spannable to the according emojicon.
     *
     * @param context
     * @param text
     * @param emojiSize
     * @param emojiAlignment
     * @param textSize
     * @param index
     * @param length
     */
    public static void addEmojis(Context context, Spannable text, int emojiSize, int emojiAlignment, int textSize, int index, int length) {
        addEmojis(context, text, emojiSize, emojiAlignment, textSize, index, length, false);
    }

    /**
     * Convert emoji characters of the given Spannable to the according emojicon.
     *
     * @param context
     * @param text
     * @param emojiSize
     * @param emojiAlignment
     * @param textSize
     * @param useSystemDefault
     */
    public static void addEmojis(Context context, Spannable text, int emojiSize, int emojiAlignment, int textSize, boolean useSystemDefault) {
        addEmojis(context, text, emojiSize, emojiAlignment, textSize, 0, -1, useSystemDefault);
    }

    /**
     * Convert emoji characters of the given Spannable to the according emojicon.
     *
     * @param context
     * @param text
     * @param emojiSize
     * @param emojiAlignment
     * @param textSize
     * @param index
     * @param length
     * @param useSystemDefault
     */
    public static void addEmojis(Context context, Spannable text, int emojiSize, int emojiAlignment, int textSize, int index, int length, boolean useSystemDefault) {
        if (useSystemDefault) {
            return;
        }

        int textLength = text.length();
        int textLengthToProcessMax = textLength - index;
        int textLengthToProcess = length < 0 || length >= textLengthToProcessMax ? textLength : (length + index);

        // remove spans throughout all text
        EmojiconSpan[] oldSpans = text.getSpans(0, textLength, EmojiconSpan.class);
        for (EmojiconSpan oldSpan : oldSpans) {
            text.removeSpan(oldSpan);
        }
        int pendingIndex = 0;
        int icon;
        StringBuilder pendingString = new StringBuilder();
        boolean pendingStatus = false;          //判定状态标记
        for (int i = index; i < textLengthToProcess; i++) {
            char c = text.charAt(i);
            if (pendingStatus) {
                pendingString.append(c);
                String pendingStr = pendingString.toString();
                if(sEmojiPendingTree.get(pendingStr) != null && sEmojiPendingTree.get(pendingStr)){
                    icon = sEmojiMap.get(pendingStr);
                    text.setSpan(new EmojiconSpan(context, icon, emojiSize, emojiAlignment, textSize), pendingIndex, i+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    pendingStatus = false;
                    continue;
                }
                if(pendingStr.length() > sMaxEscapeLength)
                    pendingStatus = false;
            }
            if (isSoftBankEmoji(c)) {
                pendingStatus = true;
                pendingIndex = i;
                pendingString = new StringBuilder();
                pendingString.append(c);
            }
        }
    }
}
