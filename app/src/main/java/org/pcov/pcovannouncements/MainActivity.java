package org.pcov.pcovannouncements;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import org.pcov.pcovannouncements.Adapters.MakeRequestTask;
import org.pcov.pcovannouncements.Fragments.AnnouncementFragment;
import org.pcov.pcovannouncements.Fragments.GalleryFragment;
import org.pcov.pcovannouncements.Fragments.InformationFragment;
import org.pcov.pcovannouncements.Fragments.SettingsFragment;
import org.pcov.pcovannouncements.Fragments.VideosFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    private static Fragment currentFrag = new VideosFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        getResultsFromApi();
        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, currentFrag, currentFrag.getTag())
                .commit();
        navigationView.setCheckedItem(R.id.nav_videos);

        orientationChangeSetUp();
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment nextFrag;

        if (id == R.id.nav_announcements) {
            nextFrag = new AnnouncementFragment();
        } else if (id == R.id.nav_gallery) {
            nextFrag = new GalleryFragment();
        } else if (id == R.id.nav_videos) {
            nextFrag = new VideosFragment();
            getResultsFromApi();
        } else if (id == R.id.nav_info) {
            nextFrag = new InformationFragment();
        } else {
            nextFrag = new SettingsFragment();
            Log.w("WARNING", "WARNING: Unexpected Fragment Item has been chosen from the navigation Drawer."
                    + "\n" + "Currently set it to the settings Fragment to avoid null pointer.");
        }

        currentFrag = nextFrag;

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, nextFrag, nextFrag.getTag())
                .commit();


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Makes necessary changes Orientation is changed (Landscape/Portrait)
     */
    public void orientationChangeSetUp() {

        int newOrientation = this.getResources().getConfiguration().orientation;

        ImageView footerPhoto = findViewById(R.id.nav_footer_photo);

        if (newOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            footerPhoto.setVisibility(View.INVISIBLE);
            Log.d("FooterVisibility", "Footer Image is invisible so the user can click buttons");
        } else {
            footerPhoto.setVisibility(View.VISIBLE);
            Log.d("FooterVisibility", "Footer Image can be seen.");
        }
    }

    /**
     * Attempt to call the API, after verifying that all the preconditions are
     * satisfied. The preconditions are: Google Play Services installed, an
     * account was selected and the device currently has online access. If any
     * of the preconditions are not satisfied, the app will prompt the user as
     * appropriate.
     */
    private void getResultsFromApi() {
        if (! isGooglePlayServicesAvailable()) {
            acquireGooglePlayServices();
        } else if (! isDeviceOnline()) {
            Toast noInternetToast = Toast.makeText(getApplicationContext(), "No network connection available", Toast.LENGTH_SHORT);
            noInternetToast.show();
        } else {
            new MakeRequestTask().execute();
        }
    }


    /**
     * Checks whether the device currently has a network connection.
     * @return true if the device has a network connection, false otherwise.
     */
    private boolean isDeviceOnline() {
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    /**
     * Check that Google Play services APK is installed and up to date.
     * @return true if Google Play Services is available and up to
     *     date on this device; false otherwise.
     */
    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(this);
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
                apiAvailability.isGooglePlayServicesAvailable(this);
        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
        }
    }

    /**
     * Display an error dialog showing that Google Play Services is missing
     * or out of date.
     * @param connectionStatusCode code describing the presence (or lack of)
     *     Google Play Services on this device.
     */
    void showGooglePlayServicesAvailabilityErrorDialog(
            final int connectionStatusCode) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        Dialog dialog = apiAvailability.getErrorDialog(
                MainActivity.this,
                connectionStatusCode,
                REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();
    }

}
