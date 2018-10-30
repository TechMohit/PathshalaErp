package in.varadhismartek.pathshalaerp.AddStudent;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import in.varadhismartek.pathshalaerp.CircleImageView;
import in.varadhismartek.pathshalaerp.Constant;
import in.varadhismartek.pathshalaerp.R;
import in.varadhismartek.pathshalaerp.ValidationViews;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudentInfoFragment extends Fragment implements View.OnClickListener {


    ImageView calendarForDob, studentIdFrontImageClick,
              studentIdBackImageClick, studentBirthCertificateFrontImageClick,
              studentBirthCertificateBackImageClick,studentTCFrontImageClick,
              StudentTCBackImageClick, studentPrvsMarkFrontClick,
              studentPrvsMarkBackClick ,studedentIdImageFront,studentIdImageBack,
              studentBcFront,studentBcBack,studentTcFront, studentTcBack,
              studentPrvsMarkFront,studentPrvsMarkBack,btnDownForAcademics,
              btnUpForAcademics , btnUpForMedical , btnDownForMedical;

    Spinner classesSpinner,sectionSpinner,communitySpinner,
            spinnerForHeight,spinnerForWeight,spinnerClassSibling, spinnerSectionSibling;


    Button genderboy, gendergirl, genderother, schoolboard_sslc
           ,schoolboard_cbse , schoolboard_icse, schoolboard_other,
            eyeColorVisionYes , eyeColorVisionNo , wearGlassYes , wearGlassNo,
            hearingbtnYes , hearingBtnNo, siblings, siblingdialogCancel , siblingDialogsubmit;

    EditText student_Firstname,student_Middlename, student_Lastname,
             student_date_of_birth , Student_religion, student_nationality ,
             student_adhar_number , student_language_known,
             student_mother_toung_language , student_birth_certfct_number ,
             student_previous_school_name , student_previous_school_address,
             student_previous_school_contact_number, student_transfer_cerfct_number,
             student_previous_school_marksheet_number,
             student_previous_class_percentage,student_caste,student_subcast,
             student_previousClass,student_height, student_weight, left_eyeVision ,
             right_eyeVision , comments_onEyes, comments_onTeeth ,
             right_earFrequency , left_earFrequency,
             comments_onEar , commentsOnDisability;


     String str_selectedClass, str_student_Firstname, str_student_Middlenamne,
            str_student_Lastname , str_Student_religion ,
            str_student_nationality , str_student_Id_number,
            str_student_language_known , str_student_mother_toung_language ,
            str_student_birth_certfct_number,str_student_community,
            str_student_caste, str_student_subcaste,StudentTeethProblem,
            disabilityProblem,str_student_DOB,studentSection,
            pathProfile;

    public static String imageFilePath;//Made it static as need to override the original image with compressed image.


     String str_student_height = "0",  str_student_weight="0" ,
            str_left_eyeVision="0" ,   str_right_eyeVision="0",
            str_comments_onEyes="NA",  str_comments_onTeeth = "NA" ,
            str_right_earFrequency="0",str_left_earFrequency="0" ,
            str_comments_onEar="NA" , str_commentsOnDisability="NA",
            str_student_previous_school_name = "NA",
            str_student_previous_school_address = "NA",
            str_student_previous_school_contact_number = "NA",
            str_student_transfer_cerfct_number = "NA",
            str_student_previous_class_percentage = "NA",
            student_prvs_class = "NA", schoolAffiliated="NA",
            visualDisability = "False", hearingDisabiity = "False",
            speechDisability = "False", physicalDisability = "False",
            str_gender = "Female",hearingDifficult = "False",
            colorVision = "False",teethCavityProblem = "False",
            teethSenstivityProblem = "False",
            str_case="A";




    CheckBox teethCavity , teethSensivity, disabilityVisual ,
             disabilityHearing_sppech , disabilityPhysical ,
             disabilityNone;

    Button colorVisionyes , colorVisionno,
             hearingDifficultYes, hearingDifficultNo,
             finalSubmitOfStudentInfo;


    private TextView student_RegistrationNumber;
    private TextView student_Name;
    private CircleImageView student_ProfileImage;
    private TextView student_Class;
    private TextView student_Section;

    private File sourceFile;
    private File destFile;
    InputStream imageStream = null;



    DatabaseReference mref1 =FirebaseDatabase.getInstance().getReference("School/SchoolId/Student_Registration");
    private int family_details_status =1;
    private int address_details_status =1;
    private int facility_details_status =1;
    ArrayList<String> stdentReg;


    RelativeLayout clickOnAcademicInfo,clickOnMedicalInfo;
    LinearLayout layoutForAcademics , layoutForMedical,head_layout, sibling_Layout,
                 linear_Layout_Attach;


    CardView spinner_Layout;

    int q=0;
    int r=0;


    String selectedYear;
    String selectedMonth;
    String selectedDate;
    int medicalCounter = 0;


    double percentage;
    CardView studentAdharIdBackcardView;






    Uri filePath;


    Uri fileStuentProfile,studentIDFront,studentIDBack,
         studentBCFront,studentBCBcack,studentTCFront,
         studentTCBack,studentPrvsMarksheetFront,
         studentprvsMarksheetBack;

    private static final int BUTTON_ENABLE = 1;


    Bundle bundle;
    Configuration config;
    public static int FROM_GALLERY= 1;
    public static int FROM_CAMERA= 2;
    Dialog dialog,settingsDialog;
    TextView studentadmissionTextView;
    AlertDialog.Builder builder = null;
    ValidationViews validationViews;
    Activity a;
    CircleImageView studentProfileImage;
    double min =35;



    DatabaseReference mref =FirebaseDatabase.getInstance().getReference("School/SchoolId/Student_Registration");
    DatabaseReference classref;
    DatabaseReference studentSnapShotRef =FirebaseDatabase.getInstance().getReference("School/SchoolId/Student_Details_SnapShot");


    Bitmap bitmap;
    ArrayAdapter<String> classadapter,sectionadapter;
    DatabaseReference sectionref;
    String studentClass;
    DatabaseReference lastIdref;
    String str_student_previous_class_marksheet_number;

    StorageReference mStorageRef = FirebaseStorage.getInstance().getReference("School/SchoolId/Student_Registration");


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity){
            a=(Activity) context;
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public StudentInfoFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_student_info, container, false);
        classref=FirebaseDatabase.getInstance().getReference("School/SchoolId/Class_Section");
        lastIdref = FirebaseDatabase.getInstance().getReference("School/SchoolId/Barriers");
        student_RegistrationNumber            = v.findViewById(R.id.studentRegistartionNumber);
        getStudentRegistrationIdFromBarriers();
        stdentReg = new ArrayList<>();
        initViews(v);
        initListener();
        studentClassPercentageTextWatcher();
        //getGuardianNumbers();
        studentDateOfBirthTextWatcher();
        getStudentDetailsFromFirebase();
        initSpinnerForCommunity();
        gettingTheClassInSpinner();
        setHasOptionsMenu(true);


        sectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                studentSection = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




            classesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    Log.d("Checkpoint3", "sdd");

                    if(position>0) {
                        Log.d("Checkpoint4", "sdd");

                        studentClass = parent.getSelectedItem().toString();
                        loadSection(parent.getSelectedItem().toString());
                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {


                }
            });

        return v;
    }

   /* private void getGuardianNumbers() {
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
                            if(guardianNumber!=0)
                            {
                                Constant.GUARDIAN_NUMBER = guardianNumber;
                            }

                        }
                    }
                }
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }*/

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


    private void getStudentRegistrationIdFromBarriers() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Getting data please wait...");
        progressDialog.show();
        lastIdref.child("Registartion_Ids").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("dataSnapShotA", ""+dataSnapshot.getKey());
                for(DataSnapshot postSnapShotA : dataSnapshot.getChildren()){
                    Log.d("postsnapShotA", ""+postSnapShotA.getKey());
                            if(postSnapShotA.getKey().equals("Default_Registration_Id")){
                                Log.d("empID_Default", ""+postSnapShotA.getValue());
                                Constant.REGISTRATION_DEFAULT_ID = (String) postSnapShotA.getValue();

                            }

                            if(postSnapShotA.getKey().equals("Start_Registration_Id")){
                                Constant.REGISTRATION_START_ID = (String) postSnapShotA.getValue();
                                incrementRegistrationID();
                            }

                            if(postSnapShotA.getKey().equals("Current_Registration_Id")){
                                Constant.REGISTRATION_CURRENT_ID = (String) postSnapShotA.getValue();

                            }
                            progressDialog.dismiss();

                }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        
    }

    private void incrementRegistrationID() {

        if(Constant.REGISTRATION_START_ID.equalsIgnoreCase(Constant.REGISTRATION_DEFAULT_ID)){
            increment();
        }

        else {

            Constant.REGISTRATION_CURRENT_ID = Constant.REGISTRATION_DEFAULT_ID;
            Constant.REGISTRATION_START_ID= Constant.REGISTRATION_DEFAULT_ID;
            increment();

        }
    }

    private void increment() {

        int val =lastAlphaNumeric(Constant.REGISTRATION_CURRENT_ID);
        String half1 = Constant.REGISTRATION_CURRENT_ID.substring(0, val+1);
        String half2 = Constant.REGISTRATION_CURRENT_ID.substring(val+1 , Constant.REGISTRATION_CURRENT_ID.length());

        String newHalf2 = "9"+half2;

        int lastnumberincrease = Integer.parseInt((newHalf2))+1;

        String newHalf2Icreae = String.valueOf(lastnumberincrease);

        String finalNumber = newHalf2Icreae.substring(1,newHalf2Icreae.length() );

        Constant.FINAL_REGISTRATION_ID = half1+finalNumber;
        Constant.REGISTRATION_CURRENT_TEMP_ID = Constant.FINAL_REGISTRATION_ID;

        Log.d("Incremet Id", ""+ Constant.FINAL_REGISTRATION_ID);
    }

    private int lastAlphaNumeric(String employeeCurrentId) {
        for (int i = employeeCurrentId.length() - 1; i >= 0; i--) {
            char c = employeeCurrentId.charAt(i);
            if (Character.isLetter(c))
                return i;
        }
        return -1;
    }

    private void loadSection(String s) {

        q=0;
        r=0;

        sectionref=classref.child(s).child("Sections");
        sectionref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChildren()) {

                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        Log.d("main_sectionvalue", d.getKey());
                        q++;
                    }

                    String[] value = new String[q + 1];
                    value[0] = "--Section--";
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        value[r + 1] = d.getKey();
                        r++;
                    }

                    if (getActivity() != null) {
                        sectionadapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item_textview, value);
                        sectionadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                        sectionSpinner.setAdapter(sectionadapter);
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void studentDateOfBirthTextWatcher() {

        student_date_of_birth.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @SuppressLint("DefaultLocale")
            @Override
            public void afterTextChanged(Editable s) {

                if (!s.toString().equals(current)){

                    String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                    String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8){
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }else{
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));
                        int currentYear = Calendar.getInstance().get(Calendar.YEAR);



                        day = day<1 ?1 : day >31 ?31 : day;
                        cal.set(Calendar.DAY_OF_MONTH, day);
                        mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                        cal.set(Calendar.MONTH, mon-1);
                        year = (year<1998)?1998:(year>currentYear-2)?currentYear-2:year;
                        cal.set(Calendar.YEAR, -2);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d",day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;


                    String d= current.substring(0,2);

                    Log.d("DDcurrent",""+current);

                    Log.d("DDDD",""+d);
                    String m = current.substring(3,5);
                    Log.d("MMM",""+m);

                    String y = current.substring(6,10);
                    Log.d("YYYY",""+y);

                    String newDate = String.valueOf(y) + "-" + String.valueOf(m)
                            + "-" + String.valueOf(d) ;

                    Log.d("NEWDAT",""+newDate);

                    str_student_DOB = newDate;
                    student_date_of_birth.setText(current);
                    student_date_of_birth.setSelection(sel < current.length() ? sel : current.length());
                }

            }
        });
    }



    private void studentClassPercentageTextWatcher() {
        student_previous_class_percentage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {



            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(!editable.toString().equals("")) {

                    try {
                        Log.d("Percentage", "input: " + editable);

                        if (editable.toString().startsWith(".")) {
                            editable.replace(0, editable.length(), "");

                        } else if (editable.toString().startsWith("0")) {
                            editable.replace(0, editable.length(), "");

                        } else if (Double.parseDouble(editable.toString()) < min) {
                            student_previous_class_percentage.setError("minimum 35% required");

                        } else if (Double.parseDouble(editable.toString()) > 100) {
                            editable.replace(0, editable.length(), "100");

                        }


                    } catch (NumberFormatException nfe) {
                        nfe.printStackTrace();
                    }
                }

            }
        });
    }



    private void initListener() {
        bundle = new Bundle();
        validationViews = new ValidationViews();
        builder = new AlertDialog.Builder(getActivity());
        schoolboard_sslc.setOnClickListener(this);
        schoolboard_cbse.setOnClickListener(this);
        schoolboard_icse.setOnClickListener(this);
        schoolboard_other.setOnClickListener(this);
        btnDownForAcademics.setOnClickListener(this);
        btnUpForAcademics.setOnClickListener(this);
        btnDownForMedical.setOnClickListener(this);
        genderboy.setOnClickListener(this);
        gendergirl.setOnClickListener(this);
        genderother.setOnClickListener(this);
        finalSubmitOfStudentInfo.setOnClickListener(this);
        clickOnAcademicInfo.setOnClickListener(this);
        clickOnMedicalInfo.setOnClickListener(this);
        calendarForDob.setOnClickListener(this);
        studentProfileImage.setOnClickListener(this);
        studentIdFrontImageClick.setOnClickListener(this);
        studentIdBackImageClick.setOnClickListener(this);
        studentBirthCertificateFrontImageClick.setOnClickListener(this);
        studentBirthCertificateBackImageClick.setOnClickListener(this);
        studentTCFrontImageClick.setOnClickListener(this);
        StudentTCBackImageClick.setOnClickListener(this);
        studentPrvsMarkFrontClick.setOnClickListener(this);
        studentPrvsMarkBackClick.setOnClickListener(this);
        siblings.setOnClickListener(this);

        if(config.smallestScreenWidthDp>=600){
            clickOnAcademicInfo.setClickable(false);
        }
    }


    private void initViews(View v) {
        config                                  = getResources().getConfiguration();
        student_previousClass                   = v.findViewById(R.id.editText_previousClass);
        studentProfileImage                     = v.findViewById(R.id.studentImage);
        student_Firstname                       = v.findViewById(R.id.editText_student_name);
        student_date_of_birth                   = v.findViewById(R.id.editText_student_dob);
        Student_religion                        = v.findViewById(R.id.editText_religion);
        student_nationality                     = v.findViewById(R.id.editText_nationality);
        student_adhar_number                    = v.findViewById(R.id.editText_aadharNumber_student);
        student_language_known                  = v.findViewById(R.id.editText_language_known);
        student_mother_toung_language           = v.findViewById(R.id.editText_mother_tounge);
        student_birth_certfct_number            = v.findViewById(R.id.editText_student_birth_certificate_num);
        student_previous_school_name            = v.findViewById(R.id.editText_previous_school_name);
        student_previous_school_address         = v.findViewById(R.id.editText_previous_school_address);
        student_previous_school_contact_number  = v.findViewById(R.id.editText_previous_school_contact);
        student_previous_school_marksheet_number = v.findViewById(R.id.student_previous_marksheet);
        student_transfer_cerfct_number          = v.findViewById(R.id.editText_transfer_certificate);
        student_previous_class_percentage       = v.findViewById(R.id.editText_previous_class_percentage);
        student_Middlename                      = v.findViewById(R.id.editText_student_middle_name);
        student_Lastname                        = v.findViewById(R.id.editText_student_last_name);
        student_caste                           = v.findViewById(R.id.editText_caste);
        student_subcast                         = v.findViewById(R.id.editText_subcaste);
        calendarForDob                          = v.findViewById(R.id.calendar);
        clickOnAcademicInfo                     = v.findViewById(R.id.academicInfoLayoutClick);
        clickOnMedicalInfo                      = v.findViewById(R.id.medicalInfoLayoutClick);
        head_layout                             = v.findViewById(R.id.headLayout);
        finalSubmitOfStudentInfo                = v.findViewById(R.id.btnStudentInfoFinalSubmit);
        studentadmissionTextView                = v.findViewById(R.id.student_admissionTextView);
        classesSpinner                          = v.findViewById(R.id.spinnerForClass);
        sectionSpinner                          = v.findViewById(R.id.spinnerForSection);
        communitySpinner                        = v.findViewById(R.id.spinnerForCommunity);
        btnDownForAcademics                     = v.findViewById(R.id.btnimageViewDownForAcademicInfo);
        btnUpForAcademics                       = v.findViewById(R.id.btnimageViewUpForAcademicInfo);
        layoutForAcademics                      = v.findViewById(R.id.layoutForAcademicInfo);
        schoolboard_sslc                        = v.findViewById(R.id.affiliated_SSLC);
        schoolboard_cbse                        = v.findViewById(R.id.affiliated_CBSE);
        schoolboard_icse                        = v.findViewById(R.id.affiliated_ICSE);
        schoolboard_other                       = v.findViewById(R.id.affiliated_OTHER);
        btnUpForMedical                         = v.findViewById(R.id.btnimageViewUpForMedicalInfo);
        btnDownForMedical                       = v.findViewById(R.id.btnimageViewDownForMedicalInfo);
        layoutForMedical                        = v.findViewById(R.id.layoutForMedicalInfo);
        genderboy                               = v.findViewById(R.id.genderboy);
        gendergirl                              = v.findViewById(R.id.gendergirl);
        genderother                             = v.findViewById(R.id.genderother);
        studentIdFrontImageClick                = v.findViewById(R.id.studentIdImageFrontClick);
        studentIdBackImageClick                 = v.findViewById(R.id.studentIdImageBackClick);
        studentBirthCertificateFrontImageClick  = v.findViewById(R.id.studentBirthCertificateImageFrontClick);
        studentBirthCertificateBackImageClick   = v.findViewById(R.id.studentBirthCertificateImageBackClick);
        studentTCFrontImageClick                = v.findViewById(R.id.studentTCImageFrontClick);
        StudentTCBackImageClick                 = v.findViewById(R.id.studentTCImageBackClick);
        studentPrvsMarkFrontClick               = v.findViewById(R.id.marksheetFrontClick);
        studentPrvsMarkBackClick                = v.findViewById(R.id.marksheetBackClick);
        studedentIdImageFront                   = v.findViewById(R.id.studentIdImageFront);
        studentIdImageBack                      = v.findViewById(R.id.studentIdImageBack);
        studentBcFront                          = v.findViewById(R.id.studentBirthCertificateImageFront);
        studentBcBack                           = v.findViewById(R.id.studentBirthCertificateImageBack);
        studentTcFront                          = v.findViewById(R.id.studentTCImageFront);
        studentTcBack                           = v.findViewById(R.id.studentTCImageBack);
        studentPrvsMarkFront                    = v.findViewById(R.id.marksheetFront);
        studentPrvsMarkBack                     = v.findViewById(R.id.marksheetBack);
        studentAdharIdBackcardView              = v.findViewById(R.id.cardViewAadharIdNumber);
        siblings                                = v.findViewById(R.id.addSiblings);
        sibling_Layout                          = v.findViewById(R.id.siblingLayout);
        linear_Layout_Attach                    = v.findViewById(R.id.linearLayoutAttach);
        gendergirl.setTextColor(getResources().getColor(R.color.orange));
        student_Name                          = v.findViewById(R.id.studentName);
        student_ProfileImage                  = v.findViewById(R.id.studentProfileImage);
        student_Class                         = v.findViewById(R.id.studentClass);
        student_Section                       = v.findViewById(R.id.studentSection);

    }



    private void gettingTheClassInSpinner() {


            classref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d("dhdhdekdg", "" + dataSnapshot.getKey());

                    q=0;
                    r=0;

                    if (dataSnapshot.hasChildren()) {
                        for (DataSnapshot d : dataSnapshot.getChildren()) {
                            Log.d("main_clasees", d.getKey());
                            q++;
                        }
                        String[] value = new String[q + 1];
                        value[0] = "--select class--";
                        for (DataSnapshot d : dataSnapshot.getChildren()) {
                            Log.d("Checkpoint1", "sdd");

                            value[r + 1] = d.getKey();
                            r++;
                            Log.d("valueLenght", ""+value.length);
                        }
                        if (value.length != 0) {
                            Log.d("Checkpoint2", "sdd");

                            classadapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item_textview, value);
                            classadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                            classesSpinner.setAdapter(classadapter);

                        }


                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }




    //here we are putting cast community into spinner-----------------------------------------------
    private void initSpinnerForCommunity() {

        ArrayList<String> qualificationArrayList = new ArrayList<>();
        qualificationArrayList.add("Community");
        qualificationArrayList.add("GENERAL");
        qualificationArrayList.add("OBC");
        qualificationArrayList.add("SC/ST");
        qualificationArrayList.add("OTHER");
        CustomSpinnerAdapter customSpinnerAdapter=new CustomSpinnerAdapter(getActivity(),qualificationArrayList);
        communitySpinner.setAdapter(customSpinnerAdapter);
        communitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_student_community = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //_____________all button click listner ________________________________________________________
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.addSiblings:
                Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();
                settingsDialog = new Dialog(getActivity());
                settingsDialog.setContentView(R.layout.attach_dialog_profile_picture);
                settingsDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                settingsDialog.setTitle("Choose your option..");
                linear_Layout_Attach = settingsDialog.findViewById(R.id.linearLayoutAttach);
                spinner_Layout = settingsDialog.findViewById(R.id.spinnerLayout);
                spinnerClassSibling = settingsDialog.findViewById(R.id.spinnerForClassSibling);
                spinnerSectionSibling = settingsDialog.findViewById(R.id.spinnerForSectionSibling);
                siblingdialogCancel = settingsDialog.findViewById(R.id.buttonSiblingCancel);
                siblingDialogsubmit = settingsDialog.findViewById(R.id.buttonSiblingSubmit);
                siblingdialogCancel.setOnClickListener(this);
                siblingDialogsubmit.setOnClickListener(this);
                initSpinnerForSiblingClass(spinnerClassSibling);
                initSpinnerForSiblingSection(spinnerSectionSibling);

                linear_Layout_Attach.setVisibility(View.GONE);
                spinner_Layout.setVisibility(View.VISIBLE);
                settingsDialog.show();


                break;


            case R.id.buttonSiblingCancel:
                settingsDialog.dismiss();
                break;

            case R.id.buttonSiblingSubmit:
                settingsDialog.dismiss();
                break;


            case R.id.academicInfoLayoutClick:
                setUiForStudentAcademicInfo();
                break;


            //_________Academics down buton handle__________________________________________________
            case R.id.btnimageViewDownForAcademicInfo:
                setUiForStudentAcademicInfo();
                break;


            //_________Academics up buton handle__________________________________________________

            case R.id.btnimageViewUpForAcademicInfo:
                setUiForStudentAcademicInfo();
                break;

            //_________Medical down buton handle__________________________________________________


            case R.id.medicalInfoLayoutClick:
                if (medicalCounter != 0) {
                    student_height.setText(str_student_height);
                    student_weight.setText(str_student_weight);
                    left_eyeVision.setText(str_left_eyeVision);
                    right_eyeVision.setText(str_right_eyeVision);
                    comments_onEyes.setText(str_comments_onEyes);
                    comments_onTeeth.setText(str_comments_onTeeth);
                    right_earFrequency.setText(str_right_earFrequency);
                    left_earFrequency.setText(str_left_earFrequency);
                    comments_onEar.setText(str_comments_onEar);
                    commentsOnDisability.setText(str_commentsOnDisability);

                } else {

                    dialog = new Dialog(getActivity());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_medical);
                    dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);


                    final Button medicalSubmit = dialog.findViewById(R.id.btnMedicalSubmit);
                    student_height = dialog.findViewById(R.id.student_height);

                    student_height.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {


                            try {

                                if (editable.toString().startsWith(".")) {
                                    editable.replace(0, editable.length(), "");

                                } else if (editable.toString().startsWith("0")) {
                                    editable.replace(0, editable.length(), "");

                                } else if (Double.parseDouble(editable.toString()) > 200) {
                                    editable.replace(0, editable.length(), "200");

                                }

                            } catch (NumberFormatException nfe) {
                                nfe.printStackTrace();
                            }

                        }
                    });

                    spinnerForHeight = dialog.findViewById(R.id.spinnerForHeight);
                    student_weight = dialog.findViewById(R.id.student_weight);

                    student_weight.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {


                            try {

                                if (editable.toString().startsWith(".")) {
                                    editable.replace(0, editable.length(), "");

                                } else if (editable.toString().startsWith("0")) {
                                    editable.replace(0, editable.length(), "");

                                } else if (Double.parseDouble(editable.toString()) > 150) {
                                    editable.replace(0, editable.length(), "150");

                                }

                            } catch (NumberFormatException nfe) {
                                nfe.printStackTrace();
                            }

                        }
                    });


                    spinnerForWeight = dialog.findViewById(R.id.spinnerForWeight);

                    left_eyeVision = dialog.findViewById(R.id.leftEyeVision);
                    right_eyeVision = dialog.findViewById(R.id.rightEyeVision);
                    left_earFrequency = dialog.findViewById(R.id.leftEarFreq);
                    right_earFrequency = dialog.findViewById(R.id.rightEarFreq);
                    comments_onEyes = dialog.findViewById(R.id.commentsOnEyes);
                    comments_onTeeth = dialog.findViewById(R.id.commentsOnTeeth);
                    comments_onEar = dialog.findViewById(R.id.commentsOnEar);
                    commentsOnDisability = dialog.findViewById(R.id.commentsOnDisability);

                    colorVisionyes = dialog.findViewById(R.id.colorVision_btnYes);
                    colorVisionno = dialog.findViewById(R.id.colorVision_btnNo);
                    hearingDifficultYes = dialog.findViewById(R.id.hearing_btnYes);
                    hearingDifficultNo = dialog.findViewById(R.id.hearing_btnNo);


                    hearingDifficultNo.setTextColor(getResources().getColor(R.color.orange));
                    colorVisionno.setTextColor(getResources().getColor(R.color.orange));


                    teethCavity = dialog.findViewById(R.id.checkbox_cavity);
                    teethSensivity = dialog.findViewById(R.id.checkbox_sensivity);
                    disabilityVisual = dialog.findViewById(R.id.checkbox_visualDisability);
                    disabilityHearing_sppech = dialog.findViewById(R.id.checkbox_Hearing_speechDisability);
                    disabilityPhysical = dialog.findViewById(R.id.checkbox_physicalDisability);
                    disabilityNone = dialog.findViewById(R.id.checkbox_none);


                    colorVisionyes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            colorVisionyes.setTextColor(getResources().getColor(R.color.orange));
                            colorVisionno.setTextColor(getResources().getColor(R.color.hintColor));
                            colorVision = "True";

                        }
                    });

                    colorVisionno.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            colorVisionno.setTextColor(getResources().getColor(R.color.orange));
                            colorVisionyes.setTextColor(getResources().getColor(R.color.hintColor));
                            colorVision = "False";

                        }
                    });

                    hearingDifficultYes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            hearingDifficultYes.setTextColor(getResources().getColor(R.color.orange));
                            hearingDifficultNo.setTextColor(getResources().getColor(R.color.hintColor));
                            hearingDifficult = "True";

                        }
                    });

                    hearingDifficultNo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            hearingDifficultNo.setTextColor(getResources().getColor(R.color.orange));
                            hearingDifficultYes.setTextColor(getResources().getColor(R.color.hintColor));
                            hearingDifficult = "False";

                        }
                    });


                    medicalSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String teethProblem = "";
                            String disablity = "";
                            if (teethCavity.isChecked()) {
                                teethCavityProblem = "True";

                            } else {
                                teethCavityProblem = "False";

                            }

                            if (teethSensivity.isChecked()) {
                                teethSenstivityProblem = "True";

                            } else {
                                teethSenstivityProblem = "False";

                            }

                            if (disabilityVisual.isChecked()) {
                                visualDisability = "True";
                            } else {
                                visualDisability = "False";

                            }

                            if (disabilityHearing_sppech.isChecked()) {
                                hearingDisabiity = "True";
                            } else {

                                hearingDisabiity = "False";

                            }

                            if (disabilityPhysical.isChecked()) {
                                physicalDisability = "True";
                            } else {
                                physicalDisability = "False";

                            }
                            if (disabilityNone.isChecked()) {
                                speechDisability = "True";
                            } else {
                                speechDisability = "False";

                            }

                            gettingDatafromMedicalDialog(teethProblem, disablity);
                            medicalCounter = 1;
                            dialog.dismiss();
                        }
                    });
                }


                dialog.show();
                break;


            //_________Case for selecting boy______________________________________________________

            case R.id.genderboy:
                genderboy.setTextColor(getResources().getColor(R.color.orange));
                gendergirl.setTextColor(getResources().getColor(R.color.hintColor));
                genderother.setTextColor(getResources().getColor(R.color.hintColor));
                str_gender = "Male";
                break;

            //_________Case for selecting girl_____________________________________________________

            case R.id.gendergirl:
                gendergirl.setTextColor(getResources().getColor(R.color.orange));
                genderboy.setTextColor(getResources().getColor(R.color.hintColor));
                genderother.setTextColor(getResources().getColor(R.color.hintColor));
                str_gender = "Female";


                break;

            //_________Case for selecting other_____________________________________________________


            case R.id.genderother:
                genderother.setTextColor(getResources().getColor(R.color.orange));
                gendergirl.setTextColor(getResources().getColor(R.color.hintColor));
                genderboy.setTextColor(getResources().getColor(R.color.hintColor));
                str_gender = "Other";

                break;


            //_________Case for selecting prvoius School board Category SSLC________________________

            case R.id.affiliated_SSLC:
                schoolboard_sslc.setTextColor(getResources().getColor(R.color.orange));
                schoolboard_cbse.setTextColor(getResources().getColor(R.color.hintColor));
                schoolboard_icse.setTextColor(getResources().getColor(R.color.hintColor));
                schoolboard_other.setTextColor(getResources().getColor(R.color.hintColor));
                schoolAffiliated = schoolboard_sslc.getText().toString();
                break;

            //_________Case for selecting prvoius School board Category CBSE________________________

            case R.id.affiliated_CBSE:
                schoolboard_cbse.setTextColor(getResources().getColor(R.color.orange));
                schoolboard_icse.setTextColor(getResources().getColor(R.color.hintColor));
                schoolboard_other.setTextColor(getResources().getColor(R.color.hintColor));
                schoolboard_sslc.setTextColor(getResources().getColor(R.color.hintColor));
                schoolAffiliated = schoolboard_cbse.getText().toString();


                break;

            //_________Case for selecting prvoius School board Category ICSE________________________

            case R.id.affiliated_ICSE:
                schoolboard_icse.setTextColor(getResources().getColor(R.color.orange));
                schoolboard_sslc.setTextColor(getResources().getColor(R.color.hintColor));
                schoolboard_other.setTextColor(getResources().getColor(R.color.hintColor));
                schoolboard_cbse.setTextColor(getResources().getColor(R.color.hintColor));
                schoolAffiliated = schoolboard_icse.getText().toString();

                break;

            //_________Case for selecting prvoius School board Category OTHER________________________

            case R.id.affiliated_OTHER:
                schoolboard_other.setTextColor(getResources().getColor(R.color.orange));
                schoolboard_sslc.setTextColor(getResources().getColor(R.color.hintColor));
                schoolboard_icse.setTextColor(getResources().getColor(R.color.hintColor));
                schoolboard_cbse.setTextColor(getResources().getColor(R.color.hintColor));
                schoolAffiliated = schoolboard_other.getText().toString();

                break;


            //_________Case for selecting student eye vision________________________

            case R.id.colorVision_btnYes:
                eyeColorVisionYes.setTextColor(getResources().getColor(R.color.orange));
                eyeColorVisionNo.setTextColor(getResources().getColor(R.color.hintColor));
                break;

            case R.id.colorVision_btnNo:
                eyeColorVisionNo.setTextColor(getResources().getColor(R.color.orange));
                eyeColorVisionYes.setTextColor(getResources().getColor(R.color.hintColor));
                break;

            case R.id.wearGlasses_btnYes:
                wearGlassYes.setTextColor(getResources().getColor(R.color.orange));
                wearGlassNo.setTextColor(getResources().getColor(R.color.hintColor));
                break;

            case R.id.wearGlasses_btnNo:
                wearGlassNo.setTextColor(getResources().getColor(R.color.orange));
                wearGlassYes.setTextColor(getResources().getColor(R.color.hintColor));
                break;


            case R.id.hearing_btnYes:
                hearingbtnYes.setTextColor(getResources().getColor(R.color.orange));
                hearingBtnNo.setTextColor(getResources().getColor(R.color.hintColor));
                break;

            case R.id.hearing_btnNo:
                hearingBtnNo.setTextColor(getResources().getColor(R.color.orange));
                hearingbtnYes.setTextColor(getResources().getColor(R.color.hintColor));
                break;

            case R.id.dialog_ll_camera:
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, FROM_CAMERA);
                settingsDialog.dismiss();
                break;


            case R.id.dialog_ll_gallery:
                /*Intent in_gallery = new Intent(Intent.ACTION_PICK);
                in_gallery.setType("image*//*");
                in_gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(in_gallery, FROM_GALLERY);*/

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i,FROM_GALLERY);
                settingsDialog.dismiss();
                break;


            case R.id.studentImage:
                str_case = "A";
                openDialogForPicture();
                break;


            case R.id.studentIdImageFrontClick:
                str_case = "B";
                openDialogForPicture();
                break;

            case R.id.studentIdImageBackClick:
                str_case = "C";
                openDialogForPicture();
                break;


            case R.id.studentBirthCertificateImageFrontClick:
                str_case = "D";
                openDialogForPicture();
                break;

            case R.id.studentBirthCertificateImageBackClick:
                str_case = "E";
                openDialogForPicture();
                break;


            case R.id.studentTCImageFrontClick:
                str_case = "F";
                openDialogForPicture();
                break;


            case R.id.studentTCImageBackClick:
                str_case = "G";
                openDialogForPicture();

                break;

            case R.id.marksheetFrontClick:
                str_case = "H";
                openDialogForPicture();

                break;

            case R.id.marksheetBackClick:
                str_case = "I";
                openDialogForPicture();
                break;


            case R.id.calendar:
                // Get Current Date
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.datepicker);
                dialog.setTitle("");
                DatePicker datePicker = dialog.findViewById(R.id.datePickerDialog);
                datePicker.setMinDate(1 / 1 / 1998);
                final Calendar calendar = Calendar.getInstance();
                selectedYear = String.valueOf(calendar.get(Calendar.YEAR));
                selectedMonth = String.valueOf(calendar.get(Calendar.MONTH));
                selectedDate = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
                calendar.add(Calendar.YEAR, -2);

                if (selectedDate.length() == 1) {
                    selectedDate = "0" + selectedDate;
                }

                if (selectedMonth.length() == 1) {
                    selectedMonth = "0" + selectedMonth;
                }

                Log.e("selected date", selectedDate + "");
                Log.e("selected month", selectedMonth + "");
                Log.e("selected year", selectedYear + "");


                datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {

                    @Override
                    public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {

                        String newMonth = "", stringNewDate = "", stringyear = String.valueOf(year);

                        if (String.valueOf(month + 1).length() == 1) {
                            newMonth = "0" + (month + 1);
                        } else {
                            newMonth = String.valueOf(month + 1);
                        }

                        if (String.valueOf(dayOfMonth).length() == 1) {
                            stringNewDate = "0" + dayOfMonth;
                        } else {
                            stringNewDate = String.valueOf(dayOfMonth);
                        }
                        Log.e("month", "" + newMonth);
                        Log.e("date", "" + stringNewDate);
                        Log.e("year", "" + stringyear);


                        if (selectedDate.equals(stringNewDate) && selectedMonth.equals(newMonth) && selectedYear.equals(stringyear)) {
                            String date = String.valueOf(stringNewDate) + "//" + String.valueOf(newMonth)
                                    + "//" + String.valueOf(stringyear);

                            student_date_of_birth.setText(date);

                            dialog.dismiss();
                        } else {

                            if (!selectedDate.equals(stringNewDate)) {
                                String date = String.valueOf(stringNewDate) + "//" + String.valueOf(newMonth)
                                        + "//" + String.valueOf(stringyear);

                                student_date_of_birth.setText(date);

                                dialog.dismiss();
                            } else {
                                if (!selectedMonth.equals(newMonth)) {
                                    String date = String.valueOf(stringNewDate) + "//" + String.valueOf(newMonth)
                                            + "//" + String.valueOf(stringyear);

                                    student_date_of_birth.setText(date);
                                    dialog.dismiss();
                                }
                            }
                        }
                        selectedDate = String.valueOf(dayOfMonth);
                        selectedMonth = String.valueOf(month);
                        selectedYear = String.valueOf(year);
                    }
                });
                dialog.show();
                datePicker.setMaxDate(calendar.getTimeInMillis());






                break;


            //_________Finally submitting all details of student info to firebase ______________________________
            case R.id.btnStudentInfoFinalSubmit:



                if (!ValidationViews.EditTextNullValidate(student_Firstname, student_Lastname, student_date_of_birth, student_adhar_number,
                        student_birth_certfct_number, Student_religion,
                        student_nationality, student_caste, student_language_known, student_mother_toung_language
                )) {

                    ValidationViews.showToast(getActivity(), "Fields are requierd");

                } else if (!ValidationViews.EditTextNumberOfDigitsValidate(student_Firstname, 3)) {
                    student_Firstname.setError("Invalid first name");
                } else if (!ValidationViews.EditTextNumberOfDigitsValidate(student_Lastname, 3)) {
                    student_Lastname.setError("Invalid last name");
                } else if (classesSpinner.getSelectedItemPosition() == 0) {
                    Toast.makeText(getActivity(), "Select class ", Toast.LENGTH_SHORT).show();
                } else if (sectionSpinner.getSelectedItemPosition() == 0) {
                    Toast.makeText(getActivity(), "Select section ", Toast.LENGTH_SHORT).show();
                } else if (communitySpinner.getSelectedItemPosition() == 0) {
                    Toast.makeText(getActivity(), "Select comunity ", Toast.LENGTH_SHORT).show();

                } else if (studentProfileImage.getDrawable() == null) {
                    Toast.makeText(getActivity(), "Upload student picture", Toast.LENGTH_SHORT).show();
                } else if (!ValidationViews.EditTextNumberOfDigitsValidate(student_adhar_number, 12)) {
                    student_adhar_number.setError("Invalid Id number");
                } else if (studedentIdImageFront.getDrawable() == null) {
                    Toast.makeText(getActivity(), "Upload Id front picture", Toast.LENGTH_SHORT).show();

                } else if (studentIdImageBack.getDrawable() == null) {
                    Toast.makeText(getActivity(), "Upload Id back picture", Toast.LENGTH_SHORT).show();

                } else if (!ValidationViews.EditTextNumberOfDigitsValidate(student_birth_certfct_number, 11)) {
                    student_birth_certfct_number.setError("Invalid birth certificate number");
                } else if (studentBcFront.getDrawable() == null) {
                    Toast.makeText(getActivity(), "Upload birth certificate front picture", Toast.LENGTH_SHORT).show();

                } else if (studentBcBack.getDrawable() == null) {
                    Toast.makeText(getActivity(), "Upload birth certificate back picture", Toast.LENGTH_SHORT).show();

                }
                else {

                    final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage("Storing data please wait...");
                    progressDialog.show();

                    //admission_Number = addmissionNumber.getText().toString();
                    str_student_Firstname = student_Firstname.getText().toString().trim();
                    str_student_Middlenamne = student_Middlename.getText().toString().trim();
                    str_student_Lastname = student_Lastname.getText().toString().trim();
                    str_Student_religion = Student_religion.getText().toString();
                    str_student_caste = student_caste.getText().toString().trim();
                    str_student_subcaste = student_subcast.getText().toString().trim();
                    str_student_nationality = student_nationality.getText().toString();
                    str_student_Id_number = student_adhar_number.getText().toString();
                    str_student_language_known = student_language_known.getText().toString();
                    str_student_mother_toung_language = student_mother_toung_language.getText().toString();
                    str_student_birth_certfct_number = student_birth_certfct_number.getText().toString();
                    str_student_previous_school_name = student_previous_school_name.getText().toString();
                    str_student_previous_school_address = student_previous_school_address.getText().toString();
                    str_student_previous_school_contact_number = student_previous_school_contact_number.getText().toString();
                    str_student_transfer_cerfct_number = student_transfer_cerfct_number.getText().toString();
                    str_student_previous_class_percentage = String.valueOf(percentage);
                    student_prvs_class = student_previousClass.getText().toString();
                    str_student_previous_class_marksheet_number = student_previous_school_marksheet_number.getText().toString();


                    if (str_student_previous_school_name.equals("")) {
                        str_student_previous_school_name = "NA";
                    }

                    if (str_student_previous_school_address.equals("")) {
                        str_student_previous_school_address = "NA";
                    }

                    if (schoolAffiliated.equals("")) {
                        schoolAffiliated = "NA";
                    }

                    if (str_student_transfer_cerfct_number.equals("")) {
                        str_student_transfer_cerfct_number = "NA";
                    }
                    if (student_prvs_class.equals("")) {
                        student_prvs_class = "NA";
                    }
                    if (str_student_previous_class_percentage.equals("")) {
                        str_student_previous_class_percentage = "NA";
                    }
                    if (str_student_previous_school_contact_number.equals("")) {
                        str_student_previous_school_contact_number = "NA";
                    }
                    if (str_student_height.equals("")) {
                        str_student_height = "NA";
                    }
                    if (str_student_weight.equals("")) {
                        str_student_weight = "NA";
                    }
                    if (str_right_eyeVision.equals("")) {
                        str_right_eyeVision = "NA";
                    }
                    if (str_left_eyeVision.equals("")) {
                        str_left_eyeVision = "NA";
                    }
                    if (colorVision.equals("")) {
                        colorVision = "False";
                    }
                    if (str_comments_onEyes.equals("")) {
                        str_comments_onEyes = "NA";
                    }
                    if (str_comments_onEyes.equals("")) {
                        str_left_eyeVision = "NA";
                    }
                    if (str_comments_onEyes.equals("")) {
                        str_comments_onEyes = "NA";
                    }
                    if (teethCavityProblem.equals("")) {
                        teethCavityProblem = "NA";
                    }
                    if (teethSenstivityProblem.equals("")) {
                        teethSenstivityProblem = "NA";
                    }
                    if (str_comments_onTeeth.equals("")) {
                        str_comments_onTeeth = "NA";
                    }
                    if (str_right_earFrequency.equals("")) {
                        str_right_earFrequency = "NA";
                    }
                    if (str_left_earFrequency.equals("")) {
                        str_left_earFrequency = "NA";
                    }
                    if (hearingDifficult.equals("")) {
                        hearingDifficult = "NA";
                    }
                    if (str_comments_onEar.equals("")) {
                        str_comments_onEar = "NA";
                    }
                    if (visualDisability.equals("")) {
                        visualDisability = "NA";
                    }
                    if (hearingDifficult.equals("")) {
                        hearingDifficult = "NA";
                    }
                    if (physicalDisability.equals("")) {
                        physicalDisability = "NA";
                    }
                    if (speechDisability.equals("")) {
                        speechDisability = "NA";
                    }
                    if (str_commentsOnDisability.equals("")) {
                        str_commentsOnDisability = "NA";
                    }


                    Log.d("Student", "class" + str_selectedClass);
                    Log.d("Student", "section" + studentSection);
                    Log.d("Student", "FName" + str_student_Firstname);
                    Log.d("Student", "MiddleName" + str_student_Middlenamne);
                    Log.d("Student", "LastName" + str_student_Lastname);
                    Log.d("Student", "DOB" + str_student_DOB);
                    Log.d("Student", "Gender" + str_gender);
                    Log.d("Student", "Id number" + str_student_Id_number);
                    Log.d("Student", "BC number" + str_student_birth_certfct_number);
                    Log.d("Student", "Religion" + str_Student_religion);
                    Log.d("Student", "nationality" + str_student_nationality);
                    Log.d("Student", "community" + str_student_community);
                    Log.d("Student", "caste" + str_student_caste);
                    Log.d("Student", "subcaste" + str_student_subcaste);
                    Log.d("Student", "languageKnown" + str_student_language_known);
                    Log.d("Student", "LanguageMother" + str_student_mother_toung_language);
                    Log.d("Student", "SchName" + str_student_previous_school_name);
                    Log.d("Student", "SchoolCity" + str_student_previous_school_address);
                    Log.d("Student", "Affliated" + schoolAffiliated);
                    Log.d("Student", "TCNum" + str_student_transfer_cerfct_number);
                    Log.d("Student", "previousClass" + student_prvs_class);
                    Log.d("Student", "PrvsPercen" + str_student_previous_class_percentage);
                    Log.d("Student", "ContactPrvs" + str_student_previous_school_contact_number);
                    Log.d("Student", "Height" + str_student_height);
                    Log.d("Student", "Wight" + str_student_weight);
                    Log.d("Student", "shortEye" + str_left_eyeVision);
                    Log.d("Student", "longEye" + str_right_eyeVision);
                    Log.d("Student", "ColorVision" + colorVision);
                    Log.d("Student", "EyeComment" + str_comments_onEyes);
                    Log.d("Student", "Cavity" + teethCavityProblem);
                    Log.d("Student", "Sensivity" + teethSenstivityProblem);
                    Log.d("Student", "TeethComment" + str_comments_onTeeth);
                    Log.d("Student", "RightEar" + str_right_earFrequency);
                    Log.d("Student", "LeftEar" + str_left_earFrequency);
                    Log.d("Student", "hearingDiff" + hearingDifficult);
                    Log.d("Student", "ComntsEar" + str_comments_onEar);
                    Log.d("Student", "VDsabl" + visualDisability);
                    Log.d("Student", "HDisab" + hearingDisabiity);
                    Log.d("Student", "PDis" + physicalDisability);
                    Log.d("Student", "SpeechDisab" + speechDisability);
                    Log.d("Student", "ComDisa" + str_commentsOnDisability);


                    //==========================================================

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date date = new Date();
                    String joiningDate = dateFormat.format(date);

                    Log.d("jDate", ""+joiningDate);
                    final String currentYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));

                    final NewStudentRegisterModel student_admission_year = new NewStudentRegisterModel(studentClass,studentSection,joiningDate);


                    final NewStudentRegisterModel model1 = new NewStudentRegisterModel(studentClass,studentSection,str_student_Firstname,str_student_Middlenamne,str_student_Lastname,student_date_of_birth.getText().toString(),str_gender,
                            str_student_Id_number,str_student_birth_certfct_number);

                    final NewStudentRegisterModel student_personal_info = new NewStudentRegisterModel(str_student_Firstname,str_student_Middlenamne,str_student_Lastname,student_date_of_birth.getText().toString(),str_gender,
                            str_student_Id_number,str_student_birth_certfct_number, str_Student_religion,str_student_nationality,
                            str_student_community,str_student_caste,str_student_subcaste,str_student_language_known,str_student_mother_toung_language);

                    final NewStudentRegisterModel student_academic_info = new NewStudentRegisterModel(student_prvs_class,str_student_previous_school_name,str_student_previous_school_address,schoolAffiliated,str_student_transfer_cerfct_number,str_student_previous_class_percentage,
                            str_student_previous_school_contact_number,str_student_previous_class_marksheet_number);

                    final NewStudentRegisterModel student_health_info = new NewStudentRegisterModel(str_student_height,str_student_weight,str_left_eyeVision,
                            str_right_eyeVision,colorVision,str_comments_onEyes,StudentTeethProblem,str_comments_onTeeth,str_right_earFrequency,
                            str_left_earFrequency, str_comments_onEar, disabilityProblem , str_commentsOnDisability);



                    final StorageReference storageReference = mStorageRef.child(Constant.FINAL_REGISTRATION_ID).child("personal_information");


                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                            mref.child(Constant.FINAL_REGISTRATION_ID).child("personal_information").setValue(student_personal_info);
                            mref.child(Constant.FINAL_REGISTRATION_ID).child("student_previous_academic_info").setValue(student_academic_info);
                            mref.child(Constant.FINAL_REGISTRATION_ID).child("student_health_info").setValue(student_health_info);
                            mref.child(Constant.FINAL_REGISTRATION_ID).child("academic_year_info").child(currentYear).setValue(student_admission_year);

                            studentSnapShotRef.child(Constant.FINAL_REGISTRATION_ID).setValue(model1);
                            NewAdmissionActivity newAdmissionActivity = (NewAdmissionActivity) getActivity();
                            FamilyInfoFragment familyInfoFragment = new FamilyInfoFragment();

                            storageReference.putFile(fileStuentProfile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    mref.child(Constant.FINAL_REGISTRATION_ID).child("personal_information").child("student_profile_picture").setValue(taskSnapshot.getDownloadUrl().toString());
                                    studentSnapShotRef.child(Constant.FINAL_REGISTRATION_ID).child("student_profile_picture").setValue(taskSnapshot.getDownloadUrl().toString());

                                }
                            });


                            storageReference.putFile(studentIDFront).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    mref.child(Constant.FINAL_REGISTRATION_ID).child("personal_information").child("str_student_Id_number_front_image").setValue(taskSnapshot.getDownloadUrl().toString());

                                }
                            });

                            storageReference.putFile(studentIDBack).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    mref.child(Constant.FINAL_REGISTRATION_ID).child("personal_information").child("str_student_Id_number_back_image").setValue(taskSnapshot.getDownloadUrl().toString());

                                }
                            });

                            storageReference.putFile(studentBCFront).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    mref.child(Constant.FINAL_REGISTRATION_ID).child("personal_information").child("str_student_birth_certfct_number_front_image").setValue(taskSnapshot.getDownloadUrl().toString());

                                }
                            });

                            storageReference.putFile(studentBCBcack).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    mref.child(Constant.FINAL_REGISTRATION_ID).child("personal_information").child("str_student_birth_certfct_number_back_image").setValue(taskSnapshot.getDownloadUrl().toString());

                                }
                            });

                            if(studentTCFront!=null){
                                storageReference.putFile(studentTCFront).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                        mref.child(Constant.FINAL_REGISTRATION_ID).child("student_previous_academic_info").child("str_student_transfer_cerfct_number_front_image").setValue(taskSnapshot.getDownloadUrl().toString());

                                    }
                                });

                            }

                            if(studentTCBack!=null){
                                storageReference.putFile(studentTCBack).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                        mref.child(Constant.FINAL_REGISTRATION_ID).child("student_previous_academic_info").child("str_student_transfer_cerfct_number_back_image").setValue(taskSnapshot.getDownloadUrl().toString());

                                    }
                                });

                            }

                            if(studentPrvsMarksheetFront!=null){
                                storageReference.putFile(studentPrvsMarksheetFront).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                        mref.child(Constant.FINAL_REGISTRATION_ID).child("student_previous_academic_info").child("str_student_previous_class_marksheet_number_front_image").setValue(taskSnapshot.getDownloadUrl().toString());

                                    }
                                });

                            }

                            if(studentprvsMarksheetBack!=null){
                                storageReference.putFile(studentprvsMarksheetBack).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                        mref.child(Constant.FINAL_REGISTRATION_ID).child("student_previous_academic_info").child("str_student_previous_class_marksheet_number_back_image").setValue(taskSnapshot.getDownloadUrl().toString());

                                    }
                                });

                            }

                            EventBus.getDefault().post(new NewStudentRegisterModel(studentClass,studentSection,str_student_Firstname,Constant.FINAL_REGISTRATION_ID));
                            newAdmissionActivity.loadNextFragment(BUTTON_ENABLE , familyInfoFragment,null);
                            Toast.makeText(getActivity(), "Student data stored", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();


                        }
                    }, 5000);

                }
        }

    }

    private void initSpinnerForSiblingSection(Spinner spinnerSectionSibling) {
        ArrayList<String> sectionSiblingArraylist = new ArrayList<>();
        sectionSiblingArraylist.add("Section");
        sectionSiblingArraylist.add("A");
        sectionSiblingArraylist.add("B");
        sectionSiblingArraylist.add("C");
        CustomSpinnerAdapter customSpinnerAdapter=new CustomSpinnerAdapter(getActivity(),sectionSiblingArraylist);
        spinnerSectionSibling.setAdapter(customSpinnerAdapter);
        spinnerSectionSibling.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //String sectionSibling = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void initSpinnerForSiblingClass(Spinner spinnerClassSibling) {

        ArrayList<String> classSiblingArraylist = new ArrayList<>();
        classSiblingArraylist.add("Class");
        classSiblingArraylist.add("1");
        classSiblingArraylist.add("2");
        classSiblingArraylist.add("3");
        classSiblingArraylist.add("4");
        CustomSpinnerAdapter customSpinnerAdapter=new CustomSpinnerAdapter(getActivity(),classSiblingArraylist);
        spinnerClassSibling.setAdapter(customSpinnerAdapter);
        spinnerClassSibling.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //String classSibling = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void openDialogForPicture() {
        settingsDialog = new Dialog(getActivity());
        settingsDialog.setContentView(R.layout.attach_dialog_profile_picture);
        settingsDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        settingsDialog.setTitle("Choose your option..");
        LinearLayout dialogcamera = settingsDialog.findViewById(R.id.dialog_ll_camera);
        LinearLayout dialoggallery = settingsDialog.findViewById(R.id.dialog_ll_gallery);
        linear_Layout_Attach = settingsDialog.findViewById(R.id.linearLayoutAttach);
        linear_Layout_Attach.setVisibility(View.VISIBLE);
        dialogcamera.setOnClickListener(this);
        dialoggallery.setOnClickListener(this);


        settingsDialog.show();
    }



    private void setUiForStudentAcademicInfo() {
        if(layoutForAcademics.getVisibility()== View.GONE){
            layoutForAcademics.setVisibility(View.VISIBLE);
            btnDownForAcademics.setVisibility(View.GONE);
            btnUpForAcademics.setVisibility(View.VISIBLE);
            studentadmissionTextView.setBackgroundColor(getResources().getColor(R.color.whitecolor));
            studentadmissionTextView.setTextColor(getResources().getColor(R.color.colorPrimary));


        }

        else {
            layoutForAcademics.setVisibility(View.GONE);
            btnDownForAcademics.setVisibility(View.VISIBLE);
            btnUpForAcademics.setVisibility(View.GONE);
            studentadmissionTextView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            studentadmissionTextView.setTextColor(getResources().getColor(R.color.whitecolor));

        }
    }

    //__________Here we will get All views data from medical dialog_________________________________
    private void gettingDatafromMedicalDialog(String teeth, String disability) {
        str_student_height = student_height.getText().toString();//+heihtUnit
        str_student_weight = student_weight.getText().toString();
        str_left_eyeVision = left_eyeVision.getText().toString().trim();
        str_right_eyeVision =right_eyeVision.getText().toString().trim();
        str_comments_onEyes = comments_onEyes.getText().toString();
        str_comments_onTeeth = comments_onTeeth.getText().toString();
        str_comments_onEar = comments_onEar.getText().toString();
        str_commentsOnDisability = commentsOnDisability.getText().toString();
        str_right_earFrequency = right_earFrequency.getText().toString();
        str_left_earFrequency = left_earFrequency.getText().toString();
        StudentTeethProblem = teeth;
        disabilityProblem = disability;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FROM_CAMERA && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), photo, "Title", null);
            filePath= Uri.parse(path);
            pathProfile = getPathFromUri(filePath);
            //fileStuentProfile = new File(pathProfile);



            switch (str_case){

                case "A":
                    fileStuentProfile = filePath;
                    setImageToView(fileStuentProfile);
                    break;

                case "B":
                    studentIDFront = filePath;
                    setImageToView(studentIDFront);
                    studentIdFrontImageClick.setVisibility(View.VISIBLE);
                    studedentIdImageFront.setVisibility(View.VISIBLE);
                    break;

                case "C":
                    studentIDBack = filePath;
                    setImageToView(studentIDBack);
                    studentIdBackImageClick.setVisibility(View.VISIBLE);
                    studentIdImageBack.setVisibility(View.VISIBLE);
                    break;

                case "D":
                    studentBCFront = filePath;
                    setImageToView(studentBCFront);
                    studentBirthCertificateFrontImageClick.setVisibility(View.VISIBLE);
                    studentBcFront.setVisibility(View.VISIBLE);
                    break;

                case "E":
                    studentBCBcack = filePath;
                    setImageToView(studentBCBcack);
                    studentBirthCertificateBackImageClick.setVisibility(View.VISIBLE);
                    studentBcBack.setVisibility(View.VISIBLE);
                    break;

                case "F":
                    studentTCFront = filePath;
                    setImageToView(studentTCFront);
                    studentTCFrontImageClick.setVisibility(View.VISIBLE);
                    studentTcFront.setVisibility(View.VISIBLE);
                    break;

                case "G":
                    studentTCBack =filePath;
                    setImageToView(studentTCBack);
                    StudentTCBackImageClick.setVisibility(View.VISIBLE);
                    studentTcBack.setVisibility(View.VISIBLE);
                    break;


                case "H":
                    studentPrvsMarksheetFront = filePath;
                    setImageToView(studentPrvsMarksheetFront);
                    studentPrvsMarkFrontClick.setVisibility(View.VISIBLE);
                    studentPrvsMarkFront.setVisibility(View.VISIBLE);
                    break;


                case "I":
                    studentprvsMarksheetBack = filePath;
                    setImageToView(studentprvsMarksheetBack);
                    studentPrvsMarkBackClick.setVisibility(View.VISIBLE);
                    studentPrvsMarkBack.setVisibility(View.VISIBLE);
                    break;
            }


        }

        if (requestCode == FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            filePath = data.getData();

            InputStream inputStream ;
            InputStream imageStream = null;


            switch (str_case){

                case "A":
                    assert filePath != null;
                    fileStuentProfile = filePath;
                    setImageToView(fileStuentProfile);

                    break;

                case "B":
                    assert filePath != null;
                    studentIDFront = filePath;
                    studentIdFrontImageClick.setVisibility(View.VISIBLE);
                    studedentIdImageFront.setVisibility(View.VISIBLE);
                    setImageToView(studentIDFront);


                    break;

                case "C":
                    assert filePath != null;
                    studentIDBack =filePath;
                    setImageToView(studentIDBack);
                    studentIdBackImageClick.setVisibility(View.VISIBLE);
                    studentIdImageBack.setVisibility(View.VISIBLE);

                    break;

                case "D":
                    assert filePath != null;
                    studentBCFront = filePath;
                    setImageToView(studentBCFront);
                    studentBirthCertificateFrontImageClick.setVisibility(View.VISIBLE);
                    studentBcFront.setVisibility(View.VISIBLE);

                    break;

                case "E":
                    assert filePath != null;
                    studentBCBcack = filePath;
                    setImageToView(studentBCBcack);
                    studentBirthCertificateBackImageClick.setVisibility(View.VISIBLE);
                    studentBcBack.setVisibility(View.VISIBLE);

                    break;

                case "F":
                    assert filePath != null;
                    studentTCFront = filePath;
                    setImageToView(studentTCFront);
                    studentTCFrontImageClick.setVisibility(View.VISIBLE);
                    studentTcFront.setVisibility(View.VISIBLE);

                    break;

                case "G":
                    assert filePath != null;
                    studentTCBack = filePath;
                    setImageToView(studentTCBack);
                    StudentTCBackImageClick.setVisibility(View.VISIBLE);
                    studentTcBack.setVisibility(View.VISIBLE);

                    break;


                case "H":
                    assert filePath != null;
                    studentPrvsMarksheetFront = filePath;
                    setImageToView(studentPrvsMarksheetFront);
                    studentPrvsMarkFrontClick.setVisibility(View.VISIBLE);
                    studentPrvsMarkFront.setVisibility(View.VISIBLE);

                    break;


                case "I":
                    assert filePath != null;
                    studentprvsMarksheetBack = filePath;
                    setImageToView(studentprvsMarksheetBack);
                    studentPrvsMarkBackClick.setVisibility(View.VISIBLE);
                    studentPrvsMarkBack.setVisibility(View.VISIBLE);
                    break;
            }

        }

    }

    private void setImageToView(Uri fileUri) {

        try {

            imageStream = getActivity().getContentResolver().openInputStream(fileUri);
            Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            selectedImage = getResizedBitmap(selectedImage, 300);

            switch (str_case){

                case "A":
                    studentProfileImage.setImageBitmap(selectedImage);
                    break;

                case "B":
                    studedentIdImageFront.setImageBitmap(selectedImage);
                    break;

                case "C":
                    studentIdImageBack.setImageBitmap(selectedImage);
                    break;

                case "D":
                    studentBcFront.setImageBitmap(selectedImage);
                    break;

                case "E":
                    studentBcBack.setImageBitmap(selectedImage);
                    break;

                case "F":
                    studentTcFront.setImageBitmap(selectedImage);
                    break;

                case "G":
                    studentTcBack.setImageBitmap(selectedImage);
                    break;

                case "H":
                    studentPrvsMarkFront.setImageBitmap(selectedImage);
                    break;

                case "I":
                    studentPrvsMarkBack.setImageBitmap(selectedImage);
                    break;

            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    private Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }


    private String getPathFromUri(Uri uri) {
        Cursor cursor =  getActivity().getContentResolver().query(uri, null, null, null, null);
        if (cursor == null) {
            return uri.getPath();

        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.dashboard, menu);
        final MenuItem myActionMenuItem = menu.findItem( R.id.action_search);

        myActionMenuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do whatever you need
                searchStudentData(myActionMenuItem);
                return true; // KEEP IT TO TRUE OR IT DOESN'T OPEN !!
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do whatever you need
                return true; // OR FALSE IF YOU DIDN'T WANT IT TO CLOSE!
            }
        });




    }

    private void searchStudentData(MenuItem myActionMenuItem) {

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
                searchAutoComplete.setText(queryString);
                Log.d("qary", ""+queryString);
                Constant.SEARCH_ID = queryString;
                Constant.FINAL_REGISTRATION_ID = Constant.SEARCH_ID;

                Log.d("dsjkdanjkdas", ""+queryString);
                studentSnapShotRef.child(queryString).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        student_RegistrationNumber.setText(queryString);

                        for (DataSnapshot postSnapShotB : dataSnapshot.getChildren()){
                            Log.d("PostSS", ""+postSnapShotB.getKey());

                            if(postSnapShotB.getKey().equals("str_selectedClass")){

                                // studentClass = (String) postSnapShotB.getValue();
                                student_Class.setText((String)postSnapShotB.getValue());

                            }

                            if(postSnapShotB.getKey().equals("str_studentSection")){

                                //studentSection = (String) postSnapShotB.getValue();
                                student_Section.setText((String) postSnapShotB.getValue());

                            }

                            if(postSnapShotB.getKey().equals("str_student_Firstname")){

                                //studentFName = (String) postSnapShotB.getValue();
                                student_Name.setText((String) postSnapShotB.getValue());

                            }

                            if(postSnapShotB.getKey().equals("student_profile_picture")){

                                //studentprofilePicture = (String) postSnapShotB.getValue();
                                Constant.STUDENT_PROFILE_IMAGE = (String) postSnapShotB.getValue();
                                Glide.with(getActivity()).load((String) postSnapShotB.getValue()).into(student_ProfileImage);

                            }


                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                mref1.child(queryString).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d("Idfinal", ""+Constant.FINAL_REGISTRATION_ID);
                        Log.d("Idtemp", ""+Constant.REGISTRATION_CURRENT_TEMP_ID);
                        Log.d("Idseacrh", ""+Constant.SEARCH_ID);

                        facility_details_status = 1;
                        family_details_status = 1;
                        address_details_status=1;
                        //1 means that node is not available
                        //0 that node is available.
                        for(DataSnapshot postSnapoShotA : dataSnapshot.getChildren()){

                            if(postSnapoShotA.getKey().equalsIgnoreCase("address_information")){
                                address_details_status =0;
                            }

                            if(postSnapoShotA.getKey().equalsIgnoreCase("family_information")){
                                family_details_status = 0;
                            }
                            if(postSnapoShotA.getKey().equalsIgnoreCase("facility_information")){
                                facility_details_status = 0;
                            }

                        }
                        Log.d("ABCaddress", ""+address_details_status);
                        Log.d("ABCfacility", ""+facility_details_status);
                        Log.d("ABCfamily", ""+family_details_status);
                        /*final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setMessage("Getting data please wait...");
                        progressDialog.show();*/

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                if(family_details_status==1){
                                    Toast.makeText(getActivity(), "family", Toast.LENGTH_SHORT).show();
                                    //Log.d("kjdjsdsd", ""+student_Class.getText().toString()+" "+student_Section.getText().toString());

                                    //Toast.makeText(getActivity(), "Please fill this page", Toast.LENGTH_SHORT).show();
                                    Constant.FINAL_REGISTRATION_ID = queryString;
                                    NewAdmissionActivity newAdmissionActivity = (NewAdmissionActivity) getActivity();
                                    FamilyInfoFragment familyInfoFragment = new FamilyInfoFragment();
                                    newAdmissionActivity.loadNextFragment(BUTTON_ENABLE , familyInfoFragment,null);
                                    EventBus.getDefault().postSticky(new NewStudentRegisterModel(student_Class.getText().toString(),student_Section.getText().toString(),student_Name.getText().toString(),Constant.FINAL_REGISTRATION_ID, Constant.STUDENT_PROFILE_IMAGE));
                                    //progressDialog.dismiss();

                                }

                                else if(address_details_status==1){
                                    Toast.makeText(getActivity(), "Address", Toast.LENGTH_SHORT).show();

                                    Log.d("kjdjweewe", ""+student_Class.getText().toString()+" "+student_Section.getText().toString());
                                    Constant.FINAL_REGISTRATION_ID = queryString;
                                    NewAdmissionActivity newAdmissionActivity = (NewAdmissionActivity) getActivity();
                                    AddressInfoFragment addressInfoFragment = new AddressInfoFragment();
                                    newAdmissionActivity.loadNextFragment(2, addressInfoFragment, null);
                                    EventBus.getDefault().postSticky(new NewStudentRegisterModel(student_Class.getText().toString(),student_Section.getText().toString(),student_Name.getText().toString(),Constant.FINAL_REGISTRATION_ID,Constant.STUDENT_PROFILE_IMAGE));
                                    //progressDialog.dismiss();

                                }

                                else if(facility_details_status==1){
                                    Toast.makeText(getActivity(), "facility", Toast.LENGTH_SHORT).show();

                                    Log.d("kjdhfrt", ""+student_Class.getText().toString()+" "+student_Section.getText().toString());
                                    Constant.FINAL_REGISTRATION_ID = queryString;
                                    NewAdmissionActivity newAdmissionActivity = (NewAdmissionActivity) getActivity();
                                    FacilityInfoFragment facilityInfoFragment = new FacilityInfoFragment();
                                    newAdmissionActivity.loadNextFragment(3, facilityInfoFragment, null);
                                    EventBus.getDefault().postSticky(new NewStudentRegisterModel(student_Class.getText().toString(),student_Section.getText().toString(),student_Name.getText().toString(),Constant.FINAL_REGISTRATION_ID,Constant.STUDENT_PROFILE_IMAGE));
                                    //progressDialog.dismiss();

                                }
                                else {
                                    Toast.makeText(getActivity(), "All details already filled", Toast.LENGTH_SHORT).show();
                                    //Constant.FINAL_REGISTRATION_ID = Constant.REGISTRATION_CURRENT_TEMP_ID;
                                    //progressDialog.dismiss();

                                }

                            }
                        },300);



                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });
    }

}
