package entity;

import java.util.*;
import java.time.LocalDate;

public class Patient implements User {
    /*
    height = in cm
    weight = in kg
    drug dosage = in mL
     */
    private final int id;
    public String fullName;
    private float height;
    private float weight;
    private ArrayList<LocalDate> appointment_dates;
    private LocalDate date_added;
    private ArrayList<Drug> prescribed_drugs;
    private ArrayList<String> allergies;
    private ArrayList<String> illnesses;
    private ArrayList<String> symptoms;

    public Patient(int id, String fullName, float height, float weight, ArrayList<LocalDate> appointment_dates,
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
            drugs_list += "Drug Name: " + drug.drug_name + "| Dosage: " + drug.getDosageAsString() + "| Start Date: " +
                    drug.getStart_date().toString() + "| End Date: " + drug.getEnd_date().toString() + "\\n";
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

    public String getLatestAppointmentDate() {
        int size = this.appointment_dates.size();
        LocalDate lastDate = this.appointment_dates.get(size - 1);
        return lastDate.toString();
    }

    public LocalDate getDateAdded() {
        return this.date_added;
    }
    public float getWeight() {
        return this.weight;
    }

    public float getHeight() {
        return this.height;
    }

    // getters for chatgpt data
    public String getWeightAsString() {
        return this.weight + " kg";
    }

    public String getHeightAsString() {
        return this.height + " cm";
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


    /*
    UPDATERS FOR PATIENT FEATURES
     */
    public void updateHeight(float newHeight) {
        this.height = newHeight;
    }

    public void updateWeight(float newWeight) {
        this.weight = newWeight;
    }

    public void addAllergy()

}
