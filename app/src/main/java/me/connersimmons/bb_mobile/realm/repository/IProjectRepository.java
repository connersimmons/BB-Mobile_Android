package me.connersimmons.bb_mobile.realm.repository;

import io.realm.RealmResults;
import me.connersimmons.bb_mobile.models.Project;

/**
 * Created by connersimmons on 3/15/16.
 */
public interface IProjectRepository {

    interface OnSaveProjectCallback {
        void onSuccess();
        void onError(String message);
    }

    interface OnDeleteProjectCallback {
        void onSuccess();
        void onError(String message);
    }

    interface OnGetProjectByIdCallback {
        void onSuccess(Project project);
        void onError(String message);
    }

    interface OnGetAllProjectsCallback {
        void onSuccess(RealmResults<Project> projects);
        void onError(String message);
    }

    void addProject(Project project, OnSaveProjectCallback callback);

    void deleteProjectById(String id, OnDeleteProjectCallback callback);

    void getAllProjects(OnGetAllProjectsCallback callback);

    void getProjectById(String id, OnGetProjectByIdCallback callback);
}
