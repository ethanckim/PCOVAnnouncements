package org.pcov.pcovannouncements.DataClass;

public class ImageCard {
    private int mImageId;
    private String mImageText;

    public ImageCard(int imageId, String imageText) {
        this.mImageId = imageId;
        this.mImageText = imageText;
    }

    public int getmImageId() {
        return mImageId;
    }

    public String getmImageText() {
        return mImageText;
    }

    public String toString() {
        return "Image Card{" +
                "imageId='" + mImageId + '\'' +
                ", text='" + mImageText + '\'' +
                '}';
    }
}
