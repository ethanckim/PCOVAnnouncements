package org.pcov.pcovannouncements;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.pcov.pcovannouncements.Fragments.AnnouncementFragment;
import org.pcov.pcovannouncements.Fragments.GalleryFragment;
import org.pcov.pcovannouncements.Fragments.InformationFragment;
import org.pcov.pcovannouncements.Fragments.SettingsFragment;
import org.pcov.pcovannouncements.Fragments.VideosFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new AnnouncementFragment()).commit();
        navigationView.setCheckedItem(R.id.nav_announcements);
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
        } else if (id == R.id.nav_info) {
            nextFrag = new InformationFragment();
        } else {
            nextFrag = new SettingsFragment();
            Log.w("WARNING", "WARNING: Unexpected Fragment Item has been chosen from the navigation Drawer."
                    + "\n" + "Currently set it to the settings Fragment to avoid null pointer.");
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, nextFrag, nextFrag.getTag())
                .commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
