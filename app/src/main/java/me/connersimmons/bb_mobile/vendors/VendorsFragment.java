package me.connersimmons.bb_mobile.vendors;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import me.connersimmons.bb_mobile.MainActivity;
import me.connersimmons.bb_mobile.R;
import me.connersimmons.bb_mobile.adapter.VendorsPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class VendorsFragment extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;

    public VendorsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate tab_layout and setup Views.
        View view = inflater.inflate(R.layout.fragment_vendors, container, false);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);

        // Set an Apater for the View Pager
        viewPager.setAdapter(new VendorsPagerAdapter(getChildFragmentManager()));

        /**
         * Workaround:
         * The setupWithViewPager doesn't work without the runnable.
         */
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle(R.string.title_vendors);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_vendors, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_local_contact:
                Context context = getActivity().getApplicationContext();
                CharSequence text = "Add local contact!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
