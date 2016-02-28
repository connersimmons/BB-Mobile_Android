/*
 * Copyright 2014 Eduardo Barrenechea
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.connersimmons.bb_mobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderAdapter;
import me.connersimmons.bb_mobile.CompanyComparator;
import me.connersimmons.bb_mobile.R;
import me.connersimmons.bb_mobile.model.Contact;

public class VendorsAlphaViewAdapter extends RecyclerView.Adapter<VendorsAlphaViewAdapter.ViewHolder> implements
        StickyHeaderAdapter<VendorsAlphaViewAdapter.HeaderHolder> {

    private LayoutInflater mInflater;
    private List<Contact> dataSet;

    public VendorsAlphaViewAdapter(List<Contact> contacts, Context context) {
        this.dataSet = contacts;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View view = mInflater.inflate(R.layout.item_test, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        //viewHolder.item.setText(dataSet.get(position).getName());
        viewHolder.item.setText(dataSet.get(position).getCompany());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    @Override
    public long getHeaderId(int position) {

        //TODO : Map Contacts to proper header
        Collections.sort(getDataSet(), new CompanyComparator());

        //return getDataSet().get(position).getName().charAt(0);
        return getDataSet().get(position).getCompany().charAt(0);
    }

    @Override
    public HeaderHolder onCreateHeaderViewHolder(ViewGroup parent) {
        final View view = mInflater.inflate(R.layout.header_test, parent, false);
        return new HeaderHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(HeaderHolder viewholder, int position) {
        //viewholder.header.setText(String.valueOf(getContact(position).getName().charAt(0)).toUpperCase());
        viewholder.header.setText(String.valueOf(getContact(position).getCompany().charAt(0)).toUpperCase());
    }

    public Contact getContact (int pos) {
        return dataSet.get(pos);
    }

    public Contact getContactByName (String name) {
        for (Contact c : dataSet) {
            if (name.equals(c.getName())) {
                return c;
            }
        }

        return null;
    }

    public List<Contact> getDataSet() {
        return dataSet;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView item;

        public ViewHolder(View itemView) {
            super(itemView);

            item = (TextView) itemView;
        }
    }

    static class HeaderHolder extends RecyclerView.ViewHolder {
        public TextView header;

        public HeaderHolder(View itemView) {
            super(itemView);

            header = (TextView) itemView;
        }
    }
}
