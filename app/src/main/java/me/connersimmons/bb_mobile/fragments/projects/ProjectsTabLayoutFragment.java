package me.connersimmons.bb_mobile.fragments.projects;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import me.connersimmons.bb_mobile.R;
import me.connersimmons.bb_mobile.activities.MainActivity;
import me.connersimmons.bb_mobile.activities.NewProjectActivity;
import me.connersimmons.bb_mobile.widgets.adapters.AdapterProjectsAlpha;
import me.connersimmons.bb_mobile.widgets.adapters.ProjectsPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectsTabLayoutFragment extends Fragment {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FloatingActionButton mFab;

    private View.OnClickListener mBtnAddListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            createNewProject();
        }
    };

    public ProjectsTabLayoutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_projects_tab_layout, container, false);

        mTabLayout = (TabLayout) view.findViewById(R.id.projects_tabLayout);
        mViewPager = (ViewPager) view.findViewById(R.id.projects_viewpager);

        mViewPager.setAdapter(new ProjectsPagerAdapter(getFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);

        mFab = (FloatingActionButton) view.findViewById(R.id.create_project_fab);
        mFab.setOnClickListener(mBtnAddListener);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle(R.string.title_projects);
    }

    private void createNewProject() {
        Context context = getActivity().getApplicationContext();
        CharSequence text = "Create new project!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        startActivity(new Intent(getActivity(), NewProjectActivity.class));
    }
}
