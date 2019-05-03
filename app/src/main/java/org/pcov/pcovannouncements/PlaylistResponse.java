package org.pcov.pcovannouncements;

import android.support.annotation.NonNull;

import java.util.List;

public class PlaylistResponse {

    public final String nextPageToken;
    public final List<VideoInfo> videoInfos;

    public PlaylistResponse(@NonNull String nextPageToken, @NonNull List<VideoInfo> videoInfos) {
        this.nextPageToken = nextPageToken;
        this.videoInfos = videoInfos;
    }
}
