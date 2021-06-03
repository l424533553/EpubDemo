package com.xuanyuan.epubdemo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xuanyuan.epubbook.EpubHelper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @author LUOFAXIN
 */
public class ChapterDetailActivity extends AppCompatActivity {

    /**
     * 数据网址
     */
    String href;

    /**
     * 内容
     */
    String strHtml1;

    private Handler handler;
    WebView mWebView;

    @SuppressLint("SetJavaScriptEnabled")
    private void initHander() {
        handler = new Handler(Looper.myLooper(), msg -> {
            if (strHtml1 != null) {
                mWebView.getSettings().setJavaScriptEnabled(true);
                mWebView.loadDataWithBaseURL(null, strHtml1, "text/html", "utf-8", null);
            }
            return false;
        });
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_detail);
        mWebView = findViewById(R.id.webView);

        initHander();
        href = getIntent().getStringExtra("href");
        startData();

    }


    private void startData() {
        new Thread(() -> {
            strHtml1 = EpubHelper.getIntance().getChapterContent(href);
            handler.sendEmptyMessage(12345);
        }).start();
    }

}
