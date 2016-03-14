package me.connersimmons.bb_mobile.fragments.vendors;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.connersimmons.bb_mobile.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class VendorsFragment extends Fragment {

    public VendorsFragment() {
        // Required empty public constructor
    }

    public static VendorsFragment newInstance() {
        return new VendorsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate tab_layout and setup Views.
        View view = inflater.inflate(R.layout.fragment_vendors, container, false);;

        return view;
    }
}
