package me.connersimmons.bb_mobile.presenters;

import me.connersimmons.bb_mobile.models.Project;

/**
 * Created by roma on 03.11.15.
 */
public interface IProjectPresenter extends IBasePresenter {

    void addProject(Project student);

    void deleteProjectById(String projectId);

    void getAllProjects();

    void getProjectById(String projectId);

}
