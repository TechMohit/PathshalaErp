package in.varadhismartek.pathshalaerp.AddStudent;



public class NewStudentRegisterModel {
    private String str_selectedClass,str_studentSection, str_student_Firstname,str_student_Middlename, str_student_Lastname, str_student_date_of_birth , str_student_gender,str_student_Id_number,str_student_birth_certfct_number, str_Student_religion , str_student_nationality,
            str_studentCommunity,str_student_caste,str_student_subcast,
            str_student_language_known , str_student_mother_toung_language,
            str_student_previous_school_name, str_student_previous_school_address,str_school_affiliatedTo,str_student_transfer_cerfct_number,str_student_previous_class_percentage, str_student_previous_school_contact_number,
            str_student_height,str_student_weight,str_student_shortEyeVision , str_student_longEyeVision,
            str_student_colorVision,str_student_commentOnEyes,str_student_teethProblem , str_student_commentOnTeeth, str_student_rightEar_frequency,
            str_student_lefttEar_frequency,str_student_rightEar_commentOnEars, str_student_disabilityProblem , str_student_commentsOnDisability,startRegistrationNumber;


    private String father_Firstname,father_Middlename,father_lastname, father_dob, father_qualification , father_occupation , father_designation , father_mobile_number,
            father_aadhar_card_number,father_address_proof_number, father_emailId , mother_Firstname, mother_Middlename,mother_Lastname, mother_dob, mother_qualification , mother_occupation , mother_designation , mother_mobile_number,
            mother_aadhar_card_number,mother_address_proof_number, mother_emailId;


    private String presentAddressStreet, presentAddressCity, presentAddressPincode,presentAddressState,
            presentAddressCountry,presentAddressContact,
            permanentAddressStreet,permanentAddressCity, permanentAddressPincode,permanentAddressState, permanentAddressCountry,permanentAddressContact;

    int number;

    String joining_date, joing_time;

    String str_previous_class;
    String str_student_previous_class_marksheet_number;
    String completeAddress;

    String studentProfilePicture;


    public NewStudentRegisterModel(String str_selectedClass, String str_studentSection, String str_student_Firstname, String startRegistrationNumber) {
        this.str_selectedClass = str_selectedClass;
        this.str_studentSection = str_studentSection;
        this.str_student_Firstname = str_student_Firstname;
        this.startRegistrationNumber = startRegistrationNumber;
    }

    public String getCompleteAddress() {
        return completeAddress;
    }

    public String getStr_student_previous_class_marksheet_number() {
        return str_student_previous_class_marksheet_number;
    }

    public NewStudentRegisterModel(String str_selectedClass, String str_studentSection, String joining_date) {
        this.str_selectedClass = str_selectedClass;
        this.str_studentSection = str_studentSection;
        this.joining_date = joining_date;
    }

    public String getFather_address_proof_number() {
        return father_address_proof_number;
    }

    public String getMother_address_proof_number() {
        return mother_address_proof_number;
    }

    public String getJoining_date() {
        return joining_date;
    }

    public String getJoing_time() {
        return joing_time;
    }

    public String getStr_previous_class() {
        return str_previous_class;
    }

    public String getStudentProfilePicture() {
        return studentProfilePicture;
    }

    public NewStudentRegisterModel(String str_selectedClass, String str_studentSection, String str_student_Firstname,String startRegistrationNumber, String studentProfilePicture) {
        this.str_selectedClass = str_selectedClass;
        this.str_studentSection = str_studentSection;
        this.str_student_Firstname = str_student_Firstname;
        this.startRegistrationNumber = startRegistrationNumber;
        this.studentProfilePicture = studentProfilePicture;
    }

    public NewStudentRegisterModel(String presentAddressStreet, String presentAddressCity, String presentAddressPincode, String presentAddressState, String presentAddressCountry, String presentAddressContact, String permanentAddressStreet, String permanentAddressCity, String permanentAddressPincode, String permanentAddressState, String permanentAddressCountry, String permanentAddressContact) {
        this.presentAddressStreet = presentAddressStreet;
        this.presentAddressCity = presentAddressCity;
        this.presentAddressPincode = presentAddressPincode;
        this.presentAddressState = presentAddressState;
        this.presentAddressCountry = presentAddressCountry;
        this.presentAddressContact = presentAddressContact;
        this.permanentAddressStreet = permanentAddressStreet;
        this.permanentAddressCity = permanentAddressCity;
        this.permanentAddressPincode = permanentAddressPincode;
        this.permanentAddressState = permanentAddressState;
        this.permanentAddressCountry = permanentAddressCountry;
        this.permanentAddressContact = permanentAddressContact;
    }

    public NewStudentRegisterModel( String str_student_Firstname, String str_student_Middlename, String str_student_Lastname, String str_student_date_of_birth, String str_student_gender, String str_student_Id_number, String str_student_birth_certfct_number, String str_Student_religion, String str_student_nationality, String str_studentCommunity, String str_student_caste, String str_student_subcast, String str_student_language_known, String str_student_mother_toung_language) {
        this.str_student_Firstname = str_student_Firstname;
        this.str_student_Middlename = str_student_Middlename;
        this.str_student_Lastname = str_student_Lastname;
        this.str_student_date_of_birth = str_student_date_of_birth;
        this.str_student_gender = str_student_gender;
        this.str_student_Id_number = str_student_Id_number;
        this.str_student_birth_certfct_number = str_student_birth_certfct_number;
        this.str_Student_religion = str_Student_religion;
        this.str_student_nationality = str_student_nationality;
        this.str_studentCommunity = str_studentCommunity;
        this.str_student_caste = str_student_caste;
        this.str_student_subcast = str_student_subcast;
        this.str_student_language_known = str_student_language_known;
        this.str_student_mother_toung_language = str_student_mother_toung_language;
    }

    public NewStudentRegisterModel(String str_previous_class,String str_student_previous_school_name, String str_student_previous_school_address, String str_school_affiliatedTo, String str_student_transfer_cerfct_number, String str_student_previous_class_percentage, String str_student_previous_school_contact_number, String str_student_previous_class_marksheet_number) {
        this.str_student_previous_class_marksheet_number = str_student_previous_class_marksheet_number;
        this.str_previous_class = str_previous_class;
        this.str_student_previous_school_name = str_student_previous_school_name;
        this.str_student_previous_school_address = str_student_previous_school_address;
        this.str_school_affiliatedTo = str_school_affiliatedTo;
        this.str_student_transfer_cerfct_number = str_student_transfer_cerfct_number;
        this.str_student_previous_class_percentage = str_student_previous_class_percentage;
        this.str_student_previous_school_contact_number = str_student_previous_school_contact_number;
    }

    public NewStudentRegisterModel(String father_Firstname, String father_Middlename, String father_lastname, String father_dob, String father_qualification, String father_occupation, String father_designation, String father_mobile_number, String father_aadhar_card_number, String father_address_proof_number, String father_emailId) {
        this.father_Firstname = father_Firstname;
        this.father_Middlename = father_Middlename;
        this.father_lastname = father_lastname;
        this.father_dob = father_dob;
        this.father_qualification = father_qualification;
        this.father_occupation = father_occupation;
        this.father_designation = father_designation;
        this.father_mobile_number = father_mobile_number;
        this.father_aadhar_card_number = father_aadhar_card_number;
        this.father_address_proof_number = father_address_proof_number;
        this.father_emailId = father_emailId;
    }

    public NewStudentRegisterModel(int number,String mother_Firstname, String mother_Middlename, String mother_Lastname, String mother_dob, String mother_qualification, String mother_occupation, String mother_designation, String mother_mobile_number, String mother_aadhar_card_number,String mother_address_proof_number, String mother_emailId) {
        this.number = number;
        this.mother_Firstname = mother_Firstname;
        this.mother_Middlename = mother_Middlename;
        this.mother_Lastname = mother_Lastname;
        this.mother_dob = mother_dob;
        this.mother_qualification = mother_qualification;
        this.mother_occupation = mother_occupation;
        this.mother_designation = mother_designation;
        this.mother_mobile_number = mother_mobile_number;
        this.mother_aadhar_card_number = mother_aadhar_card_number;
        this.mother_address_proof_number = mother_address_proof_number;
        this.mother_emailId = mother_emailId;
    }

    public NewStudentRegisterModel(String str_student_height, String str_student_weight, String str_student_shortEyeVision, String str_student_longEyeVision, String str_student_colorVision, String str_student_commentOnEyes, String str_student_teethProblem, String str_student_commentOnTeeth, String str_student_rightEar_frequency, String str_student_lefttEar_frequency, String str_student_rightEar_commentOnEars, String str_student_disabilityProblem, String str_student_commentsOnDisability) {
        this.str_student_height = str_student_height;
        this.str_student_weight = str_student_weight;
        this.str_student_shortEyeVision = str_student_shortEyeVision;
        this.str_student_longEyeVision = str_student_longEyeVision;
        this.str_student_colorVision = str_student_colorVision;
        this.str_student_commentOnEyes = str_student_commentOnEyes;
        this.str_student_teethProblem = str_student_teethProblem;
        this.str_student_commentOnTeeth = str_student_commentOnTeeth;
        this.str_student_rightEar_frequency = str_student_rightEar_frequency;
        this.str_student_lefttEar_frequency = str_student_lefttEar_frequency;
        this.str_student_rightEar_commentOnEars = str_student_rightEar_commentOnEars;
        this.str_student_disabilityProblem = str_student_disabilityProblem;
        this.str_student_commentsOnDisability = str_student_commentsOnDisability;
    }

    public NewStudentRegisterModel(String completeAddress,String presentAddressStreet, String presentAddressCity, String presentAddressPincode, String presentAddressState, String presentAddressCountry, String presentAddressContact) {
        this.completeAddress = completeAddress;
        this.presentAddressStreet = presentAddressStreet;
        this.presentAddressCity = presentAddressCity;
        this.presentAddressPincode = presentAddressPincode;
        this.presentAddressState = presentAddressState;
        this.presentAddressCountry = presentAddressCountry;
        this.presentAddressContact = presentAddressContact;
    }

    public NewStudentRegisterModel( String permanentAddressStreet, String permanentAddressCity, String permanentAddressPincode, String permanentAddressState, String permanentAddressCountry, String permanentAddressContact) {
        this.permanentAddressStreet = permanentAddressStreet;
        this.permanentAddressCity = permanentAddressCity;
        this.permanentAddressPincode = permanentAddressPincode;
        this.permanentAddressState = permanentAddressState;
        this.permanentAddressCountry = permanentAddressCountry;
        this.permanentAddressContact = permanentAddressContact;
    }

    public NewStudentRegisterModel(String str_selectedClass, String str_studentSection, String str_student_Firstname, String str_student_Middlename, String str_student_Lastname, String str_student_date_of_birth, String str_student_gender, String str_student_Id_number, String str_student_birth_certfct_number) {
        this.str_selectedClass = str_selectedClass;
        this.str_studentSection = str_studentSection;
        this.str_student_Firstname = str_student_Firstname;
        this.str_student_Middlename = str_student_Middlename;
        this.str_student_Lastname = str_student_Lastname;
        this.str_student_date_of_birth = str_student_date_of_birth;
        this.str_student_gender = str_student_gender;
        this.str_student_Id_number = str_student_Id_number;
        this.str_student_birth_certfct_number = str_student_birth_certfct_number;
    }

    public String getPresentAddressStreet() {
        return presentAddressStreet;
    }

    public String getPresentAddressCity() {
        return presentAddressCity;
    }

    public String getPresentAddressPincode() {
        return presentAddressPincode;
    }

    public String getPresentAddressState() {
        return presentAddressState;
    }

    public String getPresentAddressCountry() {
        return presentAddressCountry;
    }

    public String getPresentAddressContact() {
        return presentAddressContact;
    }

    public String getPermanentAddressStreet() {
        return permanentAddressStreet;
    }

    public String getPermanentAddressCity() {
        return permanentAddressCity;
    }

    public String getPermanentAddressPincode() {
        return permanentAddressPincode;
    }

    public String getPermanentAddressState() {
        return permanentAddressState;
    }

    public String getPermanentAddressCountry() {
        return permanentAddressCountry;
    }

    public String getPermanentAddressContact() {
        return permanentAddressContact;
    }

    public String getFather_Firstname() {
        return father_Firstname;
    }

    public String getFather_Middlename() {
        return father_Middlename;
    }

    public String getFather_lastname() {
        return father_lastname;
    }

    public String getFather_dob() {
        return father_dob;
    }

    public String getFather_qualification() {
        return father_qualification;
    }

    public String getFather_occupation() {
        return father_occupation;
    }

    public String getFather_designation() {
        return father_designation;
    }

    public String getFather_mobile_number() {
        return father_mobile_number;
    }

    public String getFather_aadhar_card_number() {
        return father_aadhar_card_number;
    }

    public String getFather_emailId() {
        return father_emailId;
    }

    public String getMother_Firstname() {
        return mother_Firstname;
    }

    public String getMother_Middlename() {
        return mother_Middlename;
    }

    public String getMother_Lastname() {
        return mother_Lastname;
    }

    public String getMother_dob() {
        return mother_dob;
    }

    public String getMother_qualification() {
        return mother_qualification;
    }

    public String getMother_occupation() {
        return mother_occupation;
    }

    public String getMother_designation() {
        return mother_designation;
    }

    public String getMother_mobile_number() {
        return mother_mobile_number;
    }

    public String getMother_aadhar_card_number() {
        return mother_aadhar_card_number;
    }

    public String getMother_emailId() {
        return mother_emailId;
    }

    public String getStartRegistrationNumber() {
        return startRegistrationNumber;
    }

    public String getStr_selectedClass() {
        return str_selectedClass;
    }

    public String getStr_studentSection() {
        return str_studentSection;
    }

    public String getStr_student_Firstname() {
        return str_student_Firstname;
    }

    public String getStr_student_Middlename() {
        return str_student_Middlename;
    }

    public String getStr_student_Lastname() {
        return str_student_Lastname;
    }

    public String getStr_student_date_of_birth() {
        return str_student_date_of_birth;
    }

    public String getStr_student_gender() {
        return str_student_gender;
    }

    public String getStr_student_Id_number() {
        return str_student_Id_number;
    }

    public String getStr_student_birth_certfct_number() {
        return str_student_birth_certfct_number;
    }

    public String getStr_Student_religion() {
        return str_Student_religion;
    }

    public String getStr_student_nationality() {
        return str_student_nationality;
    }

    public String getStr_studentCommunity() {
        return str_studentCommunity;
    }

    public String getStr_student_caste() {
        return str_student_caste;
    }

    public String getStr_student_subcast() {
        return str_student_subcast;
    }

    public String getStr_student_language_known() {
        return str_student_language_known;
    }

    public String getStr_student_mother_toung_language() {
        return str_student_mother_toung_language;
    }

    public String getStr_student_previous_school_name() {
        return str_student_previous_school_name;
    }

    public String getStr_student_previous_school_address() {
        return str_student_previous_school_address;
    }

    public String getStr_school_affiliatedTo() {
        return str_school_affiliatedTo;
    }

    public String getStr_student_transfer_cerfct_number() {
        return str_student_transfer_cerfct_number;
    }

    public String getStr_student_previous_class_percentage() {
        return str_student_previous_class_percentage;
    }

    public String getStr_student_previous_school_contact_number() {
        return str_student_previous_school_contact_number;
    }

    public String getStr_student_height() {
        return str_student_height;
    }

    public String getStr_student_weight() {
        return str_student_weight;
    }

    public String getStr_student_shortEyeVision() {
        return str_student_shortEyeVision;
    }

    public String getStr_student_longEyeVision() {
        return str_student_longEyeVision;
    }

    public String getStr_student_colorVision() {
        return str_student_colorVision;
    }

    public String getStr_student_commentOnEyes() {
        return str_student_commentOnEyes;
    }

    public String getStr_student_teethProblem() {
        return str_student_teethProblem;
    }

    public String getStr_student_commentOnTeeth() {
        return str_student_commentOnTeeth;
    }

    public String getStr_student_rightEar_frequency() {
        return str_student_rightEar_frequency;
    }

    public String getStr_student_lefttEar_frequency() {
        return str_student_lefttEar_frequency;
    }

    public String getStr_student_rightEar_commentOnEars() {
        return str_student_rightEar_commentOnEars;
    }

    public String getStr_student_disabilityProblem() {
        return str_student_disabilityProblem;
    }

    public String getStr_student_commentsOnDisability() {
        return str_student_commentsOnDisability;
    }

    public NewStudentRegisterModel() {
    }





}
