package org.pcov.pcovannouncements.Adapters;

public class YouTubeConfig {

    private static final String API_KEY = "AIzaSyCNERNNOxAggAs5ewHOqtt0g6LzZgi53gI";
    private static final String PLAYLIST_ID = "UUOnGdCsRfhaWsE3oFrKapiw";

    public YouTubeConfig() {
        super();
    }

    public static String getApiKey() {
        return API_KEY;
    }

    public static String getPlaylistId() {
        return PLAYLIST_ID;
    }
}
