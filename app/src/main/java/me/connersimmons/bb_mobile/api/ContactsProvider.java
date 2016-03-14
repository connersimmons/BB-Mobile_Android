package me.connersimmons.bb_mobile.api;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.connersimmons.bb_mobile.models.Contact;

/**
 * Created by Conner Simmons <cwsimmons149@gmail.com> on 2/27/16.
 */
public class ContactsProvider {

    private static final String BB_VENDOR_GROUP_NAME = "MyBlueBookVendors";

    public static List<Contact> load(Context pContext) {
        List<Contact> contactsList = new ArrayList<>();
        Cursor cur = null;

        try {

            ContentResolver contentResolver = pContext.getContentResolver();

            cur = contentResolver.query(
                    ContactsContract.Data.CONTENT_URI,
                    new String[]{
                            ContactsContract.Data.CONTACT_ID,
                            ContactsContract.Data.DATA1,
                            ContactsContract.Data.DISPLAY_NAME
                    },
                    ContactsContract.Data.MIMETYPE + "=?",
                    new String[]{
                            ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE
                    },
                    null
            );

            if (cur.getCount() > 0) {
                while (cur.moveToNext()) {

                    String id = cur.getString(0);
                    String group_id = cur.getString(1);
                    String display_name = cur.getString(2);
                    String groupTitle = getBlueBookGroupTitle(pContext, group_id);

                    System.out.println("Name: " + display_name + ", ID: " + id + ", Group ID: " + group_id);

                    String phone = getPhoneNumber(id, contentResolver);
                    String email = getEmail(id, contentResolver);
                    String classifications = getClassifications(id, contentResolver);
                    String address = getPostalAddress(id, contentResolver);
                    String company = getCompany(id, contentResolver);
                    String website = getWebsite(id, contentResolver);

                    if (groupTitle.equals(BB_VENDOR_GROUP_NAME)) {
                        Contact contact = new Contact(id, display_name, company, phone, email,
                                website, address, classifications);
                        /*
                        contact.set_id(id);
                        contact.setName(display_name);
                        contact.setCompany(company);
                        contact.setAddress(address);
                        contact.setWebsite(website);
                        contact.setEmail(email);
                        contact.setPhoneNumber(phone);
                        contact.setClassifications(classifications);
                        */
                        contactsList.add(contact);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (cur != null) {
            cur.close();
        }

        /*
        Collections.sort(contactsList, new Comparator<Contact>() {
            @Override
            public int compare(Contact lhs, Contact rhs) {
                return lhs.getName().compareToIgnoreCase(rhs.getName());
            }
        });
        */

        return contactsList;
    }

    private static String getBlueBookGroupTitle(Context pContext, String groupID) {
        HashMap groups = new HashMap<>();

        Cursor groups_cursor = pContext.getContentResolver().query(
                ContactsContract.Groups.CONTENT_URI,
                new String[]{
                        ContactsContract.Groups._ID,
                        ContactsContract.Groups.TITLE
                }, null, null, null
        );

        if (groups_cursor != null) {
            while (groups_cursor.moveToNext()) {
                String group_title = groups_cursor.getString(1);
                String id = groups_cursor.getString(0);
                groups.put(id, group_title);
            }
        }

        if (groups_cursor != null) {
            groups_cursor.close();
        }

        return (String) groups.get(groupID);
    }

    private static String getPhoneNumber(String id, ContentResolver contentResolver) {
        String number = null;

        Cursor pCur = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                new String[]{id}, null);
        if (pCur != null) {
            while (pCur.moveToNext()) {
                number = pCur.getString(
                        pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                System.out.println("Phone Number: " + number);
            }
        }

        if (pCur != null) {
            pCur.close();
        }

        return number;
    }

    private static String getEmail(String id, ContentResolver contentResolver) {
        String email = null;

        Cursor emailCur = contentResolver.query(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                new String[]{ id }, null);

        if (emailCur != null) {
            while (emailCur.moveToNext()) {
                // This would allow you get several email addresses if the email addresses were stored in an array
                email = emailCur.getString(
                        emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                //String emailType = emailCur.getString(
                //        emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));

                System.out.println("Email: " + email);
            }
        }

        if (emailCur != null) {
            emailCur.close();
        }

        return email;
    }

    private static String getClassifications(String id, ContentResolver contentResolver) {
        String classifications = null;

        String noteWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
        String[] noteWhereParams = new String[]{id,
                ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE};
        Cursor noteCur = contentResolver.query(ContactsContract.Data.CONTENT_URI, null, noteWhere, noteWhereParams, null);
        if (noteCur.moveToFirst()) {
            classifications = noteCur.getString(noteCur.getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE));
            System.out.println("Note: " + classifications);
        }
        noteCur.close();

        return classifications;
    }

    private static String getPostalAddress(String id, ContentResolver contentResolver) {
        String address = null;

        String addrWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
        String[] addrWhereParams = new String[]{id,
                ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE};
        Cursor addrCur = contentResolver.query(ContactsContract.Data.CONTENT_URI,
                null, addrWhere, addrWhereParams, null);

        if (addrCur != null) {
            if(addrCur.moveToFirst()) {
                //String poBox = addrCur.getString(
                //        addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX));
                //String street = addrCur.getString(
                //        addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
                String city = addrCur.getString(
                        addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
                String state = addrCur.getString(
                        addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
                String postalCode = addrCur.getString(
                        addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
                //String country = addrCur.getString(
                //        addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY));
                //String type = addrCur.getString(
                //        addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE));

                // Do something with these....
                address = city + ", " + state + " " + postalCode;
                System.out.println("Address: " + address);
            }
        }

        if (addrCur != null) {
            addrCur.close();
        }

        return address;
    }

    private static String getCompany(String id, ContentResolver contentResolver) {
        String company = null;

        String orgWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
        String[] orgWhereParams = new String[]{id,
                ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE};
        Cursor orgCur = contentResolver.query(ContactsContract.Data.CONTENT_URI,
                null, orgWhere, orgWhereParams, null);

        if (orgCur != null) {
            if (orgCur.moveToFirst()) {
                company = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DATA));
                String title = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE));

                System.out.println("Company: " + company);
            }
        }

        if (orgCur != null) {
            orgCur.close();
        }

        return company;
    }

    private static String getWebsite(String id, ContentResolver contentResolver) {
        String website = null;

        String urlWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
        String[] urlWhereParams = new String[]{id,
                ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE};
        Cursor urlCur = contentResolver.query(ContactsContract.Data.CONTENT_URI,
                null, urlWhere, urlWhereParams, null);

        if (urlCur != null) {
            if (urlCur.moveToFirst()) {
                website = urlCur.getString(urlCur.getColumnIndex(ContactsContract.CommonDataKinds.Website.URL));

                System.out.println("Website: " + website);
            }
        }

        if (urlCur != null) {
            urlCur.close();
        }

        return website;
    }
}