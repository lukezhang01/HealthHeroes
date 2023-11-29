package use_case.prescribe;
import entity.*;

import java.time.LocalDate;

public class PrescribeInputData {
    private Drug drug;

    private int patientid;

    public PrescribeInputData(Drug drug,int patientid){
        this.drug=drug;
        this.patientid = patientid;
    }

    public String getName(){
        return drug.drug_name;
    }

    public Drug getDrug(){
        return drug;
    }

    public int getPatientid(){return patientid;}

    public LocalDate getStart_date() {
        return drug.getStart_date();
    }

    public LocalDate getEnd_date() {
        return drug.getEnd_date();
    }

    public String getDosageAsString() {
        return drug.getDosageAsString();
    }
}
