package me.connersimmons.bb_mobile.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import me.connersimmons.bb_mobile.vendors.VendorsAlphaFragment;
import me.connersimmons.bb_mobile.vendors.VendorsCityFragment;
import me.connersimmons.bb_mobile.vendors.VendorsClassificationFragment;

/**
 * Created by connersimmons on 2/6/16.
 */
public class VendorsPagerAdapter extends FragmentPagerAdapter {

    public static int numItems = 3 ;

    public VendorsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * Return fragment with respect to position.
     */
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : return new VendorsAlphaFragment();
            case 1 : return new VendorsClassificationFragment();
            case 2 : return new VendorsCityFragment();
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
            case 1 : return "Classification";
            case 2 : return "City";
        }
        return null;
    }
}
