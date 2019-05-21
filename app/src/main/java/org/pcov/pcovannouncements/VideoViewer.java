package org.pcov.pcovannouncements;

import android.os.Bundle;
import android.util.Log;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.pcov.pcovannouncements.Adapters.YouTubeConfig;

public class VideoViewer extends YouTubeBaseActivity {

    YouTubePlayerView mYoutubePlayerView;
    YouTubePlayer.OnInitializedListener mOnInitializedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_viewer);
        mYoutubePlayerView = findViewById(R.id.youtube_player_view);

        mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

                String videoURL = getIntent().getStringExtra("videoURL");

                youTubePlayer.loadVideo(videoURL);
                youTubePlayer.setFullscreen(true);
                youTubePlayer.setShowFullscreenButton(false);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.w("WARNING","Failed to initialize the youtube video player");
            }

        };

        mYoutubePlayerView.initialize(YouTubeConfig.getApiKey(), mOnInitializedListener);

    }

}
