package me.connersimmons.bb_mobile.realm.repository.impl;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;
import me.connersimmons.bb_mobile.models.Project;
import me.connersimmons.bb_mobile.realm.repository.IProjectRepository;
import me.connersimmons.bb_mobile.realm.table.RealmTable;

/**
 * Created by connersimmons on 3/15/16.
 */
public class ProjectRepository implements IProjectRepository {

    private Realm mRealm;

    public ProjectRepository() {
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public void addProject(Project project, OnSaveProjectCallback callback) {
        //Realm realm = Realm.getDefaultInstance();
        mRealm.beginTransaction();
        Project realmProject = mRealm.createObject(Project.class);

        realmProject.setId(UUID.randomUUID().toString());
        realmProject.setAddress(project.getAddress());
        realmProject.setBidsDue(project.getBidsDue());
        realmProject.setBidSecurity(project.getBidSecurity());
        realmProject.setBim(project.isBim());
        realmProject.setCity(project.getCity());
        realmProject.setContractNo(project.getContractNo());
        realmProject.setEndDate(project.getEndDate());
        realmProject.setIsOutForBid(project.isOutForBid());
        realmProject.setLeed(project.isLeed());
        realmProject.setNonunion(project.isNonunion());
        realmProject.setNumBuildings(project.getNumBuildings());
        realmProject.setOwner(project.getOwner());
        realmProject.setPaymentBond(project.getPaymentBond());
        realmProject.setPerformanceBond(project.getPerformanceBond());
        realmProject.setPreBidMeeting(project.getPreBidMeeting());
        realmProject.setPrevailingWage(project.isPrevailingWage());
        realmProject.setScope(project.getScope());
        realmProject.setSquareFootage(project.getSquareFootage());
        realmProject.setStartDate(project.getStartDate());
        realmProject.setState(project.getState());
        realmProject.setStatus(project.getStatus());
        realmProject.setStoriesAboveGrade(project.getStoriesAboveGrade());
        realmProject.setStoriesBelowGrade(project.getStoriesBelowGrade());
        realmProject.setStructure(project.getStructure());
        realmProject.setTitle(project.getTitle());
        realmProject.setType(project.getType());
        realmProject.setUnion(project.isUnion());
        realmProject.setValuation(project.getValuation());
        realmProject.setZip(project.getZip());

        mRealm.commitTransaction();

        if (callback != null)
            callback.onSuccess();
    }

    @Override
    public void deleteProjectById(String id, OnDeleteProjectCallback callback) {
        mRealm.beginTransaction();
        Project result = mRealm.where(Project.class).equalTo(RealmTable.Project.ID, id).findFirst();
        result.removeFromRealm();
        mRealm.commitTransaction();

        if (callback != null)
            callback.onSuccess();
    }

    @Override
    public void getAllProjects(OnGetAllProjectsCallback callback) {
        RealmResults<Project> results = mRealm.where(Project.class).findAll();

        if (callback != null)
            callback.onSuccess(results);
    }

    @Override
    public void getProjectById(String id, OnGetProjectByIdCallback callback) {
        Project student = mRealm.where(Project.class).equalTo(RealmTable.Project.ID, id).findFirst();

        if (callback != null)
            callback.onSuccess(student);
    }
}
