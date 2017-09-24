package com.previewlibrary;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.previewlibrary.enitity.ImageInfo;

import java.util.ArrayList;


public final class ImageBrowserBuilder {

    private Activity mContext;
    private Intent mIntent;
    private Class mClassName;

    private ImageBrowserBuilder(@NonNull Activity activity) {
        mContext = activity;
        mIntent = new Intent();
    }

    /***
     * 设置开始启动预览
     * @param activity  启动
     */
    public static ImageBrowserBuilder from(@NonNull Activity activity) {
        return new ImageBrowserBuilder(activity);
    }

    /***
     * 设置开始启动预览
     * @param fragment  启动
     */
    public static ImageBrowserBuilder from(@NonNull Fragment fragment) {
        return new ImageBrowserBuilder(fragment.getActivity());
    }

    /****
     *自定义预览activity 类名
     * @param className   继承GPreviewActivity
     */
    public ImageBrowserBuilder to(@NonNull Class className) {
        this.mClassName = className;
        mIntent.setClass(mContext, className);
        return this;
    }

    /***
     * 设置数据源
     * @param imgUrls 数据
     * @return ImageBrowserBuilder
     * **/
    public ImageBrowserBuilder setData(@NonNull ArrayList<ImageInfo> imgUrls) {
        mIntent.putParcelableArrayListExtra("imagePaths", imgUrls);
        return this;
    }

    /***
     * 设置默认索引
     * @param currentIndex 数据
     * @return ImageBrowserBuilder
     * **/
    public ImageBrowserBuilder setCurrentIndex(int currentIndex) {
        mIntent.putExtra("position", currentIndex);
        return this;
    }

    /***
     * 设置超出内容点击退出（黑色区域）
     * @param isSingleFling  true  or false
     * @return ImageBrowserBuilder
     * **/
    public ImageBrowserBuilder setSingleFling(boolean isSingleFling) {
        mIntent.putExtra("isSingleFling", isSingleFling);
        return this;
    }


    public void start() {
        if (mClassName == null) {
            mIntent.setClass(mContext, PhotoActivity.class);
        } else {
            mIntent.setClass(mContext, mClassName);
        }
        mContext.startActivity(mIntent);
        mIntent = null;
        mContext = null;
    }

}
