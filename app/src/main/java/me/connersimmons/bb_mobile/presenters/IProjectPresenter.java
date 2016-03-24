package me.connersimmons.bb_mobile.presenters;

import io.realm.RealmResults;
import me.connersimmons.bb_mobile.models.Project;

/**
 * Created by roma on 03.11.15.
 */
public interface IProjectPresenter { //extends IBasePresenter {

    void addProject(Project project);

    void deleteProjectById(String projectId);

    RealmResults<Project> getAllProjects();

    void getProjectById(String projectId);

}
