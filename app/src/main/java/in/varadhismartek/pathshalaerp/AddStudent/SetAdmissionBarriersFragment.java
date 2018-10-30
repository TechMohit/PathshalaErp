package in.varadhismartek.pathshalaerp.AddStudent;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import in.varadhismartek.pathshalaerp.Constant;
import in.varadhismartek.pathshalaerp.MainActivity;
import in.varadhismartek.pathshalaerp.R;
import in.varadhismartek.pathshalaerp.ValidationViews;


/**
 * A simple {@link Fragment} subclass.
 */
public class SetAdmissionBarriersFragment extends Fragment implements View.OnClickListener {

    Spinner spinnerCustomForFather, spinnerCustomForMother, spinnerCustomForStudent;
    EditText admission_number, percentage_for_admission;
    CheckBox food_facility , transport_facility;
    TextView GuardiansNumber, nextTimeTextView , getadmissionValue;
    Button increment , decrement , submit;
    int minteger = 1;
    String admissionNumber, minPercentageForAdmission,qualification_father, qualification_mother;
    boolean foodRequired = false;
    String food = "false";
    String transport = "false";

    boolean transportaion_required = false;
    int counter;
    String numberOfGuardians;
    String regex = "([A-Za-z]+[0-9]|[0-9]+[A-Za-z])[A-Za-z0-9]*";

    String registrationNumberGet = null;
    double percentage;
    String User = "director";
    final String PREFS_NAME = "GuardianPref";
    SharedPreferences preference;
    private double min=35;
    private double max=100;
    private DatabaseReference lastIdref;
    View v;

    DatabaseReference mRef1 = FirebaseDatabase.getInstance().getReference("School/SchoolId/Barriers");



    public SetAdmissionBarriersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_admission_enquiry, container, false);


        preference = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        spinnerCustomForFather = v.findViewById(R.id.spinnerCustomForFather);
        spinnerCustomForMother = v.findViewById(R.id.spinnerCustomForMother);
        // spinnerCustomForStudent = v.findViewById(R.id.spinnerCustomForStudentQualification);
        admission_number = v.findViewById(R.id.editText_admissionNumber);
        percentage_for_admission = v.findViewById(R.id.editText_percentage_for_admission);
        food_facility = v.findViewById(R.id.checkbox_food_facility);
        transport_facility = v.findViewById(R.id.checkbox_transport_facility);
        GuardiansNumber = v.findViewById(R.id.numberOfGuardians);
        increment = v.findViewById(R.id.button_increase_count);
        decrement = v.findViewById(R.id.button_decrease_count);
        submit = v.findViewById(R.id.btnNext);
        lastIdref = FirebaseDatabase.getInstance().getReference("School/SchoolId/Barriers/Registartion_Ids");



        admission_number.setFilters(new InputFilter[] {
                new InputFilter() {
                    public CharSequence filter(CharSequence src, int start,
                                               int end, Spanned dst, int dstart, int dend) {
                        if(src.equals("")){ // for backspace
                            return src;
                        }
                        if(src.toString().matches("[a-zA-Z 0-9]+")){
                            return src;
                        }
                        return "";
                    }
                }
        });



        percentage_for_admission.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                try {
                    Log.d("Percentage", "input: " + editable);

                    if(editable.toString().startsWith(".")){
                        editable.replace(0, editable.length(), "");

                    }
                    else if(editable.toString().startsWith("0")){
                        editable.replace(0, editable.length(), "");

                    }
                    else if(Double.parseDouble(editable.toString())<min){
                        percentage_for_admission.setError("minimum 35% required");

                    }


                    else if(Double.parseDouble(editable.toString())>100){
                        editable.replace(0, editable.length(), "100");

                    }

                }
                catch(NumberFormatException nfe){
                    nfe.printStackTrace();
                }


            }
        });


        SharedPreferences sp = getActivity().getSharedPreferences("values",0);
        counter = sp.getInt("counter",0);
        if(counter==0) {
            SharedPreferences.Editor et = sp.edit();
            et.putInt("counter", 1);
            et.apply();

        }
        else {


        }

        decrement.setEnabled(true);
        decrement.setOnClickListener(this);
        increment.setOnClickListener(this);
        submit.setOnClickListener(this);
        food_facility.setOnClickListener(this);
        transport_facility.setOnClickListener(this);
        initSpinnerForFather();
        initSpinnerForMother();

        return v;

    }


    private void initSpinnerForMother() {
        ArrayList<String> qualificationArrayList = new ArrayList<String>();
        qualificationArrayList.add("Select");
        qualificationArrayList.add("Not mandatory");
        qualificationArrayList.add("Under Graduate");
        qualificationArrayList.add("Graduate");
        qualificationArrayList.add("Post Graduate");
        CustomSpinnerAdapter customSpinnerAdapter=new CustomSpinnerAdapter(getActivity(),qualificationArrayList);
        spinnerCustomForMother.setAdapter(customSpinnerAdapter);
        spinnerCustomForMother.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                qualification_mother = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initSpinnerForFather() {
        ArrayList<String> qualificationArrayList = new ArrayList<String>();
        qualificationArrayList.add("Select");
        qualificationArrayList.add("Not mandatory");
        qualificationArrayList.add("Under Graduate");
        qualificationArrayList.add("Graduate");
        qualificationArrayList.add("Post Graduate");
        CustomSpinnerAdapter customSpinnerAdapter=new CustomSpinnerAdapter(getActivity(),qualificationArrayList);
        spinnerCustomForFather.setAdapter(customSpinnerAdapter);
        spinnerCustomForFather.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                qualification_father = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.button_decrease_count:

                if(minteger==1){
                    decrement.setEnabled(false);

                }

                else{
                    minteger = minteger-1;
                    increment.setEnabled(true);
                    displayNumberOfGuardian(minteger);
                }


                break;

            case R.id.button_increase_count:
                minteger = minteger+1;
                decrement.setEnabled(true);
                displayNumberOfGuardian(minteger);
                if(minteger==3){
                    increment.setEnabled(false);
                }
                break;


            case R.id.checkbox_food_facility:
                if(food_facility.isChecked()){
                    foodRequired = true;
                    food = "true";
                }

                break;

            case R.id.checkbox_transport_facility:
                if(transport_facility.isChecked()){
                    transportaion_required = true;
                    transport = "true";
                }


                break;

            case R.id.btnNext:
                if(!ValidationViews.EditTextNullValidate(admission_number , percentage_for_admission)){
                    ValidationViews.showToast(getActivity() , "Please fill detail");
                    return;
                }

                else if (!admission_number.getText().toString().trim().matches(regex)) {
                    admission_number.setError("ABC000");
                    return;
                }
                else if(!admission_number.getText().toString().trim().matches("^.+?\\d$")){
                    admission_number.setError("ABC000");
                    return;
                }

                else if(spinnerCustomForFather.getSelectedItemPosition()==0){
                    Toast.makeText(getActivity(), "Please select father qualification ", Toast.LENGTH_SHORT).show();
                    return;
                }

               else if(spinnerCustomForMother.getSelectedItemPosition()==0){
                    Toast.makeText(getActivity(), "Please select mother qualification ", Toast.LENGTH_SHORT).show();
                    return;

                }
                else if(Double.parseDouble(percentage_for_admission.getText().toString())<35){

                Toast.makeText(getActivity(), "Please check percentage", Toast.LENGTH_SHORT).show();
                return;

            }

            else {

                //sendingValues(admission_number.getText().toString(),percentage_for_admission.getText().toString(),qualification_father, qualification_mother,foodRequired,transportaion_required,GuardiansNumber.getText().toString() );

                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Storing data please wait...");
                progressDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        admissionNumber = admission_number.getText().toString().trim();
                        minPercentageForAdmission= String.valueOf(percentage_for_admission.getText().toString());
                        numberOfGuardians = String.valueOf(minteger);
                        AdmissionEnquiryModel admissionEnquiryModel = new AdmissionEnquiryModel(minPercentageForAdmission,qualification_father, qualification_mother, food, transport, numberOfGuardians);
                        mRef1.child("Admission_Barriers").setValue(null);
                        mRef1.child("Admission_Barriers").child(admissionNumber).setValue(admissionEnquiryModel);
                        lastIdref.child("Current_Registration_Id").setValue(admissionNumber);
                        lastIdref.child("Default_Registration_Id").setValue(admissionNumber);
                        lastIdref.child("Start_Registration_Id").setValue(admissionNumber);




                        Toast.makeText(getActivity(), "Data send successfully", Toast.LENGTH_SHORT).show();
                        admission_number.setText("");
                        percentage_for_admission.setText("");
                        food_facility.setChecked(false);
                        transport_facility.setChecked(false);
                        spinnerCustomForFather.setSelection(0);
                        spinnerCustomForMother.setSelection(0);
                        MainActivity mainActivity = (MainActivity) getActivity();
                        mainActivity.loadFragment(Constant.FRAGMENT_LANDING , null);
                        progressDialog.dismiss();

                    }
                },5000);




                }


                break;
        }

    }

    private void sendingValues(String adm_number, String percentage_admission, String qualification_father,String qualification_mother, boolean foodRequired, boolean transportaion_required, String numberOfGuardians) {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Storing data please wait...");
        progressDialog.show();
        Log.d("allDetails", "RegistrationNumbrer "+admission_number.getText().toString()+"\n %Admission "+percentage_for_admission.getText().toString()+"\n FatherQual "+
                qualification_father+"\n MotherQua "+qualification_mother+"\n Food facility "+foodRequired+"\n Trans Facility "+transportaion_required+"\n Guardians"+
                GuardiansNumber.getText().toString());



    }


    private void displayNumberOfGuardian(int minteger) {
        GuardiansNumber.setText(""+minteger);
        }



}
