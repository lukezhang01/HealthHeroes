package data_access;
import entity.*;
import use_case.Drug;

import java.io.*;
import java.util.*;
import java.time.LocalDate;

public class CSVDatabaseAccessObject {
    private final File csvFile;
    private Map<String, Integer> patient_headers = new HashMap<>();
    private Map<String, Patient> patients = new HashMap<>();

    public CSVDatabaseAccessObject(String file_path) throws IOException{
        String[] headers = {"id", "username", "password", "appointment_date", "date_added", "prescribed_drugs",
                "allergies", "illnesses", "symptoms"};
        for (int i = 0; i < headers.length; i++) {
            patient_headers.put(headers[i], i);
        }
        csvFile = new File(file_path);
        if (csvFile.length() == 0) {
            save_patients();
        } else {
            find_patients(csvFile);
        }
    }

    public void find_patients(File csvFile) throws  IOException {

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String headers_csv = reader.readLine();

            String row;
            while ((row = reader.readLine()) != null) {
                String[] col = row.split(",");
                int id = Integer.parseInt(col[patient_headers.get("id")]);
                String username = String.valueOf(col[patient_headers.get("username")]);
                String password = String.valueOf(col[patient_headers.get("password")]);
                LocalDate appointment_date = LocalDate.parse(String.valueOf(col[patient_headers.get("appointment_date")]));
                LocalDate date_added = LocalDate.parse(String.valueOf(col[patient_headers.get("date_added")]));
                String[] drugs_info = String.valueOf(col[patient_headers.get("prescribed_drugs")]).split(" ");
                String[] allergies = String.valueOf(col[patient_headers.get("allergies")]).split(" ");
                String[] illnesses = String.valueOf(col[patient_headers.get("illnesses")]).split(" ");
                String[] symptoms = String.valueOf(col[patient_headers.get("symptoms")]).split(" ");

                // add dependency injection here ; automate for random amount of parameters
                ArrayList<Drug> prescribed_drugs = new ArrayList<>();
                for (int i = 0; i < drugs_info.length; i += 4) {
                    Drug drug = new Drug(drugs_info[i],
                            Float.parseFloat(drugs_info[i + 1]),
                            LocalDate.parse(drugs_info[i + 2]),
                            LocalDate.parse(drugs_info[i + 3]));
                    prescribed_drugs.add(drug);
                }

                Patient patient = new Patient(id, username, password,
                        appointment_date, date_added, prescribed_drugs,
                        new ArrayList<>(List.of(allergies)),
                        new ArrayList<>(List.of(illnesses)),
                        new ArrayList<>(List.of(symptoms)));
                patients.put(username, patient);
            }
        }
    }

    public Patient get_patient(String username) {
        return patients.get(username);
    }

    public void save_patient(Patient patient) {
        patients.put(patient.getName(), patient);
        this.save_patients();
    }

    private void save_patients() {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(csvFile));
            writer.write(String.join(",", patient_headers.keySet()));
            writer.newLine();

            for (Patient patient: patients.values()) {
                String line = String.format("%s,%s,%s,%s,%s,%s,%s,%s",
                        patient.getName(), patient.getPassword(), patient.getAppointmentDateAsString(),
                        patient.getDateAddedAsString(), patient.getPrescribedDrugsAsString(),
                        patient.getAllergiesAsString(), patient.getIllnessesAsString(), patient.getSymptomsAsString());
                writer.write(line);
                writer.newLine();
            }

            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
