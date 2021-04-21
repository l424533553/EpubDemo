package com.xuanyuan.epubdemo.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

/**
 * Created by yuanpk on 2018/4/26  10:09
 * <p>
 */
public class EpubBean implements Parcelable {
    @Nullable
    public String tilte;
    @Nullable
    public String href;
    /**
     * resource的大小
     */
    public int size;
    /**
     * 图书id
     */
    public long bookId;

    @Override
    public String toString() {
        return "EpubBean{" +
                "tilte='" + tilte + '\'' +
                ", href='" + href + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tilte);
        dest.writeString(this.href);
        dest.writeLong(this.bookId);
    }

    public EpubBean() {
    }

    protected EpubBean(Parcel in) {
        this.tilte = in.readString();
        this.href = in.readString();
        this.bookId = in.readLong();
    }

    public static final Parcelable.Creator<EpubBean> CREATOR = new Parcelable.Creator<EpubBean>() {
        @Override
        public EpubBean createFromParcel(Parcel source) {
            return new EpubBean(source);
        }

        @Override
        public EpubBean[] newArray(int size) {
            return new EpubBean[size];
        }
    };
}
