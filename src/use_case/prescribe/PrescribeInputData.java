package use_case.prescribe;
import use_case.Drug;

import java.time.LocalDate;

public class PrescribeInputData {
    private Drug drug;

    private int patientid;

    public PrescribeInputData(Drug drug){
        this.drug=drug;
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
