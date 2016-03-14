package me.connersimmons.bb_mobile;

import java.util.Comparator;

import me.connersimmons.bb_mobile.models.Contact;

/**
 * Created by Conner Simmons <cwsimmons149@gmail.com> on 2/27/16.
 */
public class CompanyComparator implements Comparator<Contact> {
    @Override
    public int compare(Contact c1, Contact c2) {
        return c1.getCompany().compareTo(c2.getCompany());
    }
}