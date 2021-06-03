package com.xuanyuan.pdfdemo

import android.Manifest.permission
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.xuanyuan.pdf.PDFView
import com.xuanyuan.pdf.PdfViewHelper


import java.io.InputStream

class PdfActivity : AppCompatActivity() {

    var input: InputStream? = null
    private lateinit var pdfView: PDFView
    private var handler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf)
        pdfView = findViewById(R.id.pdfView)

        initHandler()

        Thread {
            val path =
                "http://ziyuanyunying.zhongjiaoyuntu.com/resource/resourceFiles/bookFile/探险传奇/探险传奇.pdf"
//                "http://ziyuanyunying.zhongjiaoyuntu.com/resource/resourceFiles/bookFile/实验室安全指导手册/实验室安全指导手册.pdf"
//                "http://ziyuanyunying.zhongjiaoyuntu.com/resource/resourceFiles/bookFile/青蛙太子与好心男孩/青蛙太子与好心男孩.pdf"
            PdfViewHelper.getIntance().loadInput(path)
            handler?.sendEmptyMessage(1111)
        }.start()
    }


    private fun initHandler() {
        val permissionCheck: Int =
            ContextCompat.checkSelfPermission(this, permission.READ_EXTERNAL_STORAGE)
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(permission.READ_EXTERNAL_STORAGE), 123)
            return
        }

        val looper = Looper.myLooper()
        looper?.let {
            handler = Handler(it) {
               PdfViewHelper.getIntance().showInput(pdfView)
                false
            }
        }
    }


}