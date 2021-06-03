package com.xuanyuan.epubbook.entity;

import android.graphics.Bitmap;

import androidx.annotation.Nullable;

import java.util.List;


/**
 * @author 罗发新
 * 创建时间：2021/3/31  11:03
 * 邮件：424533553@qq.com
 * CSDN：https://blog.csdn.net/luo_boke
 * <p>
 * 更新时间：2021/3/31  11:03
 * <p>
 * 文件说明：图书信息
 */

public class EpubBean {
    /**
     * 书名
     */
    @Nullable
    public String title;
    /**
     * 作者
     */
    @Nullable
    public String author;
    /**
     * 出版社
     */
    @Nullable
    public String publisher;
    /**
     * 简介
     */
    @Nullable
    public String description;
    /**
     * 出版时间
     */
    @Nullable
    public String time;
    /**
     * 图书语言
     */
    @Nullable
    public String language;
    /**
     * 分类、科目
     */
    @Nullable
    public String subject;
    /**
     * 图书章节目录列表
     */
    @Nullable
    public List<ChapterBean> list;
    /**
     * 图书的图片
     */
    @Nullable
    public Bitmap bitmap;

    @Override
    public String toString() {
        return "EpubBean{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", description='" + description + '\'' +
                ", time='" + time + '\'' +
                ", language='" + language + '\'' +
                ", subject='" + subject + '\'' +
                ", bitmap=" + bitmap +
                '}';
    }
}
