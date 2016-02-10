package me.connersimmons.bb_mobile.api;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;

import ezvcard.VCard;

/**
 * Created by connersimmons on 2/9/16.
 */
public class ContactAPI {

    private static final String BB_GROUP_NAME = "MyBlueBookVendors";

    public static boolean insertContact(ContentResolver contentResolver, VCard vcard) { // String firstName, String mobileNumber) {
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());
        //name
        ops.add(ContentProviderOperation.newInsert(
                ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(
                        ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                        vcard.getFormattedName().getValue()).build());
        //company
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(
                        ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Organization.COMPANY,
                        vcard.getOrganization().getType())
                .withValue(
                        ContactsContract.CommonDataKinds.Organization.TYPE,
                        ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
                .build());

        //email
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(
                        ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Email.DATA,
                        vcard.getEmails().get(0).getValue())
                .withValue(ContactsContract.CommonDataKinds.Email.TYPE,
                        "BlueBook")
                .build());
        //phone number
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(
                        ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER,
                        vcard.getTelephoneNumbers().get(0).getText())
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                        ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
                .build());
        //address
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(
                        ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredPostal.CITY,
                        vcard.getAddresses().get(0).getLocality())
                .withValue(ContactsContract.CommonDataKinds.StructuredPostal.REGION,
                        vcard.getAddresses().get(0).getRegion())
                .withValue(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE,
                        vcard.getAddresses().get(0).getPostalCode())
                .withValue(ContactsContract.CommonDataKinds.StructuredPostal.TYPE,
                        ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
                .build());
        //website
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(
                        ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Website.URL,
                        vcard.getUrls().get(0).getValue())
                .withValue(ContactsContract.CommonDataKinds.Website.TYPE,
                        "ProView")
                .build());
        //website
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(
                        ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Note.NOTE,
                        vcard.getNotes().get(0).getValue())
                .build());

        //group
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(
                        ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID,
                        getGroupId(contentResolver, BB_GROUP_NAME))
                .build());

        try {
            contentResolver.applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public static Boolean checkForContact(ContentResolver contentResolver, VCard vCard) {
        Boolean contactExists = false;

        Cursor c = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);


        while (c.moveToNext()) {
            String contactName = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            //String companyName = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Organization.COMPANY));

            if (vCard.getFormattedName().getValue().equals(contactName)) { //&& vCard.getOrganization().getType().equals(companyName)) {
                contactExists = true;
                break;
            }
        }
        c.close();

        return contactExists;
    }

    public static void createGroup(ContentResolver contentResolver) {
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Groups.CONTENT_URI)
                .withValue(ContactsContract.Groups.TITLE, BB_GROUP_NAME).build());
        try {
            contentResolver.applyBatch(ContactsContract.AUTHORITY, ops);

        } catch (Exception e) {
            Log.e("Error", e.toString());
        }
    }

    public static Boolean checkForBlueBookGroup(ContentResolver contentResolver) {
        Boolean bluebookGroupExists = false;

        Cursor groupsCursor = contentResolver.query(
                ContactsContract.Groups.CONTENT_URI,
                new String[]{
                        ContactsContract.Groups.TITLE
                }, null, null, null
        );

        if(groupsCursor != null){
            while(groupsCursor.moveToNext()){
                String groupTitle = groupsCursor.getString(0);

                if (groupTitle.equals(BB_GROUP_NAME)) {
                    bluebookGroupExists = true;
                }
            }
        }

        return bluebookGroupExists;
    }

    private static String getGroupId(ContentResolver contactHelper, String groupTitle) {
        Cursor cursor = contactHelper.query(ContactsContract.Groups.CONTENT_URI, new String[]{ContactsContract.Groups._ID, ContactsContract.Groups.TITLE}, null, null, null);
        cursor.moveToFirst();
        int len = cursor.getCount();

        String groupId = null;
        for (int i = 0; i < len; i++) {
            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Groups._ID));
            String title = cursor.getString(cursor.getColumnIndex(ContactsContract.Groups.TITLE));

            if (title.equals(groupTitle)) {
                groupId = id;
                break;
            }
            cursor.moveToNext();
        }
        cursor.close();

        return groupId;
    }
}
