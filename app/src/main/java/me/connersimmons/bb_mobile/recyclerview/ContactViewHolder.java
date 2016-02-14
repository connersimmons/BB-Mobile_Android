package me.connersimmons.bb_mobile.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import me.connersimmons.bb_mobile.R;
import me.connersimmons.bb_mobile.model.Contact;

/**
 * Created by connersimmons on 2/13/16.
 */
public class ContactViewHolder extends RecyclerView.ViewHolder {
    private final TextView mName;
    private final TextView mSectionName;

    public ContactViewHolder(View itemView) {
        super(itemView);
        mName = (TextView) itemView.findViewById(R.id.name);
        mSectionName = (TextView) itemView.findViewById(R.id.section_title);

    }

    public void bind(Contact pItem, String pSection, boolean bShowSection) {
        mName.setText(pItem.getName());
        mSectionName.setText(pSection);
        mSectionName.setVisibility(bShowSection ? View.VISIBLE : View.GONE);
    }
}
