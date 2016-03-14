package me.connersimmons.bb_mobile.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import me.connersimmons.bb_mobile.R;
import me.connersimmons.bb_mobile.adapters.ProjectsPagerAdapter;

public class ProjectsActivity extends AppCompatActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ProjectsActivity.class);
        //intent.putExtra(KEY_DEMO, demo.name());
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.new_project_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();

                createNewProject();
            }
        });



        ViewPager viewPager = (ViewPager) findViewById(R.id.projectsViewPager);
        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        //demo.setup(viewPagerTab);

        /*
        FragmentPagerItems pages = new FragmentPagerItems(this);
        for (int titleResId : demo.tabs()) {
            pages.add(FragmentPagerItem.of(getString(titleResId), DemoFragment.class));
        }

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), pages);
        */

        viewPager.setAdapter(new ProjectsPagerAdapter(getSupportFragmentManager()));
        viewPagerTab.setViewPager(viewPager);

    }

    private void createNewProject() {
        Context context = getApplicationContext();
        CharSequence text = "Create new project!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        startActivity(new Intent(this, NewProjectActivity.class));
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
