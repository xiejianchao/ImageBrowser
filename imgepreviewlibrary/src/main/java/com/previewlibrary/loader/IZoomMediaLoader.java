package com.previewlibrary.loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;


public interface IZoomMediaLoader {

    /**
     * @param context
     * @param path
     * @param simpleTarget
     */
    void displayImage(@NonNull Fragment context, @NonNull String path, @NonNull
            MySimpleTarget<Bitmap> simpleTarget);

    /**
     * stop
     */
    void onStop(@NonNull Fragment context);

    /**
     * clearMemory
     *
     * @param context
     */
    void clearMemory(@NonNull Context context);
}
