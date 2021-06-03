package com.xuanyuan.epubdemo;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @author 罗发新
 * 创建时间：2021/4/22  11:32
 * 邮件：424533553@qq.com
 * CSDN：https://blog.csdn.net/luo_boke
 * <p>
 * 更新时间：2021/4/22  11:32
 * <p>
 * 文件说明：
 */
public class DataSource {

    /**
     * @param context
     * @param index   -1：网络资源
     * @return
     */
    @Nullable
    public static InputStream getInputStream(@NonNull Context context, long index) {
        try {
            InputStream in = null;
            switch ((int) index) {
                case 4:
                    in = context.getAssets().open("飞狐外传.epub");
                    break;
                case 9:
                    in = context.getAssets().open("三体.epub");
                    break;
                case 1:
                    in = context.getAssets().open("书剑恩仇录.epub");
                    break;
                case 2:
                    in = context.getAssets().open("射雕英雄传.epub");
                    break;
                case 3:
                    in = context.getAssets().open("枪炮、病菌和钢铁.epub");
                    break;
                case 5:
                    in = context.getAssets().open("球状闪电.epub");
                    break;
                case 6:
                    in = context.getAssets().open("神雕侠侣.epub");
                    break;
                case 7:
                    in = context.getAssets().open("连城诀.epub");
                    break;
                case 8:
                    in = context.getAssets().open("鹿鼎记.epub");
                    break;
                default:
                    try {
                        URL url = new URL("https://yidu.seaeverit.com/cslib_image/ebookdev/切肤之琴.epub");
                        // 打开该URL对应的资源的输入流
                        in = url.openStream();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
            return in;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
