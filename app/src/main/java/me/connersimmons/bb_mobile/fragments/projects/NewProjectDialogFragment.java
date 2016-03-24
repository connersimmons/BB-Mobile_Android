package me.connersimmons.bb_mobile.fragments.projects;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.quemb.qmbform.FormManager;
import com.quemb.qmbform.OnFormRowClickListener;
import com.quemb.qmbform.descriptor.DataSource;
import com.quemb.qmbform.descriptor.DataSourceListener;
import com.quemb.qmbform.descriptor.FormDescriptor;
import com.quemb.qmbform.descriptor.FormItemDescriptor;
import com.quemb.qmbform.descriptor.OnFormRowValueChangedListener;
import com.quemb.qmbform.descriptor.RowDescriptor;
import com.quemb.qmbform.descriptor.SectionDescriptor;
import com.quemb.qmbform.descriptor.Value;

import io.realm.Realm;
import me.connersimmons.bb_mobile.utils.AppConstants;
import me.connersimmons.bb_mobile.R;
import me.connersimmons.bb_mobile.models.Project;
import me.connersimmons.bb_mobile.presenters.impl.ProjectPresenter;
import me.connersimmons.bb_mobile.utils.DateFormatter;

import java.util.ArrayList;
import java.util.HashMap;


public class NewProjectDialogFragment extends DialogFragment implements OnFormRowValueChangedListener,
        OnFormRowClickListener {

    private ListView mListView;
    private HashMap<String, Value<?>> mChangesMap;
    private MenuItem mSaveMenuItem;

    public static String TAG = NewProjectDialogFragment.class.getName();
    private FormDescriptor mFormDescriptor;
    private FormManager mFormManager;

    private Realm realm;
    private ProjectPresenter mProjectPresenter;

    public NewProjectDialogFragment() {
        // Required empty public constructor
    }

    public static final NewProjectDialogFragment newInstance() {
        return new NewProjectDialogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_clear_white_24dp);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_project, container, false);
        mListView = (ListView) view.findViewById(R.id.list);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mProjectPresenter = new ProjectPresenter();

        mChangesMap = new HashMap<String, Value<?>>();

        mFormDescriptor = FormDescriptor.newInstance();
        generalInfoSection(mFormDescriptor);
        scopeSection(mFormDescriptor);

        //showMoreOptionsSection(mFormDescriptor);

        laborSection(mFormDescriptor);
        insuranceSection(mFormDescriptor);
        experienceSection(mFormDescriptor);


        mFormManager = new FormManager();
        mFormManager.setup(mFormDescriptor, mListView, getActivity());
        mFormManager.setOnFormRowClickListener(this);
        mFormManager.setOnFormRowValueChangedListener(this);
    }

    // General Information Section
    private void generalInfoSection(FormDescriptor descriptor) {
        SectionDescriptor genInfoSectionDescriptor = SectionDescriptor.newInstance("genInfo","General Information");
        descriptor.addSection(genInfoSectionDescriptor);

        genInfoSectionDescriptor.addRow(RowDescriptor.newInstance("title", RowDescriptor.FormRowDescriptorTypeText, "Title", null));
        genInfoSectionDescriptor.addRow(RowDescriptor.newInstance("address", RowDescriptor.FormRowDescriptorTypeText, "Address", null));
        genInfoSectionDescriptor.addRow(RowDescriptor.newInstance("city", RowDescriptor.FormRowDescriptorTypeText, "City", null));

        RowDescriptor statePickerDescriptor = RowDescriptor.newInstance("statePicker",RowDescriptor.FormRowDescriptorTypeSelectorPickerDialog, "State", new Value<String>("Select..."));
        statePickerDescriptor.setDataSource(new DataSource() {
            @Override
            public void loadData(final DataSourceListener listener) {
                CustomTask task = new CustomTask("state");
                task.execute(listener);

            }
        });
        genInfoSectionDescriptor.addRow(statePickerDescriptor);

        genInfoSectionDescriptor.addRow(RowDescriptor.newInstance("zip", RowDescriptor.FormRowDescriptorTypeText, "Zip", new Value<String>("")));

        RowDescriptor structurePickerDescriptor = RowDescriptor.newInstance("structurePicker",RowDescriptor.FormRowDescriptorTypeSelectorPickerDialog, "Structure", new Value<String>("Select..."));
        structurePickerDescriptor.setDataSource(new DataSource() {
            @Override
            public void loadData(final DataSourceListener listener) {
                CustomTask task = new CustomTask("structure");
                task.execute(listener);

            }
        });
        genInfoSectionDescriptor.addRow( structurePickerDescriptor );

        RowDescriptor typePickerDescriptor = RowDescriptor.newInstance("typePicker",RowDescriptor.FormRowDescriptorTypeSelectorPickerDialog, "Type", new Value<String>("Select..."));
        typePickerDescriptor.setDataSource(new DataSource() {
            @Override
            public void loadData(final DataSourceListener listener) {
                CustomTask task = new CustomTask("type");
                task.execute(listener);

            }
        });
        genInfoSectionDescriptor.addRow( typePickerDescriptor );

        RowDescriptor ownerPickerDescriptor = RowDescriptor.newInstance("ownerPicker",RowDescriptor.FormRowDescriptorTypeSelectorPickerDialog, "Owner", new Value<String>("Select..."));
        ownerPickerDescriptor.setDataSource(new DataSource() {
            @Override
            public void loadData(final DataSourceListener listener) {
                CustomTask task = new CustomTask("owner");
                task.execute(listener);

            }
        });
        genInfoSectionDescriptor.addRow(ownerPickerDescriptor);

        RowDescriptor statusPickerDescriptor = RowDescriptor.newInstance("statusPicker",RowDescriptor.FormRowDescriptorTypeSelectorPickerDialog, "Status", new Value<String>("Select..."));
        statusPickerDescriptor.setDataSource(new DataSource() {
            @Override
            public void loadData(final DataSourceListener listener) {
                CustomTask task = new CustomTask("status");
                task.execute(listener);

            }
        });
        genInfoSectionDescriptor.addRow(statusPickerDescriptor);

        genInfoSectionDescriptor.addRow(RowDescriptor.newInstance("numBuildings", RowDescriptor.FormRowDescriptorTypeInteger, "Number of Buildings", null));
        genInfoSectionDescriptor.addRow( RowDescriptor.newInstance("totalSqft",RowDescriptor.FormRowDescriptorTypeNumber, "Total Square Footage", null));
        genInfoSectionDescriptor.addRow(RowDescriptor.newInstance("storiesAbove", RowDescriptor.FormRowDescriptorTypeInteger, "Stories Above Grade", null));
        genInfoSectionDescriptor.addRow(RowDescriptor.newInstance("storiesBelow", RowDescriptor.FormRowDescriptorTypeInteger, "Stories Below Grade", null));

        genInfoSectionDescriptor.addRow( RowDescriptor.newInstance("preBidDateDialog",RowDescriptor.FormRowDescriptorTypeDate, "Pre-Bid Meeting Date") );
        genInfoSectionDescriptor.addRow( RowDescriptor.newInstance("preBidTimeDialog",RowDescriptor.FormRowDescriptorTypeTime, "Pre-Bid Meeting Time") );

        genInfoSectionDescriptor.addRow( RowDescriptor.newInstance("bidsDueDateDialog",RowDescriptor.FormRowDescriptorTypeDate, "Bids Due Date") );
        genInfoSectionDescriptor.addRow( RowDescriptor.newInstance("bidsDueTimeDialog",RowDescriptor.FormRowDescriptorTypeTime, "Bids Due Time") );

        genInfoSectionDescriptor.addRow( RowDescriptor.newInstance("startDateDialog",RowDescriptor.FormRowDescriptorTypeDate, "Start Date") );

        genInfoSectionDescriptor.addRow( RowDescriptor.newInstance("endDateDialog",RowDescriptor.FormRowDescriptorTypeDate, "End Date") );

        genInfoSectionDescriptor.addRow(RowDescriptor.newInstance("contractNo", RowDescriptor.FormRowDescriptorTypeText, "Contract No", new Value<String>("")));
        genInfoSectionDescriptor.addRow( RowDescriptor.newInstance("valuation",RowDescriptor.FormRowDescriptorTypeNumber, "Valuation", null));
    }

    // Scope Section
    private void scopeSection(FormDescriptor descriptor) {
        SectionDescriptor scopeSectionDescriptor = SectionDescriptor.newInstance("scopeSection","Scope");
        descriptor.addSection(scopeSectionDescriptor);

        scopeSectionDescriptor.addRow(RowDescriptor.newInstance("scope", RowDescriptor.FormRowDescriptorTypeTextView, "", new Value<String>("Place other valuable project information here.")));
    }

    // Show More Options Section
    private void showMoreOptionsSection(FormDescriptor descriptor) {
        SectionDescriptor showMoreSectionDescriptor = SectionDescriptor.newInstance("showMoreSection", "Show More Project Options");
        descriptor.addSection(showMoreSectionDescriptor);

        RowDescriptor showMoreRow = RowDescriptor.newInstance("showMore", RowDescriptor.FormRowDescriptorTypeBooleanSwitch, "Show More", new Value<Boolean>(false));
        showMoreSectionDescriptor.addRow(showMoreRow);
    }

    // Labor Section
    private void laborSection(FormDescriptor descriptor) {
        SectionDescriptor laborSectionDescriptor = SectionDescriptor.newInstance("laborSection","Labor");
        descriptor.addSection(laborSectionDescriptor);

        laborSectionDescriptor.addRow(RowDescriptor.newInstance("union", RowDescriptor.FormRowDescriptorTypeBooleanCheck, "Union"));
        laborSectionDescriptor.addRow(RowDescriptor.newInstance("nonunion", RowDescriptor.FormRowDescriptorTypeBooleanCheck, "Non-Union"));
        laborSectionDescriptor.addRow(RowDescriptor.newInstance("prevailingWage", RowDescriptor.FormRowDescriptorTypeBooleanCheck, "Prevailing Wage"));
    }

    // Insurance Section
    private void insuranceSection(FormDescriptor descriptor) {
        SectionDescriptor insuranceSectionDescriptor = SectionDescriptor.newInstance("insuranceSection","Insurance");
        descriptor.addSection(insuranceSectionDescriptor);

        insuranceSectionDescriptor.addRow(RowDescriptor.newInstance("bidSecurity", RowDescriptor.FormRowDescriptorTypeNumber, "Bid Security (%)", null));
        insuranceSectionDescriptor.addRow(RowDescriptor.newInstance("performanceBond", RowDescriptor.FormRowDescriptorTypeNumber, "Performance Bond (%)", null));
        insuranceSectionDescriptor.addRow(RowDescriptor.newInstance("paymentBond", RowDescriptor.FormRowDescriptorTypeNumber, "Payment Bond (%)", null));
    }

    // Experience Section
    private void experienceSection(FormDescriptor descriptor) {
        SectionDescriptor experienceSectionDescriptor = SectionDescriptor.newInstance("experienceSection","Experience");
        descriptor.addSection(experienceSectionDescriptor);

        experienceSectionDescriptor.addRow(RowDescriptor.newInstance("leed", RowDescriptor.FormRowDescriptorTypeBooleanCheck, "LEED"));
        experienceSectionDescriptor.addRow(RowDescriptor.newInstance("bim", RowDescriptor.FormRowDescriptorTypeBooleanCheck, "BIM"));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_new_project, menu);
        mSaveMenuItem = menu.findItem(R.id.action_save);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        setSaveItemVisibility();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item == mSaveMenuItem){

            Project newProject = createNewProject();

            mChangesMap.clear();
            //setSaveItemVisibility();

            Log.d(TAG, "PROJECT BEING CREATED: " + newProject.getTitle());
            //activity.setProject(newProject);
            //listener.onAddStudentClickListener(newProject);

            mProjectPresenter.addProject(newProject);

            //Close fragment and activity
            this.dismiss();
            getActivity().finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFormRowClick(FormItemDescriptor itemDescriptor) {

    }

    @Override
    public void onValueChanged(RowDescriptor rowDescriptor, Value<?> oldValue, Value<?> newValue) {
        Log.d(TAG, "Value Changed: " + rowDescriptor.getTitle());

        mChangesMap.put(rowDescriptor.getTag(), newValue);

        setSaveItemVisibility();
    }

    private void setSaveItemVisibility() {
        if (mSaveMenuItem != null){
            mSaveMenuItem.setVisible(mChangesMap.size() > 0);
        }
    }

    private Project createNewProject() {

        //Log.d(TAG, "Project title: " + mChangesMap.get("title").getValue().toString());

        Project project = new Project();

        project.setAddress(getValueByTag("address"));

        if (getValueByTag("bidsDueDateDialog") != null && getValueByTag("bidsDueTimeDialog") != null) {
            project.setBidsDue(DateFormatter.convertStringToDateTime(getValueByTag("bidsDueDateDialog"), getValueByTag("bidsDueTimeDialog")));
        }

        if (getValueByTag("bidSecurity") != null) {
            project.setBidSecurity(Double.parseDouble(getValueByTag("bidSecurity")));
        }

        if (getValueByTag("bim") != null) {
            project.setBim(Boolean.parseBoolean(getValueByTag("bim")));
        }

        project.setCity(getValueByTag("city"));
        project.setContractNo(getValueByTag("contractNo"));

        if (getValueByTag("endDateDialog") != null) {
            project.setEndDate(DateFormatter.convertStringToDate(getValueByTag("endDateDialog")));
        }

        //project.setIsOutForBid(Boolean.parseBoolean(getValueByTag("isOutForBid")));

        if (getValueByTag("leed") != null) {
            project.setLeed(Boolean.parseBoolean(getValueByTag("leed")));
        }

        if (getValueByTag("nonunion") != null) {
            project.setNonunion(Boolean.parseBoolean(getValueByTag("nonunion")));
        }

        if (getValueByTag("numBuildings") != null) {
            project.setNumBuildings(Integer.parseInt(getValueByTag("numBuildings")));
        }

        project.setOwner(getValueByTag("ownerPicker"));

        if (getValueByTag("paymentBond") != null) {
            project.setPaymentBond(Double.parseDouble(getValueByTag("paymentBond")));
        }

        if (getValueByTag("performanceBond") != null) {
            project.setPerformanceBond(Double.parseDouble(getValueByTag("performanceBond")));
        }

        if (getValueByTag("preBidDateDialog") != null && getValueByTag("preBidTimeDialog") != null) {
            project.setPreBidMeeting(DateFormatter.convertStringToDateTime(getValueByTag("preBidDateDialog"), getValueByTag("preBidTimeDialog")));
        }

        if (getValueByTag("prevailingWage") != null) {
            project.setPrevailingWage(Boolean.parseBoolean(getValueByTag("prevailingWage")));
        }

        project.setScope(getValueByTag("scope"));

        if (getValueByTag("totalSqft") != null) {
            project.setSquareFootage(Double.parseDouble(getValueByTag("totalSqft")));
        }

        if (getValueByTag("startDateDialog") != null) {
            project.setStartDate(DateFormatter.convertStringToDate(getValueByTag("startDateDialog")));
        }

        project.setState(getValueByTag("statePicker"));
        project.setStatus(getValueByTag("statusPicker"));

        if (getValueByTag("storiesAbove") != null) {
            project.setStoriesAboveGrade(Integer.parseInt(getValueByTag("storiesAbove")));
        }

        if (getValueByTag("storiesBelow") != null) {
            project.setStoriesBelowGrade(Integer.parseInt(getValueByTag("storiesBelow")));
        }

        project.setStructure(getValueByTag("structurePicker"));
        project.setTitle(getValueByTag("title"));
        project.setType(getValueByTag("typePicker"));

        if (getValueByTag("union") != null) {
            project.setUnion(Boolean.parseBoolean(getValueByTag("union")));
        }

        if (getValueByTag("valuation") != null) {
            project.setValuation(Double.parseDouble(getValueByTag("valuation")));
        }

        project.setZip(getValueByTag("zip"));

        //listener.onAddStudentClickListener(project);

        return project;
    }

    private String getValueByTag(String tag) {
        String result = null;
        if (mChangesMap.keySet().contains(tag)) {
            result = mChangesMap.get(tag).getValue().toString();
        }
        return result;
    }

    private class CustomTask extends AsyncTask<DataSourceListener, Void, ArrayList<String>> {

        private DataSourceListener mListener;
        private ProgressDialog mProgressDialog;
        private String dataSourceID;

        public CustomTask(String id) {
            dataSourceID = id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = ProgressDialog.show(getActivity(), "Loading",
                    "Loading picker data.", true);
        }

        protected ArrayList<String> doInBackground(DataSourceListener... listeners) {
            mListener = listeners[0];
            ArrayList<String> items = null;
            AppConstants instance = AppConstants.getInstance();

            if (dataSourceID.equals("state")) {
                items = instance.getStatesArray();
            } else if (dataSourceID.equals("structure")) {
                items = instance.getStructureArray();
            } else if (dataSourceID.equals("type")) {
                items = instance.getTypeArray();
            } else if (dataSourceID.equals("owner")) {
                items = instance.getOwnerArray();
            } else if (dataSourceID.equals("status")) {
                items = instance.getStatusArray();
            }

            return items;
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);
            mProgressDialog.dismiss();
            mListener.onDataSourceLoaded(strings);
        }
    }

    /*
    public void setListener(OnAddStudentClickListener listener) {
        this.listener = listener;
    }

    public interface OnAddStudentClickListener {
        void onAddStudentClickListener(Project student);
    }
    */
}
