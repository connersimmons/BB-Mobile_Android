package me.connersimmons.bb_mobile.widgets.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import me.connersimmons.bb_mobile.R;
import me.connersimmons.bb_mobile.fragments.projects.ProjectsAlphaFragment;
import me.connersimmons.bb_mobile.fragments.projects.ProjectsCityFragment;
import me.connersimmons.bb_mobile.fragments.projects.ProjectsDateFragment;

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
            case 0 : return "A-Z";
            case 1 : return "Date";
            case 2 : return "City";
        }
        return null;
    }

    public void setup(SmartTabLayout layout) {
        final LayoutInflater inflater = LayoutInflater.from(layout.getContext());

        layout.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
                ImageView icon = (ImageView) inflater.inflate(R.layout.tabs_icons, container,
                        false);
                switch (position) {
                    case 0:
                        icon.setImageResource(R.drawable.ic_sort_by_alpha_white_24dp);
                        break;
                    case 1:
                        icon.setImageResource(R.drawable.ic_date_range_white_24dp);
                        break;
                    case 2:
                        icon.setImageResource(R.drawable.ic_location_on_white_24dp);
                        break;
                    default:
                        throw new IllegalStateException("Invalid position: " + position);
                }
                return icon;
            }
        });
    }
}
