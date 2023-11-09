package entity;

import use_case.Drug;
import java.util.*;
import java.time.LocalDate;

public class Patient implements User {
    private int id;
    private String name;
    private String password;
    private LocalDate appointment_date;
    private  LocalDate date_added;
    private ArrayList<Drug> prescribed_drugs;
    private ArrayList<String> allergies;
    private ArrayList<String> illnesses;
    private ArrayList<String> symptoms;

    public Patient(int id, String name, String password, LocalDate appointment_date,
                   LocalDate date_added, ArrayList<Drug> prescribed_drugs,
                   ArrayList<String> allergies, ArrayList<String> illnesses, ArrayList<String> symptoms) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.appointment_date = appointment_date;
        this.date_added = date_added;
        this.prescribed_drugs = prescribed_drugs;
        this.allergies = allergies;
        this.illnesses = illnesses;
        this.symptoms = symptoms;
    }
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public int getID() { return this.id; }

    public String getPrescribedDrugsAsString() {
        String drugs_list = "";
        for (Drug drug : this.prescribed_drugs) {
            drugs_list += drug.drug_name + " " + drug.getDosageAsString() + " " +
                    drug.getStart_date().toString() + " " + drug.getEnd_date().toString() + " ";
        }
        return drugs_list.trim();
    }

    public void addDrug(Drug drug) {
        this.prescribed_drugs.add(drug);
    }

    public void clearPrescribedDrugs() {
        this.prescribed_drugs.clear();
    }

    public String getAppointmentDateAsString() {
        return appointment_date.toString();
    }

    public String getDateAddedAsString() {
        return date_added.toString();
    }

    public String getAllergiesAsString() {
        return String.join("", this.allergies);
    }

    public String getIllnessesAsString() {
        return String.join("", this.illnesses);
    }

    public String getSymptomsAsString() {
        return String.join("", this.symptoms);
    }

}
