package me.connersimmons.bb_mobile.fragments.projects;


import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import io.realm.RealmResults;
import me.connersimmons.bb_mobile.R;
import me.connersimmons.bb_mobile.adapters.ProjectsRecyclerViewAdapter;
import me.connersimmons.bb_mobile.fragments.base.BaseFragment;
import me.connersimmons.bb_mobile.models.Project;


/**
 * A simple {@link ListFragment} subclass.
 */
public class ProjectsAlphaFragment extends BaseFragment implements OnItemClickListener {

    private RealmResults<Project> projects;
    private RecyclerView rv_projects;
    private ProjectsRecyclerViewAdapter adapter;

    public ProjectsAlphaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_projects, container, false);

        rv_projects = (RecyclerView) view.findViewById(R.id.rv_projects);
        rv_projects.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_projects.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    /*
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.Planets, android.R.layout.simple_list_item_1);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }
    */

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
    }
}
