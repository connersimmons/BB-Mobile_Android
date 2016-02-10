package me.connersimmons.bb_mobile.vendors;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import me.connersimmons.bb_mobile.R;

/**
 * Created by connersimmons on 2/4/16.
 */
public class VendorsAlphaFragment extends ListFragment implements OnItemClickListener {

    private static final String TAG = "VENDORS_ALPHA_FRAGMENT";
    private static final String BB_VENDOR_GROUP_NAME = "MyBlueBookVendors";

    private ProgressDialog pDialog;
    private Handler updateBarHandler;
    ArrayList<String> contactList;
    Cursor cursor;
    int counter;

    HashMap groups;

    public VendorsAlphaFragment() {
        // Required empty public constructor
        contactList = new ArrayList<String>();


        groups = new HashMap<String, String>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Since reading contacts takes more time, let's run it on a separate thread.
        new Thread(new Runnable() {
            @Override
            public void run() {
                getContacts();
            }
        }).start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vendors_alpha, container, false);

        /*
        pDialog = new ProgressDialog(this.getContext());
        pDialog.setMessage("Reading contacts...");
        pDialog.setCancelable(false);
        pDialog.show();
        updateBarHandler = new Handler();
        */



        /*
        // Set onclicklistener to the list item.
        getListView().setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //TODO Do whatever you want with the list data
                Toast.makeText(getActivity().getApplicationContext(), "item clicked : \n"+contactList.get(position), Toast.LENGTH_SHORT).show();
            }
        });
        */

        return view;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.Planets, android.R.layout.simple_list_item_1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_list_item_1, contactList);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }


    /*
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_list_item_1, contactList);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }
    */

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
    }


    private void getContacts() {
        ContentResolver contentResolver = getActivity().getContentResolver();

        Cursor groups_cursor= contentResolver.query(
                ContactsContract.Groups.CONTENT_URI,
                new String[]{
                        ContactsContract.Groups._ID,
                        ContactsContract.Groups.TITLE
                }, null, null, null
        );


        if(groups_cursor!=null){
            while(groups_cursor.moveToNext()){
                String group_title = groups_cursor.getString(1);
                String id = groups_cursor.getString(0);
                groups.put(id, group_title);
            }
        }

        Cursor dataCursor = contentResolver.query(
                ContactsContract.Data.CONTENT_URI,
                new String[]{
                        ContactsContract.Data.CONTACT_ID,
                        ContactsContract.Data.DATA1,
                        ContactsContract.Data.DISPLAY_NAME,
                },
                ContactsContract.Data.MIMETYPE + "=?",
                new String[]{ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE}, null
        );

        if(dataCursor!=null){
            while(dataCursor.moveToNext()){
                String id = dataCursor.getString(0);
                String group_id = dataCursor.getString(1);
                String display_name = dataCursor.getString(2);
                String groupTitle = (String) groups.get(group_id);
                Log.d(TAG, "groupTitle : " + groupTitle + " contact_id: " + id);

                if (groupTitle.equals(BB_VENDOR_GROUP_NAME)) {
                    System.out.println(display_name);
                    contactList.add(display_name);

                }
            }
        }

        groups_cursor.close();
        dataCursor.close();
    }

}
