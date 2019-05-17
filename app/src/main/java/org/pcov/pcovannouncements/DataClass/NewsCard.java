package org.pcov.pcovannouncements.DataClass;

public class NewsCard {

    private int mImageResource;
    private String title;
    private String date;

    public NewsCard(int mImageResourceString, String title, String date) {
        this.mImageResource = mImageResource;
        this.title = title;
        this.date = date;
    }

    public int getmImageResource() {
        return mImageResource;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

}
