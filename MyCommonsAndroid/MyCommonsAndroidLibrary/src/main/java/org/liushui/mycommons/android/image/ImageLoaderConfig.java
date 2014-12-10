package org.liushui.mycommons.android.image;

public class ImageLoaderConfig {

    private String imageStorePath;

    private ILoadInterceptListener interceptListener;

    public String getImageStorePath() {
        return imageStorePath;
    }

    public void setImageStorePath(String imageStorePath) {
        this.imageStorePath = imageStorePath;
    }

    public ILoadInterceptListener getInterceptListener() {
        return interceptListener;
    }

    public void setInterceptListener(ILoadInterceptListener interceptListener) {
        this.interceptListener = interceptListener;
    }
}