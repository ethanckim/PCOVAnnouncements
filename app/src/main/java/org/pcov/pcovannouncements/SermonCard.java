package org.pcov.pcovannouncements;

public class SermonCard {

    private String mYoutubeThumnailLink;
    private String mTitleText;
    private String mSubtitleText;

    public SermonCard(String YoutubeThumnailLink, String TitleText, String SubtitleText) {
        this.mYoutubeThumnailLink = YoutubeThumnailLink;
        this.mTitleText = TitleText;
        this.mSubtitleText = SubtitleText;
    }

    public String getmYoutubeThumnailLink() {
        return mYoutubeThumnailLink;
    }

    public String getmTitleText() {
        return mTitleText;
    }

    public String getmSubtitleText() {
        return mSubtitleText;
    }
}
