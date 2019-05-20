package org.pcov.pcovannouncements.DataClass;

public class NewsCard {

    private int mImageResource;
    private String title;
    private String date;
    private String context;

    public NewsCard(int mImageResourceString, String title, String date, String context) {
        this.mImageResource = mImageResource;
        this.title = title;
        this.date = date;
        this.context = context;
    }

    public NewsCard() {
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

    public String getContext() {
        return context;
    }

    @Override
    public String toString() {
        return "NewsCard{" +
                "mImageResource=" + mImageResource +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", context='" + context + '\'' +
                '}';
    }
}
