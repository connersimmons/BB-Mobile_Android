package me.connersimmons.bb_mobile.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import me.connersimmons.bb_mobile.R;
import me.connersimmons.bb_mobile.adapters.VendorsPagerAdapter;

public class VendorsActivity extends AppCompatActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, VendorsActivity.class);
        //intent.putExtra(KEY_DEMO, demo.name());
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendors);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.new_local_vendor_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLocalContact(view);
            }
        });

        ViewPager viewPager = (ViewPager) findViewById(R.id.vendorsViewPager);
        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);

        VendorsPagerAdapter vendorsPagerAdapter = new VendorsPagerAdapter(getSupportFragmentManager());
        vendorsPagerAdapter.setup(viewPagerTab);

        viewPager.setAdapter(vendorsPagerAdapter);
        viewPagerTab.setViewPager(viewPager);
    }

    private void addLocalContact(View view) {
        Snackbar.make(view, "Add Local Contact", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    /*
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_vendors, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_local_contact:
                Context context = getActivity().getApplicationContext();
                CharSequence text = "Add local contact!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
    */
}
