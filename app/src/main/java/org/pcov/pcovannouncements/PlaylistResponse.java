package org.pcov.pcovannouncements;

import androidx.annotation.NonNull;

import org.pcov.pcovannouncements.DataClass.VideoInfo;

import java.util.List;

public class PlaylistResponse {

    public final String nextPageToken;
    public final List<VideoInfo> videoInfos;

    public PlaylistResponse(@NonNull String nextPageToken, @NonNull List<VideoInfo> videoInfos) {
        this.nextPageToken = nextPageToken;
        this.videoInfos = videoInfos;
    }
}
