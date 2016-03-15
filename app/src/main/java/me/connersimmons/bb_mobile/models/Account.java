package me.connersimmons.bb_mobile.models;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by connersimmons on 3/15/16.
 */
public class Account extends RealmObject {

    @Required
    private String company;
    @Required
    private String email;
    @Required
    private String firstName;
    @Required
    private String lastName;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
