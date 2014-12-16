package org.liushui.mycommons.android.image;

public class ImageLoadItem {

    private static final int DEFAULT_SIZE = -1;

    private int width = DEFAULT_SIZE;

    private int height = DEFAULT_SIZE;

    private String url;

    public ImageLoadItem() {
        super();
    }

    public ImageLoadItem(String url) {
        super();
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String toString() {
        return "ImageLoadItem [width=" + width + ", height=" + height + ", url=" + url + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + height;
        result = prime * result + ((url == null) ? 0 : url.hashCode());
        result = prime * result + width;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ImageLoadItem other = (ImageLoadItem) obj;
        if (height != other.height)
            return false;
        if (url == null) {
            if (other.url != null)
                return false;
        } else if (!url.equals(other.url))
            return false;
        if (width != other.width)
            return false;
        return true;
    }

    public boolean isNeedScale() {
        return width != DEFAULT_SIZE && height != DEFAULT_SIZE;
    }
}