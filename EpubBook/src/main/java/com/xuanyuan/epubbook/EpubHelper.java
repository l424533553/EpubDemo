package com.xuanyuan.epubbook;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xuanyuan.epubbook.entity.EpubBean;
import com.xuanyuan.epubbook.entity.ChapterBean;

import net.sf.jazzlib.ZipFile;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.MediaType;
import nl.siegmann.epublib.domain.Metadata;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.Resources;
import nl.siegmann.epublib.domain.Spine;
import nl.siegmann.epublib.domain.SpineReference;
import nl.siegmann.epublib.domain.TOCReference;
import nl.siegmann.epublib.domain.TableOfContents;
import nl.siegmann.epublib.epub.EpubReader;
import nl.siegmann.epublib.service.MediatypeService;

/**
 * @author 罗发新
 * 创建时间：2021/4/19  11:36
 * 邮件：424533553@qq.com
 * CSDN：https://blog.csdn.net/luo_boke
 * <p>
 * 更新时间：2021/4/19  11:36
 * <p>
 * 文件说明： 图书解析辅助类
 */
public class EpubHelper {

    private EpubHelper() {
    }

    private Book book;


    /**
     * 使用时间
     */
    @NonNull
    public static EpubHelper getIntance() {
        return SingletonHolder.sIntance;
    }

    public static class SingletonHolder {
        private static final EpubHelper sIntance = new EpubHelper();
    }


    /**
     * 懒加载图书
     *
     * @param bookFile 图书文件
     */
    public void loadBookLazy(@Nullable File bookFile) {
        if (bookFile == null) {
            return;
        }
        EpubReader epubReader = new EpubReader();
        MediaType[] lazyTypes = {
                MediatypeService.CSS,
                MediatypeService.GIF,
                MediatypeService.JPG,
                MediatypeService.PNG,
                MediatypeService.MP3,
                MediatypeService.MP4};

        //fileName 文件的绝对位置
        try {
            book = epubReader.readEpubLazy(new ZipFile(bookFile), "UTF-8", Arrays.asList(lazyTypes));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得章节内容
     * @param href href路径
     * @return 返回 html 字符串
     */
    @Nullable
    public String getChapterContent(@Nullable String href) {
        if (book == null) {
            return "";
        }
        Resource resource = book.getResources().getByHref(href);
        //和 resource.getInputStream() 返回的都是html格式的文章内容，只不过读取方式不一样
        if (resource != null) {
            byte[] data = resource.getData();
            return StringUtils.bytes2Hex(data);
        }
        return "";
    }

    /**
     * 时间内容
     */
    public void loadBook(@Nullable File bookFile) {
        if (bookFile == null) {
            return;
        }
        try {
            EpubReader reader = new EpubReader();
            this.book = reader.readEpub(new ZipFile(bookFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadBook(@Nullable InputStream in) {
        if (in == null) {
            return;
        }
        EpubReader reader = new EpubReader();
        this.book = reader.readEpub(in);
    }

    public void loadBook(@Nullable String urlPath) {
        try {
            URL url = new URL("https://yidu.seaeverit.com/cslib_image/ebookdev/切肤之琴.epub");
            // 打开该URL对应的资源的输入流
            EpubReader reader = new EpubReader();
            this.book = reader.readEpub(url.openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 耗时操作
     * 解析一本图书
     *
     * @return 图书
     */
    @Nullable
    public EpubBean parseBook() {
        if (book == null) {
            return null;
        }
        /* *********************************/
        EpubBean epubBean = new EpubBean();
        parseBaseInfo(epubBean, book.getMetadata());

//      getChapterContents(bookBean,book.getTableOfContents());
//      List<TOCReference> list= book.getTableOfContents().getTocReferences();

//        getChapterContents(epubBean, book.getSpine());

        getChapterContents(epubBean, book.getTableOfContents());
        try {
            parseImage(epubBean, book);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(getClass().getSimpleName(), e.getMessage());
        }

//      getTableContents4(book);

        return epubBean;
    }

    /**
     * 获取章节的方式, 不是特好用
     */
    public void getChapterContents(@NonNull EpubBean epubBean, @NonNull Book book) throws IOException {
        List<Resource> resources = book.getContents();
        if (resources != null && resources.size() > 0) {
            for (int i = 0; i < resources.size(); i++) {
                Resource resource = resources.get(i);
                byte[] data = resource.getData();

//                InputStream inputStream = resource.getInputStream();
//                String strHtml = StringUtils.convertStreamToString(inputStream);

                String strHtml = StringUtils.bytes2Hex(data);
                List<ChapterBean> list = parseHtml(strHtml);
                if (list.size() > 0) {
                    epubBean.list = list;
                    break;
                }
            }
        }
    }

    /**
     * 获取章节的方式, 通过spine获取线性阅读菜单
     */
    public void getChapterContents(@NonNull EpubBean epubBean, @NonNull Spine spine) {
        try {
            List<SpineReference> spineReferences = spine.getSpineReferences();
            if (spineReferences != null && spineReferences.size() > 0) {
                for (int i = 0; i < spineReferences.size(); i++) {
                    Resource resource = spineReferences.get(i).getResource();
                    byte[] data = resource.getData();
                    String strHtml = StringUtils.bytes2Hex(data);
                    List<ChapterBean> list = parseHtml(strHtml);
                    if (list.size() > 0) {
                        epubBean.list = (list);
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
    public void getChapterContents(@NonNull EpubBean epubBean, @Nullable TableOfContents tableOfContents) {
        try {
            if (tableOfContents != null && tableOfContents.getTocReferences().size() > 0) {
                List<TOCReference> list = tableOfContents.getTocReferences();

                List<ChapterBean> chapterBeanList = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    ChapterBean chapterBean = new ChapterBean();
                    String title = list.get(i).getTitle();
                    Resource resource = list.get(i).getResource();
                    chapterBean.tilte = title;
                    chapterBean.href = resource.getHref();
                    chapterBean.size = resource.getData().length;
                    chapterBeanList.add(chapterBean);


//                    byte[] data = resource.getData();
//                    String strHtml = StringUtils.bytes2Hex(data);
//                    List<ChapterBean> data22 = parseHtml(strHtml);
//                    if (list.size() > 0) {
//                        epubBean.list = (list);
//                        break;
//                    }

                }
                epubBean.list = chapterBeanList;
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

    public void parseImage(@NonNull EpubBean epubBean, @NonNull Book book) throws IOException {
        Resource resImage = book.getCoverImage();
        if (resImage != null) {
            // 获取图片方法 一
//            byte[] data = resImage.getData();
//            bookBean.setBitmap(BitmapFactory.decodeByteArray(data, 0, data.length));
            // 获取图片方法 二
            Bitmap coverImage = BitmapFactory.decodeStream(resImage.getInputStream());
            if (coverImage != null) {
                epubBean.bitmap = (coverImage);
            }
        } else {
            Resources ress = book.getResources();


            boolean contains = ress.containsByHref("cover-image");
            if (contains) {
                Resource res = ress.getById("cover-image");
                if (res != null) {
                    byte[] data = res.getData();
                    epubBean.bitmap = (BitmapFactory.decodeByteArray(data, 0, data.length));
                }
            }
        }
    }

    /**
     * 获取图书的基本信息
     */
    @NonNull
    public EpubBean parseBaseInfo(@NonNull EpubBean epubBean, @NonNull Metadata metadata) {
        String author = "";
        if (metadata.getAuthors().size() > 0) {
            author = metadata.getAuthors().get(0).toString();
        }
        epubBean.author = (author);

        //出版社
        String publisher = "";
        if (metadata.getPublishers().size() > 0) {
            publisher = metadata.getPublishers().get(0);
        }
        epubBean.publisher = (publisher);


        String time = "";
        if (metadata.getDates().size() > 0) {
            time = metadata.getDates().get(0).toString();
        }
        epubBean.time = (time);


        String title = "";
        if (metadata.getTitles().size() > 0) {
            title = metadata.getTitles().get(0);
        }
        epubBean.title = (title);

        String subject = "";
        if (metadata.getSubjects().size() > 0) {
            subject = metadata.getSubjects().get(0);
        }
        epubBean.subject = (subject);

        //简介
        String description = "";
        if (metadata.getDescriptions().size() > 0) {
            description = metadata.getDescriptions().get(0);
        }
        epubBean.description = (description);
        epubBean.language = (metadata.getLanguage());
        return epubBean;
    }

    /**
     * 解析html
     */
    public List<ChapterBean> parseHtml(String strHtml) {
        List<ChapterBean> list = new ArrayList<>();
        Document doc = Jsoup.parse(strHtml);

        // a标签
        Elements eles = doc.getElementsByTag("a");
        // 遍历Elements的每个Element
        ChapterBean chapterBean;
        for (Element link : eles) {
            // a标签的href属性
            String linkHref = link.attr("href");
            String text = link.text();
            chapterBean = new ChapterBean();
            chapterBean.href = linkHref;
            chapterBean.tilte = text;
            list.add(chapterBean);
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


}
