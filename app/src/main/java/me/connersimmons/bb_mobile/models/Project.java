package me.connersimmons.bb_mobile.models;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by connersimmons on 3/15/16.
 */
public class Project extends RealmObject {

    @PrimaryKey
    private String id;
    @Required
    private String address;
    @Required
    private Date bidsDue;
    private double bidSecurity;
    private boolean bim;
    @Required
    private String city;
    private String contractNo;
    private Date endDate;
    private boolean isOutForBid;
    private boolean leed;
    private boolean nonunion;
    private int numBuildings;
    @Required
    private String owner;
    private double paymentBond;
    private double performanceBond;
    @Required
    private Date preBidMeeting;
    private boolean prevailingWage;
    private String scope;
    private double squareFootage;
    private Date startDate;
    @Required
    private String state;
    @Required
    private String status;
    private int storiesAboveGrade;
    private int storiesBelowGrade;
    @Required
    private String structure;
    @Required
    private String title;
    @Required
    private String type;
    private boolean union;
    private double valuation;
    @Required
    private String zip;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBidsDue() {
        return bidsDue;
    }

    public void setBidsDue(Date bidsDue) {
        this.bidsDue = bidsDue;
    }

    public double getBidSecurity() {
        return bidSecurity;
    }

    public void setBidSecurity(double bidSecurity) {
        this.bidSecurity = bidSecurity;
    }

    public boolean isBim() {
        return bim;
    }

    public void setBim(boolean bim) {
        this.bim = bim;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isOutForBid() {
        return isOutForBid;
    }

    public void setIsOutForBid(boolean isOutForBid) {
        this.isOutForBid = isOutForBid;
    }

    public boolean isLeed() {
        return leed;
    }

    public void setLeed(boolean leed) {
        this.leed = leed;
    }

    public boolean isNonunion() {
        return nonunion;
    }

    public void setNonunion(boolean nonunion) {
        this.nonunion = nonunion;
    }

    public int getNumBuildings() {
        return numBuildings;
    }

    public void setNumBuildings(int numBuildings) {
        this.numBuildings = numBuildings;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public double getPaymentBond() {
        return paymentBond;
    }

    public void setPaymentBond(double paymentBond) {
        this.paymentBond = paymentBond;
    }

    public double getPerformanceBond() {
        return performanceBond;
    }

    public void setPerformanceBond(double performanceBond) {
        this.performanceBond = performanceBond;
    }

    public Date getPreBidMeeting() {
        return preBidMeeting;
    }

    public void setPreBidMeeting(Date preBidMeeting) {
        this.preBidMeeting = preBidMeeting;
    }

    public boolean isPrevailingWage() {
        return prevailingWage;
    }

    public void setPrevailingWage(boolean prevailingWage) {
        this.prevailingWage = prevailingWage;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public double getSquareFootage() {
        return squareFootage;
    }

    public void setSquareFootage(double squareFootage) {
        this.squareFootage = squareFootage;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStoriesAboveGrade() {
        return storiesAboveGrade;
    }

    public void setStoriesAboveGrade(int storiesAboveGrade) {
        this.storiesAboveGrade = storiesAboveGrade;
    }

    public int getStoriesBelowGrade() {
        return storiesBelowGrade;
    }

    public void setStoriesBelowGrade(int storiesBelowGrade) {
        this.storiesBelowGrade = storiesBelowGrade;
    }

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isUnion() {
        return union;
    }

    public void setUnion(boolean union) {
        this.union = union;
    }

    public double getValuation() {
        return valuation;
    }

    public void setValuation(double valuation) {
        this.valuation = valuation;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
