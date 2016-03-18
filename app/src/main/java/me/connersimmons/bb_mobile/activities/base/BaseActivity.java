package me.connersimmons.bb_mobile.activities.base;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import io.realm.RealmResults;
import me.connersimmons.bb_mobile.models.Project;

/**
 * Created by roma on 16.10.15.
 */
public abstract class BaseActivity extends AppCompatActivity {

    //abstract protected void initComponents();

    public void showMessage(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    //public abstract void showProjects(RealmResults<Project> projects);
}
