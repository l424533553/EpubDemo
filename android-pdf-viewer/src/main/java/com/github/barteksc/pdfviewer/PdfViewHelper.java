package com.github.barteksc.pdfviewer;

import android.net.Uri;

import androidx.annotation.NonNull;


import com.github.barteksc.pdfviewer.source.DocumentSource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @author 罗发新
 * 创建时间：2021/6/3  10:04
 * 邮件：424533553@qq.com
 * CSDN：https://blog.csdn.net/luo_boke
 * <p>
 * 更新时间：2021/6/3  10:04
 * <p>
 * 文件说明：PdfView框架  这玩意儿加载老慢了。 感觉很坑爹玩意
 * implementation 'com.github.barteksc:android-pdf-viewer:3.1.0-beta.1'
 */
public class PdfViewHelper {
    PDFView.Configurator configurator;
    InputStream input;

    PdfViewHelper() {

    }

    public void loadInput(String path) {
        try {
            URL url = new URL(path);
            //打开URL对应的资源输入流
            input = url.openStream();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    public static PdfViewHelper getIntance() {
        return SingletonHolder.SINTANCE;
    }

    public static class SingletonHolder {
        private static final PdfViewHelper SINTANCE = new PdfViewHelper();
    }


    private void loadView(PDFView.Configurator configurator) {

        configurator
                // 夜间模式
//                .nightMode(false)
                // 呈现注释（例如注释，颜色或表单）
//                .enableAnnotationRendering(false)
                //以下配置实现 ViewPage
                //pdf文档翻页是否是垂直翻页，默认是左右滑动翻页
                .swipeHorizontal(true)
                ///捕捉页面到屏幕边界
//                .pageSnap(true)
                //在屏幕上添加动态间距以适应每个页面
//                .autoSpacing(true)
                //像viewPage 一样效果
                .pageFling(true)
                //是否允许翻页，默认是允许翻页
                .enableSwipe(true)
                //默认显示所有页面
//                .pages(0, 2, 1, 3, 3, 3)
                //是否双击
//                .enableDoubletap(false)
                //开始页数, 默认的页面
                .defaultPage(0)
                // 密码为空
//                .password(null)
//                .scrollHandle(null)
                // 在低分辨率屏幕上稍微改进渲染
//                .enableAntialiasing(true)
                // 以 dp 为单位的页面间距。要定义间距颜色，请设置视图背景
//                .spacing(0)
//                .pageFitPolicy(FitPolicy.WIDTH)

                // called after document is loaded and starts to be rendered
//                .onLoad(onLoadCompleteListener)
//                .onPageChange(onPageChangeListener)
//                .onPageScroll(onPageScrollListener)
//                .onError(onErrorListener)
//                .onPageError(onPageErrorListener)
//                 //called after document is rendered for the first time
//                .onRender(onRenderListener)
//                 //called on single tap, return true if handled, false to toggle scroll handle visibility
//                .onTap(onTapListener)
//                .linkHandler(DefaultLinkHandler)
                //允许在当前页面上绘制一些东西，通常在屏幕中间可见
//                .onDraw(onDrawListener)
                //允许在所有页面上绘制一些东西，分别为每个页面绘制。仅对可见页面调用
//                .onDrawAll(onDrawListener)
                .load();
    }

    /**
     * "http://ziyuanyunying.zhongjiaoyuntu.com/resource/resourceFiles/bookFile/青蛙太子与好心男孩/青蛙太子与好心男孩.pdf");
     */
    public void showPdf(@NonNull String uriPath, @NonNull PDFView pdfView) {
        Uri uri = Uri.parse(uriPath);
        loadView(pdfView.fromUri(uri));
    }

    public void showPdf(@NonNull File file, @NonNull PDFView pdfView) {
        loadView(pdfView.fromFile(file));
    }

    public void showPdf(@NonNull byte[] bytes, @NonNull PDFView pdfView) {
        loadView(pdfView.fromBytes(bytes));
    }

    public void showPdf(@NonNull InputStream input, @NonNull PDFView pdfView) {
        loadView(pdfView.fromStream(input));
    }

    public void showInput(@NonNull PDFView pdfView) {
        if (input != null) {
            PDFView.Configurator configurator = pdfView.fromStream(input);
            loadView(configurator);
        }
    }

    public void showPdf(@NonNull DocumentSource source, @NonNull PDFView pdfView) {
        loadView(pdfView.fromSource(source));
    }

    public void showAssetPdf(@NonNull String assetName, @NonNull PDFView pdfView) {
        loadView(pdfView.fromAsset(assetName));
    }

}
