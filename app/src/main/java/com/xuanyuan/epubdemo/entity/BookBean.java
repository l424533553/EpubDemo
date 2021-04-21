package com.xuanyuan.epubdemo.entity;

import android.graphics.Bitmap;

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

public class BookBean extends BaseBookBean {
    /**
     * 图书Id
     */

    private long bookId;

//    private long bookKey;



    private String url;

    /**
     * 书名
     */
    private String title;
    /**
     * 作者
     */
    private String author = "烟雨江南";
    /**
     * 出版社
     */
    private String publisher = "中华出版社";

    /**
     * 简介
     */
    private String description = "本书从朱元璋的出身开始写起，到永乐大帝夺位的靖难之役结束为止。叙述了明朝最艰苦卓绝的开国过程。朱元璋pk陈友谅，谁堪问鼎天下？战太平、太湖大决战。卧榻之侧埋恶虎，铲除张士诚。徐达、常遇春等不世名...";

    /**
     * false:未加入书架
     */
    private boolean bookSign;

    /**
     * 出版时间
     */
    private String time;

    /**
     * false:未下载  true:下载
     */
    private boolean isDownLoad;

    private   String language;

    /**
     * 保存路径
     */
    private String savePath;
    /**
     * 分类、科目
     */
    private String subject;


    List<EpubBean> list;

    private Bitmap bitmap;



//    /**
//     * 章节目录
//     */
//    private List<ChapterBean> chapterList;


//    public long getBookKey() {
//        return bookKey;
//    }
//
//    public void setBookKey(long bookKey) {
//        this.bookKey = bookKey;
//    }

    public long getBookTypeId() {
        return bookTypeId;
    }

    public void setBookTypeId(long bookTypeId) {
        this.bookTypeId = bookTypeId;
    }

    public int getBookClassId() {
        return bookClassId;
    }

    public void setBookClassId(int bookClassId) {
        this.bookClassId = bookClassId;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


    //    public List<ChapterBean> getChapterList() {
//        return chapterList;
//    }
//
//    public void setChapterList(List<ChapterBean> chapterList) {
//        this.chapterList = chapterList;
//    }


    public void setBookSign(boolean bookSign) {
        this.bookSign = bookSign;
    }

    public boolean isBookSign() {
        return bookSign;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isDownLoad() {
        return isDownLoad;
    }

    public void setDownLoad(boolean downLoad) {
        isDownLoad = downLoad;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }


    public List<EpubBean> getList() {
        return list;
    }

    public void setList(List<EpubBean> list) {
        this.list = list;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    public String toString() {
        return "BookBean{" +
                "bookId=" + bookId +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", description='" + description + '\'' +
                ", bookSign=" + bookSign +
                ", time='" + time + '\'' +
                ", isDownLoad=" + isDownLoad +
                ", language='" + language + '\'' +
                ", savePath='" + savePath + '\'' +
                '}';
    }


    //    //这里写了一个static方法，用于特殊处理传入的图片
//    //第一个参数是默认的绑定数据类型是什么就会传入
//    //第二个参数是设定的参数
//    @BindingAdapter({"loadImage"})
//    public  void loadImage(ImageView imageView, String url){
//        Glide.with(imageView).load(url).into(imageView);
//    }


//    @BindingAdapter({"app:imageUrl", "app:placeHolder", "app:error"})



//    @BindingAdapter({"loadImage"})
//    public void loadImage(@NonNull ImageView imageView, String url) {
//        Glide.with(imageView.getContext())
//                .load(url)
//                .placeholder(com.xuanyuan.baselibrary.R.mipmap.ic_launcher_round)
//                .error(com.xuanyuan.baselibrary.R.mipmap.ic_launcher)
//                .into(imageView);
//    }


    /**
     * 图书图片加载方法
     */
//    @BindingAdapter({"app:image"})
//    public static void image(@NonNull ImageView imageView, @Nullable String url) {
//        if (TextUtils.isEmpty(url)) {
//            imageView.setImageResource(R.mipmap.img_splash);
//        } else {
//            Glide.with(imageView.getContext())
//                    .load(url)
//                    .placeholder(R.mipmap.img_splash)
////                .error(errorDrawable)
//                    .into(imageView);
//        }
//    }

}
