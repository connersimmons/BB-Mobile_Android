package me.connersimmons.bb_mobile.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.TimePicker;

import io.realm.RealmResults;
import me.connersimmons.bb_mobile.R;
import me.connersimmons.bb_mobile.activities.base.BaseActivity;
import me.connersimmons.bb_mobile.fragments.projects.NewProjectDialogFragment;
import me.connersimmons.bb_mobile.models.Project;
import me.connersimmons.bb_mobile.presenters.IProjectPresenter;
import me.connersimmons.bb_mobile.presenters.impl.ProjectPresenter;


public class NewProjectActivity extends BaseActivity implements
        DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    private Project newProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        if (savedInstanceState == null) {
            createFullScreenDialog();
        }
    }

    private void createFullScreenDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        final NewProjectDialogFragment newFragment = new NewProjectDialogFragment();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment, NewProjectDialogFragment.class.getName())
                .commit();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        NewProjectDialogFragment fragment = (NewProjectDialogFragment) getSupportFragmentManager().findFragmentByTag(NewProjectDialogFragment.class.getName());
        if (fragment != null) {
            //fragment.setDateView(year, monthOfYear, dayOfMonth);
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        NewProjectDialogFragment fragment = (NewProjectDialogFragment) getSupportFragmentManager().findFragmentByTag(NewProjectDialogFragment.class.getName());
        if (fragment != null) {
            //fragment.setTimeView(hourOfDay, minute);
        }
    }

}
