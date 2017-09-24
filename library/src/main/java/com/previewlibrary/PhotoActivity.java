package com.previewlibrary;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.previewlibrary.wight.PhotoViewPager;
import com.previewlibrary.wight.SmoothImageView;
import com.previewlibrary.enitity.ImageInfo;

import java.util.ArrayList;
import java.util.List;


public class PhotoActivity extends FragmentActivity {

    private boolean mIsTransformOut;
    private List<ImageInfo> mImgUrls;
    private int mCurrentIndex;
    private List<PhotoFragment> fragments = new ArrayList<>();
    private PhotoViewPager mViewPager;
    private TextView mTvIndicator;
    private TextView mTvIndicatorCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getContentLayout() == 0) {
            setContentView(R.layout.activity_image_preview_photo);
        } else {
            setContentView(getContentLayout());
        }
        initData();
        initView();
    }

    private void initData() {
        mImgUrls = getIntent().getParcelableArrayListExtra("imagePaths");
        mCurrentIndex = getIntent().getIntExtra("position", -1);
        if (mImgUrls != null) {
            Bundle bundle;
            for (int i = 0; i < mImgUrls.size(); i++) {
                PhotoFragment fragment = PhotoFragment.getInstance();
                bundle = new Bundle();
                bundle.putSerializable(PhotoFragment.KEY_PATH, mImgUrls.get(i).getUrl());
                bundle.putParcelable(PhotoFragment.KEY_START_BOUND, mImgUrls.get(i).getBounds());
                bundle.putBoolean(PhotoFragment.KEY_TRANS_PHOTO, mCurrentIndex == i);
                bundle.putBoolean(PhotoFragment.KEY_SING_FILING, getIntent().getBooleanExtra
                        ("isSingleFling", false));
                fragment.setArguments(bundle);
                fragments.add(fragment);
            }
        } else {
            finish();
        }
    }

    private void initView() {
        mViewPager = (PhotoViewPager) findViewById(R.id.viewPager);
        PhotoPagerAdapter adapter = new PhotoPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(mCurrentIndex);
        mTvIndicator = (TextView) findViewById(R.id.indicator);
        mTvIndicatorCount = (TextView) findViewById(R.id.indicator_count);
        mTvIndicator.setVisibility(View.VISIBLE);
        mTvIndicator.setText(String.valueOf(mCurrentIndex + 1));
        mTvIndicatorCount.setText("/" + mImgUrls.size());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (mTvIndicator != null) {
                    mTvIndicator.setText(String.valueOf(position + 1));
                }
                mCurrentIndex = position;
                mViewPager.setCurrentItem(mCurrentIndex, true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver
                .OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mViewPager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                PhotoFragment fragment = fragments.get(mCurrentIndex);
                fragment.transformIn();
            }
        });
    }

    //exit transform
    protected void transformOut() {
        if (mIsTransformOut) {
            return;
        }
        mIsTransformOut = true;
        int currentItem = mViewPager.getCurrentItem();
        if (currentItem < mImgUrls.size()) {
            PhotoFragment fragment = fragments.get(currentItem);
            if (mTvIndicator != null) {
                mTvIndicator.setVisibility(View.GONE);
            }
            fragment.changeBg(Color.TRANSPARENT);
            fragment.transformOut(new SmoothImageView.onTransformListener() {
                @Override
                public void onTransformCompleted(SmoothImageView.Status status) {
                    exit();
                }
            });
        } else {
            exit();
        }
    }

    private void exit() {
        finish();
        overridePendingTransition(0, 0);
    }

    public PhotoViewPager getmViewPager() {
        return mViewPager;
    }

    public int getContentLayout() {
        return 0;
    }

    @Override
    public void onBackPressed() {
        transformOut();
    }

    private class PhotoPagerAdapter extends FragmentPagerAdapter {

        PhotoPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    @Override
    protected void onDestroy() {
        ZoomMediaLoader.getInstance().getLoader().clearMemory(this);
        fragments.clear();
        fragments = null;
        mViewPager.setAdapter(null);
        mViewPager.clearOnPageChangeListeners();
        mViewPager.removeAllViews();
        if (mImgUrls != null) {
            mImgUrls.clear();
            mImgUrls = null;
        }
        super.onDestroy();
    }

}
