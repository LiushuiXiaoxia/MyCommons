package org.liushui.mycommons.android.image;

public class ImageLoadItem {

	public String url;

	public ImageLoadItem() {
		super();
	}

	public ImageLoadItem(String url) {
		super();
		this.url = url;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ImageLoadItem other = (ImageLoadItem) obj;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	public String toString() {
		return "ImageLoadItem [url=" + url + "]";
	}
}