package test;

import entity.Drug;
import entity.Patient;

import java.time.LocalDate;
import java.util.ArrayList;

public class TestDataFactory {


    public static Patient createTestPatient(){
        // BASIC ATTRIBUTES
        int id = 1;
        String fullName = "John Doe";
        float height = 1.75f; // in meters
        float weight = 70.0f; // in kilograms

        ArrayList<LocalDate> appointmentDates = new ArrayList<>();
        // november 30th 2023 appointment
        appointmentDates.add(LocalDate.of(2023, 11, 30));
        // set date added as right now
        LocalDate dateAdded = LocalDate.now(); // Current date as date added

        ArrayList<Drug> prescribedDrugs = new ArrayList<>();
        // PRESCRIBED DRUGS
        prescribedDrugs.add(new Drug("Paracetamol", 500.0f, LocalDate.of(2023, 11, 1), LocalDate.of(2023, 11, 10)));
        // ALLERGIES
        ArrayList<String> allergies = new ArrayList<>();
        allergies.add("Pollen");

        // ILLNESSES
        ArrayList<String> illnesses = new ArrayList<>();
        illnesses.add("Hypertension");
        // SYMPTOMS
        ArrayList<String> symptoms = new ArrayList<>();
        symptoms.add("Headache");

        return new Patient(id, fullName, height, weight, appointmentDates, dateAdded, prescribedDrugs, allergies, illnesses, symptoms);
    }







}
