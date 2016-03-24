package me.connersimmons.bb_mobile.fragments.projects;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import me.connersimmons.bb_mobile.R;
import me.connersimmons.bb_mobile.widgets.adapters.AdapterProjectsAlpha;
import me.connersimmons.bb_mobile.models.Project;
import me.connersimmons.bb_mobile.presenters.impl.ProjectPresenter;
import me.connersimmons.bb_mobile.widgets.Divider;
import me.connersimmons.bb_mobile.widgets.AlphaRecyclerView;


/**
 * A simple {@link ListFragment} subclass.
 */
public class ProjectsAlphaFragment extends Fragment implements OnItemClickListener {

    private final String TAG = ProjectsAlphaFragment.class.getName();

    private RecyclerView mRecycler;
    private RealmResults<Project> mResults;
    private AdapterProjectsAlpha mAdapter;
    private Realm mRealm;
    private ProjectPresenter mProjectPresenter;

    private RealmChangeListener mChangeListener = new RealmChangeListener() {
        @Override
        public void onChange() {
            mAdapter.update(mResults);
        }
    };

    public ProjectsAlphaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRealm = Realm.getDefaultInstance();

        mProjectPresenter = new ProjectPresenter();
        mResults = mProjectPresenter.getAllProjects();
        mResults.addChangeListener(mChangeListener);

        Log.d(TAG, "mResults: " + mResults.toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_projects, container, false);



        //mEmptyView = findViewById(R.id.empty_drops);

        mRecycler = (AlphaRecyclerView) view.findViewById(R.id.rv_projects);
        mRecycler.addItemDecoration(new Divider(getContext(), LinearLayoutManager.VERTICAL));
        mRecycler.setItemAnimator(new DefaultItemAnimator());
        //mRecycler.hideIfEmpty(mToolbar);
        //mRecycler.showIfEmpty(mEmptyView);
        mAdapter = new AdapterProjectsAlpha(getContext(), mRealm, mResults);
        mAdapter.setHasStableIds(true);
        mRecycler.setAdapter(mAdapter);

        //SimpleTouchCallback callback = new SimpleTouchCallback(mAdapter);
        //ItemTouchHelper helper = new ItemTouchHelper(callback);
        //helper.attachToRecyclerView(mRecycler);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        mResults.addChangeListener(mChangeListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        mResults.removeChangeListener(mChangeListener);
    }
}
