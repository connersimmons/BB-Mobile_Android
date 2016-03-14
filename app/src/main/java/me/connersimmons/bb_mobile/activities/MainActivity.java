package me.connersimmons.bb_mobile.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import me.connersimmons.bb_mobile.AppConstants;
import me.connersimmons.bb_mobile.fragments.BluesearchFragment;
import me.connersimmons.bb_mobile.fragments.HomeFragment;
import me.connersimmons.bb_mobile.R;
import me.connersimmons.bb_mobile.fragments.SettingsFragment;
import me.connersimmons.bb_mobile.api.ContactsProvider;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    AppConstants mConstantsInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.mainContainerView, new HomeFragment()).commit();

        mConstantsInstance = AppConstants.getInstance();

        //Since reading contacts takes more time, let's run it on a separate thread.
        new Thread(new Runnable() {
            @Override
            public void run() {
                mConstantsInstance.setContactsList(ContactsProvider.load(getApplicationContext()));
                //constantsInstance.s = ContactsProvider.load(mContext);
            }
        }).start();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*
        if (id == R.id.action_settings) {
            return true;
        }
        */

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        if (id == R.id.nav_home) {
            fragmentTransaction.replace(R.id.mainContainerView,new HomeFragment()).commit();
        } else if (id == R.id.nav_search) {
            fragmentTransaction.replace(R.id.mainContainerView,new BluesearchFragment()).commit();
        } else if (id == R.id.nav_vendors) {
            //fragmentTransaction.replace(R.id.mainContainerView,new VendorsFragment()).commit();
            VendorsActivity.startActivity(this);
        } else if (id == R.id.nav_projects) {
            //fragmentTransaction.replace(R.id.containerView,new ProjectsFragment()).commit();
            ProjectsActivity.startActivity(this);
        } else if (id == R.id.nav_settings) {
            fragmentTransaction.replace(R.id.mainContainerView,new SettingsFragment()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setActionBarTitle(int title) {
        getSupportActionBar().setTitle(title);
    }

    public void setActionBarIcon(int icon) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(icon);
    }
}
