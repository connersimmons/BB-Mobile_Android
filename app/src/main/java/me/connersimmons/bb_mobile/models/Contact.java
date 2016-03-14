package me.connersimmons.bb_mobile.models;


import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Contact implements Parcelable, Comparable<Contact> {
    /*
    private String mName;

    public Contact(String pName) {
        mName = pName;
    }

    public String getName() {
        return mName;
    }

    public void setName(String pName) {
        mName = pName;
    }
    */

    private String _id;
    private String name;
    private String company;
    private String phoneNumber;
    private String email;
    private String website;
    private String address;
    private String classifications;
    private Uri thumbnail;

    public Contact () {
    }

    public Contact(String id, String name, String company, String phone, String email, String website, String address, String classifications) {
        this._id = id;
        this.name = name;
        this.company = company;
        this.phoneNumber = phone;
        this.email = email;
        this.website = website;
        this.address = address;
        this.classifications = classifications;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id != null ? _id : "?";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name != null ? name : "Unkown Name";
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getClassifications() {
        return classifications;
    }

    public void setClassifications(String classifications) {
        this.classifications = classifications;
    }

    public Uri getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        if (thumbnail != null) {
            this.thumbnail = Uri.parse(thumbnail);
        } else {
            this.thumbnail = null;
        }
    }

    // Parcelable Interface Implementation _________________________________________________________
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(name);
        dest.writeString(thumbnail != null ? thumbnail.toString() : "");
    }

    // Parcelable Creator Implementation ___________________________________________________________
    public static final Parcelable.Creator<Contact> CREATOR = new Parcelable.Creator<Contact>() {

        public Contact createFromParcel(Parcel in) {
            Contact contact = new Contact();

            contact.set_id(in.readString());
            contact.setName(in.readString());
            contact.setCompany(in.readString());
            contact.setPhoneNumber(in.readString());
            contact.setEmail(in.readString());
            contact.setWebsite(in.readString());
            contact.setAddress(in.readString());
            contact.setClassifications(in.readString());
            contact.setThumbnail(in.readString());

            return contact;
        }

        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    // _____________________________________________________________________________________________
    @Override
    public boolean equals (Object o) {
        if ((o instanceof Contact) && (this.name.equalsIgnoreCase(((Contact) o).getName()))) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(Contact another) {
        return this.getName().compareToIgnoreCase(another.getName());
    }
}
