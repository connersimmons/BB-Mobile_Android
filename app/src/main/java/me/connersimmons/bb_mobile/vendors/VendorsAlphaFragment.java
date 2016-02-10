package me.connersimmons.bb_mobile.vendors;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
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
import android.widget.ListView;
import android.widget.Toast;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.HashMap;

import me.connersimmons.bb_mobile.ContactHelper;
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

    /*
    private void getContacts() {
        //ArrayList<String> contacts = new ArrayList<String>();
        ContentResolver contentResolver = getActivity().getContentResolver();
        String groupID = ContactHelper.getGroupId(contentResolver, "MyBlueBookVendors");

        System.out.println(groupID);

        Uri groupURI = ContactsContract.Data.CONTENT_URI;
        String[] projection = new String[] {
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.GroupMembership.CONTACT_ID };

        Cursor c = contentResolver.query(
                groupURI,
                projection,
                ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID
                        + "=" + groupID, null, null);

        while (c.moveToNext()) {
            String id = c
                    .getString(c
                            .getColumnIndex(ContactsContract.CommonDataKinds.GroupMembership.CONTACT_ID));
            Cursor pCur = contentResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                    new String[]{id}, null);

            while (pCur.moveToNext()) {


                String contactName = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phNumber = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                contactList.add(contactName + ":" + phNumber);
            }

            pCur.close();


            System.out.println(contactList);
        }
        */

        /*
        String[] projection = {
                ContactsContract.Groups._ID,
                ContactsContract.Groups.TITLE,
                ContactsContract.Groups.ACCOUNT_NAME,
                ContactsContract.Groups.ACCOUNT_TYPE
        };

        Cursor c = contentResolver.query(
                ContactsContract.Groups.CONTENT_URI,
                projection,
                ContactsContract.Groups._ID + "=" + groupID,
                null, null);


        while (c.moveToNext()) {
            String contactName = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phNumber = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            contactList.add(contactName + ":" + phNumber);
        }
        c.close();

    }
    */

    /*
    public static Cursor getContactsOfGroup(Group group) {
        // getting ids of contacts that are in this specific group
        String where = ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID + "="
                + group.id + " AND "
                + ContactsContract.CommonDataKinds.GroupMembership.MIMETYPE + "='"
                + ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE + "'";

        Cursor query = context.getContentResolver().query(
                ContactsContract.Data.CONTENT_URI,
                new String[] {
                        ContactsContract.CommonDataKinds.GroupMembership.CONTACT_ID
                }, where, null, ContactsContract.Data.DISPLAY_NAME + " COLLATE LOCALIZED ASC");

        String ids = "";
        for (query.moveToFirst(); !query.isAfterLast(); query.moveToNext()) {
            ids += "," + query.getString(0);
        }
        if (ids.length() > 0) {
            ids = ids.substring(1);
        }

        // getting all of information of contacts. it fetches all of number from every one
        String[] projection = new String[]{
                "_id",
                "contact_id",
                "lookup",
                "display_name",
                "data1",
                "photo_id",
                "data2", // number type: 1:home, 2:mobile, 3: work, else : other
        };
        String selection = "mimetype ='" + ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "'"
                + " AND account_name='" + group.accountName + "' AND account_type='" + group.accountType + "'"
                + " AND contact_id in (" + ids + ")";

        return context.getContentResolver().query(BASE_URI, projection, selection, null, null);
    }
    */

    /*
    private void getContacts() {
        contactList = new ArrayList<String>();
        String phoneNumber = null;
        String email = null;

        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
        String _ID = ContactsContract.Contacts._ID;
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
        Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
        Uri EmailCONTENT_URI =  ContactsContract.CommonDataKinds.Email.CONTENT_URI;
        String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
        String DATA = ContactsContract.CommonDataKinds.Email.DATA;

        StringBuffer output;
        ContentResolver contentResolver = getActivity().getContentResolver();

        Uri CONTENT_GROUPS_URI = ContactsContract.Groups.CONTENT_URI;
        String GR_ID = ContactsContract.Groups._ID;
        String GR_NAME = ContactsContract.Groups.TITLE;

        //cursor = contentResolver.query(CONTENT_URI, null,null, null, null);

        Uri groupURI = ContactsContract.Data.CONTENT_URI;

        String groupID = getGroupId("'MyBlueBookVendors'");
        System.out.println("GROUP ID: " + groupID);

        String[] projection = new String[]{
                ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID ,
                ContactsContract.CommonDataKinds.GroupMembership.CONTACT_ID};

        cursor = contentResolver.query(groupURI,
                projection,
                ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID+"="+groupID,
                null,null);

        // Iterate every contact in the phone
        if (cursor.getCount() > 0) {
            counter = 0;
            while (cursor.moveToNext()) {
                output = new StringBuffer();
                // Update the progress message
                updateBarHandler.post(new Runnable() {
                    public void run() {
                        pDialog.setMessage("Reading contacts : "+ counter++ +"/"+cursor.getCount());
                    }
                });
                String contact_id = cursor.getString(cursor.getColumnIndex( _ID ));
                String name = cursor.getString(cursor.getColumnIndex( DISPLAY_NAME ));
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER )));
                if (hasPhoneNumber > 0) {
                    output.append(name);
                    //This is to read multiple phone numbers associated with the same contact
                    Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[] { contact_id }, null);
                    while (phoneCursor.moveToNext()) {
                        phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                        output.append("\n" + phoneNumber);
                    }
                    phoneCursor.close();
                    // Read every email id associated with the contact
                    Cursor emailCursor = contentResolver.query(EmailCONTENT_URI,    null, EmailCONTACT_ID+ " = ?", new String[] { contact_id }, null);
                    while (emailCursor.moveToNext()) {
                        email = emailCursor.getString(emailCursor.getColumnIndex(DATA));
                        output.append("\n" + email);
                    }
                    emailCursor.close();
                }
                // Add the contact to the ArrayList
                contactList.add(output.toString());
            }

            // ListView has to be updated using a ui thread
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, contactList);

                    getListView().setAdapter(adapter);
                }
            });
            // Dismiss the progressbar after 500 millisecondds
            updateBarHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    pDialog.cancel();
                }
            }, 500);
        }
    }
    */


}
