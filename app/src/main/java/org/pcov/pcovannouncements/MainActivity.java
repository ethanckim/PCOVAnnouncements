package org.pcov.pcovannouncements;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import org.pcov.pcovannouncements.Fragments.AnnouncementFragment;
import org.pcov.pcovannouncements.Fragments.GalleryFragment;
import org.pcov.pcovannouncements.Fragments.InformationFragment;
import org.pcov.pcovannouncements.Fragments.VideosFragment;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static Fragment currentFrag = new VideosFragment();
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getString(R.string.nav_select_sermons));

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //If main activity is opened with notification
        String menuFragment = getIntent().getStringExtra("menuFragment");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // If menuFragment is defined, then this activity was launched with a fragment selection
        if (menuFragment != null) {
            if (menuFragment.equals("announcementsFragment")) {
                AnnouncementFragment announcementFragment = new AnnouncementFragment();
                fragmentTransaction.replace(R.id.fragmentContainer, announcementFragment).commit();
            }
        } else {
            // Activity was not launched with a menuFragment selected -- continue as if this activity was opened from a launcher (for example)
            fragmentTransaction.replace(R.id.fragmentContainer, currentFrag, currentFrag.getTag())
                    .commit();
        }

        String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

        int permissionWriteExternal = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionReadExternal = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionWriteExternal != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
        } else if (permissionReadExternal != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
        }

        if (currentFrag.equals(AnnouncementFragment.class)) {
            navigationView.setCheckedItem(R.id.nav_announcements);
        } else if (currentFrag.equals(InformationFragment.class)) {
            navigationView.setCheckedItem(R.id.nav_info);
        } else if (currentFrag.equals(GalleryFragment.class)) {
            navigationView.setCheckedItem(R.id.nav_gallery);
        } else {
            navigationView.setCheckedItem(R.id.nav_videos);
        }

        orientationChangeSetUp();

        //FCM Token
        getFirebaseInstanceId();
        //For back-end FCM automatic sending
        FirebaseMessaging.getInstance().subscribeToTopic("Announcements");

        db.collection("Other")
                .document("churchVision")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                TextView visionText = findViewById(R.id.vision_text);
                String language = Locale.getDefault().getISO3Language();
                if (task.getResult() != null) {
                    visionText.setText(task.getResult().getString(language));
                }
            }
        });
    }


    private void getFirebaseInstanceId() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful()) {
                    Log.w("FCM", "getInstanceId failed", task.getException());
                    return;
                }

                // Get new Instance ID token
                String token = task.getResult().getToken();

                // Log
                Log.d("FCM", "Token: " + token);

                Map<String, Object> docData = new HashMap<>();
                docData.put(token.substring(0, 3), token);
                //Send to firestore
                db.collection("Tokens").document("tokensList").set(docData);
            }
        });
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
            getSupportActionBar().setTitle(R.string.nav_select_announcements);
        } else if (id == R.id.nav_gallery) {
            nextFrag = new GalleryFragment();
            getSupportActionBar().setTitle(R.string.nav_select_gallery);
        } else if (id == R.id.nav_info) {
            nextFrag = new InformationFragment();
            getSupportActionBar().setTitle(R.string.nav_select_information);
        } else {
            nextFrag = new VideosFragment();
            getSupportActionBar().setTitle(R.string.nav_select_sermons);
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
}
