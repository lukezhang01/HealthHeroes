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
    private final String[] doctor_headers = {"username", "password", "patients"};
    private Map<Integer, Patient> patients = new HashMap<>();
    private String[] allPatientIDs;
    private final String filePath;
    private String username;
    private String password;

    public CSVDatabaseAccessObject(String doctorFilePath) throws IOException{
        this.filePath = doctorFilePath;
        File doctorFile = new File(doctorFilePath);
        if (doctorFile.length() == 0) {
            save();
        } else {
            try (BufferedReader reader = new BufferedReader(new FileReader(doctorFile))) {
                reader.readLine();
                this.username = String.valueOf(reader.readLine());
                reader.readLine();
                this.password = String.valueOf(reader.readLine());
                reader.readLine();
                String[] patientList = String.valueOf(reader.readLine()).split(",");
                for (String patientID : patientList) {
                    patients.put(Integer.parseInt(patientID), readPatientFromCSV(new File("Patient " + patientID + ".csv")));
                }
            }
        }
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
                    new ArrayList<>(List.of(allergies)),
                    new ArrayList<>(List.of(illnesses)),
                    new ArrayList<>(List.of(symptoms)));
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

    public Patient getPatient(int id) {
        return patients.get(id);
    }


    private void savePatients() {
        BufferedWriter writer;
        try {
            for (Patient patient: patients.values()) {
                String file_path = "Patient " + patient.getID();
                writer = new BufferedWriter(new FileWriter(file_path));
                String[] patientData = new String[]{String.valueOf(patient.getID()), patient.fullName, String.valueOf(patient.getHeight()),
                            String.valueOf(patient.getWeight()), patient.getAppointmentDatesAsString(), patient.getDateAdded().toString(),
                            patient.getPrescribedDrugsAsString(), patient.getAllergiesAsString(), patient.getIllnessesAsString(),
                            patient.getSymptomsAsString()};
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
    public void save() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write("username");
            writer.newLine();
            writer.write(username);
            writer.newLine();
            writer.write("password");
            writer.write(password);
            writer.newLine();
            writer.write("patients");
            writer.newLine();
            String temp = "";
            for (int id : patients.keySet()) {
                temp += "," + id;
            }
            writer.write(temp);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
