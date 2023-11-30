package data_access;

import entity.Drug;
import entity.Patient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public interface CSVDatabaseAccessInterface {
    Map<Integer, Patient> getAllPatients();

    Patient getPatient(int id);

    void deletePatient(int id);

    void addPatient(int id, String fullName, float height, float weight,
                    ArrayList<LocalDate> appointmentDates, ArrayList<Drug> prescribedDrugs,
                    ArrayList<String> allergies, ArrayList<String> illnesses, ArrayList<String> symptoms);

}
