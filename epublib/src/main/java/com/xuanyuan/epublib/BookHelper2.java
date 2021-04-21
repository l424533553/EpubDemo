//package com.xuanyuan.epublib;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Environment;
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//
//import com.xuanyuan.epublib.entity.BookBean;
//import com.xuanyuan.epublib.entity.EpubBean;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.safety.Whitelist;
//import org.jsoup.select.Elements;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import nl.siegmann.epublib.domain.Book;
//import nl.siegmann.epublib.domain.Guide;
//import nl.siegmann.epublib.domain.GuideReference;
//import nl.siegmann.epublib.domain.Metadata;
//import nl.siegmann.epublib.domain.Resource;
//import nl.siegmann.epublib.domain.Resources;
//import nl.siegmann.epublib.domain.Spine;
//import nl.siegmann.epublib.domain.SpineReference;
//import nl.siegmann.epublib.domain.TOCReference;
//import nl.siegmann.epublib.domain.TableOfContents;
//
///**
// * @author 罗发新
// * 创建时间：2021/4/19  11:36
// * 邮件：424533553@qq.com
// * CSDN：https://blog.csdn.net/luo_boke
// * <p>
// * 更新时间：2021/4/19  11:36
// * <p>
// * 文件说明：
// */
//public class BookHelper {
//    EpubHelper epubHelper;
//
//    public BookHelper() {
//        epubHelper = new EpubHelper();
//    }
//
//    private long bookId;
//
//    /**
//     * 耗时操作
//     * 解析一本图书
//     *
//     * @return 图书
//     */
//    @NonNull
//    public BookBean parseBook(@NonNull Book book) {
//        return epubHelper.parseBook(book);
//    }
//
//    /**
//     * 获取章节的方式, 不是特好用
//     */
//    private void getChapterContents(@NonNull BookBean bookBean, @NonNull Book book) throws IOException {
//        epubHelper.getChapterContents(bookBean, book);
//    }
//
//    /**
//     * 获取章节的方式, 通过spine获取线性阅读菜单
//     */
//    public void getChapterContents(@NonNull BookBean bookBean, @NonNull Spine spine) {
//        epubHelper.getChapterContents(bookBean, spine);
//    }
//
//    /**
//     * 获取章节的方式, 获得树形结构的章节目录
//     * 此种方式 部分不兼容 ,容易获取不到目录列表
//     */
//    public void getChapterContents(@NonNull BookBean bookBean, @Nullable TableOfContents tableOfContents) {
//        epubHelper.getChapterContents(bookBean, tableOfContents);
//    }
//
//    /**
//     * 另一种获得章节的方法，具体解析待研究
//     */
//    private void getTableContents4(@NonNull Book book) throws IOException {
//        epubHelper.getTableContents4(book);
//
//    }
//
//    /**
//     * 另一种获得章节的方法，具体解析待研究
//     */
//    private void getTableContents3(@NonNull Book book) throws IOException {
//        epubHelper.getTableContents3(book);
//
//    }
//
//
//    private void parseImage(@NonNull BookBean bookBean, @NonNull Book book) throws IOException {
//        epubHelper.parseImage(bookBean, book);
//    }
//
//    /**
//     * 获取图书的基本信息
//     */
//    @NonNull
//    public BookBean parseBaseInfo(@NonNull BookBean bookBean, @NonNull Metadata metadata) {
//        return epubHelper.parseBaseInfo(bookBean, metadata);
//
//    }
//
//
//    /**
//     * 解析html
//     */
//    private List<EpubBean> parseHtml(String strHtml) {
//        return epubHelper.parseHtml(strHtml);
//    }
//
//    /**
//     * 测试数据
//     */
//    private void parseHtml2(String strHtml) {
//        epubHelper.parseHtml2(strHtml);
//    }
//
//    /**
//     * 解析html 内容
//     */
//    private void parseHtml3(String url) {
//        epubHelper.parseHtml3(url);
//    }
//
//
//    @Nullable
//    public InputStream getInputStream(@NonNull Context context, long index) {
//        return  epubHelper.getInputStream(context, index);
//    }
//
//
//}
