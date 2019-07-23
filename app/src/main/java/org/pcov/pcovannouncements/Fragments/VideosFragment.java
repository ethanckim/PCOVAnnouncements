package org.pcov.pcovannouncements.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;

import org.pcov.pcovannouncements.Adapters.SermonCardAdapter;
import org.pcov.pcovannouncements.Adapters.YouTubeConfig;
import org.pcov.pcovannouncements.DataClass.SermonCard;
import org.pcov.pcovannouncements.DataClass.VideoInfo;
import org.pcov.pcovannouncements.Utils;
import org.pcov.pcovannouncements.PlaylistResponse;
import org.pcov.pcovannouncements.R;
import org.pcov.pcovannouncements.VideoViewer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;


public class VideosFragment extends Fragment {

    private static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    private final AtomicReference<AsyncTask<Void, Void, PlaylistResponse>> lastApiCall = new AtomicReference<>(null);
    private View v;
    private RecyclerView mRecyclerView;
    private ArrayList<SermonCard> mSermonVideosList = new ArrayList<>();
    private SermonCardAdapter madapter = new SermonCardAdapter(mSermonVideosList);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Updates the list of videos based off the YouTube API call.
        getResultsFromApiFirstTime();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_videos, container, false);

        mRecyclerView = v.findViewById(R.id.videosRecyclerView);

        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(madapter);

        madapter.setOnClickListener(new SermonCardAdapter.OnCardClickListener() {
            @Override
            public void onCardClick(int position) {
                //Navigate to the new activity, based off the card (Use position the distinguish cards)
                Intent i;
                i = new Intent(getActivity(), VideoViewer.class);
                i.putExtra("videoURL", mSermonVideosList.get(position).getmVideoId());
                startActivity(i);

            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    final AsyncTask<Void, Void, PlaylistResponse> currentTask = lastApiCall.get();
                    if (currentTask != null && (!currentTask.isCancelled() && currentTask.getStatus() == AsyncTask.Status.FINISHED)) {
                        try {
                            final String nextPageToken = currentTask.get().nextPageToken;
                            if (!nextPageToken.isEmpty()) {
                                lastApiCall.set(new MakeRequestTask(nextPageToken).execute());
                            }
                        } catch (ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        TextView txtnoInternet = (TextView) v.findViewById(R.id.txt_no_connection);
        if (Utils.isDeviceOnline(this.getActivity())) {
            txtnoInternet.setVisibility(View.GONE);
        } else {
            txtnoInternet.setVisibility(View.VISIBLE);
        }

        return v;
    }

    /**
     * Attempt to call the API, after verifying that all the preconditions are
     * satisfied. The preconditions are: Google Play Services installed, an
     * account was selected and the device currently has online access. If any
     * of the preconditions are not satisfied, the app will prompt the user as
     * appropriate.
     */
    private void getResultsFromApiFirstTime() {
        if (!isGooglePlayServicesAvailable()) {
            acquireGooglePlayServices();
        } else if (!Utils.isDeviceOnline(this.getActivity())) {
            Toast noInternetToast = Toast.makeText(getActivity().getApplicationContext(),
                    R.string.no_connection_video, Toast.LENGTH_LONG);
            noInternetToast.show();
        } else {
            final AsyncTask<?, ?, ?> currentTask = lastApiCall.get();
            if (currentTask == null) {
                mSermonVideosList.clear();
                lastApiCall.set(new MakeRequestTask("").execute());
            }
        }
    }

    /**
     * Check that Google Play services APK is installed and up to date.
     *
     * @return true if Google Play Services is available and up to
     * date on this device; false otherwise.
     */
    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(getActivity());
        return connectionStatusCode == ConnectionResult.SUCCESS;
    }

    /**
     * Attempt to resolve a missing, out-of-date, invalid or disabled Google
     * Play Services installation via a user dialog, if possible.
     */
    private void acquireGooglePlayServices() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(getActivity());
        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
        }
    }

    /**
     * Display an error dialog showing that Google Play Services is missing
     * or out of date.
     *
     * @param connectionStatusCode code describing the presence (or lack of)
     *                             Google Play Services on this device.
     */
    void showGooglePlayServicesAvailabilityErrorDialog(
            final int connectionStatusCode) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        Dialog dialog = apiAvailability.getErrorDialog(
                getActivity(),
                connectionStatusCode,
                REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();
    }


    /**
     * An asynchronous task that handles the YouTube Data API call.
     * Placing the API calls in their own task ensures the UI stays responsive.
     */
    public class MakeRequestTask extends AsyncTask<Void, Void, PlaylistResponse> {
        private static final String appName = "PCOV Announcements";
        private final String nextPageToken;
        private YouTube mService;

        public MakeRequestTask(String nextPageToken) {
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mService = new YouTube.Builder(transport, jsonFactory, null)
                    .setApplicationName(appName)
                    .build();
            this.nextPageToken = nextPageToken == null ? "" : nextPageToken;
        }

        /**
         * Background task to call YouTube Data API.
         *
         * @param params no parameters needed for this task.
         */
        @Override
        protected PlaylistResponse doInBackground(Void... params) {
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
         *
         * @return List of Strings containing information about the channel.
         * @throws IOException
         */
        private PlaylistResponse getDataFromApi() throws IOException {
            // Get a list of playlist item information lists.
            //the child list includes videoID and title in order.
            String nextPage = "";
            List<VideoInfo> playlistItemInfo = new ArrayList<>();

            YouTube.PlaylistItems.List request = mService.playlistItems()
                    .list("snippet,contentDetails");

            PlaylistItemListResponse response = request.setKey(YouTubeConfig.getApiKey())
                    .setMaxResults(25L)
                    .setPageToken(nextPageToken)
                    .setPlaylistId(YouTubeConfig.getPlaylistId())
                    .execute();

            List<PlaylistItem> playlistItems = response.getItems();
            if (playlistItems != null) {
                nextPage = response.getNextPageToken();
                for (int i = 0; i < playlistItems.size(); i++) {
                    PlaylistItem playlistItem = playlistItems.get(i);
                    String videoId = playlistItem.getContentDetails().getVideoId();
                    String title = playlistItem.getSnippet().getTitle();
                    playlistItemInfo.add(new VideoInfo(title, videoId));

                }
                //log
                Log.d("GetFullAPI", playlistItems.toString());
            }
            return new PlaylistResponse(nextPage == null ? "" : nextPage, playlistItemInfo);
        }


        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(PlaylistResponse output) {
            if (output == null || output.videoInfos.size() == 0) {
                //no results returned - Error!
                Log.w("WARNING", "Warning - No results returned from Youtube Data API");
            } else {
                for (VideoInfo vi : output.videoInfos) {
                    mSermonVideosList.add(new SermonCard(vi.videoId, vi.title));
                    madapter.notifyDataSetChanged();
                }
                Log.d("GetOnlyNecessaryInfo", mSermonVideosList.toString());
            }
        }
    }

}
