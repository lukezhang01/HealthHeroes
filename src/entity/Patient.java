package entity;

import use_case.Drug;
import java.util.*;
import java.time.LocalDate;

public class Patient implements User {
    private int id;
    public String fullName;
    private int height;
    private int weight;
    private ArrayList<LocalDate> appointment_dates;
    private LocalDate date_added;
    private ArrayList<Drug> prescribed_drugs;
    private ArrayList<String> allergies;
    private ArrayList<String> illnesses;
    private ArrayList<String> symptoms;

    public Patient(int id, String fullName, int height, int weight, ArrayList<LocalDate> appointment_dates,
                   LocalDate date_added, ArrayList<Drug> prescribed_drugs,
                   ArrayList<String> allergies, ArrayList<String> illnesses, ArrayList<String> symptoms) {
        this.id = id;
        this.fullName = fullName;
        this.height = height;
        this.weight = weight;
        this.appointment_dates = appointment_dates;
        this.date_added = date_added;
        this.prescribed_drugs = prescribed_drugs;
        this.allergies = allergies;
        this.illnesses = illnesses;
        this.symptoms = symptoms;
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

    public String getAppointmentDatesAsString() {
        String allDates = "";
        for (LocalDate date : this.appointment_dates) {
            allDates += date.toString();
        }
        return allDates;
    }

    public LocalDate getDateAdded() {
        return this.date_added;
    }
    public int getWeight() {
        return this.weight;
    }

    public int getHeight() {
        return this.height;
    }
    // the following methods insert commas between each element in the array
    // so that this matches the csv formatting

    public String getAllergiesAsString() {
        return String.join(",", this.allergies);
    }

    public String getIllnessesAsString() {
        return String.join(",", this.illnesses);
    }

    public String getSymptomsAsString() {
        return String.join(",", this.symptoms);
    }

//    public String[] getAllData() {
//        return new String[]{String.valueOf(this.id), this.fullName, String.valueOf(this.height),
//                            String.valueOf(this.weight), this.getAppointmentDatesAsString(), this.date_added.toString(),
//                            this.getPrescribedDrugsAsString(), this.getAllergiesAsString(), this.getIllnessesAsString(),
//                            this.getSymptomsAsString()};
//    }

}
