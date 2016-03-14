package me.connersimmons.bb_mobile.fragments.vendors;

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
import me.connersimmons.bb_mobile.AppConstants;
import me.connersimmons.bb_mobile.R;
import me.connersimmons.bb_mobile.adapters.VendorsAlphaViewAdapter;
import me.connersimmons.bb_mobile.models.Contact;
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
    private AppConstants constantsInstance;

    //HashMap groups;

    public VendorsAlphaFragment() {

        //groups = new HashMap<String, String>();
        constantsInstance = AppConstants.getInstance();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        //Since reading contacts takes more time, let's run it on a separate thread.
        new Thread(new Runnable() {
            @Override
            public void run() {
                myContacts = ContactsProvider.load(mContext);
            }
        }).start();
        */

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }



    @Override
    protected void setAdapterAndDecor(RecyclerView list) {
        //myContacts = ContactsProvider.load(mContext);
        myContacts = constantsInstance.getContactsList();
        final VendorsAlphaViewAdapter adapter = new VendorsAlphaViewAdapter(myContacts, this.getActivity());
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

}
