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

    void addPatient(Patient patient);

}
