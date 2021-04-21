package com.xuanyuan.epubdemo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.xuanyuan.epublib.BookHelper;
import com.xuanyuan.epublib.StringUtils;
import com.xuanyuan.epublib.entity.EpubBean;

import java.io.InputStream;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.epub.EpubReader;

/**
 * @author LUOFAXIN
 */
public class ChapterDetailActivity extends AppCompatActivity {

    private WebView mWebView;
    BookHelper helper = new BookHelper();

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_detail);
        mWebView = findViewById(R.id.webView);

            EpubBean bean = getIntent().getParcelableExtra("href");
            EpubReader reader = new EpubReader();
            InputStream in = helper.getInputStream(this, bean.bookId);
            Book book = reader.readEpub(in);
            Resource resource = book.getResources().getByHref(bean.href);
            //和 resource.getInputStream() 返回的都是html格式的文章内容，只不过读取方式不一样
            byte[] data = resource.getData();
            String strHtml1 = StringUtils.bytes2Hex(data);
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.loadDataWithBaseURL(null, strHtml1, "text/html", "utf-8", null);

    }
}
