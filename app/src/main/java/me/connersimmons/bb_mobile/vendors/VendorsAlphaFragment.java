package me.connersimmons.bb_mobile.vendors;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderDecoration;
import me.connersimmons.bb_mobile.R;
import me.connersimmons.bb_mobile.adapter.StickyTestAdapter;
import me.connersimmons.bb_mobile.api.ContactsProvider;
import me.connersimmons.bb_mobile.model.Contact;
import me.connersimmons.bb_mobile.ui.BaseDecorationFragment;

/**
 * Created by connersimmons on 2/4/16.
 */
public class VendorsAlphaFragment extends BaseDecorationFragment implements RecyclerView.OnItemTouchListener {

    private static final String TAG = "VENDORS_ALPHA_FRAGMENT";
    private static final String BB_VENDOR_GROUP_NAME = "MyBlueBookVendors";

    private StickyHeaderDecoration decor;


    private Context mContext;
    private List<Contact> myContacts;

    //HashMap groups;

    public VendorsAlphaFragment() {

        //groups = new HashMap<String, String>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Since reading contacts takes more time, let's run it on a separate thread.
        new Thread(new Runnable() {
            @Override
            public void run() {
                //getContacts();
                //myContacts = ContactsProvider.load(mContext);
                //System.out.println(myContacts);
            }
        }).start();


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }



    @Override
    protected void setAdapterAndDecor(RecyclerView list) {
        myContacts = ContactsProvider.load(mContext);
        final StickyTestAdapter adapter = new StickyTestAdapter(myContacts, this.getActivity());
        decor = new StickyHeaderDecoration(adapter);
        setHasOptionsMenu(true);

        list.setAdapter(adapter);
        list.addItemDecoration(decor, 1);
        list.addOnItemTouchListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_local_contact) {
            // TODO: load contact picker
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        // really bad click detection just for demonstration purposes
        // it will not allow the list to scroll if the swipe motion starts
        // on top of a header
        View v = rv.findChildViewUnder(e.getX(), e.getY());
        return v == null;
//        return rv.findChildViewUnder(e.getX(), e.getY()) != null;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        // only use the "UP" motion event, discard all others
        if (e.getAction() != MotionEvent.ACTION_UP) {
            return;
        }

        // find the header that was clicked
        View view = decor.findHeaderViewUnder(e.getX(), e.getY());

        if (view instanceof TextView) {
            Toast.makeText(this.getActivity(), ((TextView) view).getText() + " clicked", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        // do nothing
    }

    /*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_vendors_alpha, container, false);

        return rootView;

    }
    */

    /*
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
    */
}
