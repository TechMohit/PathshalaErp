package in.varadhismartek.pathshalaerp.AddStudent;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.varadhismartek.pathshalaerp.CircleImageView;
import in.varadhismartek.pathshalaerp.Constant;
import in.varadhismartek.pathshalaerp.MainActivity;
import in.varadhismartek.pathshalaerp.R;
import in.varadhismartek.pathshalaerp.ValidationViews;


/**
 * A simple {@link Fragment} subclass.
 */
public class FacilityInfoFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    SwitchCompat tbuttonTransport, tbuttonFacility;
    EditText showParentAddress;
    DatabaseReference mRef1, mRef2;
    Spinner routespinner,stopSpinner;
    CheckBox transport_Fees_Checkbox,selectedTransportFeesCheckbox;
    TextView totalFeesTextView,stop_numberTextView , transport_feesTextView,stopSchoolDistance;
    Button showRoute;
    String latLngParentAddress;
    DatabaseReference mRef;
    ArrayList<Float> sortDistance = new ArrayList<>();
    TextView selectedStopName , selectedStopFees , selectedStopRouteNumber, selectedStopDistanceTextView;
    ProgressBar progressBarFac;
    private DatabaseReference studentSnapShotRef;
    private String studentClass;
    private String studentSection;
    private String studentFName;
    private String studentprofilePicture;

    Activity parentActivity =null;





    //Map<String, TranportLatLongDataModel> newMapValue;
    ArrayList<String> dataItemArrayList;
    ArrayList<String> dataItemFeeMonthlyArrayList;
    ArrayList<String> dataItemFeeWeeklyArrayList;


    GridLayout add_Linear_Layout;
    LinearLayout add_linear_layoutForItems;
    int totalFees = 0;
    int childcountValue;
    DatabaseReference db_reference;
    LinearLayout linearLayout_ForTransport_Facility;
    String address;
    String transportCharge = null;
    ImageView addressChage;

    TextView nearestStopName;
    TextView stopDistance;
    EditText changedAddress,changedPincode ,changedCity, changedState, changedCountry;
    DatabaseReference mref1 = FirebaseDatabase.getInstance().getReference("School/SchoolId/Student_Registration");

    DatabaseReference parentAddressRef = FirebaseDatabase.getInstance().getReference("School/SchoolId/Student_Registration");
    private Button changedAddressSave;
    TranportLatLongDataModel tranportLatLongDataModel;


    Dialog dialog = null;
    private DatabaseReference mref;
    private String foodfacilityStatus;
    private String transportfacilityStatus;
    private DatabaseReference lastIdref;
    private CircleImageView student_ProfileImage;
    private TextView student_Class;
    private TextView student_Section;
    private TextView student_Name;
    ArrayList<String> stdentReg;
    private int address_details_status=1;

    //addressChage.setOnClickListener(this);
    TextView student_RegistrationNumber;
    String studentRegistrationNumber;

    public FacilityInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_facility_info, container, false);
        parentActivity=(Activity) v.getContext();

        mref = FirebaseDatabase.getInstance().getReference("School/SchoolId/Barriers");
        studentSnapShotRef = FirebaseDatabase.getInstance().getReference("School/SchoolId/Student_Details_SnapShot");
        getFacilityStatus();
        getParentAddressFromFirebase();
        getAllTransportDetailfromFirebse();
        getStudentDetailsFromFirebase();

        showRoute = v.findViewById(R.id.showRouteButton);
        showRoute.setOnClickListener(this);
        stdentReg = new ArrayList<>();


        setHasOptionsMenu(true);

        progressBarFac = v.findViewById(R.id.progressBarFacilty);

        //progressBarFac.setVisibility(View.VISIBLE);

        routespinner = v.findViewById(R.id.spinnerForRoute);
        stopSpinner = v.findViewById(R.id.spinnerForStops);

        //--------------Total fees textView for food facility--------------------------------------

        totalFeesTextView = v.findViewById(R.id.textShowTotal);

        stopDistance = v.findViewById(R.id.stopDistance);

        stop_numberTextView = v.findViewById(R.id.stopNumber);
        transport_feesTextView = v.findViewById(R.id.transportCharge);

        nearestStopName = v.findViewById(R.id.stopName);

        stopSchoolDistance = v.findViewById(R.id.stopToSchoolDistance);
        //getLastAddedStudentInfo();



        transport_Fees_Checkbox = v.findViewById(R.id.transportFeesCheckboxOption1);

        addressChage  = v.findViewById(R.id.editAddress);
        student_RegistrationNumber            = v.findViewById(R.id.studentRegistartionNumber);
        student_Name                          = v.findViewById(R.id.studentName);
        student_ProfileImage                  = v.findViewById(R.id.studentProfileImage);
        student_Class                         = v.findViewById(R.id.studentClass);
        student_Section                       = v.findViewById(R.id.studentSection);

        //addressChage.setOnClickListener(this);
        //-----------------ArrayList of All items inside a food facility----------------------------

        dataItemArrayList = new ArrayList<>();

        //-----------------ArrayList of All items monthly feeses------------------------------------

        dataItemFeeMonthlyArrayList = new ArrayList<>();

        //---------------ArrayList of all items weekly fees-----------------------------------------
        dataItemFeeWeeklyArrayList = new ArrayList<>();


        //---------------Linear layout for showing all food facility(Breakfast,Snacks,Lunch)--------
        add_Linear_Layout = v.findViewById(R.id.addLinearlayout);


        //------------Linear Layout for showing all facility with checkbox--------------------------

        add_linear_layoutForItems = v.findViewById(R.id.addLinearlayout1);

        //------------------parent changed address in transport facility----------------------------
        //sendChangedAdress = v.findViewById(R.id.sendChangedAddress);


        //-------------Linear Layout for showing facility of transport------------------------------
        linearLayout_ForTransport_Facility = v.findViewById(R.id.linearLayoutForTransportFacility);


        //---------------Show parent current Address-----------------------------------------------
        showParentAddress = v.findViewById(R.id.parentAddress);



        selectedStopName = v.findViewById(R.id.selectedStopTextView);
        selectedStopFees = v.findViewById(R.id.selectedStopTransportCharge);
        selectedStopRouteNumber = v.findViewById(R.id.selectedStopNumber);
        selectedTransportFeesCheckbox = v.findViewById(R.id.checkboxSelectedRouteAddressOption2);
        selectedStopDistanceTextView = v.findViewById(R.id.selectedStopDistance);

        addressChage.setOnClickListener(this);

        // sortDistance = new ArrayList<>();

        //---------Refrence for going student_registration
        db_reference = FirebaseDatabase.getInstance().getReference("School/SchoolId/Student_Registration");


        //Refrence for getting menu from Canteen----------------------------------------------------
        mRef1 = FirebaseDatabase.getInstance().getReference("School/SchoolId/Canteen/Menu");


        //Reference for getting Admission number from Admission module------------------------------
        mRef2 = FirebaseDatabase.getInstance().getReference("School/SchoolId/Admissions");


        //----button ON/OFF for transportaion facility---------------------------------------------
        tbuttonTransport = v.findViewById(R.id.transportFacilityToggle);
        tbuttonFacility = v.findViewById(R.id.foodFacilityToggle);


        tbuttonTransport.setOnCheckedChangeListener(this);
        tbuttonFacility.setOnCheckedChangeListener(this);



        routespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){

                    //on selecting route from spinner we are calling one method there in this meethod
                    //we will fetch alll stops names which are in that route
                    loadRouteName(parent.getItemAtPosition(position).toString());

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        stopSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> adapterView, View view, final int i, long l) {

                if(i!=0){


                    //selecting the particular stops name we are preparing Aysnctask that will do
                    //perform parent current address and parent choosed address ...it will give
                    //distance between parent address and parent choosed address.
                    //we are also setting the selected route stops number and charge to that stop.

                    new AsyncTaskParseJson(showParentAddress.getText().toString(),  adapterView.getItemAtPosition(i).toString()).execute();
                    selectedStopName.setText(""+adapterView.getItemAtPosition(i).toString());
                    selectedStopRouteNumber.setText(""+i);
                    selectedStopFees.setText(""+Constant.stopfeesArrayList.get(i));


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        selectedTransportFeesCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedTransportFeesCheckbox.isChecked()){
                    transport_Fees_Checkbox.setChecked(false);
                }
            }
        });



        //if select 1, we will store their default nearest address and charges for
        //that stop.
        transport_Fees_Checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(transport_Fees_Checkbox.isChecked()){
                    selectedTransportFeesCheckbox.setChecked(false);
                }
            }
        });




        return v;

    }



    private void getFacilityStatus() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Getting data please wait...");
        progressDialog.show();
        mref.child("Admission_Barriers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot postSnapShotA : dataSnapshot.getChildren()){
                    for(DataSnapshot postSnapShotB : postSnapShotA.getChildren()){
                        Log.d("postSHH", ""+postSnapShotB.getKey());
                        if(postSnapShotB.getKey().equals("food_facility")){
                            foodfacilityStatus = postSnapShotB.getValue().toString();
                            Log.d("ff", ""+postSnapShotB.getValue().toString());
                            /*if(postSnapShotB.getValue().toString().equals("false")){
                                tbuttonFacility.setClickable(false);

                            }*/

                        }

                        if(postSnapShotB.getKey().equals("transport_facility")){
                            transportfacilityStatus = postSnapShotB.getValue().toString();
                            Log.d("tf", ""+postSnapShotB.getValue().toString());
                            /*if(postSnapShotB.getValue().toString().equals("false")){
                                tbuttonTransport.setClickable(false);
                            }*/

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

    private void loadRouteName(String routeNumber) {
        mRef = FirebaseDatabase.getInstance().getReference("School/SchoolId/Transport");

        mRef.child("Routes").child(routeNumber).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> stopName = new ArrayList<>();
                stopName.add(0, "--Stop name--");
                Constant.stopfeesArrayList = new ArrayList<>();
                Constant.stopfeesArrayList.add(0,"null");
                for(DataSnapshot snapshot1 : dataSnapshot.getChildren()){
                    Log.d("A1", ""+snapshot1.getKey());

                    for(DataSnapshot snapshot2 : snapshot1.getChildren()){
                        Log.d("A2", ""+snapshot2.getKey());

                        for(DataSnapshot snapshot3 : snapshot2.getChildren()) {
                            Log.d("A3", "" + snapshot3.getKey());

                            String a = (String) snapshot3.child("stop_name").getValue();
                            stopName.add(a);

                            String b = (String) snapshot3.child("trasnport_fees").getValue();
                            Constant.stopfeesArrayList.add(b);

                        }
                        //we are setting stop names to the spinner route name.
                        ArrayAdapter<String> routeNameAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, stopName);
                        routeNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        stopSpinner.setAdapter(routeNameAdapter);

                    }

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getAllTransportDetailfromFirebse() {

        mRef = FirebaseDatabase.getInstance().getReference("School/SchoolId/Transport");
        mRef.child("Routes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("snapahot0", ""+dataSnapshot.getKey());
                //get the route gere
                final List<String> routeNumberList = new ArrayList<String>();
                routeNumberList.add(0, "--Select route--");

                for(DataSnapshot snapshot1 : dataSnapshot.getChildren()){
                    Log.d("snapahot1", ""+snapshot1.getKey());
                    String routeNumber = snapshot1.getKey();
                    routeNumberList.add(routeNumber);

                    TranportLatLongDataModel tpModel = snapshot1.getValue(TranportLatLongDataModel.class);


                    for(DataSnapshot snapshot2 : snapshot1.getChildren()){
                        Log.d("snapahot2", ""+snapshot2.getKey());


                        //go inside stoplist
                        for(DataSnapshot snapshot3 : snapshot2.getChildren()) {
                            Log.d("snapahot3", "" + snapshot3.getKey());

                            //take all latlong and address from all stops
                            Constant.h2c = new HashMap<>();


                            for(DataSnapshot snapshot4 : snapshot3.getChildren()) {
                                Log.d("snapshot4", ""+snapshot4.getKey());


                                TranportLatLongDataModel tranportLatLongDataModel = snapshot4.getValue(TranportLatLongDataModel.class);
                                String latLong = tranportLatLongDataModel.getLatLng();
                                String stopName = tranportLatLongDataModel.getStop_name();
                                String ff = tranportLatLongDataModel.getTrasnport_fees();






                                //Store lat long and stop name to arrayList
                                Constant.tranportLatLongDataModelArrayList.add(new TranportLatLongDataModel(snapshot1.getKey(),latLong,stopName,tranportLatLongDataModel.getStop_number(),tranportLatLongDataModel.getTrasnport_fees(),tranportLatLongDataModel.getDest(),tranportLatLongDataModel.getStop_distance(),
                                        tpModel.getBusno() , tpModel.getDriver_name() , tpModel.getDriver_mobno(), tpModel.getTrspt_mgr_name() , tpModel.getTrspt_mgr_mobno()));

                                //Info.h1c.put(snapshot1.getKey()+"\n" , Info.h2c);


                            }


                        }

                    }

                }


                //setting route number into route spinner
                Log.d("routSize", ""+routeNumberList.size());
                if(routeNumberList.size()>0){
                    ArrayAdapter<String> routeNumberAdapter = new ArrayAdapter<String>(parentActivity, android.R.layout.simple_spinner_item, routeNumberList);
                    routeNumberAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    routespinner.setAdapter(routeNumberAdapter);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getParentAddressFromFirebase() {
        parentAddressRef.child(Constant.FINAL_REGISTRATION_ID).
                child("address_information").child("parent_present_address_info").
                addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()){
                    Log.d("datasssg", ""+postSnapShot.getKey());
                    if(postSnapShot.getKey().equals("completeAddress")){
                        String addressparent = (String) postSnapShot.getValue();
                        showParentAddress.setText(addressparent);
                        progressBarFac.setVisibility(View.GONE);
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_send, menu);
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
                            if(postSnapoShotA.getKey().equalsIgnoreCase("facility_information")){
                                Toast.makeText(getActivity(), "Page already filled", Toast.LENGTH_SHORT).show();
                                address_details_status=0;
                            }

                        }
                        if(address_details_status==1){
                            Toast.makeText(getActivity(), "facility details not exists, please fill details", Toast.LENGTH_SHORT).show();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.send:

                if(!student_RegistrationNumber.getText().toString().equals("Registration Number")) {

                    lastIdref = FirebaseDatabase.getInstance().getReference("School/SchoolId/Barriers/Registartion_Ids");


                    final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage("Sending data...");

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            totalFees=0;
                            int childcount = add_linear_layoutForItems.getChildCount();

                            for(int i = 0 ; i <childcount ; i++){
                                View addView = add_linear_layoutForItems.getChildAt(i);
                                final TextView text = addView.findViewById(R.id.selectedFoodTextView);
                                TextView textView1 = addView.findViewById(R.id.selectedFoodFeesTextView);
                                final int f = Integer.parseInt(textView1.getText().toString());
                                totalFees = totalFees+f;
                                db_reference.child(Constant.FINAL_REGISTRATION_ID).child("facility_information").child("food_facility").child(text.getText().toString()).setValue(textView1.getText().toString());
                                db_reference.child(Constant.FINAL_REGISTRATION_ID).child("facility_information").child("food_facility").child("Total pay").setValue(""+totalFees);
                            }

                            if(transport_Fees_Checkbox.isChecked() ){
                                db_reference.child(Constant.FINAL_REGISTRATION_ID).child("facility_information").child("tranport_facility").child("transport_charges").setValue(transport_feesTextView.getText().toString());
                                db_reference.child(Constant.FINAL_REGISTRATION_ID).child("facility_information").child("tranport_facility").child("selected_stop").setValue(nearestStopName.getText().toString());
                                db_reference.child(Constant.FINAL_REGISTRATION_ID).child("facility_information").child("tranport_facility").child("selected_stopDistance").setValue(stopDistance.getText().toString());


                            }
                            else if(selectedTransportFeesCheckbox.isChecked()) {
                                db_reference.child(Constant.FINAL_REGISTRATION_ID).child("facility_information").child("tranport_facility").child("transport_charges").setValue(selectedStopFees.getText().toString());
                                db_reference.child(Constant.FINAL_REGISTRATION_ID).child("facility_information").child("tranport_facility").child("selected_stop").setValue(selectedStopName.getText().toString());
                                db_reference.child(Constant.FINAL_REGISTRATION_ID).child("facility_information").child("tranport_facility").child("selected_stopDistance").setValue(stopDistance.getText().toString());


                            }

                            if(!Constant.REGISTRATION_CURRENT_TEMP_ID.equals(Constant.FINAL_REGISTRATION_ID)){
                                //lastIdref.child("Current_Registration_Id").setValue(Constant.FINAL_REGISTRATION_ID);
                                Toast.makeText(getActivity(), "Registration Finished...", Toast.LENGTH_SHORT).show();
                                Intent in = new Intent(getActivity() , MainActivity.class);
                                startActivity(in);
                                progressDialog.dismiss();
                            }
                            else {

                                lastIdref.child("Current_Registration_Id").setValue(Constant.FINAL_REGISTRATION_ID);
                                Toast.makeText(getActivity(), "Registration Finished...", Toast.LENGTH_SHORT).show();
                                Intent in = new Intent(getActivity() , MainActivity.class);
                                startActivity(in);
                                progressDialog.dismiss();
                            }



                        }
                    },3500);


                    progressDialog.show();
                }
                else {

                    Toast.makeText(getActivity(), "Please select student", Toast.LENGTH_SHORT).show();
                }



                //getting all data from textview and sending to the firebase------------------------

               break;

            /*case R.id.action_search:


                break;*/
        }

        return true;

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.editAddress:
                dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.changed_parent_address_ui);
                dialog.setTitle("Choose your option..");
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

                //below are dialog views....four edittext and 1 button
                changedAddress = dialog.findViewById(R.id.changed_present_address);
                changedPincode = dialog.findViewById(R.id.changed_pincode);
                changedCity = dialog.findViewById(R.id.changed_city);
                changedState = dialog.findViewById(R.id.changed_state);
                changedCountry = dialog.findViewById(R.id.changed_country);
                changedAddressSave = dialog.findViewById(R.id.btnSaveChangedAddress);

                changedPincode.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        int length = charSequence.length();
                        Log.d("textLength", ""+length);
                        if(length==6){
                            Log.d("textLength", ""+charSequence);

                            //calling asynctask for getting api details
                            new AsynchTaskForStateAndCity(charSequence.toString(), Constant.PERMANENT_PINCODE).execute();

                        }

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

                changedAddressSave.setOnClickListener(this);
                dialog.show();

                break;


            case R.id.btnSaveChangedAddress:

                if(!ValidationViews.EditTextNullValidate(changedAddress)){
                    return;
                }
                else if(!ValidationViews.EditTextNullValidate(changedPincode)){
                    return;
                }

                else if(!ValidationViews.EditTextPinCodeValidate(changedPincode)){
                    return;
                }
                else if(!ValidationViews.EditTextNullValidate(changedCity)){
                    return;
                }
                else if(!ValidationViews.EditTextNullValidate(changedState)){
                    return;
                }
                else if(!ValidationViews.EditTextNullValidate(changedCountry)){
                    return;
                }
                else {


                    String str_changedStreet = changedAddress.getText().toString();
                    String str_changedPincode = changedPincode.getText().toString();
                    String str_changedCity = changedCity.getText().toString();
                    String str_changedState = changedState.getText().toString();
                    String str_changedCountry = changedCountry.getText().toString();
                    String completeChangedAddress = str_changedStreet+","
                            +str_changedCity+", "+str_changedState+", "+str_changedPincode;

                    parentAddressRef.child(Constant.FINAL_REGISTRATION_ID).
                            child("address_information").child("parent_present_address_info").
                            child("completeAddress").setValue(completeChangedAddress);

                    parentAddressRef.child(Constant.FINAL_REGISTRATION_ID).
                            child("address_information").child("parent_present_address_info")
                            .child("presentAddressCity").setValue(str_changedCity);

                    parentAddressRef.child(Constant.FINAL_REGISTRATION_ID).
                            child("address_information").child("parent_present_address_info").
                            child("presentAddressCountry").setValue(str_changedCountry);

                    parentAddressRef.child(Constant.FINAL_REGISTRATION_ID).
                            child("address_information").child("parent_present_address_info").
                            child("presentAddressPincode").setValue(str_changedPincode);

                    parentAddressRef.child(Constant.FINAL_REGISTRATION_ID).
                            child("address_information").child("parent_present_address_info").
                            child("presentAddressState").setValue(str_changedState);

                    parentAddressRef.child(Constant.FINAL_REGISTRATION_ID).
                            child("address_information").child("parent_present_address_info").
                            child("presentAddressStreet").setValue(str_changedStreet);

                    dialog.dismiss();
                    Toast.makeText(getActivity(), "New Address saved!", Toast.LENGTH_SHORT).show();

                }


                break;

            case R.id.showRouteButton:
                Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();
                if(!showParentAddress.getText().toString().isEmpty()){
                    String currentAddress = showParentAddress.getText().toString();

                /*Here we are calling getLocationFromAdreess method that will give
                * LatLong of parent address*/
                    address = getLocationFromAddress(currentAddress);
                     /*if parent latlong is not null that time we will
                     * call one more method there we will match the latlong
                     * of parent to the latlong stored in firebase*/
                    if(address!=null)
                    {

                        //here we are sending parentLatLong to new method
                        matchLatLongShowAddress(address);

                    }
                    else {
                        Toast.makeText(getActivity(), "Address not found", Toast.LENGTH_SHORT).show();
                    }

                }
                break;
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.transportFacilityToggle:
                if(transportfacilityStatus.equals("false")){
                    tbuttonTransport.setClickable(false);
                    tbuttonTransport.setChecked(false);

                    dialogForServiceNotAvaialble();
                }

                else {

                    if(tbuttonTransport.isChecked())
                        linearLayout_ForTransport_Facility.setVisibility(View.VISIBLE);

                    else
                        linearLayout_ForTransport_Facility.setVisibility(View.GONE);

                }


                break;


            case R.id.foodFacilityToggle:
                if(foodfacilityStatus.equals("false")){
                    tbuttonFacility.setClickable(false);
                    tbuttonFacility.setChecked(false);
                    dialogForServiceNotAvaialble();

                }

                else {
                    if (tbuttonFacility.isChecked()) {
                        totalFeesTextView.setVisibility(View.VISIBLE);

                        //linearLayoutWeeklyMonthlyFees.setVisibility(View.VISIBLE);
                        //clearly all arraylist for storing again---------------------------------------
                        dataItemArrayList.clear();
                        dataItemFeeMonthlyArrayList.clear();
                        dataItemFeeWeeklyArrayList.clear();

                        mRef1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    Log.d("keys", "" + postSnapshot.getKey());
                                    //ArrayList for adding all food facilty from firebase---------------
                                    dataItemArrayList.add((String) postSnapshot.getKey());
                                    Log.d("arraSi", "" + dataItemArrayList.size());
                                    for (DataSnapshot postSnapshot1 : postSnapshot.getChildren()) {
                                        if (postSnapshot1.getKey().equals("monthly_charge")) {
                                            Log.d("ba", "" + postSnapshot1.getValue());

                                            //Arraylist for adding facility prica monthly
                                            dataItemFeeMonthlyArrayList.add((String) postSnapshot1.getValue());
                                        }
                                        if (postSnapshot1.getKey().equals("daily_charge")) {
                                            Log.d("ssss", "" + postSnapshot1.getValue());

                                            //Arraylsit for adding fees of facilitty weekly-------------
                                            dataItemFeeWeeklyArrayList.add((String) postSnapshot1.getValue());
                                        }

                                    }
                                }

                                //inflating the layout based on the facilies---------------------------
                                for (int i = 0; i < dataItemArrayList.size(); i++) {
                                    LayoutInflater layoutInflater =
                                            (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    final View addView = layoutInflater.inflate(R.layout.dynamic_buttos_row, null);
                                    final Button textOut = addView.findViewById(R.id.itemNameCheckbox);

                                    //setting all facilty in button------------------------------------
                                    textOut.setText(dataItemArrayList.get(i));
                                    textOut.setTextColor(Color.WHITE);

                                    //-----------getting fees from arraylist monthly-------------------
                                    final String fees = dataItemFeeMonthlyArrayList.get(i);

                                    //------------click listner for food facility listner---------------

                                    //Onclick button snacks/lunch or anything one row will infalte
                                    //their we will show food facilty items with feeses-----------------

                                /*when clicking on particular food facility one row will come with
                                *  two textview and one delete button
                                *
                                *  on clicking of delete button that row will delete and again on clciking that particular button of facility
                                *  row will come
                                *  Their green button means row alreay added if you delete row button
                                *  color will change*/
                                    textOut.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            textOut.setBackgroundColor(getResources().getColor(R.color.green));
                                            textOut.setEnabled(false);
                                            LayoutInflater layoutInflater1 =
                                                    (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                            final View addView1 = layoutInflater1.inflate(R.layout.row_for_food_selecting, null);

                                            // here two textView for showing facilty name and feeses--------
                                            //Button for delete that facilty----------------------------
                                            final TextView textViewSelectedItemNAme = addView1.findViewById(R.id.selectedFoodTextView);
                                            final TextView textViewSelectedItemFees = addView1.findViewById(R.id.selectedFoodFeesTextView);
                                            final ImageView imageView = addView1.findViewById(R.id.deleteItem);
                                            textViewSelectedItemNAme.setText(textOut.getText().toString() + " Fees");
                                            textViewSelectedItemFees.setText(fees);

                                            //delete row of food facilty item---------------------------
                                            imageView.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    textOut.setEnabled(true);
                                                    textOut.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                                    ((LinearLayout) addView1.getParent()).removeView(addView1);

                                                    totalFees = 0;
                                                    int childcount = add_linear_layoutForItems.getChildCount();
                                                    childcountValue = childcount;
                                                    for (int j = 0; j < childcount; j++) {
                                                        View addView2 = add_linear_layoutForItems.getChildAt(j);
                                                        TextView text = addView2.findViewById(R.id.selectedFoodFeesTextView);
                                                        int f = Integer.parseInt(text.getText().toString());
                                                        totalFees = totalFees + f;

                                                    }

                                                    totalFeesTextView.setText("Food facility monthly fees is: " + totalFees);
                                                }
                                            });


                                            //add all views(Row) to the linearLayout in Xml-------------
                                            add_linear_layoutForItems.addView(addView1, 0);


                                            //---------calculate fees total of selected facilty---------
                                            totalFees = 0;
                                            int childcount = add_linear_layoutForItems.getChildCount();
                                            childcountValue = childcount;
                                            for (int j = 0; j < childcount; j++) {
                                                View addView2 = add_linear_layoutForItems.getChildAt(j);
                                                TextView text = addView2.findViewById(R.id.selectedFoodFeesTextView);
                                                int f = Integer.parseInt(text.getText().toString());
                                                totalFees = totalFees + f;
                                            }
                                            totalFeesTextView.setText("Food facility monthly fees is: " + totalFees);

                                        }

                                    });


                                    //----------Add food facility buttons to linear layout Xml----------
                                    add_Linear_Layout.addView(addView, 0);

                                }


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                    } else {

                        //----- if transport facilty ON/OFF buttton becomes OFF-----------------------
                        //Clear all data
                        totalFeesTextView.setText(null);
                        totalFeesTextView.setVisibility(View.GONE);
                        add_Linear_Layout.removeAllViews();
                        add_linear_layoutForItems.removeAllViews();


                    }
                }


                break;





        }
    }

    private void dialogForServiceNotAvaialble() {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setIcon(R.drawable.ic_sad);
        builder.setTitle("Sorry");
        builder.setMessage("This service is not available to you!");
        AlertDialog alert=builder.create();
        alert.show();


    }

    private void matchLatLongShowAddress(final String address) {

        Log.d("SSJSJ" , address);

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("getting data...please wait");
        progressDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                /*After getting LatLong of parent first we will split lat and Long form
                * then we will store that lot long into Location Class*/

                if (address != null) {
                    //here we are splitting the latlong into two parts
                    String[] parts = address.split(",");
                    String latString = parts[0];
                    String longString = parts[1];
                    //Toast.makeText(getActivity(), ""+latString+"ddd"+longString, Toast.LENGTH_SHORT).show();

                    //here we re converting the splitted lat long into Locations
                    Location parentLocation = new Location("");
                    parentLocation.setLatitude(Double.parseDouble(latString));
                    parentLocation.setLongitude(Double.parseDouble(longString));

                    Location routeLatlong = new Location("");


                    float distanceLeast = Float.MAX_VALUE;
                    String stopName = null;
                    String stopNumber = null;
                    String stopToSchoolDistance = null;
                    String busNumber = null;
                    String driverName = null;
                    String driverNumber = null;
                    String transportMgrName = null;
                    String transportMgrNumber = null;




                    if(sortDistance.size()!=0){
                        sortDistance.clear();
                    }
                    //we are calling this for loop there we will match the current address of parent to the
                    //stored address in transport module
                    for (int i = 0; i < Constant.tranportLatLongDataModelArrayList.size(); i++) {

                        tranportLatLongDataModel = Constant.tranportLatLongDataModelArrayList.get(i);
                        String routeAltlong = tranportLatLongDataModel.getLatLng();
                        //Log.d("Loa", ""+routeAltlong);
                        String[] parts1 = routeAltlong.split(",");


                        /*After getting stored latlong from firebase again we are creating
                        * one more location class and adding Lat long that are coming
                        * from firebase*/
                        routeLatlong.setLatitude(Double.parseDouble(parts1[0]));
                        routeLatlong.setLongitude(Double.parseDouble(parts1[1]));

                        /*There we are getting distance between parent latlong and firebase stored
                        * latlong one by one we will get distance*/
                        float distance = parentLocation.distanceTo(routeLatlong);

                        Log.d("distance", "" + distance / 1000);


                        /*after getting distance we will store that distance to arraylist*/
                        sortDistance.add((float) (distance / 1000));

                        //finally here we are getting the sortest distance

                        Log.d("distan", ""+distanceLeast);

                        if (distanceLeast > sortDistance.get(i)) {

                            //after getting the sortest distance we are storing into string
                            //stop name + stop number + transport charge + distance sortest into string variable
                            stopToSchoolDistance = tranportLatLongDataModel.getStop_distance();

                            stopName = tranportLatLongDataModel.getStop_name();
                            Log.d("sksASDk", ""+tranportLatLongDataModel.getStop_name());


                            stopNumber = tranportLatLongDataModel.getStop_number();

                            busNumber = tranportLatLongDataModel.getBusno();
                            Log.d("busNumber", ""+busNumber);


                            transportCharge = tranportLatLongDataModel.getTrasnport_fees();
                            distanceLeast = sortDistance.get(i);



                        }


                    }


                    /*Here we are settting getting value from firebase to the textview*/
                    stopSchoolDistance.setText(stopToSchoolDistance);
                    nearestStopName.setText(stopName);
                    stopDistance.setText(distanceLeast + " Km");
                    stop_numberTextView.setText(stopNumber);
                    transport_feesTextView.setText(transportCharge);
                    progressDialog.dismiss();

                }
            }
        },5000);

    }

    private String getLocationFromAddress(String currentAddress) {

        Log.d("MYADD", currentAddress);

        Geocoder coder = new Geocoder(getContext());

        try {
            List<Address> geoResults = coder.getFromLocationName(currentAddress, 1);

            Log.d("geoResult", ""+geoResults.size());
            /*while (geoResults.size() == 0) {
                geoResults = coder.getFromLocationName(currentAddress, 1);
            }*/
            if (geoResults.size() !=0) {
                Log.d("geoResult", "Inside if");


                Address addr = geoResults.get(0);
                double parentLat = addr.getLatitude();
                double parentLong = addr.getLongitude();
                Log.d("latlong", ""+parentLat+""+parentLong);
                //latLngParentAddress = new LatLng(addr.getLatitude() , addr.getLongitude());
                latLngParentAddress = String.valueOf(parentLat+","+parentLong);
            }

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        Log.d("geoResult", ""+latLngParentAddress);

        return latLngParentAddress;
    }

    private class AsynchTaskForStateAndCity extends AsyncTask<String , String , String> {
        String pincode;
        String state , city , country;
        String presentPermanentTag;
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());


        public AsynchTaskForStateAndCity(String charSequence, String present_permanent_code) {
            this.pincode = (String) charSequence;
            this.presentPermanentTag = present_permanent_code;


        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("getting data...please wait");
            progressDialog.show();
        }



        @Override
        protected String doInBackground(String... strings) {
            JsonCitySateParser jsonCitySateParser = new JsonCitySateParser();
            JSONObject json = jsonCitySateParser.getJSONFromUrl(pincode);
            Log.d("json", ""+json);

            JSONArray array = null;

            try{
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

            changedCity.setText(city);
            changedState.setText(state);
            changedCountry.setText(country);



        }

    }

    private class AsyncTaskParseJson extends AsyncTask<String, String, String> {
        String parentAddress;
        String stopAddress ;
        String parsedDistance=null;
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());



        public AsyncTaskParseJson(String parentAddress, String stopAddress) {
            this.parentAddress = parentAddress;
            this.stopAddress = stopAddress;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("getting data...please wait");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //tv.setText(parsedDistance);
            selectedStopDistanceTextView.setText(s);
            progressDialog.dismiss();

        }

        @Override
        protected String doInBackground(String... strings) {

            //here we are creating one class there we will get response of tht url
            JsonParser jParser = new JsonParser();

            // below method will send parent address and choosed stop adderess to Jsonparser class
            //there we will create response of that google map distance api link
            JSONObject json = jParser.getJSONFromUrl(parentAddress, stopAddress);
            Log.d("JSON", "" + json);

            // after getting response in JSon form
            //here we will parse the json and will get the distance between those two stops
            JSONArray array = null;
            try {
                array = json.getJSONArray("routes");
                JSONObject routes = array.getJSONObject(0);
                JSONArray legs = routes.getJSONArray("legs");
                JSONObject steps = legs.getJSONObject(0);
                JSONObject distance = steps.getJSONObject("distance");
                parsedDistance = distance.getString("text");
                Log.d("disssss", ""+parsedDistance);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return parsedDistance;

        }


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


   /* @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }*/

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessage(NewStudentRegisterModel event){
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






