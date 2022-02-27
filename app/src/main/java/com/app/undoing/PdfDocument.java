package com.app.undoing;


import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.undoing.interfaces.toolbarInterface;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;


public class PdfDocument extends AppCompatActivity implements toolbarInterface {
    private PDFView pdfView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.education_document);

        /* set up toolbar */
        ImageView toolbar_title=(ImageView) findViewById(R.id.toolbar_title);
        toolbar_title.setImageDrawable(getResources().getDrawable(R.drawable.ic_green_title));
        ImageView toolbar_education=(ImageView) findViewById(R.id.toolbar_education);
        toolbar_education.setVisibility(View.INVISIBLE);
        setupBackListener(this);
        setTitleListener(this);

        pdfView = (PDFView) findViewById( R.id.pdfView );

        Intent intent = getIntent();
        String value = intent.getStringExtra("pdfName");
        //从assets目录读取pdf
        displayFromAssets(value);
    }

    private void displayFromAssets(String assetFileName ) {
        pdfView.fromAsset(assetFileName) //设置pdf文件地址
                //.pages(0, 2, 1, 3, 3, 3) // all pages are displayed by default
                .enableSwipe(true) // allows to block changing pages using swipe
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                .onRender(new OnRenderListener() {
                    @Override
                    public void onInitiallyRendered(int pages, float pageWidth, float pageHeight) {
                        pdfView.fitToWidth(0);
                    }
                }) // called after document is rendered for the first time
                .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                .password(null)
                .scrollHandle(null)
                .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                // spacing between pages in dp. To define spacing color, set view background
                .spacing(0)
                .load();
    }

}

