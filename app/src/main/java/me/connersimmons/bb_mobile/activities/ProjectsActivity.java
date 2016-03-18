package me.connersimmons.bb_mobile.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;
import me.connersimmons.bb_mobile.R;
import me.connersimmons.bb_mobile.activities.base.BaseActivity;
import me.connersimmons.bb_mobile.adapters.ProjectsPagerAdapter;
import me.connersimmons.bb_mobile.adapters.ProjectsRecyclerViewAdapter;
import me.connersimmons.bb_mobile.fragments.projects.NewProjectDialogFragment;
import me.connersimmons.bb_mobile.models.Project;
import me.connersimmons.bb_mobile.presenters.IProjectPresenter;
import me.connersimmons.bb_mobile.presenters.impl.ProjectPresenter;

public class ProjectsActivity extends BaseActivity {

    private static IProjectPresenter presenter;
    private ProjectsRecyclerViewAdapter adapter;

    private RealmResults<Project> projects;
    private RecyclerView recyclerViewProjects;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ProjectsActivity.class);
        //intent.putExtra(KEY_DEMO, demo.name());
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);

        presenter = new ProjectPresenter(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.new_project_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewProject();
            }
        });

        ViewPager viewPager = (ViewPager) findViewById(R.id.projectsViewPager);
        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);

        recyclerViewProjects = (RecyclerView) findViewById(R.id.rv_projects);

        ProjectsPagerAdapter projectsPagerAdapter = new ProjectsPagerAdapter(getSupportFragmentManager());
        projectsPagerAdapter.setup(viewPagerTab);

        viewPager.setAdapter(projectsPagerAdapter);
        viewPagerTab.setViewPager(viewPager);

        /*
        final NewProjectDialogFragment fragment = NewProjectDialogFragment.newInstance();

        fragment.setListener(new NewProjectDialogFragment.OnAddStudentClickListener() {
            @Override
            public void onAddStudentClickListener(Project project) {
                fragment.dismiss();
                //presenter.addStudentByUniversityId(student, universityId);
                //presenter.getAllStudentsByUniversityId(universityId);
                presenter.addProject(project);
            }
        });
        */
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.subscribeCallbacks();

        //presenter.getUniversityById(universityId);
        //presenter.getAllStudentsByUniversityId(universityId);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.unSubscribeCallbacks();
    }

    private void createNewProject() {
        Context context = getApplicationContext();
        CharSequence text = "Create new project!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        startActivity(new Intent(this, NewProjectActivity.class));
    }

    public void showProjects(RealmResults<Project> projects) {
        this.projects = projects;
        adapter = new ProjectsRecyclerViewAdapter(projects);
        recyclerViewProjects.setAdapter(adapter);
    }

    public static IProjectPresenter getPresenter() {
        return presenter;
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_projects, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new_project:
                Context context = getApplicationContext();
                CharSequence text = "Create new project!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();



                startActivity(new Intent(this, NewProjectActivity.class));

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
    */
}
