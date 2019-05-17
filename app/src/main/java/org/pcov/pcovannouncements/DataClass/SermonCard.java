package org.pcov.pcovannouncements.DataClass;

public class SermonCard {

    private String mVideoId;
    private String mTitleText;

    public SermonCard(String videoId, String titleText) {
        this.mVideoId = videoId;
        this.mTitleText = titleText;
    }

    public String getmVideoId() {
        return mVideoId;
    }

    public String getmTitleText() {
        return mTitleText;
    }

    public String toString() {
        return "Sermon Card{" +
                "videoId='" + mVideoId + '\'' +
                ", title='" + mTitleText + '\'' +
                '}';
    }

}
