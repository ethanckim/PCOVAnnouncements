package org.pcov.pcovannouncements;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import org.pcov.pcovannouncements.Fragments.AnnouncementFragment;
import org.pcov.pcovannouncements.Fragments.GalleryFragment;
import org.pcov.pcovannouncements.Fragments.InformationFragment;
import org.pcov.pcovannouncements.Fragments.VideosFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static Fragment currentFrag = new VideosFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, currentFrag, currentFrag.getTag())
                .commit();

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
        } else if (id == R.id.nav_videos) {
            nextFrag = new VideosFragment();
            getSupportActionBar().setTitle(R.string.nav_select_sermons);
        } else {
            nextFrag = new InformationFragment();
            getSupportActionBar().setTitle(R.string.nav_select_information);
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
