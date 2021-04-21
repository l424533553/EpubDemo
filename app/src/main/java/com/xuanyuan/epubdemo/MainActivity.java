package com.xuanyuan.epubdemo;

import android.content.Context;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.xuanyuan.epubdemo.databinding.ActivityMainBinding;
import com.xuanyuan.epubdemo.entity.BookBean;
import com.xuanyuan.epubdemo.entity.EpubBean;
import com.xuanyuan.epubdemo.utils.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.Spine;
import nl.siegmann.epublib.domain.SpineReference;
import nl.siegmann.epublib.epub.EpubReader;

/**
 * @author LUOFAXIN
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private final List<EpubBean> indexTitleList = new ArrayList<>();
    ActivityMainBinding binding;
    Book book;
    Handler handler;
    int index;
    BookBean bookBean;
    List<String> list = new ArrayList<>();
    private MyAdatper mAdatper;
    BookHelper helper = new BookHelper();

    private Context context;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        context = this;

        initListView();
        binding.image.setOnClickListener(this);
        initHandler();
        initData();

    }

    private void initHandler() {
        handler = new Handler(Looper.getMainLooper(), msg -> {
            if (bookBean != null) {
                binding.tvText.setText(bookBean.toString());
                binding.image.setImageBitmap(bookBean.getBitmap());
            }

            mAdatper.notifyDataSetChanged();
            return false;
        });
    }


    /**
     * 初始化数据
     */
    private void initData() {

        new Thread(() -> {
            try {

                EpubReader reader = new EpubReader();
                InputStream in = helper.getInputStream(context, index);

                index = (index + 1) % 9;
                book = reader.readEpub(in);

                if (book != null) {
                    bookBean = helper.parseBook(book);
                    List<EpubBean> list = bookBean.getList();
                    if (list != null) {
                        indexTitleList.clear();
                        indexTitleList.addAll(list);
                    }

                    handler.sendEmptyMessage(101);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }


    private void initListView() {
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        mAdatper = new MyAdatper(indexTitleList, this);
        binding.recycler.setAdapter(mAdatper);
    }



    /**
     * 打印数据
     */
    void printSpine(Spine spine) {
        try {
            List<SpineReference> spineReferences = spine.getSpineReferences();
            if (spineReferences != null && spineReferences.size() > 0) {
                for (int i = 0; i < spineReferences.size(); i++) {
                    // 获取带章节信息的那个html页面

                    Resource resource = spineReferences.get(i).getResource();
                    byte[] data = resource.getData();
                    String strHtml = StringUtils.bytes2Hex(data);
                    Log.i("22222222", "initView: strHtml= " + strHtml);
//                    parseHtml(strHtml);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        initData();
    }
}
