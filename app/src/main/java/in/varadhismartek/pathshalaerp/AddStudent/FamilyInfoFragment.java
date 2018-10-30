package in.varadhismartek.pathshalaerp.AddStudent;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import in.varadhismartek.pathshalaerp.CircleImageView;
import in.varadhismartek.pathshalaerp.Constant;
import in.varadhismartek.pathshalaerp.R;
import in.varadhismartek.pathshalaerp.ValidationViews;


/**
 * A simple {@link Fragment} subclass.
 */
public class FamilyInfoFragment extends Fragment implements View.OnClickListener {

    final int         BUTTON_ENABLE=2;
    SharedPreferences prefs;
    final String PREFS_NAME1 = "GuardianPref";
    final String PREFS_NAME = "MyPrefsFile";
    SharedPreferences preferenceFatherDetails ;
    SharedPreferences preference1;
    SharedPreferences sharedPreferencesStudent;
    final String SchoolID = "KV_26";
    final String STUDENT_PERSONAL = "studentpersonal";
    final String FATHER_PERSONAL = "fatherpersonal";
    LinearLayout layout_child;
    Configuration config;
    JSONObject jsonObjectGuardians;
    ProgressDialog progressDialog=null;
    Calendar cal =    Calendar.getInstance();
    AutoCompleteViewAdapter recyclerAdapter;
    EventBus bus = EventBus.getDefault();







    Spinner spinnerForFatherQualification, spinnerForFatherOccupation ,
                      spinnerForMotherQualification, spinnerForMotheroccupation;

    ImageView btnDownForFather, btnDownForMother,
                      btnUpForFather,btnUpForMother,
                      btnUpForGuardians, btnDownForGuardians,
                      dobCalendarFather,dobCalendarMother;



    LinearLayout layoutForFather, layoutForMother,
                      layoutForGuardians;


    RelativeLayout clickOnFatherInfoLayout,
                      clickOnMotherInfoLayout,
                      clickOnGuardianInfoLayout;


    EditText fatherFirstName,fatherMiddleName,fatherLastName,
                      fatherAge, fatherDesignation, fatherMobileNumber,
                      fatherAdharCardorIdNumber,fatherEmailId,
                      motherFirstName,motherMiddleName,motherLastName,
                      motherAge, motherDesignation, motherMobileNumber,
                      motherAdharCardorIdNumber,motherEmailId,
                      guardian_name, guardian_mobile_number,
                      guardian_aadhar_number, guardian_emailid,
                      father_current_address_Number,
                      mother_current_address_number,
                      guardianCurrentAddressNumber;


    Button submitfamilyinfo;


    TextView fatherInfoTxtView,motherInfoTxtView,
                      guardiansInfoTxtView,guardian_Number;

    InputStream imageStream = null;




    String school_id,student_first_name,student_last_name,
                      student_DOB,student_BerthCertificate,
                      father_FirstName,father_MiddleName = "NA",
                      father_Lastname,father_DOB,father_MobileNumber,
                      father_Qualification,father_Occupation,
                      father_Designation="NA",father_AadharCard,
                      father_EmailID,mother_FirstName,mother_MiddleName="NA",
                      mother_Lastname,mother_DOB,mother_MobileNumber,
                      mother_Qualification,mother_Occupation,
                      mother_Designation="NA",mother_AadharCard,
                      mother_EmailID,str_father_DOB,str_mother_DOB,
                      current = "",ddmmyyyy = "DDMMYYYY",a = "VR",
                      father_qualification,father_occupation,
                      mother_qualification,mother_occupation,fatherDOB,
                      motherDOB,selectedYear,selectedMonth,selectedDate;



    List<EditText> guardianName_allEds   = new ArrayList<>();
    List<EditText> guardianMobile_allEds = new ArrayList<EditText>();
    List<EditText> guardianAadhar_allEds = new ArrayList<EditText>();
    List<EditText> guardianEmail_allEds  = new ArrayList<EditText>();
    List<EditText> guardianCurrentAddress_allEds  = new ArrayList<EditText>();

    List<ImageView> guardianGuardian_allProfilePictre  = new ArrayList<ImageView>();
    List<ImageView> guardianGuardian_allAadharCardPictreFrontClick = new ArrayList<ImageView>();
    List<ImageView> guardianGuardian_allAadharCardPictreBackClick = new ArrayList<ImageView>();
    List<ImageView> guardianGuardian_allCurrentAddressPictreFrontClick = new ArrayList<ImageView>();
    List<ImageView> guardianGuardian_allCurrentAddressPictreBackClick = new ArrayList<ImageView>();



    StorageReference mStorageRef = FirebaseStorage.getInstance().getReference("School/SchoolId/Student_Registration");


    ArrayList<NewStudentRegisterModel> studentdetailsModelArrayList;




    LinearLayout layoutFather;


    ImageView fatherProfileImage,
                      fatherAadhar_FrontImageClick,
                      fatherAadharCard_ImageFront,
                      fatherAadharBack_ImageClick,
                      fatherAadharCard_ImageBack,
                      fatherCurrentAddress_ProofFrontClick,
                      fatherCurrentAddress_ProofFront,
                      fatherCurrentAddress_ProofBackClick,
                      fatherCurrentAddress_ProofBack,
                      motherProfileImage,
                      motherAadharCardImage_FrontClick,
                      motherAadharCard_ImageFront,
                      motherAadharCard_ImageBackClick,
                      motherAadharCard_ImageBack,
                      motherCurrentAddress_ProofFrontClick,
                      motherCurrentAddress_ProofFront,
                      motherCurrentAddress_ProofBackClick,
                      motherCurrentAddress_ProofBack,
                      guardianAadharCard_ImageFrontClick,
                      guardianAadharCard_ImageFront,
                      guardianAadharCard_ImageBackClick,
                      guardianAadharCard_ImageBack,
                      guardianCurrentAddress_ProofFrontClick,
                      guardianCurrentAddress_ProofFront,
                      guardianCurrentAddress_ProofBackClick,
                      guardianCurrentAddress_ProofBack,
                      guardian_aadharCardFrontClick,guardian_aadharCardFront,
                      guardian_aadharCardBackClick,guardian_aadharCardBack,
                      guardian_currentAddressFrontClick,guardian_currentAddressFront,
                      guardian_currentAddressBackClick,guardian_currentAddressBack,
                      guardianProfle_Image,guardianAadharFront,
                      guardianAadharBack,guardianCurrentAddressFront,
                      guardianCurrentAddressBack,guardian_Profile_Picture;



    Uri              fileFatherProfile,fileFatherAadharIDFront,fileFatherAadharIDBack,
                     fileFatherCurrentAddressFront,fileFatherCurrentAddressBack,
                     fileMotherProfile,fileMotherAadharIDFront,fileMotherAadharIDBack,
                     fileMotherCurrentAddressFront,fileMotherCurrentAddressBack;



    Uri              fileGuardianProfilePicture0,fileGuardianAdharCardFront0,
                     fileGuardianAdharCardBack0,fileGuardianCurrentAddressFront0,
                     fileGuardianCurrentAddressBack0,fileGuardianProfilePicture1,
                     fileGuardianAdharCardFront1 ,fileGuardianCurrentAddressFront1,
                     fileGuardianAdharCardBack1,fileGuardianCurrentAddressBack1,
                     fileGuardianProfilePicture2,fileGuardianAdharCardFront2,
                     fileGuardianAdharCardBack2,fileGuardianCurrentAddressFront2,
                     fileGuardianCurrentAddressBack2;


    String str_case = "A";
    Dialog settingsDialog;
    public static int FROM_GALLERY= 1;
    public static int FROM_CAMERA= 2;
    Uri filePath;
    String pathProfile;
    Bitmap bitmap;
    EditText guardian_currebtAddress_number;

    DatabaseReference mref;
    DatabaseReference mref1 =FirebaseDatabase.getInstance().getReference("School/SchoolId/Student_Registration");
    private EditText name;
    private EditText mobileNumber;
    private EditText aadharNumber;
    private EditText emailId;
    private String fatherAddressProofNumber;
    private String motherAddressProofNumber;
    private TextView student_RegistrationNumber;
    private TextView student_Name;
    private CircleImageView student_ProfileImage;
    private TextView student_Class;
    private TextView student_Section;
    private DatabaseReference studentSnapShotRef;
    private String studentClass;
    private String studentSection;
    String studentFName;
    String studentprofilePicture;
    ArrayList<String> stdentReg;
    private String studentRegistrationNumber;
    Activity parentActivity =null;






    public FamilyInfoFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_family_info, container, false);
        parentActivity=(Activity) v.getContext();

        mref = FirebaseDatabase.getInstance().getReference("School/SchoolId/Barriers");
        studentSnapShotRef = FirebaseDatabase.getInstance().getReference("School/SchoolId/Student_Details_SnapShot");
        initViews(v);
        studentdetailsModelArrayList = new ArrayList<>();
        stdentReg = new ArrayList<>();
        getGuardianNumbers();
        getStudentDetailsFromFirebase();
        initListner();
        initFatherTextChangeListner();
        initMotherTextChangeListner();
        initSpinnerForFatherQualification();
        initSpinnerForFatherOccupation();
        initSpinnerForMotherQualification();
        initSpinnerForMotherOccupation();
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


                    NewStudentRegisterModel newStudentRegisterModel = new NewStudentRegisterModel(postSnapShotA.getKey(),studentClass,studentSection,studentFName,studentprofilePicture);
                    studentdetailsModelArrayList.add(newStudentRegisterModel);


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
                int guardianNumber = 0;

                for(DataSnapshot postSnapShotA : dataSnapshot.getChildren()){
                    for(DataSnapshot postSnapShotB : postSnapShotA.getChildren()){
                        Log.d("postSHH", ""+postSnapShotB.getKey());
                        if(postSnapShotB.getKey().equals("number_of_guardians")){
                            Log.d("guardian", ""+postSnapShotB.getValue().toString());
                            guardianNumber = Integer.parseInt(postSnapShotB.getValue().toString());
                            if(guardianNumber!=0) {
                                dynamicCreateGuardianDetails(guardianNumber);
                            }

                        }

                    }
                }
                //dynamicCreateGuardianDetails(guardianNumber);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    private void dynamicCreateGuardianDetails(int guardianNumber) {
        if(guardianNumber!=0) {
            Log.d("elemt", ""+guardianNumber);
            LinearLayout layout_child = new LinearLayout(parentActivity);
            layout_child.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            layout_child.setOrientation(LinearLayout.VERTICAL);
            layoutForGuardians.addView(layout_child);//add layout into parent view
            Log.d("dsa", ""+layoutForGuardians.getChildCount());

            if (config.smallestScreenWidthDp >= 600) {

                for (int i = 1; i <= guardianNumber; i++) {

                    View child = getLayoutInflater().inflate(R.layout.dynamic_add_views_family_info, null);
                    guardian_Number = child.findViewById(R.id.GuardianNumber);
                    guardian_Number.setText("Guardian " + i);
                    guardian_name = child.findViewById(R.id.guardianName);
                    guardian_aadhar_number = child.findViewById(R.id.guardian_aadhar_card);
                    guardian_mobile_number = child.findViewById(R.id.mobileNumberguardian);
                    guardian_emailid = child.findViewById(R.id.emailGuardian);

                    guardian_name.setFilters(new InputFilter[]{
                            new InputFilter() {
                                @Override
                                public CharSequence filter(CharSequence cs, int start,
                                                           int end, Spanned spanned, int dStart, int dEnd) {
                                    // TODO Auto-generated method stub
                                    if (cs.equals("")) {
                                        // for backspace
                                        return cs;
                                    }
                                    if (cs.toString().matches("[a-zA-Z ]+")) {
                                        return cs;
                                    }
                                    return "";
                                }
                            }
                    });

                    guardianName_allEds.add(guardian_name);
                    guardianAadhar_allEds.add(guardian_aadhar_number);
                    guardianMobile_allEds.add(guardian_mobile_number);
                    guardianEmail_allEds.add(guardian_emailid);
                    layout_child.addView(child);
                    clickOnFatherInfoLayout.setClickable(false);
                    clickOnMotherInfoLayout.setClickable(false);
                    clickOnGuardianInfoLayout.setClickable(false);


                }


            }

            //Change guardian giving static
            else {

                Log.d("swejri", ""+guardianNumber);

                if(guardianName_allEds.size()!=0){

                    guardianName_allEds.clear();
                    guardianAadhar_allEds.clear();
                    guardianMobile_allEds.clear();
                    guardianEmail_allEds.clear();
                    guardianGuardian_allProfilePictre.clear();
                    guardianGuardian_allAadharCardPictreFrontClick.clear();
                    guardianGuardian_allAadharCardPictreBackClick.clear();
                    guardianGuardian_allCurrentAddressPictreFrontClick.clear();
                    guardianGuardian_allCurrentAddressPictreBackClick.clear();
                    guardianCurrentAddress_allEds.clear();

                }


                for (int i = 1; i <= guardianNumber; i++) {
                    Log.d("ddsjsd", ""+i);
                    View child = parentActivity.getLayoutInflater().inflate(R.layout.dynamic_add_views_family_info, null);
                    guardian_Number = child.findViewById(R.id.GuardianNumber);
                    guardian_Number.setText("Guardian " + i);

                    guardian_Profile_Picture = child.findViewById(R.id.guardianProfilePicture);
                    guardian_Profile_Picture.setId(10 + i);
                    guardian_Profile_Picture.setOnClickListener(this);
                    guardian_name = child.findViewById(R.id.guardianName);
                    guardian_mobile_number = child.findViewById(R.id.mobileNumberguardian);
                    guardian_aadhar_number = child.findViewById(R.id.guardian_aadhar_card);
                    guardian_currebtAddress_number = child.findViewById(R.id.guardian_currentAddress);
                    guardian_emailid = child.findViewById(R.id.emailGuardian);

                    guardian_aadharCardFrontClick = child.findViewById(R.id.guardianAadharCardImageFrontClick);
                    guardian_aadharCardFrontClick.setId(100 + i);
                    guardian_aadharCardFrontClick.setOnClickListener(this);
                    guardian_aadharCardFront = child.findViewById(R.id.guardianAadharCardImageFront);

                    guardian_aadharCardBackClick = child.findViewById(R.id.guardianAadharCardImageBackClick);
                    guardian_aadharCardBackClick.setId(1000 + i);
                    guardian_aadharCardBackClick.setOnClickListener(this);
                    guardian_aadharCardBack = child.findViewById(R.id.guardianAadharCardImageBack);


                    guardian_currentAddressFrontClick = child.findViewById(R.id.guardianCurrentAddressProofFrontClick);
                    guardian_currentAddressFrontClick.setId(10000 + i);
                    guardian_currentAddressFrontClick.setOnClickListener(this);
                    guardian_currentAddressFront = child.findViewById(R.id.guardianCurrentAddressProofFront);

                    guardian_currentAddressBackClick = child.findViewById(R.id.guardianCurrentAddressProofBackClick);
                    guardian_currentAddressBackClick.setId(100000 + i);
                    guardian_currentAddressBackClick.setOnClickListener(this);
                    guardian_currentAddressBack = child.findViewById(R.id.guardianCurrentAddressProofBack);

                    guardian_name.setFilters(new InputFilter[]{
                            new InputFilter() {
                                @Override
                                public CharSequence filter(CharSequence cs, int start,
                                                           int end, Spanned spanned, int dStart, int dEnd) {
                                    // TODO Auto-generated method stub
                                    if (cs.equals("")) {
                                        // for backspace
                                        return cs;
                                    }
                                    if (cs.toString().matches("[a-zA-Z ]+")) {
                                        return cs;
                                    }
                                    return "";
                                }
                            }
                    });


                    guardianName_allEds.add(guardian_name);
                    guardianAadhar_allEds.add(guardian_aadhar_number);
                    guardianMobile_allEds.add(guardian_mobile_number);
                    guardianEmail_allEds.add(guardian_emailid);
                    guardianGuardian_allProfilePictre.add(guardian_Profile_Picture);
                    guardianGuardian_allAadharCardPictreFrontClick.add(guardian_aadharCardFront);
                    guardianGuardian_allAadharCardPictreBackClick.add(guardian_aadharCardBack);
                    guardianGuardian_allCurrentAddressPictreFrontClick.add(guardian_currentAddressFront);
                    guardianGuardian_allCurrentAddressPictreBackClick.add(guardian_currentAddressBack);
                    guardianCurrentAddress_allEds.add(guardian_currebtAddress_number);
                    layout_child.addView(child);


                }


            }
        }



    }

    private void initMotherTextChangeListner() {

        motherAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

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



                        mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                        cal.set(Calendar.MONTH, mon-1);
                        year = (year<1970)?1970:(year>currentYear-20)?currentYear-20:year;
                        cal.set(Calendar.YEAR, year);
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

                    motherDOB = String.valueOf(y) + "-" + String.valueOf(m)
                            + "-" + String.valueOf(d) ;

                    Log.d("NEWDAT",""+mother_DOB);
                    motherAge.setText(current);
                    motherAge.setSelection(sel < current.length() ? sel : current.length());
                }


            }
        });
    }


    private void initFatherTextChangeListner() {

        fatherAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

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
                        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
                        int currentDay = cal.get(Calendar.DAY_OF_MONTH);

                        //cal.set(currentYear ,currentMonth, currentDay);


                        mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                        cal.set(Calendar.MONTH, mon-1);
                        year = (year<1970)?1970:(year>currentYear-20)?currentYear-20:year;
                        cal.set(Calendar.YEAR, year);
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

                    fatherDOB = String.valueOf(y) + "-" + String.valueOf(m)
                            + "-" + String.valueOf(d) ;





                    fatherAge.setText(current);
                    fatherAge.setSelection(sel < current.length() ? sel : current.length());
                }


            }
        });
    }


    private void initListner() {

        btnUpForFather.setOnClickListener(this);
        btnDownForFather.setOnClickListener(this);
        btnUpForMother.setOnClickListener(this);
        btnDownForMother.setOnClickListener(this);
        btnUpForGuardians.setOnClickListener(this);
        btnDownForGuardians.setOnClickListener(this);
        clickOnFatherInfoLayout.setOnClickListener(this);
        clickOnMotherInfoLayout.setOnClickListener(this);
        clickOnGuardianInfoLayout.setOnClickListener(this);
        submitfamilyinfo.setOnClickListener(this);
        dobCalendarFather.setOnClickListener(this);
        dobCalendarMother.setOnClickListener(this);
        fatherAadhar_FrontImageClick.setOnClickListener(this);
        fatherAadharCard_ImageFront.setOnClickListener(this);
        fatherAadharBack_ImageClick.setOnClickListener(this);
        fatherAadharCard_ImageBack.setOnClickListener(this);
        fatherCurrentAddress_ProofFrontClick.setOnClickListener(this);
        fatherCurrentAddress_ProofFront.setOnClickListener(this);
        fatherCurrentAddress_ProofBackClick.setOnClickListener(this);
        fatherCurrentAddress_ProofBack.setOnClickListener(this);
        motherAadharCardImage_FrontClick.setOnClickListener(this);
        motherAadharCard_ImageFront.setOnClickListener(this);
        motherAadharCard_ImageBackClick.setOnClickListener(this);
        motherAadharCard_ImageBack.setOnClickListener(this);
        motherCurrentAddress_ProofFrontClick.setOnClickListener(this);
        motherCurrentAddress_ProofFront.setOnClickListener(this);
        motherCurrentAddress_ProofBackClick.setOnClickListener(this);
        motherCurrentAddress_ProofBack.setOnClickListener(this);
        fatherProfileImage.setOnClickListener(this);
        motherProfileImage.setOnClickListener(this);


    }

    private void initViews(View v) {
        btnDownForFather                      = v.findViewById(R.id.btnimageViewDownForFather);
        btnUpForFather                        = v.findViewById(R.id.btnimageViewUpForFather);
        btnDownForMother                      = v.findViewById(R.id.btnimageViewDownForMother);
        btnUpForMother                        = v.findViewById(R.id.btnimageViewUpForMother);
        btnUpForGuardians                     = v.findViewById(R.id.btnimageViewUpForGuardians);
        btnDownForGuardians                   = v.findViewById(R.id.btnimageViewDownForGuardians);
        layoutForFather                       = v.findViewById(R.id.layoutForFather);
        layoutForMother                       = v.findViewById(R.id.layoutForMother);
        layoutForGuardians                    = v.findViewById(R.id.layoutForGuardians);
        clickOnFatherInfoLayout               = v.findViewById(R.id.fatherInfoLayoutClick);
        clickOnMotherInfoLayout               = v.findViewById(R.id.motherInfoLayoutClick);
        clickOnGuardianInfoLayout             = v.findViewById(R.id.guardiansInfoLayoutClick);
        submitfamilyinfo                      = v.findViewById(R.id.submitFinalDetailFamily);
        spinnerForFatherQualification         = v.findViewById(R.id.spinnerForQualification);
        spinnerForFatherOccupation            = v.findViewById(R.id.spinnerForOccupation);
        spinnerForMotherQualification         = v.findViewById(R.id.spinnerForMotherQualification);
        spinnerForMotheroccupation            = v.findViewById(R.id.spinnerForMotherOccupation);
        fatherFirstName                       = v.findViewById(R.id.editText_fatherName);
        fatherMiddleName                      = v.findViewById(R.id.editText_FatherMiddleName);
        fatherLastName                        = v.findViewById(R.id.editText_FatherLastName);
        fatherAge                             = v.findViewById(R.id.editText_fatherAge);
        fatherDesignation                     = v.findViewById(R.id.editText_fatherDesignation);
        fatherMobileNumber                    = v.findViewById(R.id.editText_fatherMobileNumber);
        fatherAdharCardorIdNumber             = v.findViewById(R.id.editText_fatherAdharCardNumber);
        fatherEmailId                         = v.findViewById(R.id.editText_fatherEmailId);
        dobCalendarFather                     = v.findViewById(R.id.calendarFather);
        dobCalendarMother                     = v.findViewById(R.id.calendarMother);
        motherFirstName                       = v.findViewById(R.id.editText_motherName);
        motherMiddleName                      = v.findViewById(R.id.editText_MotherMiddleName);
        motherLastName                        = v.findViewById(R.id.editText_MotherLastName);
        motherAge                             = v.findViewById(R.id.editText_motherAge);
        motherDesignation                     = v.findViewById(R.id.editText_motherDesignation);
        motherMobileNumber                    = v.findViewById(R.id.editText_motherMobileNumber);
        motherAdharCardorIdNumber             = v.findViewById(R.id.editText_motherAdharCardNumber);
        motherEmailId                         = v.findViewById(R.id.editText_motherEmailId);
        fatherInfoTxtView                     = v.findViewById(R.id.fatherInfoTextView);
        motherInfoTxtView                     = v.findViewById(R.id.motherInfoTextView);
        guardiansInfoTxtView                  = v.findViewById(R.id.guardiansInfoTextView);
        layoutFather                          = v.findViewById(R.id.linearLayout_father);
        fatherAadhar_FrontImageClick          = v.findViewById(R.id.fatherAadharFrontImageClick);
        fatherAadharCard_ImageFront           = v.findViewById(R.id.fatherAadharCardImageFront);
        fatherAadharBack_ImageClick           = v.findViewById(R.id.fatherAadharBackImageClick);
        fatherAadharCard_ImageBack            = v.findViewById(R.id.fatherAadharCardImageBack);
        fatherCurrentAddress_ProofFrontClick  = v.findViewById(R.id.fatherCurrentAddressProofFrontClick);
        fatherCurrentAddress_ProofFront       = v.findViewById(R.id.fatherCurrentAddressProofFront);
        fatherCurrentAddress_ProofBackClick   = v.findViewById(R.id.fatherCurrentAddressProofBackClick);
        fatherCurrentAddress_ProofBack        = v.findViewById(R.id.fatherCurrentAddressProofBack);
        motherAadharCardImage_FrontClick      = v.findViewById(R.id.motherAadharCardImageFrontClick);
        motherAadharCard_ImageFront           = v.findViewById(R.id.motherAadharCardImageFront);
        motherAadharCard_ImageBackClick       = v.findViewById(R.id.motherAadharCardImageBackClick);
        motherAadharCard_ImageBack            = v.findViewById(R.id.motherAadharCardImageBack);
        motherCurrentAddress_ProofFrontClick  = v.findViewById(R.id.motherCurrentAddressProofFrontClick);
        motherCurrentAddress_ProofFront       = v.findViewById(R.id.motherCurrentAddressProofFront);
        motherCurrentAddress_ProofBackClick   = v.findViewById(R.id.motherCurrentAddressProofBackClick);
        motherCurrentAddress_ProofBack        = v.findViewById(R.id.motherCurrentAddressProofBack);
        father_current_address_Number         = v.findViewById(R.id.father_current_address);
        mother_current_address_number         = v.findViewById(R.id.mother_current_address);
        guardianAadharCard_ImageFrontClick    = v.findViewById(R.id.guardianAadharCardImageFrontClick);
        guardianAadharCard_ImageFront         = v.findViewById(R.id.guardianAadharCardImageFront);
        guardianAadharCard_ImageBackClick     = v.findViewById(R.id.guardianAadharCardImageBackClick);
        guardianAadharCard_ImageBack          = v.findViewById(R.id.guardianAadharCardImageBack);
        guardianCurrentAddress_ProofFrontClick = v.findViewById(R.id.guardianCurrentAddressProofFrontClick);
        guardianCurrentAddress_ProofFront     = v.findViewById(R.id.guardianCurrentAddressProofFront);
        guardianCurrentAddress_ProofBackClick = v.findViewById(R.id.guardianCurrentAddressProofBackClick);
        guardianCurrentAddress_ProofBack      = v.findViewById(R.id.guardianCurrentAddressProofBack);
        config                                = parentActivity.getResources().getConfiguration();
        fatherProfileImage                    = v.findViewById(R.id.fatherImage);
        motherProfileImage                    = v.findViewById(R.id.mother_image);
        student_RegistrationNumber            = v.findViewById(R.id.studentRegistartionNumber);
        student_Name                          = v.findViewById(R.id.studentName);
        student_ProfileImage                  = v.findViewById(R.id.studentProfileImage);
        student_Class                         = v.findViewById(R.id.studentClass);
        student_Section                       = v.findViewById(R.id.studentSection);
    }


    //called spinner for mother occupation from onCreateView----------------------------------------
    private void initSpinnerForMotherOccupation() {

        ArrayList<String> motheroccupationArrayList = new ArrayList<>();
        motheroccupationArrayList.add("-Occupation-");
        motheroccupationArrayList.add("Business");
        motheroccupationArrayList.add("Employee");
        motheroccupationArrayList.add("Unemployee");
        motheroccupationArrayList.add("Housewife");
        CustomSpinnerAdapter customSpinnerAdapter=new CustomSpinnerAdapter(getActivity(),motheroccupationArrayList);
        spinnerForMotheroccupation.setAdapter(customSpinnerAdapter);
        spinnerForMotheroccupation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // qualification_mother = parent.getItemAtPosition(position).toString();

                mother_occupation = parent.getItemAtPosition(position).toString();

                if(position==0 || position==3 || position==4){
                    motherDesignation.setText("");
                    motherDesignation.setFocusable(false);
                    motherDesignation.setFocusableInTouchMode(false);
                }
                else {
                    motherDesignation.setFocusableInTouchMode(true);
                    motherDesignation.setFocusable(true);
                }

                //Toast.makeText(parent.getContext(), "Android Custom Spinner Example Output..." + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    //-------called method for mother qualification from onCreateView-------------------------------
    private void initSpinnerForMotherQualification() {
        ArrayList<String> qualificationArrayList = new ArrayList<>();
        qualificationArrayList.add("-Qualification-");
        qualificationArrayList.add("Under Graduate");
        qualificationArrayList.add("Graduate");
        qualificationArrayList.add("Post Graduate");
        CustomSpinnerAdapter customSpinnerAdapter=new CustomSpinnerAdapter(getActivity(),qualificationArrayList);
        spinnerForMotherQualification.setAdapter(customSpinnerAdapter);
        spinnerForMotherQualification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                mother_qualification = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    //Called method from onCreateView for father Occupation-----------------------------------------

    private void initSpinnerForFatherOccupation() {
        ArrayList<String> occupationArrayList = new ArrayList<>();
        occupationArrayList.add("-Occupation-");
        occupationArrayList.add("Business");
        occupationArrayList.add("Employee");
        occupationArrayList.add("Unemployee");
        CustomSpinnerAdapter customSpinnerAdapter=new CustomSpinnerAdapter(getActivity(),occupationArrayList);
        spinnerForFatherOccupation.setAdapter(customSpinnerAdapter);
        spinnerForFatherOccupation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                father_occupation = parent.getItemAtPosition(position).toString();
                if(position==3 || position==0){
                    fatherDesignation.setText("");
                    fatherDesignation.setFocusable(false);
                    fatherDesignation.setFocusableInTouchMode(false);
                }
                else {
                    fatherDesignation.setFocusable(true);
                    fatherDesignation.setFocusableInTouchMode(true);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    //Called method from OncreateView for father Qualification--------------------------------------
    private void initSpinnerForFatherQualification() {
        ArrayList<String> qualificationArrayList = new ArrayList<>();
        qualificationArrayList.add("-Qualification-");
        qualificationArrayList.add("Under Graduate");
        qualificationArrayList.add("Graduate");
        qualificationArrayList.add("Post Graduate");
        CustomSpinnerAdapter customSpinnerAdapter=new CustomSpinnerAdapter(getActivity(),qualificationArrayList);
        spinnerForFatherQualification.setAdapter(customSpinnerAdapter);
        spinnerForFatherQualification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                father_qualification = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            //___________on clcick father info layout_______________________________________________


            case R.id.calendarFather:
                String fatherDob = "FATHER";
                getDOB(fatherDob);
                break;

            case R.id.calendarMother:
                String motherDob = "MOTHER";
                getDOB(motherDob);
                break;


            case R.id.fatherInfoLayoutClick:
                setUiforFatherInfo();
                if(layoutForMother.getVisibility()== View.VISIBLE){
                    layoutForMother.setVisibility(View.GONE);
                    btnDownForMother.setVisibility(View.VISIBLE);
                    btnUpForMother.setVisibility(View.GONE);
                    motherInfoTxtView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    motherInfoTxtView.setTextColor(getResources().getColor(R.color.whitecolor));

                }

                else if(layoutForGuardians.getVisibility()== View.VISIBLE){
                    layoutForGuardians.setVisibility(View.GONE);
                    btnDownForGuardians.setVisibility(View.VISIBLE);
                    btnUpForGuardians.setVisibility(View.GONE);
                    guardiansInfoTxtView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    guardiansInfoTxtView.setTextColor(getResources().getColor(R.color.whitecolor));

                }
                break;


           //____________On clcik father info layout button down____________________________________
            case R.id.btnimageViewDownForFather:
                setUiforFatherInfo();
                if(layoutForMother.getVisibility()== View.VISIBLE){
                    layoutForMother.setVisibility(View.GONE);
                    btnDownForMother.setVisibility(View.VISIBLE);
                    btnUpForMother.setVisibility(View.GONE);
                    motherInfoTxtView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    motherInfoTxtView.setTextColor(getResources().getColor(R.color.whitecolor));

                }

                else if(layoutForGuardians.getVisibility()== View.VISIBLE){
                    layoutForGuardians.setVisibility(View.GONE);
                    btnDownForGuardians.setVisibility(View.VISIBLE);
                    btnUpForGuardians.setVisibility(View.GONE);
                    guardiansInfoTxtView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    guardiansInfoTxtView.setTextColor(getResources().getColor(R.color.whitecolor));

                }
                break;

            //____________On clcik father info layout button up____________________________________

            case R.id.btnimageViewUpForFather:
                setUiforFatherInfo();
                if(layoutForMother.getVisibility()== View.VISIBLE){
                    layoutForMother.setVisibility(View.GONE);
                    btnDownForMother.setVisibility(View.VISIBLE);
                    btnUpForMother.setVisibility(View.GONE);
                    motherInfoTxtView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    motherInfoTxtView.setTextColor(getResources().getColor(R.color.whitecolor));

                }

                else if(layoutForGuardians.getVisibility()== View.VISIBLE){
                    layoutForGuardians.setVisibility(View.GONE);
                    btnDownForGuardians.setVisibility(View.VISIBLE);
                    btnUpForGuardians.setVisibility(View.GONE);
                    guardiansInfoTxtView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    guardiansInfoTxtView.setTextColor(getResources().getColor(R.color.whitecolor));

                }
                break;


            //____________On clcik mother info layout ______________________________________________

            case R.id.motherInfoLayoutClick:
                setUiforMotherInfo();
                if(layoutForFather.getVisibility()== View.VISIBLE){
                    layoutForFather.setVisibility(View.GONE);
                    btnDownForFather.setVisibility(View.GONE);
                    btnUpForFather.setVisibility(View.VISIBLE);
                    fatherInfoTxtView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    fatherInfoTxtView.setTextColor(getResources().getColor(R.color.whitecolor));
                }

                else if(layoutForGuardians.getVisibility()== View.VISIBLE){
                    layoutForGuardians.setVisibility(View.GONE);
                    btnDownForGuardians.setVisibility(View.VISIBLE);
                    btnUpForGuardians.setVisibility(View.GONE);
                    guardiansInfoTxtView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    guardiansInfoTxtView.setTextColor(getResources().getColor(R.color.whitecolor));

                }


                break;

            //____________On clcik mother info layout button down____________________________________

            case R.id.btnimageViewDownForMother:
                setUiforMotherInfo();

                if(layoutForFather.getVisibility()== View.VISIBLE){
                    layoutForFather.setVisibility(View.GONE);
                    btnDownForFather.setVisibility(View.GONE);
                    btnUpForFather.setVisibility(View.VISIBLE);
                    fatherInfoTxtView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    fatherInfoTxtView.setTextColor(getResources().getColor(R.color.whitecolor));
                }


                else if(layoutForGuardians.getVisibility()== View.VISIBLE){
                    layoutForGuardians.setVisibility(View.GONE);
                    btnDownForGuardians.setVisibility(View.VISIBLE);
                    btnUpForGuardians.setVisibility(View.GONE);
                    guardiansInfoTxtView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    guardiansInfoTxtView.setTextColor(getResources().getColor(R.color.whitecolor));

                }

                break;

            //____________On clcik mother info layout button up____________________________________

            case R.id.btnimageViewUpForMother:
                setUiforMotherInfo();
                if(layoutForFather.getVisibility()== View.VISIBLE){
                    layoutForFather.setVisibility(View.GONE);
                    btnDownForFather.setVisibility(View.GONE);
                    btnUpForFather.setVisibility(View.VISIBLE);
                    fatherInfoTxtView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    fatherInfoTxtView.setTextColor(getResources().getColor(R.color.whitecolor));


                }

                else if(layoutForGuardians.getVisibility()== View.VISIBLE){
                    layoutForGuardians.setVisibility(View.GONE);
                    btnDownForGuardians.setVisibility(View.VISIBLE);
                    btnUpForGuardians.setVisibility(View.GONE);
                    guardiansInfoTxtView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    guardiansInfoTxtView.setTextColor(getResources().getColor(R.color.whitecolor));

                }
                break;
            //____________On clcik guardian info layout ______________________________________________

            case R.id.guardiansInfoLayoutClick:
                setUiforGuardiansInfo();
                if(layoutForMother.getVisibility()== View.VISIBLE){
                    layoutForMother.setVisibility(View.GONE);
                    btnDownForMother.setVisibility(View.VISIBLE);
                    btnUpForMother.setVisibility(View.GONE);
                    motherInfoTxtView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    motherInfoTxtView.setTextColor(getResources().getColor(R.color.whitecolor));

                }
                else if(layoutForFather.getVisibility()== View.VISIBLE){
                    layoutForFather.setVisibility(View.GONE);
                    btnDownForFather.setVisibility(View.GONE);
                    btnUpForFather.setVisibility(View.VISIBLE);
                    fatherInfoTxtView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    fatherInfoTxtView.setTextColor(getResources().getColor(R.color.whitecolor));
                }

                break;

            //____________On clcik guadian info layout button down____________________________________

            case R.id.btnimageViewDownForGuardians:
                setUiforGuardiansInfo();
                if(layoutForMother.getVisibility()== View.VISIBLE){
                    layoutForMother.setVisibility(View.GONE);
                    btnDownForMother.setVisibility(View.VISIBLE);
                    btnUpForMother.setVisibility(View.GONE);
                    motherInfoTxtView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    motherInfoTxtView.setTextColor(getResources().getColor(R.color.whitecolor));

                }

                else if(layoutForFather.getVisibility()== View.VISIBLE){
                    layoutForFather.setVisibility(View.GONE);
                    btnDownForFather.setVisibility(View.GONE);
                    btnUpForFather.setVisibility(View.VISIBLE);
                    fatherInfoTxtView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    fatherInfoTxtView.setTextColor(getResources().getColor(R.color.whitecolor));


                }
                break;


            //____________On clcik guadian info layout button up____________________________________

            case R.id.btnimageViewUpForGuardians:
                setUiforGuardiansInfo();
                if(layoutForMother.getVisibility()== View.VISIBLE){
                    layoutForMother.setVisibility(View.GONE);
                    btnDownForMother.setVisibility(View.VISIBLE);
                    btnUpForMother.setVisibility(View.GONE);
                    motherInfoTxtView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    motherInfoTxtView.setTextColor(getResources().getColor(R.color.whitecolor));

                }
                else if(layoutForFather.getVisibility()== View.VISIBLE){
                    layoutForFather.setVisibility(View.GONE);
                    btnDownForFather.setVisibility(View.GONE);
                    btnUpForFather.setVisibility(View.VISIBLE);
                    fatherInfoTxtView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    fatherInfoTxtView.setTextColor(getResources().getColor(R.color.whitecolor));


                }
                break;


            case R.id.dialog_ll_camera:
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, FROM_CAMERA);
                settingsDialog.dismiss();
                break;


            case R.id.dialog_ll_gallery:
                /*Intent in_gallery=new Intent(Intent.ACTION_PICK);
                in_gallery.setType("image*//*");
                in_gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(in_gallery,FROM_GALLERY);*/

                Intent in_gallery = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(in_gallery,FROM_GALLERY);
                settingsDialog.dismiss();
                break;


            case R.id.fatherImage:
                str_case = "A";
                openDialogForPicture();
                break;


            case R.id.mother_image:
                str_case = "B";
                openDialogForPicture();
                break;

            case  R.id.fatherAadharFrontImageClick:
                str_case = "C";
                openDialogForPicture();
                break;


            case  R.id.fatherAadharBackImageClick:
                str_case = "D";
                openDialogForPicture();
                break;

            case  R.id.fatherCurrentAddressProofFrontClick:
                str_case = "E";
                openDialogForPicture();
                break;

            case  R.id.fatherCurrentAddressProofBackClick:
                str_case = "F";
                openDialogForPicture();
                break;


            case  R.id.motherAadharCardImageFrontClick:
                str_case = "G";
                openDialogForPicture();
                break;


            case  R.id.motherAadharCardImageBackClick:
                str_case = "H";
                openDialogForPicture();
                break;

            case  R.id.motherCurrentAddressProofFrontClick:
                str_case = "I";
                openDialogForPicture();
                break;

            case  R.id.motherCurrentAddressProofBackClick:
                str_case = "J";
                openDialogForPicture();
                break;

            case 11:
                str_case = "K";
                openDialogForPicture();
                break;

            case 12:
                str_case = "L";
                openDialogForPicture();
                break;

            case 13:
                str_case = "M";
                openDialogForPicture();
                break;


            case 101:
                str_case = "N";
                openDialogForPicture();
                break;


            case 102:
                str_case = "O";
                openDialogForPicture();
                break;


            case 103:
                str_case = "P";
                openDialogForPicture();
                break;


            case 1001:
                str_case = "Q";
                openDialogForPicture();
                break;

            case 1002:
                str_case = "R";
                openDialogForPicture();
                break;

            case 1003:
                str_case = "S";
                openDialogForPicture();
                break;


            case 10001:
                str_case = "T";
                openDialogForPicture();
                break;

            case 10002:
                str_case = "U";
                openDialogForPicture();
                break;

            case 10003:
                str_case = "V";
                openDialogForPicture();
                break;

            case 100001:
                str_case = "W";
                openDialogForPicture();
                break;

            case 100002:
                str_case = "X";
                openDialogForPicture();
                break;

            case 100003:
                str_case = "Y";
                openDialogForPicture();
                break;




            //____________On clcik final submission_________________________________________________

            case R.id.submitFinalDetailFamily:

                if(!student_RegistrationNumber.getText().toString().equals("Registration Number")){
                    if (fatherProfileImage.getDrawable()==null){
                        Toast.makeText(getActivity(), "Please upload father profile picture", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    else if(!ValidationViews.EditTextNullValidate(fatherFirstName )){
                        if(layoutForFather.getVisibility()== View.GONE){
                            layoutForFather.setVisibility(View.VISIBLE);
                            changeUIFather();
                            fatherFirstName.requestFocus();
                        }
                        return;
                    }
                    else if(!ValidationViews.EditTextNullValidate(fatherLastName)){
                        if(layoutForFather.getVisibility()== View.GONE){
                            layoutForFather.setVisibility(View.VISIBLE);
                            changeUIFather();
                            fatherLastName.requestFocus();
                        }
                        return;
                    }
                    else if(!ValidationViews.EditTextNullValidate(fatherAge)){
                        if(layoutForFather.getVisibility()== View.GONE){
                            layoutForFather.setVisibility(View.VISIBLE);
                            changeUIFather();
                            fatherAge.requestFocus();
                        }
                        return;
                    }

                    else if(!ValidationViews.EditTextNullValidate(fatherMobileNumber)){
                        if(layoutForFather.getVisibility()== View.GONE){
                            layoutForFather.setVisibility(View.VISIBLE);
                            changeUIFather();
                            fatherMobileNumber.requestFocus();
                        }
                        return;
                    }



                    else if(!ValidationViews.EditTextMobileNumberValidate(fatherMobileNumber)){
                        if(layoutForFather.getVisibility()== View.GONE){
                            layoutForFather.setVisibility(View.VISIBLE);
                            changeUIFather();
                            fatherMobileNumber.requestFocus();
                        }
                        return;
                    }

                    else if(spinnerForFatherQualification.getSelectedItemPosition()==0){
                        if(layoutForFather.getVisibility()== View.GONE){
                            layoutForFather.setVisibility(View.VISIBLE);
                            changeUIFather();
                        }
                        Toast.makeText(getActivity(), "Please select father qualification", Toast.LENGTH_SHORT).show();
                        return;

                    }

               /* else if(spinnerForFatherQualification.getSelectedItemPosition()==2){
                    fatherDesignation.setFocusable(true);
                    fatherDesignation.setFocusableInTouchMode(true);

                }*/

                    else if(spinnerForFatherOccupation.getSelectedItemPosition()==0 ){
                        if(layoutForFather.getVisibility()== View.GONE){
                            layoutForFather.setVisibility(View.VISIBLE);
                            changeUIFather();

                        }
                        Toast.makeText(getActivity(), "Please select father  occupation", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    else if(!ValidationViews.EditTextNullValidate(fatherEmailId)){
                        if(layoutForFather.getVisibility()== View.GONE){
                            layoutForFather.setVisibility(View.VISIBLE);
                            changeUIFather();
                            fatherEmailId.requestFocus();
                        }
                        return;
                    }



                    else if(!ValidationViews.EditTextEmailPatternValidate(fatherEmailId)){
                        if(layoutForFather.getVisibility()== View.GONE){
                            layoutForFather.setVisibility(View.VISIBLE);
                            changeUIFather();
                            fatherEmailId.setError("Invalid email");
                            fatherEmailId.requestFocus();
                        }
                    }


                    else if(!ValidationViews.EditTextNullValidate(fatherAdharCardorIdNumber)){
                        if(layoutForFather.getVisibility()== View.GONE){
                            layoutForFather.setVisibility(View.VISIBLE);
                            changeUIFather();
                            fatherAdharCardorIdNumber.requestFocus();
                        }
                        return;
                    }

                    else if(!ValidationViews.EditTextNumberOfDigitsValidate(fatherAdharCardorIdNumber , 12)){
                        if(layoutForFather.getVisibility()== View.GONE){
                            layoutForFather.setVisibility(View.VISIBLE);
                            changeUIFather();
                            fatherAdharCardorIdNumber.setError("Invalid aadhar number");
                            fatherAdharCardorIdNumber.requestFocus();
                        }

                    }

                    else if(fatherAadharCard_ImageFront.getDrawable()==null){
                        Toast.makeText(getActivity(), "Upload father aadhar card front image", Toast.LENGTH_SHORT).show();

                    }

                    else if(fatherAadharCard_ImageBack.getDrawable()==null){
                        Toast.makeText(getActivity(), "Upload father aadhar card back image", Toast.LENGTH_SHORT).show();

                    }

                    else if(!ValidationViews.EditTextNullValidate(father_current_address_Number)){
                        if(layoutForFather.getVisibility()== View.GONE){
                            layoutForFather.setVisibility(View.VISIBLE);
                            changeUIFather();
                            father_current_address_Number.requestFocus();
                        }
                        return;
                    }



                    else if(!ValidationViews.EditTextNumberOfDigitsValidate(father_current_address_Number , 15)){
                        if(layoutForFather.getVisibility()== View.GONE){
                            layoutForFather.setVisibility(View.VISIBLE);
                            changeUIFather();
                            father_current_address_Number.setError("Invalid current address number");
                            father_current_address_Number.requestFocus();
                        }

                    }

                    else if(fatherCurrentAddress_ProofFront.getDrawable()==null){
                        Toast.makeText(getActivity(), "Upload father current address front image", Toast.LENGTH_SHORT).show();

                    }

                    else if(fatherCurrentAddress_ProofBack.getDrawable()==null){
                        Toast.makeText(getActivity(), "Upload father current address back image", Toast.LENGTH_SHORT).show();

                    }


                    else if (motherProfileImage.getDrawable()==null){
                        Toast.makeText(getActivity(), "Please upload mother profile picture", Toast.LENGTH_SHORT).show();
                    }



                    else if(!ValidationViews.EditTextNullValidate(motherFirstName)){
                        if(layoutForMother.getVisibility()== View.GONE){
                            layoutForMother.setVisibility(View.VISIBLE);
                            changeUIMother();
                            motherFirstName.requestFocus();
                        }
                        return;
                    }

                    else if(!ValidationViews.EditTextNullValidate(motherLastName)){
                        if(layoutForMother.getVisibility()== View.GONE){
                            layoutForMother.setVisibility(View.VISIBLE);
                            changeUIMother();

                            motherLastName.requestFocus();
                        }
                        return;

                    }

                    else if(!ValidationViews.EditTextNullValidate(motherAge)){
                        if(layoutForMother.getVisibility()== View.GONE){
                            layoutForMother.setVisibility(View.VISIBLE);
                            changeUIMother();

                            motherLastName.requestFocus();
                        }
                        return;

                    }

                    else if(!ValidationViews.EditTextNullValidate(motherMobileNumber)){
                        if(layoutForMother.getVisibility()== View.GONE){
                            layoutForMother.setVisibility(View.VISIBLE);
                            changeUIMother();

                            motherMobileNumber.requestFocus();
                        }
                        return;

                    }

                    else if(!ValidationViews.EditTextMobileNumberValidate(motherMobileNumber)){
                        if(layoutForMother.getVisibility()== View.GONE){
                            layoutForMother.setVisibility(View.VISIBLE);
                            changeUIMother();

                            motherMobileNumber.requestFocus();
                        }
                        return;

                    }


                    else if(spinnerForMotherQualification.getSelectedItemPosition()==0){
                        if(layoutForMother.getVisibility()== View.GONE){
                            layoutForMother.setVisibility(View.VISIBLE);
                            changeUIMother();

                        }
                        Toast.makeText(getActivity(), "Please select mother  qualification", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    else if(spinnerForMotheroccupation.getSelectedItemPosition()==0){
                        if(layoutForMother.getVisibility()== View.GONE){
                            layoutForMother.setVisibility(View.VISIBLE);
                            changeUIMother();

                        }
                        Toast.makeText(getActivity(), "Please select mother  occupation", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    else if(!ValidationViews.EditTextNullValidate(motherEmailId)){
                        if(layoutForMother.getVisibility()== View.GONE){
                            layoutForMother.setVisibility(View.VISIBLE);
                            changeUIMother();

                            motherEmailId.requestFocus();
                        }
                        return;

                    }

                    else if(!ValidationViews.EditTextEmailPatternValidate(motherEmailId)){
                        if(layoutForMother.getVisibility()== View.GONE){
                            layoutForMother.setVisibility(View.VISIBLE);
                            changeUIMother();
                            motherEmailId.setError("Invalid email");
                            motherEmailId.requestFocus();
                        }
                    }


                    else if(!ValidationViews.EditTextNullValidate(motherAdharCardorIdNumber)){
                        if(layoutForMother.getVisibility()== View.GONE){
                            layoutForMother.setVisibility(View.VISIBLE);
                            motherAdharCardorIdNumber.requestFocus();
                            changeUIMother();

                        }
                        return;

                    }

                    else if(!ValidationViews.EditTextNumberOfDigitsValidate(motherAdharCardorIdNumber , 12)){
                        if(layoutForMother.getVisibility()== View.GONE){
                            layoutForMother.setVisibility(View.VISIBLE);
                            changeUIMother();

                            motherAdharCardorIdNumber.setError("Invalid aadhar number");
                            motherAdharCardorIdNumber.requestFocus();
                        }

                    }

                    else if(motherAadharCard_ImageFront.getDrawable()==null){
                        Toast.makeText(getActivity(), "Upload mother aadhar card front image", Toast.LENGTH_SHORT).show();

                    }

                    else if(motherAadharCard_ImageBack.getDrawable()==null){
                        Toast.makeText(getActivity(), "Upload mother aadhar card back image", Toast.LENGTH_SHORT).show();

                    }


                    else if(!ValidationViews.EditTextNullValidate(mother_current_address_number)){
                        if(layoutForMother.getVisibility()== View.GONE){
                            layoutForMother.setVisibility(View.VISIBLE);
                            changeUIMother();
                            mother_current_address_number.requestFocus();
                        }
                        return;
                    }



                    else if(!ValidationViews.EditTextNumberOfDigitsValidate(mother_current_address_number , 15)){
                        if(layoutForMother.getVisibility()== View.GONE){
                            layoutForMother.setVisibility(View.VISIBLE);
                            changeUIMother();
                            mother_current_address_number.setError("Invalid current address number");
                            mother_current_address_number.requestFocus();
                        }

                    }
                    else if(motherCurrentAddress_ProofFront.getDrawable()==null){
                        Toast.makeText(getActivity(), "Upload mother current address front image", Toast.LENGTH_SHORT).show();

                    }

                    else if(motherCurrentAddress_ProofBack.getDrawable()==null){
                        Toast.makeText(getActivity(), "Upload mother current address back image", Toast.LENGTH_SHORT).show();

                    }



                    //if edittext not null then send all detail to firebase_____________________________
                    else {

                        if(a.equals("VR")) {
                            for (int i = 0; i < guardianName_allEds.size(); i++) {

                                guardian_name = guardianName_allEds.get(i);
                                guardian_mobile_number = guardianMobile_allEds.get(i);
                                guardian_aadhar_number = guardianAadhar_allEds.get(i);
                                guardian_emailid = guardianEmail_allEds.get(i);
                                guardianProfle_Image =guardianGuardian_allProfilePictre.get(i);
                                guardianAadharFront = guardianGuardian_allAadharCardPictreFrontClick.get(i);
                                guardianAadharBack = guardianGuardian_allAadharCardPictreBackClick.get(i);
                                guardianCurrentAddressFront = guardianGuardian_allCurrentAddressPictreFrontClick.get(i);
                                guardianCurrentAddressBack = guardianGuardian_allCurrentAddressPictreBackClick.get(i);
                                guardianCurrentAddressNumber = guardianCurrentAddress_allEds.get(i);

                                if(guardianProfle_Image.getDrawable()==null){
                                    Toast.makeText(getActivity(), "Please select guardian photo", Toast.LENGTH_SHORT).show();
                                    return;

                                }

                                else if(!ValidationViews.EditTextNullValidate(guardian_name)){
                                    if(layoutForGuardians.getVisibility()== View.GONE){
                                        layoutForGuardians.setVisibility(View.VISIBLE);
                                        changeUIGuardians();
                                        guardian_name.requestFocus();
                                    }
                                    return;
                                }

                                if(!ValidationViews.EditTextNullValidate(guardian_mobile_number)){
                                    if(layoutForGuardians.getVisibility()== View.GONE){
                                        layoutForGuardians.setVisibility(View.VISIBLE);
                                        changeUIGuardians();
                                        guardian_mobile_number.requestFocus();
                                    }
                                    return;
                                }

                                else if(!ValidationViews.EditTextMobileNumberValidate(guardian_mobile_number)){
                                    if(layoutForGuardians.getVisibility()== View.GONE){
                                        layoutForGuardians.setVisibility(View.VISIBLE);
                                        changeUIGuardians();
                                        guardian_mobile_number.requestFocus();
                                    }
                                    return;

                                }

                                else if(!ValidationViews.EditTextNullValidate(guardian_emailid)){
                                    if(layoutForGuardians.getVisibility()== View.GONE){
                                        layoutForGuardians.setVisibility(View.VISIBLE);
                                        changeUIGuardians();
                                        guardian_emailid.requestFocus();
                                    }
                                    return;
                                }

                                else if(!ValidationViews.EditTextEmailPatternValidate(guardian_emailid)){
                                    if(layoutForGuardians.getVisibility()== View.GONE){
                                        layoutForGuardians.setVisibility(View.VISIBLE);
                                        changeUIGuardians();
                                        guardian_emailid.setError("Invalid email");
                                        guardian_emailid.requestFocus();
                                    }
                                    return;
                                }


                                if(!ValidationViews.EditTextNullValidate(guardian_aadhar_number)){
                                    if(layoutForGuardians.getVisibility()== View.GONE){
                                        layoutForGuardians.setVisibility(View.VISIBLE);
                                        changeUIGuardians();
                                        guardian_aadhar_number.requestFocus();
                                    }
                                    return;
                                }

                                else if(!ValidationViews.EditTextNumberOfDigitsValidate(guardian_aadhar_number , 12)){
                                    if(layoutForGuardians.getVisibility()== View.GONE){
                                        layoutForGuardians.setVisibility(View.VISIBLE);
                                        changeUIGuardians();
                                        guardian_aadhar_number.setError("Invalid aadhar number");
                                        guardian_aadhar_number.requestFocus();
                                    }
                                }

                                else if(guardianAadharFront.getDrawable()==null){
                                    Toast.makeText(getActivity(), "Please select aadhar card front image", Toast.LENGTH_SHORT).show();
                                    return;

                                }
                                else if(guardianAadharBack.getDrawable()==null){
                                    Toast.makeText(getActivity(), "Please select aadhar card back image", Toast.LENGTH_SHORT).show();
                                    return;

                                }

                                if(!ValidationViews.EditTextNullValidate(guardianCurrentAddressNumber)){
                                    if(layoutForGuardians.getVisibility()== View.GONE){
                                        layoutForGuardians.setVisibility(View.VISIBLE);
                                        changeUIGuardians();
                                        guardianCurrentAddressNumber.requestFocus();
                                    }
                                    return;
                                }

                                else if(!ValidationViews.EditTextNumberOfDigitsValidate(guardianCurrentAddressNumber,15)){
                                    if(layoutForGuardians.getVisibility()== View.GONE){
                                        layoutForGuardians.setVisibility(View.VISIBLE);
                                        changeUIGuardians();
                                        guardianCurrentAddressNumber.requestFocus();
                                    }
                                    return;
                                }


                                else if(guardianCurrentAddressFront.getDrawable()==null){
                                    Toast.makeText(getActivity(), "Please select current address front image", Toast.LENGTH_SHORT).show();
                                    return;

                                }

                                else if(guardianCurrentAddressBack.getDrawable()==null){
                                    Toast.makeText(getActivity(), "Please select current address back image", Toast.LENGTH_SHORT).show();
                                    return;

                                }

                                //sending the value of dynamically created edittext to firebase________________

                            }

                        }

                        if(a.equals("VR")) {


                            father_FirstName = fatherFirstName.getText().toString().trim();
                            father_MiddleName = fatherMiddleName.getText().toString().trim();
                            father_Lastname = fatherLastName.getText().toString().trim();
                            str_father_DOB = fatherAge.getText().toString().trim();
                            father_MobileNumber = fatherMobileNumber.getText().toString().trim();
                            father_Qualification = father_qualification;
                            father_Occupation = father_occupation;
                            father_Designation = fatherDesignation.getText().toString().trim();
                            father_AadharCard  =fatherAdharCardorIdNumber.getText().toString().trim();
                            fatherAddressProofNumber = father_current_address_Number.getText().toString();

                            father_EmailID = fatherEmailId.getText().toString().trim();


                            mother_FirstName = motherFirstName.getText().toString().trim();
                            mother_MiddleName = motherMiddleName.getText().toString().trim();
                            mother_Lastname = motherLastName.getText().toString().trim();
                            str_mother_DOB = motherAge.getText().toString().trim();
                            mother_MobileNumber = motherMobileNumber.getText().toString().trim();
                            mother_Qualification = mother_qualification;
                            mother_Occupation = mother_occupation;
                            mother_Designation = motherDesignation.getText().toString().trim();
                            mother_AadharCard  =motherAdharCardorIdNumber.getText().toString().trim();
                            motherAddressProofNumber = mother_current_address_number.getText().toString();
                            mother_EmailID = motherEmailId.getText().toString().trim();




                            sendingFamilyDetailsToServer();




                        }



                    }
                }

                else {
                    Toast.makeText(getActivity(), "Please select student", Toast.LENGTH_SHORT).show();
                }



                break;
        }

    }

    private void getDOB(final String dob) {

        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.datepicker);
        dialog.setTitle("");
        DatePicker datePicker = (DatePicker) dialog.findViewById(R.id.datePickerDialog);
        final Calendar calendar = Calendar.getInstance();
        selectedYear = String.valueOf(calendar.get(Calendar.YEAR));
        selectedMonth = String.valueOf(calendar.get(Calendar.MONTH));
        selectedDate = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        calendar.add(Calendar.YEAR, -20);

        if(selectedDate.length()==1){
            selectedDate = "0"+selectedDate;
        }

        if(selectedMonth.length()==1){
            selectedMonth = "0"+selectedMonth;
        }

        Log.e("selected date", selectedDate+"");
        Log.e("selected month", selectedMonth+"");
        Log.e("selected year", selectedYear+"");


        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {

                String newMonth= "" ,stringNewDate="", stringyear = String.valueOf(year);

                if(String.valueOf(month+1).length()==1){
                    newMonth = "0"+(month+1);
                }
                else {
                    newMonth = String.valueOf(month+1);
                }

                if(String.valueOf(dayOfMonth).length()==1){
                    stringNewDate = "0"+dayOfMonth;
                }
                else {
                    stringNewDate = String.valueOf(dayOfMonth);
                }
                Log.e("month", ""+newMonth);
                Log.e("date", ""+stringNewDate);
                Log.e("year", ""+stringyear);




                switch (dob){

                    case "FATHER":
                        if(selectedDate.equals(stringNewDate) && selectedMonth.equals(newMonth) && selectedYear.equals(stringyear)) {
                            String date = String.valueOf(stringNewDate) + "//" + String.valueOf(newMonth)
                                    + "//" + String.valueOf(stringyear);

                            fatherAge.setText(date);

                            dialog.dismiss();
                        }else {

                            if(!selectedDate.equals(stringNewDate)){
                                String date = String.valueOf(stringNewDate) + "//" + String.valueOf(newMonth)
                                        + "//" + String.valueOf(stringyear);

                                fatherAge.setText(date);

                                dialog.dismiss();
                            }else {
                                if(!selectedMonth.equals(newMonth)){
                                    String date = String.valueOf(stringNewDate) + "//" + String.valueOf(newMonth)
                                            + "//" + String.valueOf(stringyear);

                                    fatherAge.setText(date);
                                    dialog.dismiss();
                                }
                            }
                        }
                        break;



                    case "MOTHER":
                        if(selectedDate.equals(stringNewDate) && selectedMonth.equals(newMonth) && selectedYear.equals(stringyear)) {
                            String date = String.valueOf(stringNewDate) + "//" + String.valueOf(newMonth)
                                    + "//" + String.valueOf(stringyear);

                            motherAge.setText(date);

                            dialog.dismiss();
                        }else {

                            if(!selectedDate.equals(stringNewDate)){
                                String date = String.valueOf(stringNewDate) + "//" + String.valueOf(newMonth)
                                        + "//" + String.valueOf(stringyear);

                                motherAge.setText(date);

                                dialog.dismiss();
                            }else {
                                if(!selectedMonth.equals(newMonth)){
                                    String date = String.valueOf(stringNewDate) + "//" + String.valueOf(newMonth)
                                            + "//" + String.valueOf(stringyear);

                                    motherAge.setText(date);
                                    dialog.dismiss();
                                }
                            }
                        }
                        break;
                }

                selectedDate= String.valueOf(dayOfMonth);
                selectedMonth= String.valueOf(month);
                selectedYear= String.valueOf(year);
            }
        });
        dialog.show();
        datePicker.setMaxDate(calendar.getTimeInMillis());
    }


    private void openDialogForPicture() {
        settingsDialog = new Dialog(getActivity());
        settingsDialog.setContentView(R.layout.attach_dialog_profile_picture);
        settingsDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);


        settingsDialog.setTitle("Choose your option..");



        LinearLayout dialogcamera = settingsDialog.findViewById(R.id.dialog_ll_camera);
        LinearLayout dialoggallery = settingsDialog.findViewById(R.id.dialog_ll_gallery);


        dialogcamera.setOnClickListener(this);
        dialoggallery.setOnClickListener(this);
        settingsDialog.show();
    }

    private void changeUIFather() {
        btnDownForFather.setVisibility(View.VISIBLE);
        btnUpForFather.setVisibility(View.GONE);
        fatherInfoTxtView.setBackgroundColor(getResources().getColor(R.color.whitecolor));
        fatherInfoTxtView.setTextColor(getResources().getColor(R.color.colorPrimary));
    }

    private void changeUIMother() {
        btnDownForMother.setVisibility(View.GONE);
        btnUpForMother.setVisibility(View.VISIBLE);
        motherInfoTxtView.setBackgroundColor(getResources().getColor(R.color.whitecolor));
        motherInfoTxtView.setTextColor(getResources().getColor(R.color.colorPrimary));
    }

    private void changeUIGuardians() {
        btnDownForGuardians.setVisibility(View.GONE);
        btnUpForGuardians.setVisibility(View.VISIBLE);
        guardiansInfoTxtView.setBackgroundColor(getResources().getColor(R.color.whitecolor));
        guardiansInfoTxtView.setTextColor(getResources().getColor(R.color.colorPrimary));
    }

    private void  sendingFamilyDetailsToServer() {

            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Storing data please wait...");
            progressDialog.show();


            final NewStudentRegisterModel father_personal_info  = new NewStudentRegisterModel(father_FirstName,father_MiddleName,father_Lastname,
                    fatherDOB,father_Qualification,father_Occupation,
                    father_Designation,father_MobileNumber,father_AadharCard,fatherAddressProofNumber,father_EmailID);


            final NewStudentRegisterModel mother_personal_info  = new NewStudentRegisterModel(1,mother_FirstName,
                    mother_MiddleName,mother_Lastname,motherDOB,
                    mother_Qualification,mother_Occupation,mother_Designation,mother_MobileNumber,mother_AadharCard,
                    motherAddressProofNumber,mother_EmailID);

            final StorageReference storageReference = mStorageRef.child(Constant.FINAL_REGISTRATION_ID).child("family_information");


            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    mref1.child(Constant.FINAL_REGISTRATION_ID).child("family_information").child("father_personal_info").setValue(father_personal_info);
                    mref1.child(Constant.FINAL_REGISTRATION_ID).child("family_information").child("mother_personal_info").setValue(mother_personal_info);

                    storageReference.child("father_personal_info").putFile(fileFatherProfile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            mref1.child(Constant.FINAL_REGISTRATION_ID).child("family_information").child("father_personal_info")
                                    .child("father_profile_picture").setValue(taskSnapshot.getDownloadUrl().toString());


                        }
                    });

                    storageReference.child("father_personal_info").putFile(fileFatherAadharIDFront).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            mref1.child(Constant.FINAL_REGISTRATION_ID).child("family_information").child("father_personal_info")
                                    .child("father_aadhar_card_number_front_Image").setValue(taskSnapshot.getDownloadUrl().toString());


                        }
                    });


                    storageReference.child("father_personal_info").putFile(fileFatherAadharIDBack).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            mref1.child(Constant.FINAL_REGISTRATION_ID).child("family_information").child("father_personal_info")
                                    .child("father_aadhar_card_number_back_Image").setValue(taskSnapshot.getDownloadUrl().toString());


                        }
                    });

                    storageReference.child("father_personal_info").putFile(fileFatherCurrentAddressFront).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            mref1.child(Constant.FINAL_REGISTRATION_ID).child("family_information").child("father_personal_info")
                                    .child("father_address_number_front_Image").setValue(taskSnapshot.getDownloadUrl().toString());


                        }
                    });

                    storageReference.child("father_personal_info").putFile(fileFatherCurrentAddressBack).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            mref1.child(Constant.FINAL_REGISTRATION_ID).child("family_information").child("father_personal_info")
                                    .child("father_address_number_back_Image").setValue(taskSnapshot.getDownloadUrl().toString());


                        }
                    });

                    storageReference.child("mother_personal_info").putFile(fileMotherProfile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            mref1.child(Constant.FINAL_REGISTRATION_ID).child("family_information").child("mother_personal_info")
                                    .child("mother_profile_picture").setValue(taskSnapshot.getDownloadUrl().toString());


                        }
                    });


                    storageReference.child("mother_personal_info").putFile(fileMotherAadharIDFront).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            mref1.child(Constant.FINAL_REGISTRATION_ID).child("family_information").child("mother_personal_info")
                                    .child("mother_aadhar_card_number_front_Image").setValue(taskSnapshot.getDownloadUrl().toString());


                        }
                    });

                    storageReference.child("mother_personal_info").putFile(fileMotherAadharIDBack).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            mref1.child(Constant.FINAL_REGISTRATION_ID).child("family_information").child("mother_personal_info")
                                    .child("mother_aadhar_card_number_back_Image").setValue(taskSnapshot.getDownloadUrl().toString());


                        }
                    });

                    storageReference.child("mother_personal_info").putFile(fileMotherCurrentAddressFront).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            mref1.child(Constant.FINAL_REGISTRATION_ID).child("family_information").child("mother_personal_info")
                                    .child("mother_address_number_front_Image").setValue(taskSnapshot.getDownloadUrl().toString());


                        }
                    });

                    storageReference.child("mother_personal_info").putFile(fileMotherCurrentAddressBack).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            mref1.child(Constant.FINAL_REGISTRATION_ID).child("family_information").child("mother_personal_info")
                                    .child("mother_address_number_back_Image").setValue(taskSnapshot.getDownloadUrl().toString());


                        }
                    });


                    DatabaseReference db_gaurdian = mref1.child(Constant.FINAL_REGISTRATION_ID).child("family_information").child("guardian_personal_info");

                    for (int i = 0; i < guardianName_allEds.size(); i++) {

                        name = guardianName_allEds.get(i);
                        mobileNumber = guardianMobile_allEds.get(i);
                        aadharNumber = guardianAadhar_allEds.get(i);
                        emailId = guardianEmail_allEds.get(i);
                        guardian_currebtAddress_number = guardianCurrentAddress_allEds.get(i);
                        guardian_Profile_Picture = guardianGuardian_allProfilePictre.get(i);


                        DatabaseReference db_child = db_gaurdian.child("guardian" + (i + 1));
                        db_child.child("guardian_name").setValue(name.getText().toString());
                        db_child.child("guardian_mobile_number").setValue(mobileNumber.getText().toString());
                        db_child.child("guardian_aadhar_number").setValue(aadharNumber.getText().toString());
                        db_child.child("guardian_address_proof_number").setValue(guardian_currebtAddress_number.getText().toString());
                        db_child.child("guardian_email_id").setValue(emailId.getText().toString());


                        //sending the value of dynamically created edittext to firebase________________


                    }

                    if(fileGuardianProfilePicture0!=null) {
                        storageReference.child("guardian_personal_info").putFile(fileGuardianProfilePicture0).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                mref1.child(Constant.FINAL_REGISTRATION_ID).child("family_information").child("guardian_personal_info")
                                        .child("guardian1").child("guardian_profile_picture").setValue(taskSnapshot.getDownloadUrl().toString());


                            }
                        });
                    }

                    if(fileGuardianAdharCardFront0!=null) {
                        storageReference.child("guardian_personal_info").putFile(fileGuardianAdharCardFront0).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                mref1.child(Constant.FINAL_REGISTRATION_ID).child("family_information").child("guardian_personal_info")
                                        .child("guardian1").child("guardian_aadhar_card_front_image").setValue(taskSnapshot.getDownloadUrl().toString());


                            }
                        });
                    }

                    if(fileGuardianAdharCardBack0!=null) {
                        storageReference.child("guardian_personal_info").putFile(fileGuardianAdharCardBack0).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                mref1.child(Constant.FINAL_REGISTRATION_ID).child("family_information").child("guardian_personal_info")
                                        .child("guardian1").child("guardian_aadhar_card_back_image").setValue(taskSnapshot.getDownloadUrl().toString());


                            }
                        });
                    }

                    if(fileGuardianCurrentAddressFront0!=null) {
                        storageReference.child("guardian_personal_info").putFile(fileGuardianCurrentAddressFront0).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                mref1.child(Constant.FINAL_REGISTRATION_ID).child("family_information").child("guardian_personal_info")
                                        .child("guardian1").child("guardian_address_proof_front_image").setValue(taskSnapshot.getDownloadUrl().toString());


                            }
                        });
                    }

                    if(fileGuardianCurrentAddressBack0!=null) {
                        storageReference.child("guardian_personal_info").putFile(fileGuardianCurrentAddressBack0).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                mref1.child(Constant.FINAL_REGISTRATION_ID).child("family_information").child("guardian_personal_info")
                                        .child("guardian1").child("guardian_address_proof_back_image").setValue(taskSnapshot.getDownloadUrl().toString());


                            }
                        });
                    }


                /*---------------------------------------------------------------*/

                    if(fileGuardianProfilePicture1!=null) {
                        storageReference.child("guardian_personal_info").putFile(fileGuardianProfilePicture1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                mref1.child(Constant.FINAL_REGISTRATION_ID).child("family_information").child("guardian_personal_info")
                                        .child("guardian2").child("guardian_profile_picture").setValue(taskSnapshot.getDownloadUrl().toString());


                            }
                        });
                    }

                    if(fileGuardianAdharCardFront1!=null) {
                        storageReference.child("guardian_personal_info").putFile(fileGuardianAdharCardFront1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                mref1.child(Constant.FINAL_REGISTRATION_ID).child("family_information").child("guardian_personal_info")
                                        .child("guardian2").child("guardian_aadhar_card_front_image").setValue(taskSnapshot.getDownloadUrl().toString());


                            }
                        });
                    }

                    if(fileGuardianAdharCardBack1!=null) {
                        storageReference.child("guardian_personal_info").putFile(fileGuardianAdharCardBack1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                mref1.child(Constant.FINAL_REGISTRATION_ID).child("family_information").child("guardian_personal_info")
                                        .child("guardian2").child("guardian_aadhar_card_back_image").setValue(taskSnapshot.getDownloadUrl().toString());


                            }
                        });
                    }

                    if(fileGuardianCurrentAddressFront1!=null) {
                        storageReference.child("guardian_personal_info").putFile(fileGuardianCurrentAddressFront1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                mref1.child(Constant.FINAL_REGISTRATION_ID).child("family_information").child("guardian_personal_info")
                                        .child("guardian2").child("guardian_address_proof_front_image").setValue(taskSnapshot.getDownloadUrl().toString());


                            }
                        });
                    }

                    if(fileGuardianCurrentAddressBack1!=null) {
                        storageReference.child("guardian_personal_info").putFile(fileGuardianCurrentAddressBack1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                mref1.child(Constant.FINAL_REGISTRATION_ID).child("family_information").child("guardian_personal_info")
                                        .child("guardian2").child("guardian_address_proof_back_image").setValue(taskSnapshot.getDownloadUrl().toString());


                            }
                        });
                    }




                /*-----------------------------------------------------------------*/

                    if(fileGuardianProfilePicture2!=null) {
                        storageReference.child("guardian_personal_info").putFile(fileGuardianProfilePicture2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                mref1.child(Constant.FINAL_REGISTRATION_ID).child("family_information").child("guardian_personal_info")
                                        .child("guardian3").child("guardian_profile_picture").setValue(taskSnapshot.getDownloadUrl().toString());


                            }
                        });


                        if(fileGuardianAdharCardFront2!=null) {
                            storageReference.child("guardian_personal_info").putFile(fileGuardianAdharCardFront2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    mref1.child(Constant.FINAL_REGISTRATION_ID).child("family_information").child("guardian_personal_info")
                                            .child("guardian3").child("guardian_aadhar_card_front_image").setValue(taskSnapshot.getDownloadUrl().toString());


                                }
                            });
                        }

                        if(fileGuardianAdharCardBack2!=null) {
                            storageReference.child("guardian_personal_info").putFile(fileGuardianAdharCardBack2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    mref1.child(Constant.FINAL_REGISTRATION_ID).child("family_information").child("guardian_personal_info")
                                            .child("guardian3").child("guardian_aadhar_card_back_image").setValue(taskSnapshot.getDownloadUrl().toString());


                                }
                            });
                        }

                        if(fileGuardianCurrentAddressFront2!=null) {
                            storageReference.child("guardian_personal_info").putFile(fileGuardianCurrentAddressFront2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    mref1.child(Constant.FINAL_REGISTRATION_ID).child("family_information").child("guardian_personal_info")
                                            .child("guardian3").child("guardian_address_proof_front_image").setValue(taskSnapshot.getDownloadUrl().toString());


                                }
                            });
                        }

                        if(fileGuardianCurrentAddressBack2!=null) {
                            storageReference.child("guardian_personal_info").putFile(fileGuardianCurrentAddressBack2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    mref1.child(Constant.FINAL_REGISTRATION_ID).child("family_information").child("guardian_personal_info")
                                            .child("guardian3").child("guardian_address_proof_back_image").setValue(taskSnapshot.getDownloadUrl().toString());


                                }
                            });
                        }


                    /*-----------------------------------------------------------*/
                    }
                    EventBus.getDefault().post(new NewStudentRegisterModel(studentClass,studentSection,studentFName,studentRegistrationNumber));
                    NewAdmissionActivity newAdmissionActivity = (NewAdmissionActivity) getActivity();
                    AddressInfoFragment addressInfoFragment = new AddressInfoFragment();
                    newAdmissionActivity.loadNextFragment(BUTTON_ENABLE, addressInfoFragment, null);
                    Toast.makeText(getActivity(), "Family information data stored", Toast.LENGTH_SHORT).show();

                    progressDialog.dismiss();


                }
            }, 5000);



    }



    private void setUiforGuardiansInfo() {

        if(layoutForGuardians.getVisibility()== View.GONE){
            layoutForGuardians.setVisibility(View.VISIBLE);
            btnDownForGuardians.setVisibility(View.GONE);
            btnUpForGuardians.setVisibility(View.VISIBLE);
            guardiansInfoTxtView.setBackgroundColor(getResources().getColor(R.color.whitecolor));
            guardiansInfoTxtView.setTextColor(getResources().getColor(R.color.colorPrimary));


        }

        else {
            layoutForGuardians.setVisibility(View.GONE);
            btnDownForGuardians.setVisibility(View.VISIBLE);
            btnUpForGuardians.setVisibility(View.GONE);
            guardiansInfoTxtView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            guardiansInfoTxtView.setTextColor(getResources().getColor(R.color.whitecolor));

        }
    }

    private void setUiforMotherInfo() {


        if(layoutForMother.getVisibility()== View.GONE){
            layoutForMother.setVisibility(View.VISIBLE);
            btnDownForMother.setVisibility(View.GONE);
            btnUpForMother.setVisibility(View.VISIBLE);
            motherInfoTxtView.setBackgroundColor(getResources().getColor(R.color.whitecolor));
            motherInfoTxtView.setTextColor(getResources().getColor(R.color.colorPrimary));


        }

        else {
            layoutForMother.setVisibility(View.GONE);
            btnDownForMother.setVisibility(View.VISIBLE);
            btnUpForMother.setVisibility(View.GONE);
            motherInfoTxtView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            motherInfoTxtView.setTextColor(getResources().getColor(R.color.whitecolor));

        }
    }

    private void setUiforFatherInfo() {
        if(layoutForFather.getVisibility()== View.VISIBLE){
            layoutForFather.setVisibility(View.GONE);
            btnDownForFather.setVisibility(View.GONE);
            btnUpForFather.setVisibility(View.VISIBLE);
            fatherInfoTxtView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            fatherInfoTxtView.setTextColor(getResources().getColor(R.color.whitecolor));


        }

        else {
            layoutForFather.setVisibility(View.VISIBLE);
            btnDownForFather.setVisibility(View.VISIBLE);
            btnUpForFather.setVisibility(View.GONE);
            fatherInfoTxtView.setBackgroundColor(getResources().getColor(R.color.whitecolor));
            fatherInfoTxtView.setTextColor(getResources().getColor(R.color.colorPrimary));

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FROM_CAMERA && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), photo, "Title", null);
            filePath= Uri.parse(path);
            pathProfile = getPathFromUri(filePath);

            switch (str_case){

                case "A":
                    fileFatherProfile = filePath;
                    setImageToView(fileFatherProfile);

                    //Glide.with(getActivity()).load(filePath).into(fatherProfileImage);
                    break;

                case "B":
                    fileMotherProfile = filePath;
                    setImageToView(fileMotherProfile);

                    //Glide.with(getActivity()).load(filePath).into(motherProfileImage);
                    break;

                case "C":
                    fileFatherAadharIDFront = filePath;
                    setImageToView(fileFatherAadharIDFront);

                    //Glide.with(getActivity()).load(filePath).into(fatherAadharCard_ImageFront);
                    fatherAadhar_FrontImageClick.setVisibility(View.VISIBLE);
                    fatherAadharCard_ImageFront.setVisibility(View.VISIBLE);
                    break;

                case "D":
                    fileFatherAadharIDBack = filePath;
                    setImageToView(fileFatherAadharIDBack);

                    //Glide.with(getActivity()).load(filePath).into(fatherAadharCard_ImageBack);
                    fatherAadharBack_ImageClick.setVisibility(View.VISIBLE);
                    fatherAadharCard_ImageBack.setVisibility(View.VISIBLE);
                    break;

                case "E":
                    fileFatherCurrentAddressFront = filePath;
                    setImageToView(fileFatherCurrentAddressFront);

                    // Glide.with(getActivity()).load(filePath).into(fatherCurrentAddress_ProofFront);
                    fatherCurrentAddress_ProofFrontClick.setVisibility(View.VISIBLE);
                    fatherCurrentAddress_ProofFront.setVisibility(View.VISIBLE);
                    break;

                case "F":
                    fileFatherCurrentAddressBack =filePath;
                    setImageToView(fileFatherCurrentAddressBack);

                    //Glide.with(getActivity()).load(filePath).into(fatherCurrentAddress_ProofBack);
                    fatherCurrentAddress_ProofBackClick.setVisibility(View.VISIBLE);
                    fatherCurrentAddress_ProofBack.setVisibility(View.VISIBLE);
                    break;


                case "G":
                    fileMotherAadharIDFront = filePath;
                    setImageToView(fileMotherAadharIDFront);

                    //Glide.with(getActivity()).load(filePath).into(motherAadharCard_ImageFront);
                    motherAadharCardImage_FrontClick.setVisibility(View.VISIBLE);
                    motherAadharCard_ImageFront.setVisibility(View.VISIBLE);
                    break;


                case "H":
                    fileMotherAadharIDBack =filePath;
                    setImageToView(fileMotherAadharIDBack);

                    //Glide.with(getActivity()).load(filePath).into(motherAadharCard_ImageBack);
                    motherAadharCard_ImageBackClick.setVisibility(View.VISIBLE);
                    motherAadharCard_ImageBack.setVisibility(View.VISIBLE);
                    break;


                case "I":
                    fileMotherCurrentAddressFront = filePath;
                    setImageToView(fileMotherCurrentAddressFront);
                    //Glide.with(getActivity()).load(filePath).into(motherCurrentAddress_ProofFront);
                    motherCurrentAddress_ProofFrontClick.setVisibility(View.VISIBLE);
                    motherCurrentAddress_ProofFront.setVisibility(View.VISIBLE);
                    break;

                case "J":
                    fileMotherCurrentAddressBack = filePath;
                    setImageToView(fileMotherCurrentAddressBack);

                    //Glide.with(getActivity()).load(filePath).into(motherCurrentAddress_ProofBack);
                    motherCurrentAddress_ProofBackClick.setVisibility(View.VISIBLE);
                    motherCurrentAddress_ProofBack.setVisibility(View.VISIBLE);
                    break;

                case "K":

                    Glide.with(getActivity()).load(filePath).into(guardianGuardian_allProfilePictre.get(0));
                    break;

                case "L":
                    Glide.with(getActivity()).load(filePath).into(guardianGuardian_allProfilePictre.get(1));
                    break;

                case "M":
                    Glide.with(getActivity()).load(filePath).into(guardianGuardian_allProfilePictre.get(2));
                    break;

                case "N":
                    fileGuardianAdharCardFront0 = filePath;
                    guardianGuardian_allAadharCardPictreFrontClick.get(0).setVisibility(View.VISIBLE);
                    Glide.with(getActivity()).load(filePath).into(guardianGuardian_allAadharCardPictreFrontClick.get(0));
                    break;

                case "O":
                    fileGuardianAdharCardFront1 = filePath;
                    guardianGuardian_allAadharCardPictreFrontClick.get(1).setVisibility(View.VISIBLE);
                    Glide.with(getActivity()).load(filePath).into(guardianGuardian_allAadharCardPictreFrontClick.get(1));
                    break;

                case "P":
                    fileGuardianAdharCardFront2 = filePath;
                    guardianGuardian_allAadharCardPictreFrontClick.get(2).setVisibility(View.VISIBLE);
                    Glide.with(getActivity()).load(filePath).into(guardianGuardian_allAadharCardPictreFrontClick.get(2));
                    break;


                case "Q":
                    fileGuardianAdharCardBack0 = filePath;
                    guardianGuardian_allAadharCardPictreBackClick.get(0).setVisibility(View.VISIBLE);
                    Glide.with(getActivity()).load(filePath).into(guardianGuardian_allAadharCardPictreBackClick.get(0));
                    break;


                case "R":
                    fileGuardianAdharCardBack1 = filePath;
                    guardianGuardian_allAadharCardPictreBackClick.get(1).setVisibility(View.VISIBLE);
                    Glide.with(getActivity()).load(filePath).into(guardianGuardian_allAadharCardPictreBackClick.get(1));
                    break;


                case "S":
                    fileGuardianAdharCardBack2 = filePath;
                    guardianGuardian_allAadharCardPictreBackClick.get(2).setVisibility(View.VISIBLE);
                    Glide.with(getActivity()).load(filePath).into(guardianGuardian_allAadharCardPictreBackClick.get(2));
                    break;

                case "T":
                    fileGuardianCurrentAddressFront0 = filePath;
                    guardianGuardian_allCurrentAddressPictreFrontClick.get(0).setVisibility(View.VISIBLE);
                    Glide.with(getActivity()).load(filePath).into(guardianGuardian_allCurrentAddressPictreFrontClick.get(0));
                    break;

                case "U":
                    fileGuardianCurrentAddressFront1 = filePath;

                    guardianGuardian_allCurrentAddressPictreFrontClick.get(1).setVisibility(View.VISIBLE);
                    Glide.with(getActivity()).load(filePath).into(guardianGuardian_allCurrentAddressPictreFrontClick.get(1));
                    break;

                case "V":
                    fileGuardianCurrentAddressFront2 = filePath;
                    guardianGuardian_allCurrentAddressPictreFrontClick.get(2).setVisibility(View.VISIBLE);
                    Glide.with(getActivity()).load(filePath).into(guardianGuardian_allCurrentAddressPictreFrontClick.get(2));
                    break;

                case "W":
                    fileGuardianCurrentAddressBack0 = filePath;
                    guardianGuardian_allCurrentAddressPictreBackClick.get(0).setVisibility(View.VISIBLE);
                    Glide.with(getActivity()).load(filePath).into(guardianGuardian_allCurrentAddressPictreBackClick.get(0));
                    break;

                case "X":
                    fileGuardianCurrentAddressBack1 = filePath;
                    guardianGuardian_allCurrentAddressPictreBackClick.get(1).setVisibility(View.VISIBLE);
                    Glide.with(getActivity()).load(filePath).into(guardianGuardian_allCurrentAddressPictreBackClick.get(1));
                    break;

                case "Y":
                    fileGuardianCurrentAddressBack2 = filePath;
                    guardianGuardian_allCurrentAddressPictreBackClick.get(2).setVisibility(View.VISIBLE);
                    Glide.with(getActivity()).load(filePath).into(guardianGuardian_allCurrentAddressPictreBackClick.get(2));
                    break;





            }


        }

        if (requestCode == FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            filePath = data.getData();
/*
            InputStream inputStream = null;
            try {
                inputStream = getContext().getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap, "Title", null);
                filePath= Uri.parse(path);
                pathProfile = getPathFromUri(filePath);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }*/


            switch (str_case){

                case "A":
                    fileFatherProfile = filePath;
                    setImageToView(fileFatherProfile);

                    //Glide.with(getActivity()).load(filePath).into(fatherProfileImage);
                    break;

                case "B":
                    fileMotherProfile = filePath;
                    setImageToView(fileMotherProfile);

                    //Glide.with(getActivity()).load(filePath).into(motherProfileImage);
                    break;

                case "C":
                    fileFatherAadharIDFront = filePath;
                    setImageToView(fileFatherAadharIDFront);

                    //Glide.with(getActivity()).load(filePath).into(fatherAadharCard_ImageFront);
                    fatherAadhar_FrontImageClick.setVisibility(View.VISIBLE);
                    fatherAadharCard_ImageFront.setVisibility(View.VISIBLE);
                    break;

                case "D":
                    fileFatherAadharIDBack = filePath;
                    setImageToView(fileFatherAadharIDBack);

                    //Glide.with(getActivity()).load(filePath).into(fatherAadharCard_ImageBack);
                    fatherAadharBack_ImageClick.setVisibility(View.VISIBLE);
                    fatherAadharCard_ImageBack.setVisibility(View.VISIBLE);
                    break;

                case "E":
                    fileFatherCurrentAddressFront =filePath;
                    setImageToView(fileFatherCurrentAddressFront);

                    //Glide.with(getActivity()).load(filePath).into(fatherCurrentAddress_ProofFront);
                    fatherCurrentAddress_ProofFrontClick.setVisibility(View.VISIBLE);
                    fatherCurrentAddress_ProofFront.setVisibility(View.VISIBLE);
                    break;

                case "F":
                    fileFatherCurrentAddressBack = filePath;
                    setImageToView(fileFatherCurrentAddressBack);

                    //Glide.with(getActivity()).load(filePath).into(fatherCurrentAddress_ProofBack);
                    fatherCurrentAddress_ProofBackClick.setVisibility(View.VISIBLE);
                    fatherCurrentAddress_ProofBack.setVisibility(View.VISIBLE);
                    break;


                case "G":
                    fileMotherAadharIDFront =filePath;
                    setImageToView(fileMotherAadharIDFront);
                    //Glide.with(getActivity()).load(filePath).into(motherAadharCard_ImageFront);
                    motherAadharCardImage_FrontClick.setVisibility(View.VISIBLE);
                    motherAadharCard_ImageFront.setVisibility(View.VISIBLE);
                    break;


                case "H":
                    fileMotherAadharIDBack =filePath;
                    setImageToView(fileMotherAadharIDBack);

                    //Glide.with(getActivity()).load(filePath).into(motherAadharCard_ImageBack);
                    motherAadharCard_ImageBackClick.setVisibility(View.VISIBLE);
                    motherAadharCard_ImageBack.setVisibility(View.VISIBLE);
                    break;


                case "I":
                    fileMotherCurrentAddressFront =filePath;
                    setImageToView(fileMotherCurrentAddressFront);

                    //Glide.with(getActivity()).load(filePath).into(motherCurrentAddress_ProofFront);
                    motherCurrentAddress_ProofFrontClick.setVisibility(View.VISIBLE);
                    motherCurrentAddress_ProofFront.setVisibility(View.VISIBLE);
                    break;

                case "J":
                    fileMotherCurrentAddressBack = filePath;
                    setImageToView(fileMotherCurrentAddressBack);

                    //Glide.with(getActivity()).load(filePath).into(motherCurrentAddress_ProofBack);
                    motherCurrentAddress_ProofBackClick.setVisibility(View.VISIBLE);
                    motherCurrentAddress_ProofBack.setVisibility(View.VISIBLE);
                    break;

                case "K":
                    fileGuardianProfilePicture0 = filePath;
                    Glide.with(getActivity()).load(filePath).into(guardianGuardian_allProfilePictre.get(0));
                    break;

                case "L":
                    fileGuardianProfilePicture1 = filePath;
                    Glide.with(getActivity()).load(filePath).into(guardianGuardian_allProfilePictre.get(1));
                    break;

                case "M":
                    fileGuardianProfilePicture2 = filePath;
                    Glide.with(getActivity()).load(filePath).into(guardianGuardian_allProfilePictre.get(2));
                    break;

                case "N":
                    fileGuardianAdharCardFront0 = filePath;
                    guardianGuardian_allAadharCardPictreFrontClick.get(0).setVisibility(View.VISIBLE);
                    Glide.with(getActivity()).load(filePath).into(guardianGuardian_allAadharCardPictreFrontClick.get(0));
                    break;

                case "O":
                    fileGuardianAdharCardFront1 = filePath;
                    guardianGuardian_allAadharCardPictreFrontClick.get(1).setVisibility(View.VISIBLE);
                    Glide.with(getActivity()).load(filePath).into(guardianGuardian_allAadharCardPictreFrontClick.get(1));
                    break;

                case "P":
                    fileGuardianAdharCardFront2 = filePath;
                    guardianGuardian_allAadharCardPictreFrontClick.get(2).setVisibility(View.VISIBLE);
                    Glide.with(getActivity()).load(filePath).into(guardianGuardian_allAadharCardPictreFrontClick.get(2));
                    break;


                case "Q":
                    fileGuardianAdharCardBack0 = filePath;
                    guardianGuardian_allAadharCardPictreBackClick.get(0).setVisibility(View.VISIBLE);
                    Glide.with(getActivity()).load(filePath).into(guardianGuardian_allAadharCardPictreBackClick.get(0));
                    break;


                case "R":
                    fileGuardianAdharCardBack1 = filePath;
                    guardianGuardian_allAadharCardPictreBackClick.get(1).setVisibility(View.VISIBLE);
                    Glide.with(getActivity()).load(filePath).into(guardianGuardian_allAadharCardPictreBackClick.get(1));
                    break;


                case "S":
                    fileGuardianAdharCardBack2 = filePath;
                    guardianGuardian_allAadharCardPictreBackClick.get(2).setVisibility(View.VISIBLE);
                    Glide.with(getActivity()).load(filePath).into(guardianGuardian_allAadharCardPictreBackClick.get(2));
                    break;

                case "T":
                    fileGuardianCurrentAddressFront0 = filePath;
                    guardianGuardian_allCurrentAddressPictreFrontClick.get(0).setVisibility(View.VISIBLE);
                    Glide.with(getActivity()).load(filePath).into(guardianGuardian_allCurrentAddressPictreFrontClick.get(0));
                    break;

                case "U":
                    fileGuardianCurrentAddressFront1 = filePath;

                    guardianGuardian_allCurrentAddressPictreFrontClick.get(1).setVisibility(View.VISIBLE);
                    Glide.with(getActivity()).load(filePath).into(guardianGuardian_allCurrentAddressPictreFrontClick.get(1));
                    break;

                case "V":
                    fileGuardianCurrentAddressFront2 = filePath;
                    guardianGuardian_allCurrentAddressPictreFrontClick.get(2).setVisibility(View.VISIBLE);
                    Glide.with(getActivity()).load(filePath).into(guardianGuardian_allCurrentAddressPictreFrontClick.get(2));
                    break;

                case "W":
                    fileGuardianCurrentAddressBack0 = filePath;
                    guardianGuardian_allCurrentAddressPictreBackClick.get(0).setVisibility(View.VISIBLE);
                    Glide.with(getActivity()).load(filePath).into(guardianGuardian_allCurrentAddressPictreBackClick.get(0));
                    break;

                case "X":
                    fileGuardianCurrentAddressBack1 = filePath;
                    guardianGuardian_allCurrentAddressPictreBackClick.get(1).setVisibility(View.VISIBLE);
                    Glide.with(getActivity()).load(filePath).into(guardianGuardian_allCurrentAddressPictreBackClick.get(1));
                    break;

                case "Y":
                    fileGuardianCurrentAddressBack2 = filePath;
                    guardianGuardian_allCurrentAddressPictreBackClick.get(2).setVisibility(View.VISIBLE);
                    Glide.with(getActivity()).load(filePath).into(guardianGuardian_allCurrentAddressPictreBackClick.get(2));
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
                    fatherProfileImage.setImageBitmap(selectedImage);
                    break;

                case "B":
                    motherProfileImage.setImageBitmap(selectedImage);
                    break;

                case "C":
                    fatherAadharCard_ImageFront.setImageBitmap(selectedImage);
                    break;

                case "D":
                    fatherAadharCard_ImageBack.setImageBitmap(selectedImage);
                    break;

                case "E":
                    fatherCurrentAddress_ProofFront.setImageBitmap(selectedImage);
                    break;

                case "F":
                    fatherCurrentAddress_ProofBack.setImageBitmap(selectedImage);
                    break;

                case "G":
                    motherAadharCard_ImageFront.setImageBitmap(selectedImage);
                    break;

                case "H":
                    motherAadharCard_ImageBack.setImageBitmap(selectedImage);
                    break;

                case "I":
                    motherCurrentAddress_ProofFront.setImageBitmap(selectedImage);
                    break;

                case "J":
                    motherCurrentAddress_ProofBack.setImageBitmap(selectedImage);
                    break;

                case "K":
                    motherCurrentAddress_ProofFront.setImageBitmap(selectedImage);
                    break;

                case "L":
                    motherCurrentAddress_ProofFront.setImageBitmap(selectedImage);
                    break;

                case "M":
                    motherCurrentAddress_ProofFront.setImageBitmap(selectedImage);
                    break;

            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Bitmap getResizedBitmap(Bitmap image, int maxSize ) {

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
    public void onAttach(Context context) {
        super.onAttach(context);
        bus.register(this);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        bus.removeAllStickyEvents();
        bus.unregister(this);

    }


    @Subscribe
    public void onEvent(NewStudentRegisterModel event) {
        Log.d("djahsfdkkfhk", ""+event.getStartRegistrationNumber());
        studentRegistrationNumber = event.getStartRegistrationNumber();
        studentClass = event.getStr_selectedClass();
        studentSection =event.getStr_studentSection();
        studentFName = event.getStr_student_Firstname();
        student_RegistrationNumber.setText(event.getStartRegistrationNumber());
        student_Name.setText(event.getStr_student_Firstname());
        student_Class.setText(event.getStr_selectedClass());
        student_Section.setText(event.getStr_studentSection());
        if(!Constant.STUDENT_PROFILE_IMAGE.equals("")) {
            Glide.with(getActivity()).load(Constant.STUDENT_PROFILE_IMAGE).into(student_ProfileImage);
            //bus.removeAllStickyEvents();
        }

    }



}
