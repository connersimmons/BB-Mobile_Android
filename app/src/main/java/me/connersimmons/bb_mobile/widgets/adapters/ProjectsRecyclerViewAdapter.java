package me.connersimmons.bb_mobile.widgets.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.realm.RealmResults;
import me.connersimmons.bb_mobile.R;
import me.connersimmons.bb_mobile.models.Project;

/**
 * Created by connersimmons on 03.16.16.
 */
public class ProjectsRecyclerViewAdapter extends RecyclerView.Adapter<ProjectsRecyclerViewAdapter.ProjectViewHolder> {

    private RealmResults<Project> projects;

    public ProjectsRecyclerViewAdapter(RealmResults<Project> projects) {
        this.projects = projects;
    }

    @Override
    public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_project, parent, false);
        return new ProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProjectViewHolder holder, int position) {
        holder.tvProjectTitle.setText(projects.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    public class ProjectViewHolder extends RecyclerView.ViewHolder {

        private TextView tvProjectTitle;

        public ProjectViewHolder(View itemView) {
            super(itemView);

            tvProjectTitle = (TextView) itemView.findViewById(R.id.tv_project_name);
        }
    }

}
