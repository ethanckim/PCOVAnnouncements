package org.pcov.pcovannouncements.Adapters;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;

import org.pcov.pcovannouncements.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * An asynchronous task that handles the YouTube Data API call.
 * Placing the API calls in their own task ensures the UI stays responsive.
 */
public class MakeRequestTask extends AsyncTask<Void, Void, List<String>> {
    private YouTube mService;
    private static final String appName = "PCOV Announcements";

    public MakeRequestTask() {
        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        mService = new YouTube.Builder(transport, jsonFactory, null)
                .setApplicationName(appName)
                .build();
    }

    /**
     * Background task to call YouTube Data API.
     * @param params no parameters needed for this task.
     */
    @Override
    protected List<String> doInBackground(Void... params) {
        try {
            return getDataFromApi();
        } catch (Exception e) {
            cancel(true);
            return null;
        }
    }

    /**
     * Fetch information about the "Phil Church" Uploads Playlist Item.
     * The aim is to get the videoId, which would be used to view the videos in the Youtube Viewer.
     * @return List of Strings containing information about the channel.
     * @throws IOException
     */
    private List<String> getDataFromApi() throws IOException {
        // Get a list of up to 10 files.
        List<String> playlistItemInfo = new ArrayList<String>();

        YouTube.PlaylistItems.List request = mService.playlistItems()
                .list("contentDetails");

        PlaylistItemListResponse response = request.setKey(YouTubeConfig.getApiKey())
                .setMaxResults(50L)
                .setPlaylistId("UUOnGdCsRfhaWsE3oFrKapiw")
                .execute();


        List<PlaylistItem> playlistItems = response.getItems();
        if (playlistItems != null) {
            for (int i = 0; i < playlistItems.size(); i++) {
                PlaylistItem playlistItem = playlistItems.get(i);
                playlistItemInfo.add(playlistItem.getContentDetails().getVideoId());
            }
            //log
            Log.d("GetFullAPI", playlistItems.toString());
            Log.d("GetOnlyNecessaryInfo", playlistItemInfo.toString());
        }
        return playlistItemInfo;
    }


    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onPostExecute(List<String> output) {
        if (output == null || output.size() == 0) {
            //no results returned - Error!
            Log.w("WARNING", "Warning - No results returned from Youtube Data API");
        } else {
            //Results returned
        }
    }
}

