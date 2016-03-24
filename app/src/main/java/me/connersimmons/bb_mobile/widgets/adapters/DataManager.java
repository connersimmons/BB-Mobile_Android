package me.connersimmons.bb_mobile.widgets.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import me.connersimmons.bb_mobile.R;
import me.connersimmons.bb_mobile.models.Contact;

/**
 * Created by connersimmons on 3/23/16.
 */
public class DataManager extends RecyclerView.Adapter<DataManager.VendorRecyclerViewHolder> {

    private List<Contact> dataSet;

    public DataManager(List<Contact> contacts) {
        this.dataSet = contacts;
    }

    @Override
    public VendorRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_test, viewGroup, false);
        return new VendorRecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(VendorRecyclerViewHolder viewHolder, int i) {
        viewHolder.mVendorName.setText(dataSet.get(i).getCompany());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class VendorRecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView mVendorName;

        VendorRecyclerViewHolder(View itemView) {
            super(itemView);
            mVendorName = (TextView) itemView.findViewById(R.id.text_item);
        }
    }
}
