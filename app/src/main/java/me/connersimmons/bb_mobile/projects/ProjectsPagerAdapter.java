package me.connersimmons.bb_mobile.projects;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import me.connersimmons.bb_mobile.vendors.VendorsAlphaFragment;
import me.connersimmons.bb_mobile.vendors.VendorsCityFragment;
import me.connersimmons.bb_mobile.vendors.VendorsClassificationFragment;

/**
 * Created by connersimmons on 2/6/16.
 */
public class ProjectsPagerAdapter extends FragmentPagerAdapter {

    public static int numItems = 3 ;

    public ProjectsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * Return fragment with respect to position.
     */
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : return new ProjectsAlphaFragment();
            case 1 : return new ProjectsDateFragment();
            case 2 : return new ProjectsCityFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return numItems;
    }

    /**
     * This method returns the title of the tab according to the position.
     */
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0 : return "Alphabetical";
            case 1 : return "Date";
            case 2 : return "City";
        }
        return null;
    }
}
