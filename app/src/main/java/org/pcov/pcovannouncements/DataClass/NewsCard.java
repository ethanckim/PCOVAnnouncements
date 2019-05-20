package org.pcov.pcovannouncements.DataClass;

public class NewsCard {

    private String type;
    private String date;
    private String context;

    public NewsCard(String type, String date, String context) {
        this.type = type;
        this.date = date;
        this.context = context;
    }

    public NewsCard() {
    }

    public String getType() {
        return type;
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
                " title='" + type + '\'' +
                ", date='" + date + '\'' +
                ", context='" + context + '\'' +
                '}';
    }
}
