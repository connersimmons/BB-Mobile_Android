package me.connersimmons.bb_mobile.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import me.connersimmons.bb_mobile.R;
import me.connersimmons.bb_mobile.api.ContactsProvider;
import me.connersimmons.bb_mobile.model.Contact;

/**
 * Created by connersimmons on 2/13/16.
 */
public class ContactsAdapter extends RecyclerView.Adapter<ContactViewHolder> {
    private Context mContext;
    private List<Contact> mContacts;
    private LinkedHashMap<String, Integer> mMapIndex;
    private ArrayList<String> mSectionList;
    private String[] mSections;

    public ContactsAdapter(Context pContext) {
        mContext = pContext;
        mContacts = ContactsProvider.load(pContext);
        fillSections();
    }

    private void fillSections() {
        mMapIndex = new LinkedHashMap<String, Integer>();

        for (int x = 0; x < mContacts.size(); x++) {
            String fruit = mContacts.get(x).getName();
            if (fruit.length() > 1) {
                String ch = fruit.substring(0, 1);
                ch = ch.toUpperCase();
                if (!mMapIndex.containsKey(ch)) {
                    mMapIndex.put(ch, x);
                }
            }
        }
        Set<String> sectionLetters = mMapIndex.keySet();
        // create a list from the set to sort
        mSectionList = new ArrayList<String>(sectionLetters);
        Collections.sort(mSectionList);

        mSections = new String[mSectionList.size()];
        mSectionList.toArray(mSections);
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View content = LayoutInflater.from(mContext.getApplicationContext()).inflate(R.layout.contact_item, null);
        return new ContactViewHolder(content);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        Contact lContact = getItem(position);
        String section = getSection(lContact);
        holder.bind(lContact, section, mMapIndex.get(section) == position);
    }

    private String getSection(Contact pContact) {
        return pContact.getName().substring(0, 1).toUpperCase();
    }

    private Contact getItem(int pPosition) {
        return mContacts.get(pPosition);
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }
}
