package in.varadhismartek.pathshalaerp.AddStudent;

public class AdmissionEnquiryModel {
    String father_qualification , mother_qualification , food_facility , transport_facility, number_of_guardians,minPercentageForAdmission;

    public AdmissionEnquiryModel(String minPercentageForAdmission, String father_qualification, String mother_qualification, String food_facility, String transport_facility, String number_of_guardians) {
        this.father_qualification = father_qualification;
        this.mother_qualification = mother_qualification;
        this.food_facility = food_facility;
        this.transport_facility = transport_facility;
        this.number_of_guardians = number_of_guardians;
        this.minPercentageForAdmission = minPercentageForAdmission;
    }


    public String getMinPercentageForAdmission() {
        return minPercentageForAdmission;
    }

    public String getFather_qualification() {
        return father_qualification;
    }

    public String getMother_qualification() {
        return mother_qualification;
    }

    public String getFood_facility() {
        return food_facility;
    }

    public String getTransport_facility() {
        return transport_facility;
    }

    public String getNumber_of_guardians() {
        return number_of_guardians;
    }
}
