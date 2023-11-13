package data_access;
import entity.*;
import use_case.Drug;
import use_case.signup.SignupUserDataAccessInterface;

import java.io.*;
import java.util.*;
import java.time.LocalDate;

public class CSVDatabaseAccessObject implements SignupUserDataAccessInterface {

    private final String[] patient_headers = {"id", "full_name", "height", "weight", "appointment_date", "date_added", "prescribed_drugs",
            "allergies", "illnesses", "symptoms"};
    private Map<String, Patient> patients = new HashMap<>();

    public CSVDatabaseAccessObject(String file_path) throws IOException{
        
    }

    public Patient readPatientFromCSV(File csvFile) throws  IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            reader.readLine();
            int id = Integer.parseInt(String.valueOf(reader.readLine()));
            reader.readLine();
            String fullName = String.valueOf(reader.readLine());
            reader.readLine();
            int height = Integer.parseInt(String.valueOf(reader.readLine()));
            reader.readLine();
            int weight = Integer.parseInt(String.valueOf(reader.readLine()));
            reader.readLine();
            String[] appointmentDates = String.valueOf(reader.readLine()).split(",");
            reader.readLine();
            LocalDate dateAdded = LocalDate.parse(String.valueOf(reader.readLine()));
            String[] drugs = String.valueOf(reader.readLine()).split(",");
            reader.readLine();
            String[] allergies = String.valueOf(reader.readLine()).split(",");
            reader.readLine();
            String[] illnesses = String.valueOf(reader.readLine()).split(",");
            reader.readLine();
            String[] symptoms = String.valueOf(reader.readLine()).split(",");
            return new Patient(id, fullName, height, weight, getDates(appointmentDates), dateAdded, getDrugs(drugs),
                    new ArrayList(List.of(allergies)),
                    new ArrayList(List.of(illnesses)),
                    new ArrayList(List.of(symptoms)));
        }
    }

    public ArrayList<LocalDate> getDates(String[] dates) {
        ArrayList<LocalDate> localDates = new ArrayList<>();
        for (String date : dates) {
            localDates.add(LocalDate.parse(date));
        }
        return localDates;
    }

    public ArrayList<Drug> getDrugs(String[] drugs) {
        ArrayList<Drug> drugsList = new ArrayList<>();
        for (String drug : drugs) {
            String[] drug_info = drug.split(" ");
            drugsList.add(new Drug(drug_info[0], Float.parseFloat(drug_info[1]),
                    LocalDate.parse(drug_info[2]),
                    LocalDate.parse(drug_info[3])));
        }
        return drugsList;
    }

    public Patient getPatient(String username) {
        return patients.get(username);
    }


    private void savePatients() {
        BufferedWriter writer;
        try {

            for (Patient patient: patients.values()) {
                String file_path = "Patient " + patient.getID();
                writer = new BufferedWriter(new FileWriter(file_path));
                String[] patientData = patient.getAllData();
                for (int i = 0; i < patient_headers.length; i++) {
                    writer.write(patient_headers[i]);
                    writer.newLine();
                    writer.write(patientData[i]);
                }
                writer.close();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsByName(String identifier) {
        return false;
    }

    @Override
    public void save(Doctor doctor) {
        BufferedWriter writer;
        try {


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
