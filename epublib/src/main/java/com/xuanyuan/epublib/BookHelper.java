package com.xuanyuan.epublib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xuanyuan.epublib.entity.BookBean;
import com.xuanyuan.epublib.entity.EpubBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Metadata;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.Resources;
import nl.siegmann.epublib.domain.Spine;
import nl.siegmann.epublib.domain.SpineReference;
import nl.siegmann.epublib.domain.TableOfContents;

/**
 * @author 罗发新
 * 创建时间：2021/4/19  11:36
 * 邮件：424533553@qq.com
 * CSDN：https://blog.csdn.net/luo_boke
 * <p>
 * 更新时间：2021/4/19  11:36
 * <p>
 * 文件说明：
 */
public class BookHelper {

    private long bookId;

    /**
     * 耗时操作
     * 解析一本图书
     *
     * @return 图书
     */
    @NonNull
    public BookBean parseBook(@NonNull Book book)  {
        /* *********************************/
        BookBean bookBean = new BookBean();
        parseBaseInfo(bookBean, book.getMetadata());

//      getChapterContents(bookBean,book.getTableOfContents());


//        List<TOCReference> list= book.getTableOfContents().getTocReferences();

        getChapterContents(bookBean, book.getSpine());
        try {
            parseImage(bookBean, book);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(getClass().getSimpleName(),e.getMessage());
        }

//      getTableContents4(book);

        return bookBean;
    }

    /**
     * 获取章节的方式, 不是特好用
     */
    public void getChapterContents(@NonNull BookBean bookBean, @NonNull Book book) throws IOException {
        List<Resource> resources = book.getContents();
        if (resources != null && resources.size() > 0) {
            for (int i = 0; i < resources.size(); i++) {
                Resource resource = resources.get(i);
                byte[] data = resource.getData();

//                InputStream inputStream = resource.getInputStream();
//                String strHtml = StringUtils.convertStreamToString(inputStream);

                String strHtml = StringUtils.bytes2Hex(data);
                List<EpubBean> list = parseHtml(strHtml);
                if (list.size() > 0) {
                    bookBean.setList(list);
                    break;
                }
            }
        }
    }

    /**
     * 获取章节的方式, 通过spine获取线性阅读菜单
     */
    public void getChapterContents(@NonNull BookBean bookBean, @NonNull Spine spine) {
        try {
            List<SpineReference> spineReferences = spine.getSpineReferences();
            if (spineReferences != null && spineReferences.size() > 0) {
                for (int i = 0; i < spineReferences.size(); i++) {
                    Resource resource = spineReferences.get(i).getResource();
                    byte[] data = resource.getData();
                    String strHtml = StringUtils.bytes2Hex(data);
                    List<EpubBean> list = parseHtml(strHtml);
                    if (list.size() > 0) {
                        bookBean.setList(list);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取章节的方式, 获得树形结构的章节目录
     * 此种方式 部分不兼容 ,容易获取不到目录列表
     */
    public void getChapterContents(@NonNull BookBean bookBean, @Nullable TableOfContents tableOfContents) {
        try {
            if (tableOfContents != null && tableOfContents.size() > 0) {
                for (int i = 0; i < tableOfContents.size(); i++) {
                    Resource resource = tableOfContents.getTocReferences().get(i).getResource();
                    byte[] data = resource.getData();
                    String strHtml = StringUtils.bytes2Hex(data);
                    List<EpubBean> list = parseHtml(strHtml);
                    if (list.size() > 0) {
                        bookBean.setList(list);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 另一种获得章节的方法，具体解析待研究
     */
    public void getTableContents4(@NonNull Book book) throws IOException {
        Resource resource = book.getOpfResource();
        byte[] data = resource.getData();
        String strHtml = StringUtils.bytes2Hex(data);
        Document doc = Jsoup.parse(strHtml);
    }

    /**
     * 另一种获得章节的方法，具体解析待研究
     */
    public void getTableContents3(@NonNull Book book) throws IOException {
        Resource resource = book.getNcxResource();
        byte[] data = resource.getData();
        String strHtml = StringUtils.bytes2Hex(data);
        Document doc = Jsoup.parse(strHtml);

        // a标签
        Elements eles = doc.getElementsByTag("navPoint");

        if (eles != null) {
            for (Element link : eles) {

//                // a标签的href属性
                String text = link.text();
                Log.i("333333333", " 书名信息=");
            }
        }
    }


    public void parseImage(@NonNull BookBean bookBean, @NonNull Book book) throws IOException {
        Resource resImage = book.getCoverImage();
        if (resImage != null) {
            // 获取图片方法 一
//            byte[] data = resImage.getData();
//            bookBean.setBitmap(BitmapFactory.decodeByteArray(data, 0, data.length));
            // 获取图片方法 二
            Bitmap coverImage = BitmapFactory.decodeStream(resImage.getInputStream());
            if (coverImage != null) {
                bookBean.setBitmap(coverImage);
            }
        } else {
            Resources ress = book.getResources();
            Resource res = ress.getById("cover-image");
            boolean contains = ress.containsByHref("cover-image");
            byte[] data = res.getData();
            bookBean.setBitmap(BitmapFactory.decodeByteArray(data, 0, data.length));
        }
    }

    /**
     * 获取图书的基本信息
     */
    @NonNull
    public BookBean parseBaseInfo(@NonNull BookBean bookBean, @NonNull Metadata metadata) {
        String author = "";
        if (metadata.getAuthors().size() > 0) {
            author = metadata.getAuthors().get(0).toString();
        }
        bookBean.setAuthor(author);

        //出版社
        String publisher = "";
        if (metadata.getPublishers().size() > 0) {
            publisher = metadata.getPublishers().get(0);
        }
        bookBean.setPublisher(publisher);


        String time = "";
        if (metadata.getDates().size() > 0) {
            time = metadata.getDates().get(0).toString();
        }
        bookBean.setTime(time);


        String title = "";
        if (metadata.getTitles().size() > 0) {
            title = metadata.getTitles().get(0);
        }
        bookBean.setTitle(title);

        String subject = "";
        if (metadata.getSubjects().size() > 0) {
            subject = metadata.getSubjects().get(0);
        }
        bookBean.setSubject(subject);

        //简介
        String description = "";
        if (metadata.getDescriptions().size() > 0) {
            description = metadata.getDescriptions().get(0);
        }
        bookBean.setDescription(description);
        bookBean.setLanguage(metadata.getLanguage());
        return bookBean;
    }


    /**
     * 解析html
     */
    public List<EpubBean> parseHtml(String strHtml) {
        List<EpubBean> list = new ArrayList<>();
        Document doc = Jsoup.parse(strHtml);

        // a标签
        Elements eles = doc.getElementsByTag("a");
        // 遍历Elements的每个Element
        EpubBean epubBean;
        for (Element link : eles) {
            // a标签的href属性
            String linkHref = link.attr("href");
            String text = link.text();
            epubBean = new EpubBean();
            epubBean.href = linkHref;
            epubBean.bookId = bookId;
            epubBean.tilte = text;
            list.add(epubBean);
        }
        return list;
    }

    /**
     * 测试数据
     */
    public void parseHtml2(String strHtml) {
        // html文本，url，本地html
        Document doc = Jsoup.parse(strHtml);
        doc.title();
        // a标签
        Elements eles = doc.getElementsByTag("a");
        // 遍历Elements的每个Element
        for (Element link : eles) {
            // a标签的href属性
            String linkHref = link.attr("href");
            String text = link.text();
            String href = linkHref;
        }

        // 使用选择器语法来查找元素
        Elements elements = doc.select("a[href]");
        Elements elements2 = doc.select("img[src$=.png]");
        // 如：Elements elements = doc.select("div.unit");   <div class="unit"> </div>
        Element element3 = doc.select("div.className").first();

        // 数据修改
        doc.select("div.className").attr("key", "value");
        // class="myclass"  新增class
        doc.select("div.className").addClass("myclass");
        // 移除属性
        doc.select("img").removeAttr("onclick");

        // 消除不受信任的html (来防止xss攻击) ,转成安全的
        String safe = Jsoup.clean("", Whitelist.basic()); //
    }

    /**
     * 解析html 内容
     */
    public void parseHtml3(String url) {

        try {
            Document doc2 = Jsoup.connect(url).get();
            Document doc3 = Jsoup.connect(url).data("key", "value").timeout(3000).post();

            // 若根目录有个index.html文件，否则程序会crash
            File input = new File(Environment.getExternalStorageDirectory() + "/index.html");
            Document doc4 = Jsoup.parse(input, "utf-8", "http://baidu.com");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Nullable
    public InputStream getInputStream(@NonNull Context context, long index) {
        try {
            bookId = index;
            InputStream in;
            switch ((int) index) {
                case 4:
                    in = context.getAssets().open("飞狐外传.epub");
                    break;
                case 0:
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
                    in = context.getAssets().open("176116.epub");
                    break;
            }
            return in;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
