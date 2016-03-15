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
import io.realm.RealmConfiguration;
import me.connersimmons.bb_mobile.AppConstants;
import me.connersimmons.bb_mobile.R;
import me.connersimmons.bb_mobile.models.Project;

import java.util.ArrayList;
import java.util.HashMap;


public class NewProjectFragment extends DialogFragment implements OnFormRowValueChangedListener,
        OnFormRowClickListener {

    private ListView mListView;
    private HashMap<String, Value<?>> mChangesMap;
    private MenuItem mSaveMenuItem;

    public static String TAG = NewProjectFragment.class.getName();
    private FormDescriptor mFormDescriptor;
    private FormManager mFormManager;

    private Realm realm;

    public NewProjectFragment() {
        // Required empty public constructor
    }

    public static final NewProjectFragment newInstance() {
        return new NewProjectFragment();
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

        realm = Realm.getDefaultInstance();
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

            createNewProject();

            mChangesMap.clear();
            setSaveItemVisibility();
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

    private void createNewProject() {
        realm.beginTransaction();
        Project project = realm.createObject(Project.class); // Create a new object
        System.out.print("Project address: " + mChangesMap.get("address").getValue().toString());
        //project.setAddress(mChangesMap.get("address").getValue().toString());
        realm.commitTransaction();
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
}
