package me.connersimmons.bb_mobile.realm.table;

/**
 * Created by connersimmons on 3/15/16.
 */
public interface RealmTable {

    interface Project {
        String ID = "id";
        String ADDRESS = "address";
        String BIDS_DUE = "bidsDue";
        String BID_SECURITY = "bidSecurity";
        String BIM = "bim";
        String CITY = "city";
        String CONTRACT_NO = "contractNo";
        String END_DATE = "address";
        String IS_OUT_FOR_BID = "isOutForBid";
        String LEED = "leed";
        String NON_UNION = "nonunion";
        String NUM_BUILDINGS = "numBuildings";
        String OWNER = "owner";
        String PAYMENT_BOND = "paymentBond";
        String PERFORMANCE_BOND = "performanceBond";
        String PRE_BID_MEETING = "preBidMeeting";
        String PREVAILING_WAGE = "prevailingWage";
        String SCOPE = "scope";
        String SQUARE_FOOTAGE = "squareFootage";
        String START_DATE = "startDate";
        String STATE = "state";
        String STATUS = "status";
        String STORIES_ABOVE_GRADE = "storiesAboveGrade";
        String STORIES_BELOW_GRADE = "storiesBelowGrade";
        String STRUCTURE = "structure";
        String TITLE = "title";
        String TYPE = "type";
        String UNION = "union";
        String VALUATION = "valuation";
        String ZIP = "zip";
    }

    interface Account{
        String COMPANY = "company";
        String EMAIL = "email";
        String FIRST_NAME = "firstName";
        String LAST_NAME = "lastName";
    }

}
