package com.xuanyuan.epubbook.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

/**
 * Created by yuanpk on 2018/4/26  10:09
 * <p>
 *
 * @author LUOFAXIN
 */
public class ChapterBean {
    @Nullable
    public String tilte;
    /**
     * 章节资源的href,根据该参数能快速找到章节内容
     */
    @Nullable
    public String href;
    /**
     * resource的大小
     */
    public int size;
}
