package me.connersimmons.bb_mobile.fragments.vendors;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import me.connersimmons.bb_mobile.utils.AppConstants;
import me.connersimmons.bb_mobile.R;
import me.connersimmons.bb_mobile.models.Contact;
import me.connersimmons.bb_mobile.utils.CompanyComparator;
import me.connersimmons.bb_mobile.widgets.Divider;
import me.connersimmons.bb_mobile.widgets.AlphaRecyclerView;
import me.connersimmons.bb_mobile.widgets.RecyclerClickListener;
import me.connersimmons.bb_mobile.widgets.adapters.DataManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class VendorsAlphaFragment extends Fragment {

    private RecyclerView mRecycler;
    private DataManager mAdapter;
    private List<Contact> contactList;
    private AppConstants constantsInstance;
    private String TAG = VendorsAlphaFragment.class.getName();

    public VendorsAlphaFragment() {
        // Required empty public constructor

        constantsInstance = AppConstants.getInstance();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contactList = constantsInstance.getContactsList();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Log.d(TAG, "Contacts: " + contactList.toString());

        View view = inflater.inflate(R.layout.fragment_vendors, container, false);

        Collections.sort(contactList, new CompanyComparator());

        mRecycler = (AlphaRecyclerView) view.findViewById(R.id.rv_vendors);
        mRecycler.addItemDecoration(new Divider(getContext(), LinearLayoutManager.VERTICAL));
        mRecycler.setItemAnimator(new DefaultItemAnimator());
        //mRecycler.hideIfEmpty(mToolbar);
        //mRecycler.showIfEmpty(mEmptyView);

        mRecycler.addOnItemTouchListener( // and the click is handled
                new RecyclerClickListener(getContext(), new RecyclerClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // STUB:
                        // The click on the item must be handled
                        Snackbar.make(view, "Present " + contactList.get(position).getCompany() + " Detail View", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }));

        mAdapter = new DataManager(contactList);
        mRecycler.setAdapter(mAdapter);



        return view;
    }

}
