package me.connersimmons.bb_mobile.fragments.projects;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.connersimmons.bb_mobile.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectsDateFragment extends Fragment {


    public ProjectsDateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_projects, container, false);
    }

}
