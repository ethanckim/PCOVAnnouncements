package org.pcov.pcovannouncements.DataClass;

public class InformationCard {

    private int mImageResource;
    private String mtext;

    public InformationCard(int imageResource, String text) {
        mImageResource = imageResource;
        mtext = text;
    }

    public int getImageResource() {
        return mImageResource;
    }

    public String getText() {
        return mtext;
    }
}
