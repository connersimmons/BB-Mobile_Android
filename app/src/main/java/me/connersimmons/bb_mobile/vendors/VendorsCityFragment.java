package me.connersimmons.bb_mobile.vendors;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import me.connersimmons.bb_mobile.R;
import me.connersimmons.bb_mobile.api.ContactsProvider;
import me.connersimmons.bb_mobile.model.Contact;


/**
 * A simple {@link Fragment} subclass.
 */
public class VendorsCityFragment extends Fragment {

    private Context mContext;
    private RecyclerView recyclerView;
    private List<Contact> myContacts;
    private FloatingActionButton fab;
    private View rootView;

    public VendorsCityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Thread(new Runnable() {
            @Override
            public void run() {
                //getContacts();
                myContacts = ContactsProvider.load(getContext());
            }
        }).start();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_vendors_city, container, false);

        return rootView;
    }
}
