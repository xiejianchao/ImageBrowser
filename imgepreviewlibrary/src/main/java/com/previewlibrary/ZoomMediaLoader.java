package com.previewlibrary;

import com.previewlibrary.loader.IZoomMediaLoader;


public class ZoomMediaLoader {
    private volatile IZoomMediaLoader loader;
    public  static ZoomMediaLoader getInstance(){
        return  Holder.holder;
    }
    private ZoomMediaLoader(){

    }
    private  static  class  Holder{
           static ZoomMediaLoader holder=new ZoomMediaLoader();
    }
    /****
     * 初始化加载图片类
     * @param  loader 自定义
     * **/
    public  void init(IZoomMediaLoader loader){
        this.loader=loader;
    }

     IZoomMediaLoader getLoader() {
        if (loader==null){
            throw  new  NullPointerException("loader no init");
        }
        return loader;
    }
}
