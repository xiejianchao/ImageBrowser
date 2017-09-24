package com.github.imagebrowser;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.previewlibrary.ImageBrowserBuilder;
import com.previewlibrary.enitity.ImageInfo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<ImageInfo> mImageInfoList = new ArrayList<>();
    private ArrayList<ImageView> imageViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ImageView imageView = (ImageView) findViewById(R.id.imageview);
        final ImageView imageView2 = (ImageView) findViewById(R.id.imageview2);
        imageViews.add(imageView);
        imageViews.add(imageView2);

        initData();

        imageView2.post(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < mImageInfoList.size(); i++) {
                    Rect bounds = new Rect();
                    ImageView imageView1 = imageViews.get(i);
                    imageViews.get(i).getGlobalVisibleRect(bounds);
                    mImageInfoList.get(i).setBounds(bounds);


                    imageView1.setTag(i);
                    imageView1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int index = (int) view.getTag();
                            ImageBrowserBuilder.from(MainActivity.this)
                                    .setData(mImageInfoList)
                                    .setCurrentIndex(index)
                                    .setSingleFling(true)
                                    .start();
                        }
                    });
                }
            }
        });

    }

    private void initData() {
        for (int i = 0; i < imageViews.size(); i++) {
            String url = ImageUrlConfig.getUrls().get(i);
            ImageInfo imageInfo = new ImageInfo(url);
            mImageInfoList.add(imageInfo);

            Glide.with(MainActivity.this)
                    .load(url)
                    .error(R.mipmap.ic_iamge_zhanwei)
                    .into(imageViews.get(i));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
