package me.connersimmons.bb_mobile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.connersimmons.bb_mobile.models.Contact;

/**
 * Created by connersimmons on 3/6/16.
 */
public class AppConstants {

    private static AppConstants instance = null;

    // Array of states
    static ArrayList<String> statesArray = new ArrayList<>(Arrays.asList("Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska","Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming", "Alberta"));

    // Array of structure types
    static ArrayList<String> structureArray = new ArrayList<>(Arrays.asList("Residential Building", "     House", "     Tract Housing", "     Apartments & Condominiums", "Non-Residential Building", "     Animal Hospital / Kennel", "     Arena / Stadium", "     Assisted Living", "     Auto Dealership / Service", "     Bank", "     Casino", "     Church / Synagogue ", "     City / Town Hall", "     Club House / Community Center", "     Convention Center", "     Court House", "     Fitness Center", "     Fire / Police Station", "     Gas Station", "     Hospital / Nursing Home", "     Hotel / Motel", "     Industrial Maintenance", "     Jail / Prison", "     Laboratory", "     Library", "     Manufacturing Plant", "     Medical Office", "     Mixed Use", "     Museum", "     Office Building", "     Parking Garage", "     Post Office", "     Radio / Television Station", "     Restaurant", "     Retail Store", "     School / College / University", "     Sewage / Water Treatment Plant", "     Storage Facility / Warehouse", "     Terminal - Airport / Bus / Railroad", "     Theater", "     Theme Park", "Non-Building Construction", "     Airport Runway", "     Athletic Field", "     Bridge", "     Golf Course", "     Marine", "     Park / Playground", "     Road / Highway", "     Parking Lots", "     Swimming Pool", "     Tennis Court", "     Tunnel", "     Utilities"));

    // Array of project types
    static ArrayList<String> typeArray = new ArrayList<>(Arrays.asList("New", "Addition", "Interior Fit-Up", "Tenant Improvements", "Alterations / Renovations", "Improvements", "Service Contract", "Individual Trade", "Equipment", "Material"));

    // Array of owner types
    static ArrayList<String> ownerArray = new ArrayList<>(Arrays.asList("Private", "Local Government", "State Government", "Federal Government", "Military"));

    // Array of project statuses
    static ArrayList<String> statusArray = new ArrayList<>(Arrays.asList("Pre-Design", "Design", "Construction Documents", "Bidding/Negotiating", "Negotiating-Owner", "Negotiating-Architect", "Negotiating-General Contractor", "Bidding-Owner", "Bidding-Architect", "Bidding-General Contractor", "Award Pending-General Contractor", "Awarded-General Contractor", "Bid Results", "Start", "Delayed", "Abandoned", "Subcontractor Bidding", "Construction-In Progress"));

    static List<Contact> contactsList;

    protected AppConstants() {
        // Exists only to defeat instantiation.
    }

    public static AppConstants getInstance() {
        if(instance == null) {
            instance = new AppConstants();
        }
        return instance;
    }

    public static ArrayList<String> getStatesArray() {
        return statesArray;
    }

    public static ArrayList<String> getStructureArray() {
        return structureArray;
    }

    public static ArrayList<String> getTypeArray() {
        return typeArray;
    }

    public static ArrayList<String> getOwnerArray() {
        return ownerArray;
    }

    public static ArrayList<String> getStatusArray() {
        return statusArray;
    }

    public static List<Contact> getContactsList() {
        return contactsList;
    }

    public static void setContactsList(List<Contact> contactsList) {
        AppConstants.contactsList = contactsList;
    }
}
