package me.connersimmons.bb_mobile.widgets.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmResults;
import me.connersimmons.bb_mobile.R;
import me.connersimmons.bb_mobile.models.Project;

/**
 * Created by connersimmons on 3/18/16.
 */
public class AdapterProjectsAlpha extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int ITEM = 0;
    public static final int NO_ITEM = 1;

    private Context mContext;
    private LayoutInflater mInflater;
    private Realm mRealm;
    private RealmResults<Project> mResults;

    public AdapterProjectsAlpha(Context context, Realm realm, RealmResults<Project> results) { //, AddListener listener, MarkListener markListener, ResetListener resetListener) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        update(results);
        mRealm = realm;
        //mAddListener = listener;
        //mMarkListener = markListener;
        //mResetListener = resetListener;
    }

    public void update(RealmResults<Project> results) {
        mResults = results;
        //mFilterOption = AppBucketDrops.load(mContext);
        notifyDataSetChanged();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == NO_ITEM) {
            View view = mInflater.inflate(R.layout.no_item, parent, false);
            return new NoItemsHolder(view);
        } else {
            View view = mInflater.inflate(R.layout.row_project, parent, false);
            return new ProjectHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ProjectHolder projectHolder = (ProjectHolder) holder;
        Project project = mResults.get(position);
        projectHolder.setProjectTitle(project.getTitle());
        //projectHolder.setmBidsDue(project.getBidsDue().toString());
    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (!mResults.isEmpty()) {
            return ITEM;
        } else {
            return NO_ITEM;
        }
    }

    public static class ProjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mProjectTitle;
        TextView mBidsDue;
        //MarkListener mMarkListener;
        Context mContext;
        View mItemView;

        public ProjectHolder(View itemView) {//, MarkListener listener) {
            super(itemView);
            mItemView = itemView;
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
            mProjectTitle = (TextView) itemView.findViewById(R.id.tv_project_title);
            mBidsDue = (TextView) itemView.findViewById(R.id.tv_bids_due);
            //AppBucketDrops.setRalewayRegular(mContext, mTextWhat, mTextWhen);
            //mMarkListener = listener;
        }

        public void setProjectTitle(String title) {
            mProjectTitle.setText(title);
        }

        public void setmBidsDue(String bidsDue) {
            mBidsDue.setText(bidsDue);
        }

        @Override
        public void onClick(View v) {
            //mMarkListener.onMark(getAdapterPosition());
        }

        /*
        public void setWhen(long when) {
            mTextWhen.setText(DateUtils.getRelativeTimeSpanString(when, System.currentTimeMillis(), DateUtils.DAY_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL));
        }
        */
    }

    public static class NoItemsHolder extends RecyclerView.ViewHolder {

        public NoItemsHolder(View itemView) {
            super(itemView);
        }
    }
}
