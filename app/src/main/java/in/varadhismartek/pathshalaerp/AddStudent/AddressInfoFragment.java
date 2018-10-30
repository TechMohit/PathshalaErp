package in.varadhismartek.pathshalaerp.AddStudent;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import in.varadhismartek.pathshalaerp.CircleImageView;
import in.varadhismartek.pathshalaerp.Constant;
import in.varadhismartek.pathshalaerp.R;
import in.varadhismartek.pathshalaerp.ValidationViews;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddressInfoFragment extends Fragment implements View.OnClickListener {


    EditText parent_present_address_street,
            parent_present_city, parent_present_state,
            parent_present_pincode, parent_present_country,
            parent_present_contact_number,
            parent_permanent_address_street,
            parent_permanent_city, parent_permanent_state,
            parent_permanent_pincode, parent_permanent_country,
            parent_permanent_contact_number,
            guardian_address, guardian_city, guardian_state,
            guardian_pincode, guardian_country;


    String  a = "VR";

    CheckBox checkBoxForOffPermanentAddress;
    String checkBoxChecked = "Permanent address same as present";

    LinearLayout layoutForPermanentAddress,
            layoutForGuardiansAddress;


    List<EditText> guardian_address_arrayList = new ArrayList<>();
    List<EditText> guardian_pincode_arrayList = new ArrayList<>();
    List<EditText> guardian_city_arrayList = new ArrayList<>();
    List<EditText> guardian_state_arrayList = new ArrayList<>();
    List<EditText> guardian_country_arrayList = new ArrayList<>();

    private TextView student_Name;
    private CircleImageView student_ProfileImage;
    private TextView student_Class;
    private TextView student_Section;
    private DatabaseReference studentSnapShotRef;
    private String studentClass;
    private String studentSection;
    private String studentFName;
    private String studentprofilePicture;
    ArrayList<String> stdentReg;


    Button finalSubmitAddress;
    final int BUTTON_ENABLE = 3;
    View child;


    private String g_address;
    private String g_pincode;
    private String g_city;
    private String g_state;
    private String g_country;
    DatabaseReference mref1 = FirebaseDatabase.getInstance().getReference("School/SchoolId/Student_Registration");
    private DatabaseReference mref;
    private String completeAddress;
    private int address_details_status=1;
    private TextView student_RegistrationNumber;
    private String studentRegistrationNumber;

    Activity parentActivity =null;

    public AddressInfoFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_address_info, container, false);
        parentActivity=(Activity) v.getContext();
        mref = FirebaseDatabase.getInstance().getReference("School/SchoolId/Barriers");
        studentSnapShotRef = FirebaseDatabase.getInstance().getReference("School/SchoolId/Student_Details_SnapShot");
        getGuardianNumbers();
        initViews(v);
        initListner();
        stdentReg = new ArrayList<>();
        getStudentDetailsFromFirebase();
        getFatherDetails();
        textWatcherForPincode();



        return v;


    }

    private void getStudentDetailsFromFirebase() {

        Log.d("dfdsjl", ""+Constant.FINAL_REGISTRATION_ID);
        studentSnapShotRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(stdentReg.size()>=0){
                    stdentReg.clear();

                }

                Log.d("sksk", ""+dataSnapshot.getKey());

                for (DataSnapshot postSnapShotA : dataSnapshot.getChildren()){
                    Log.d("allregistartionNo", ""+postSnapShotA.getKey());
                    stdentReg.add(postSnapShotA.getKey());

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void getGuardianNumbers() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Getting data please wait...");
        progressDialog.show();
        mref.child("Admission_Barriers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot postSnapShotA : dataSnapshot.getChildren()){
                    for(DataSnapshot postSnapShotB : postSnapShotA.getChildren()){
                        Log.d("postSHH", ""+postSnapShotB.getKey());
                        if(postSnapShotB.getKey().equals("number_of_guardians")){
                            Log.d("guardian", ""+postSnapShotB.getValue().toString());
                            int guardianNumber = Integer.parseInt(postSnapShotB.getValue().toString());
                            dynamicLayoutGuardianAddress(guardianNumber);

                        }
                    }
                }
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



    @SuppressLint("SetTextI18n")
    private void dynamicLayoutGuardianAddress(int guardianNumber) {

        if(guardianNumber!=0) {
            LinearLayout layout_child = new LinearLayout(parentActivity);
            layout_child.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            layout_child.setOrientation(LinearLayout.VERTICAL);
            layoutForGuardiansAddress.addView(layout_child);


            Configuration config = parentActivity.getResources().getConfiguration();
            if (config.smallestScreenWidthDp >= 600) {

                for (int i = 1; i <= guardianNumber; i++) {
                    View child = parentActivity.getLayoutInflater().inflate(R.layout.guardian_address, null);
                    TextView txt = child.findViewById(R.id.guardianAddress);
                    txt.setText(Constant.GUARDIAN + i);
                    layout_child.addView(child);

                }


            } else {

                if (guardian_address_arrayList.size() != 0) {
                    guardian_address_arrayList.clear();
                    guardian_pincode_arrayList.clear();
                    guardian_city_arrayList.clear();
                    guardian_state_arrayList.clear();
                    guardian_country_arrayList.clear();
                }

                for (int i = 1; i <= guardianNumber; i++) {

                    child = parentActivity.getLayoutInflater().inflate(R.layout.guardian_address, null);
                    TextView txt = child.findViewById(R.id.guardianNumber);
                    txt.setText(Constant.GUARDIAN + i);
                    guardian_address = child.findViewById(R.id.guardianAddress);
                    guardian_pincode = child.findViewById(R.id.guardianAddressPincode);
                    guardian_pincode.setId(100 + i);
                    guardian_pincode.addTextChangedListener(new GenericTextWatcher(guardian_pincode));
                    guardian_city = child.findViewById(R.id.guardianAddressCity);
                    guardian_state = child.findViewById(R.id.guardianAddressState);
                    guardian_country = child.findViewById(R.id.guardianAddressCountry);

                    guardian_address_arrayList.add(guardian_address);
                    guardian_pincode_arrayList.add(guardian_pincode);
                    guardian_city_arrayList.add(guardian_city);
                    guardian_state_arrayList.add(guardian_state);
                    guardian_country_arrayList.add(guardian_country);


                    layout_child.addView(child);


                }
            }
        }
    }

    private void textWatcherForPincode() {

        parent_present_pincode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int length = charSequence.length();
                Log.d("textLength", "" + length);
                if (length == 6) {
                    Log.d("textLength", "" + charSequence);

                    new AsynchTaskForStateAndCity(charSequence.toString(), Constant.PRESENT_PINCODE).execute();

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        parent_permanent_pincode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                int length = charSequence.length();
                Log.d("textLength", "" + length);
                if (length == 6) {
                    Log.d("textLength", "" + charSequence);

                    new AsynchTaskForStateAndCity(charSequence.toString(), Constant.PERMANENT_PINCODE).execute();

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void initListner() {

        checkBoxForOffPermanentAddress.setOnClickListener(this);
        finalSubmitAddress.setOnClickListener(this);

    }

    private void initViews(View v) {

        parent_present_address_street = v.findViewById(R.id.parent_present_address);
        parent_present_city = v.findViewById(R.id.parent_present_city);
        parent_present_state = v.findViewById(R.id.parent_present_state);
        parent_present_pincode = v.findViewById(R.id.parent_present_pincode);
        parent_present_country = v.findViewById(R.id.parent_present_country);
        parent_present_contact_number = v.findViewById(R.id.parent_present_contact_number);
        parent_permanent_address_street = v.findViewById(R.id.parent_permanent_address);
        parent_permanent_city = v.findViewById(R.id.parent_permanent_city);
        parent_permanent_state = v.findViewById(R.id.parent_permanent_state);
        parent_permanent_pincode = v.findViewById(R.id.parent_permanent_pincode);
        parent_permanent_country = v.findViewById(R.id.parent_permanent_country);
        parent_permanent_contact_number = v.findViewById(R.id.parent_permanent_contact_number);
        checkBoxForOffPermanentAddress = v.findViewById(R.id.checkBoxAddressSameOrNot);
        layoutForPermanentAddress = v.findViewById(R.id.layoutPermanentAddress);
        layoutForGuardiansAddress = v.findViewById(R.id.layout_for_guardianAddress);
        finalSubmitAddress = v.findViewById(R.id.btnAddressFinalSubmit);
        student_RegistrationNumber            = v.findViewById(R.id.studentRegistartionNumber);
        student_Name                          = v.findViewById(R.id.studentName);
        student_ProfileImage                  = v.findViewById(R.id.studentProfileImage);
        student_Class                         = v.findViewById(R.id.studentClass);
        student_Section                       = v.findViewById(R.id.studentSection);


    }

    private void getFatherDetails() {


    }




    //----------------Onclick of buttons------------------------------------------------------------
    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.checkBoxAddressSameOrNot:

                if (checkBoxForOffPermanentAddress.isChecked()) {
                    layoutForPermanentAddress.setVisibility(View.GONE);
                } else {
                    layoutForPermanentAddress.setVisibility(View.VISIBLE);
                    checkBoxChecked = "";

                }
                break;


            case R.id.btnAddressFinalSubmit:

                if(!student_RegistrationNumber.getText().toString().equals("Registration Number")){


                    if (!ValidationViews.EditTextNullValidate(parent_present_address_street,
                            parent_present_city, parent_present_state, parent_present_pincode, parent_present_country, parent_present_contact_number)) {

                        ValidationViews.showToast(getActivity(), "Fields are requierd");
                    } else {

                        if (a.equals("VR")) {

                            for (int i = 0; i < guardian_address_arrayList.size(); i++) {
                                guardian_address = guardian_address_arrayList.get(i);
                                guardian_pincode = guardian_pincode_arrayList.get(i);
                                guardian_city = guardian_city_arrayList.get(i);
                                guardian_state = guardian_state_arrayList.get(i);
                                guardian_country = guardian_country_arrayList.get(i);


                                if (!ValidationViews.EditTextNullValidate(guardian_address)) {
                                    guardian_address.requestFocus();
                                    return;
                                }

                                if (!ValidationViews.EditTextNullValidate(guardian_pincode)) {
                                    guardian_pincode.requestFocus();
                                    return;
                                } else if (!ValidationViews.EditTextNumberOfDigitsValidate(guardian_pincode, 6)) {
                                    guardian_pincode.setError("invalid");
                                    guardian_pincode.requestFocus();
                                    return;
                                }

                                if (!ValidationViews.EditTextNullValidate(guardian_city)) {
                                    guardian_city.requestFocus();
                                    return;
                                } else if (!ValidationViews.EditTextNullValidate(guardian_state)) {
                                    guardian_state.requestFocus();
                                    return;
                                } else if (!ValidationViews.EditTextNullValidate(guardian_country)) {
                                    guardian_country.requestFocus();
                                    return;
                                }

                            }
                        }


                        if (a.equals("VR")) {

                            sendParentAddressToserver();

                        }
                    }

                }
                else {

                    Toast.makeText(getActivity(), "Please select student", Toast.LENGTH_SHORT).show();
                }



                break;
        }

    }

    private void sendParentAddressToserver() {



            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Storing data...please wait");
            progressDialog.show();


            String presentAddressStreet = parent_present_address_street.getText().toString();

            String presentAddressCity = parent_present_city.getText().toString();

            String presentAddressPincode= parent_present_pincode.getText().toString();

            String presentAddressState= parent_present_state.getText().toString();

            String presentAddressCountry= parent_present_country.getText().toString();

            String presentAddressContact= parent_present_contact_number.getText().toString();

            String permanentAddressStreet = parent_permanent_address_street.getText().toString();

            String permanentAddressCity = parent_permanent_city.getText().toString();

            String permanentAddressPincode= parent_permanent_pincode.getText().toString();

            String permanentAddressState= parent_permanent_state.getText().toString();

            String permanentAddressCountry= parent_permanent_country.getText().toString();

            String permanentAddressContact= parent_permanent_contact_number.getText().toString();


            completeAddress = presentAddressStreet + ", " + presentAddressCity +
                    ", " + presentAddressState + " " + presentAddressPincode;


            final NewStudentRegisterModel model = new NewStudentRegisterModel(presentAddressStreet,presentAddressCity,
                    presentAddressPincode,presentAddressState,presentAddressCountry,presentAddressContact,
                    permanentAddressStreet,permanentAddressCity,permanentAddressPincode,permanentAddressState,
                    permanentAddressCountry,permanentAddressContact);


            final NewStudentRegisterModel parent_present_address = new NewStudentRegisterModel(completeAddress,presentAddressStreet,presentAddressCity,
                    presentAddressPincode,presentAddressState,presentAddressCountry,presentAddressContact);


            final NewStudentRegisterModel parent_permanent_address = new NewStudentRegisterModel(presentAddressStreet,presentAddressCity,
                    presentAddressPincode,presentAddressState,presentAddressCountry,presentAddressContact);





            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    Log.d("dsd;ls", "sn");
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    mref1.child(Constant.FINAL_REGISTRATION_ID).child("address_information").child("parent_present_address_info").setValue(parent_present_address);
                    mref1.child(Constant.FINAL_REGISTRATION_ID).child("address_information").child("parent_permanent_address_info").setValue(parent_permanent_address);


                        DatabaseReference db_gaurdian = mref1.child(Constant.FINAL_REGISTRATION_ID).child("address_information").child("guardian_address_info");

                    for (int i = 0; i < guardian_address_arrayList.size(); i++) {

                        g_address = guardian_address_arrayList.get(i).getText().toString();
                        g_pincode = guardian_pincode_arrayList.get(i).getText().toString();
                        g_city    = guardian_city_arrayList.get(i).getText().toString();
                        g_state = guardian_state_arrayList.get(i).getText().toString();
                        g_country = guardian_country_arrayList.get(i).getText().toString();

                        DatabaseReference db_child = db_gaurdian.child("guardian" + (i + 1));
                        db_child.child("address").setValue(g_address);
                        db_child.child("pincode").setValue(g_pincode);
                        db_child.child("city").setValue(g_city);
                        db_child.child("state").setValue(g_state);
                        db_child.child("country").setValue(g_country);



                        //sending the value of dynamically created edittext to firebase________________

                    }
                    EventBus.getDefault().post(new NewStudentRegisterModel(studentClass,studentSection,studentFName,studentRegistrationNumber));
                    NewAdmissionActivity newAdmissionActivity = (NewAdmissionActivity) getActivity();
                    FacilityInfoFragment facilityInfoFragment = new FacilityInfoFragment();
                    newAdmissionActivity.loadNextFragment(BUTTON_ENABLE, facilityInfoFragment, null);
                    Toast.makeText(getActivity(), "Address data stored", Toast.LENGTH_SHORT).show();

                    progressDialog.dismiss();


                }
            }, 5000);



    }




    /*we are using asynctask for getting city state and country
    * from Indianpost Api*/
    @SuppressLint("StaticFieldLeak")
    private class AsynchTaskForStateAndCity extends AsyncTask<String , String , String > {
        String pincode;
        String state , city , country;
        String presentPermanentTag;
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());


        AsynchTaskForStateAndCity(String charSequence, String present_permanent_code) {
            this.pincode = (String) charSequence;
            this.presentPermanentTag = present_permanent_code;


        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("getting data...please wait");
            progressDialog.show();
        }


        /*doInBackground we are careting first api link and that link we are setting in Jparser class
        * Jpasrser class give the response in JSON format after getting json we
        * will parse the Json there we will get city state and country based on the pincode*/
        @Override
        protected String doInBackground(String... strings) {
            /*Creating object of JsonCityStateParser*/
            JsonCitySateParser jsonCitySateParser = new JsonCitySateParser();

            /*Afer creating object we are sending the pincode entered by user to
            * JsonCityStateParser class
            * That will resturn response in JSon Format and I m storing
            * that response in JSONObject class*/
            JSONObject json = jsonCitySateParser.getJSONFromUrl(pincode);
            Log.d("json", ""+json);

            JSONArray array = null;

            try{

                /*here we are parsing Json and getting city country and state
                * based on PIncode*/
                array = json.getJSONArray("PostOffice");
                JSONObject jsonObject = array.getJSONObject(0);
                state = jsonObject.getString("State");
                city = jsonObject.getString("District");
                country = jsonObject.getString("Country");
                Log.d("state", ""+state+"  city:  "+city );




            } catch (JSONException e) {
                e.printStackTrace();
            }


            progressDialog.dismiss();


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            switch (presentPermanentTag){
                /*After finishing doInBackground it will excute onPost method
                * there we are setting all getting data from json parsing to edittext*/

                /*Below case is for father present address pin code*/
                case Constant.PRESENT_PINCODE:
                    parent_present_state.setText(state);
                    parent_present_city.setText(city);
                    parent_present_country.setText(country);
                    break;

                /*Below case is for paarent permanent address pin code*/
                case Constant.PERMANENT_PINCODE:
                    parent_permanent_state.setText(state);
                    parent_permanent_city.setText(city);
                    parent_permanent_country.setText(country);
                    break;



                case Constant.GUARDIANPINCODE1:
                    guardian_city_arrayList.get(0).setText(city);
                    guardian_state_arrayList.get(0).setText(state);
                    guardian_country_arrayList.get(0).setText(country);
                    break;

                case Constant.GUARDIANPINCODE2:
                    guardian_city_arrayList.get(1).setText(city);
                    guardian_state_arrayList.get(1).setText(state);
                    guardian_country_arrayList.get(1).setText(country);
                    break;

                case Constant.GUARDIANPINCODE3:
                    guardian_city_arrayList.get(2).setText(city);
                    guardian_state_arrayList.get(2).setText(state);
                    guardian_country_arrayList.get(2).setText(country);
                    break;

            }

        }
    }

    private class GenericTextWatcher implements TextWatcher {
        private View view;
        private GenericTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            int length = charSequence.length();
            Log.d("textLength", ""+length);
            if(length==6){

                switch (view.getId()){

                    case 101:
                        new AsynchTaskForStateAndCity(guardian_pincode_arrayList.get(0).getText().toString(),
                                Constant.GUARDIANPINCODE1).execute();

                        break;

                    case 102:
                        new AsynchTaskForStateAndCity(guardian_pincode_arrayList.get(1).getText().toString(),
                                Constant.GUARDIANPINCODE2).execute();
                        break;

                    case 103:
                        new AsynchTaskForStateAndCity(guardian_pincode_arrayList.get(2).getText().toString(),
                                Constant.GUARDIANPINCODE3).execute();
                        break;

                }

            }

        }

        @Override
        public void afterTextChanged(Editable editable) {


        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.dashboard, menu);
        final MenuItem myActionMenuItem = menu.findItem( R.id.action_search);
        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setQueryHint("Registration Number");

        final SearchView.SearchAutoComplete searchAutoComplete = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setDropDownBackgroundResource(android.R.color.white);



        //String dataArr[] = {"VS018BH0001"};
        ArrayAdapter<String> newsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, stdentReg);
        searchAutoComplete.setAdapter(newsAdapter);

        searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
               final String queryString=(String)adapterView.getItemAtPosition(itemIndex);
                searchAutoComplete.setText("" + queryString);

                mref1.child(queryString).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        address_details_status=1;
                        for(DataSnapshot postSnapoShotA : dataSnapshot.getChildren()){
                            Log.d("djdj", ""+postSnapoShotA.getKey());
                            if(postSnapoShotA.getKey().equalsIgnoreCase("address_information")){
                                Toast.makeText(getActivity(), "Page already filled, please move to next page", Toast.LENGTH_SHORT).show();
                                address_details_status=0;
                            }


                        }
                        if(address_details_status==1){
                            Toast.makeText(getActivity(), "Address details not exists, please fill details", Toast.LENGTH_SHORT).show();
                            address_details_status=1;
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                studentSnapShotRef.child(queryString).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        student_RegistrationNumber.setText(""+queryString);

                        for (DataSnapshot postSnapShotB : dataSnapshot.getChildren()){
                            Log.d("PostSS", ""+postSnapShotB.getKey());

                            if(postSnapShotB.getKey().equals("str_selectedClass")){

                                studentClass = (String) postSnapShotB.getValue();
                                student_Class.setText(studentClass);

                            }

                            if(postSnapShotB.getKey().equals("str_studentSection")){

                                studentSection = (String) postSnapShotB.getValue();
                                student_Section.setText(studentSection);

                            }

                            if(postSnapShotB.getKey().equals("str_student_Firstname")){

                                studentFName = (String) postSnapShotB.getValue();
                                student_Name.setText(studentFName);

                            }

                            if(postSnapShotB.getKey().equals("student_profile_picture")){

                                studentprofilePicture = (String) postSnapShotB.getValue();
                                Glide.with(getActivity()).load(studentprofilePicture).into(student_ProfileImage);

                            }

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessage(NewStudentRegisterModel event){
        Log.d("djAddk", ""+event.getStartRegistrationNumber());
        studentRegistrationNumber = event.getStartRegistrationNumber();
        studentClass = event.getStr_selectedClass();
        studentSection =event.getStr_studentSection();
        studentFName = event.getStr_student_Firstname();
        student_RegistrationNumber.setText(event.getStartRegistrationNumber());
        student_Name.setText(event.getStr_student_Firstname());
        student_Class.setText(event.getStr_selectedClass());
        student_Section.setText(event.getStr_studentSection());
        Glide.with(getActivity()).load(event.studentProfilePicture).into(student_ProfileImage);
        EventBus.getDefault().removeAllStickyEvents();
    }


}
