package me.connersimmons.bb_mobile.fragments.vendors;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.connersimmons.bb_mobile.R;
import me.connersimmons.bb_mobile.activities.MainActivity;
import me.connersimmons.bb_mobile.adapters.VendorsPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class VendorsTabLayoutFragment extends Fragment {


    public VendorsTabLayoutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vendors_tab_layout, container, false);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.vendors_tabLayout);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.vendors_viewpager);

        viewPager.setAdapter(new VendorsPagerAdapter(getFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle(R.string.title_activity_vendors);
    }

}
