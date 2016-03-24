package me.connersimmons.bb_mobile.activities;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import me.connersimmons.bb_mobile.utils.AppConstants;
import me.connersimmons.bb_mobile.fragments.BluesearchFragment;
import me.connersimmons.bb_mobile.fragments.HomeFragment;
import me.connersimmons.bb_mobile.R;
import me.connersimmons.bb_mobile.fragments.SettingsFragment;
import me.connersimmons.bb_mobile.api.ContactsProvider;
import me.connersimmons.bb_mobile.fragments.projects.ProjectsTabLayoutFragment;
import me.connersimmons.bb_mobile.fragments.vendors.VendorsTabLayoutFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {



    NavigationView mNavigationView;
    Toolbar mToolbar;

    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    private AppBarLayout mAppBarLayout;
    AppConstants mConstantsInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mConstantsInstance = AppConstants.getInstance();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        /*
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            ab.setDisplayHomeAsUpEnabled(true);
        }
        */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        initialFragmentSetup();

        //Since reading contacts takes more time, let's run it on a separate thread.
        new Thread(new Runnable() {
            @Override
            public void run() {
                mConstantsInstance.setContactsList(ContactsProvider.load(getApplicationContext()));
            }
        }).start();
    }

    private void initialFragmentSetup() {
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.mainContainerView, new HomeFragment()).commit();
        mNavigationView.getMenu().getItem(0).setChecked(true);
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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        item.setChecked(true);
        int id = item.getItemId();
        mFragmentTransaction = mFragmentManager.beginTransaction();

        if (id == R.id.nav_home) {
            setActionBarElevation(mConstantsInstance.getDefaultElevation());
            mFragmentTransaction.replace(R.id.mainContainerView,new HomeFragment()).commit();
        } else if (id == R.id.nav_search) {
            setActionBarElevation(mConstantsInstance.getZeroElevation());
            mFragmentTransaction.replace(R.id.mainContainerView,new BluesearchFragment()).commit();
        } else if (id == R.id.nav_vendors) {
            setActionBarElevation(mConstantsInstance.getZeroElevation());
            mFragmentTransaction.replace(R.id.mainContainerView,new VendorsTabLayoutFragment()).commit();
        } else if (id == R.id.nav_projects) {
            setActionBarElevation(mConstantsInstance.getZeroElevation());
            mFragmentTransaction.replace(R.id.mainContainerView,new ProjectsTabLayoutFragment()).commit();
        } else if (id == R.id.nav_settings) {
            setActionBarElevation(mConstantsInstance.getDefaultElevation());
            mFragmentTransaction.replace(R.id.mainContainerView,new SettingsFragment()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setActionBarTitle(int title) {
        getSupportActionBar().setTitle(title);
    }

    public void setActionBarElevation(int elevation) {
        mAppBarLayout=(AppBarLayout) findViewById(R.id.appbar);
        mAppBarLayout.setElevation(elevation);
    }
}
