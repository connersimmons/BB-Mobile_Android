package me.connersimmons.bb_mobile.presenters.impl;


import io.realm.RealmList;
import io.realm.RealmResults;
import me.connersimmons.bb_mobile.activities.NewProjectActivity;
import me.connersimmons.bb_mobile.activities.ProjectsActivity;
import me.connersimmons.bb_mobile.fragments.base.BaseFragment;
import me.connersimmons.bb_mobile.models.Project;
import me.connersimmons.bb_mobile.presenters.IProjectPresenter;
import me.connersimmons.bb_mobile.realm.repository.IProjectRepository;
import me.connersimmons.bb_mobile.realm.repository.impl.ProjectRepository;

/**
 * Created by roma on 03.11.15.
 */
public class ProjectPresenter implements IProjectPresenter {

    private ProjectsActivity view;

    private IProjectRepository.OnDeleteProjectCallback onDeleteProjectCallback;
    private IProjectRepository.OnGetAllProjectsCallback onGetAllProjectsCallback;
    private IProjectRepository.OnGetProjectByIdCallback onGetProjectByIdCallback;
    private IProjectRepository.OnSaveProjectCallback onSaveProjectCallback;

    private IProjectRepository projectRepository;

    public ProjectPresenter(ProjectsActivity view) {
        this.view = view;
        projectRepository = new ProjectRepository();
    }

    @Override
    public void addProject(Project project) {
        projectRepository.addProject(project, onSaveProjectCallback);
    }

    @Override
    public void deleteProjectById(String projectId) {
        projectRepository.deleteProjectById(projectId, onDeleteProjectCallback);
    }

    @Override
    public void getAllProjects() {
        projectRepository.getAllProjects(onGetAllProjectsCallback);
    }

    @Override
    public void getProjectById(String projectId) {
        projectRepository.getProjectById(projectId, onGetProjectByIdCallback);
    }

    @Override
    public void subscribeCallbacks() {
        onSaveProjectCallback = new IProjectRepository.OnSaveProjectCallback() {
            @Override
            public void onSuccess() {
                view.showMessage("Project Added");
            }

            @Override
            public void onError(String message) {
                view.showMessage(message);
            }
        };
        onDeleteProjectCallback = new IProjectRepository.OnDeleteProjectCallback() {
            @Override
            public void onSuccess() {
                view.showMessage("Project Deleted");
            }

            @Override
            public void onError(String message) {
                view.showMessage(message);
            }
        };
        onGetAllProjectsCallback = new IProjectRepository.OnGetAllProjectsCallback() {
            @Override
            public void onSuccess(RealmResults<Project> projects) {
                view.showProjects(projects);
            }

            @Override
            public void onError(String message) {
                view.showMessage(message);
            }
        };
        onGetProjectByIdCallback = new IProjectRepository.OnGetProjectByIdCallback() {
            @Override
            public void onSuccess(Project project) {

            }

            @Override
            public void onError(String message) {

            }
        };
    }

    @Override
    public void unSubscribeCallbacks() {
        onDeleteProjectCallback = null;
        onSaveProjectCallback = null;
        onGetAllProjectsCallback = null;
        onGetProjectByIdCallback = null;
    }

}
