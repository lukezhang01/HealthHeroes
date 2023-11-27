package data_access;

import entity.Patient;

import java.util.ArrayList;
import java.util.Map;

public interface CSVDatabaseAccessInterface {
    Map<Integer, Patient> getAllPatients();

    Patient getPatient(int id);
}
