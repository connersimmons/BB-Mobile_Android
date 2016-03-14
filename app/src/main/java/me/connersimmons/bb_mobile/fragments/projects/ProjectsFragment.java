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
public class ProjectsFragment extends Fragment {

    public static String TAG = "ProjectsFragment";

    public ProjectsFragment() {
        // Required empty public constructor
    }

    public static final ProjectsFragment newInstance() {
        return new ProjectsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate tab_layout and setup Views.
        View view = inflater.inflate(R.layout.fragment_projects, container, false);;



        return view;
    }


}
