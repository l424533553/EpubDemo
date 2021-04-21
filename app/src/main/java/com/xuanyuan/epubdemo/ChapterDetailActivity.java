package com.xuanyuan.epubdemo;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.webkit.WebView;


import androidx.appcompat.app.AppCompatActivity;

import com.xuanyuan.epubdemo.entity.EpubBean;
import com.xuanyuan.epubdemo.utils.StringUtils;

import java.io.IOException;
import java.io.InputStream;


import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.epub.EpubReader;

/**
 * @author LUOFAXIN
 */
public class ChapterDetailActivity extends AppCompatActivity {

    private  WebView mWebView;
    BookHelper helper = new BookHelper();
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_detail);
        mWebView=findViewById(R.id.webView);


        EpubBean href = getIntent().getParcelableExtra("href");
        try {
            EpubReader reader = new EpubReader();
            InputStream in = helper.getInputStream(this, href.bookId);

            Book book = reader.readEpub(in);
            Resource byHref = book.getResources(). getByHref(href.href);

            //和 resource.getInputStream() 返回的都是html格式的文章内容，只不过读取方式不一样
            byte[] data = byHref.getData();
            String strHtml1 = StringUtils.bytes2Hex(data);

            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.loadDataWithBaseURL(null, strHtml1, "text/html", "utf-8", null);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
