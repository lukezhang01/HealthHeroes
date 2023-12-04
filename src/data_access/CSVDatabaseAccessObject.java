package data_access;
import entity.*;
import entity.Drug;


import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CSVDatabaseAccessObject implements CSVDatabaseAccessInterface {


    private final String[] patient_headers = {"id", "full_name", "height", "weight", "date_of_birth", "gender", "appointment_date", "date_added", "prescribed_drugs",
            "allergies", "illnesses", "symptoms", "lifestyle_information", "isPregnant", "additional_notes"};
    private Map<Integer, Patient> patients = new HashMap<>();
    private final String filePath;
    private String username;
    private String password;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    public CSVDatabaseAccessObject(String doctorFilePath){
        this.filePath = doctorFilePath;
        File doctorFile = new File(doctorFilePath);
        if (doctorFile.length() == 0) {
            System.out.println("Need to enter doctor file path");
        } else {
            try (BufferedReader reader = new BufferedReader(new FileReader(doctorFile))) {
                reader.readLine();
                this.username = String.valueOf(reader.readLine());
                System.out.println(username);
                reader.readLine();
                this.password = String.valueOf(reader.readLine());
                System.out.println(password);
                reader.readLine();
                String patientsLine = String.valueOf(reader.readLine());
                System.out.println(patientsLine);
                if (patientsLine.equals("") || patientsLine.equals("null")) {
                    System.out.println("no patients");
                } else {
                    String[] patientList = patientsLine.split(",");
                    for (String patientID : patientList) {
                        patients.put(Integer.parseInt(patientID), readPatientFromCSV(new File("data/Patient " + patientID + ".csv")));
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
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
            float height = Float.parseFloat(String.valueOf(reader.readLine()));
            reader.readLine();
            float weight = Float.parseFloat(String.valueOf(reader.readLine()));
            reader.readLine();
            LocalDate dateOfBirth = LocalDate.parse(String.valueOf(reader.readLine()), formatter);
            reader.readLine();
            String gender = String.valueOf(reader.readLine());
            reader.readLine();
            String[] appointmentDates = String.valueOf(reader.readLine()).split(",");
            reader.readLine();
            LocalDate dateAdded = LocalDate.parse(String.valueOf(reader.readLine()), formatter);
            reader.readLine();
            String[] drugs = String.valueOf(reader.readLine()).split(",");
            reader.readLine();
            String[] allergies = String.valueOf(reader.readLine()).split(",");
            reader.readLine();
            String[] illnesses = String.valueOf(reader.readLine()).split(",");
            reader.readLine();
            String[] symptoms = String.valueOf(reader.readLine()).split(",");
            reader.readLine();
            String lifestyleInfomation = String.valueOf(reader.readLine());
            reader.readLine();
            boolean isPregnant = Boolean.parseBoolean(String.valueOf(reader.readLine()));
            reader.readLine();
            String additionalNotes = String.valueOf(reader.readLine());
            return new Patient(id, fullName, height, weight, dateOfBirth, gender,
                    getDates(appointmentDates), dateAdded, getDrugs(drugs),
                    new ArrayList<>(List.of(allergies)),
                    new ArrayList<>(List.of(illnesses)),
                    new ArrayList<>(List.of(symptoms)),
                    lifestyleInfomation, isPregnant, additionalNotes);
        }
    }


    public ArrayList<LocalDate> getDates(String[] dates) {
        ArrayList<LocalDate> localDates = new ArrayList<>();
        for (String date : dates) {
            if (date.isEmpty()) {
                continue;
            }
            localDates.add(LocalDate.parse(date, formatter));
        }
        return localDates;
    }


    public ArrayList<Drug> getDrugs(String[] drugs) {
        ArrayList<Drug> drugsList = new ArrayList<>();
        for (String drug : drugs) {
            if (drug.isEmpty()) {
                continue;
            }
            ArrayList<String> drug_info = extractInfo(drug);
            System.out.println(drug_info);
            drugsList.add(new Drug(drug_info.get(0), Float.parseFloat(drug_info.get(1)),
                    LocalDate.parse(drug_info.get(2)),
                    LocalDate.parse(drug_info.get(3))));
        }
        return drugsList;
    }

    public ArrayList<String> extractInfo(String inputString) {
        // Define the regex pattern
        String patternString = "Drug Name: (\\w+)| Dosage: (\\d+\\.\\d+)| Start Date: (\\d{4}-\\d{2}-\\d{2})| End Date: (\\d{4}-\\d{2}-\\d{2})";

        // Compile the regex
        Pattern pattern = Pattern.compile(patternString);

        // Create a matcher for the input string
        Matcher matcher = pattern.matcher(inputString);

        // ArrayList to hold the extracted data
        ArrayList<String> extractedData = new ArrayList<>();

        // Find and extract the data
        while (matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                if (matcher.group(i) != null) {
                    extractedData.add(matcher.group(i));
                }
            }
        }

        return extractedData;
    }

    @Override
    public Patient getPatient(int id) {
        return patients.get(id);
    }

    public void saveSinglePatient(String id, Patient patient) {
        BufferedWriter writer;
        try {
            String file_path = "data/Patient " + id + ".csv";
            writer = new BufferedWriter(new FileWriter(file_path, false));
            String[] patientData = new String[]{String.valueOf(patient.getID()), patient.fullName, String.valueOf(patient.getHeight()),
                    String.valueOf(patient.getWeight()), patient.getBirthDateAsString(), patient.getGender(), patient.getAppointmentDatesAsString(), patient.getDateAdded().toString(),
                    patient.getPrescribedDrugsAsString(), patient.getAllergiesAsString(), patient.getIllnessesAsString(),
                    patient.getSymptomsAsString(), patient.getLifestyleInformation(), String.valueOf(patient.getIsPregnant()),
                    patient.getAdditionalNotes()};
            for (int i = 0; i < patient_headers.length; i++) {
                writer.write(patient_headers[i]);
                writer.newLine();
                writer.write(patientData[i]);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }


    @Override
    public Map<Integer, Patient> getAllPatients() {
        return this.patients;
    }

    @Override
    public void savePatients() {
        BufferedWriter writer;
        try {
            for (Patient patient: patients.values()) {
                String file_path = "data/Patient " + patient.getID() + ".csv";
                writer = new BufferedWriter(new FileWriter(file_path, false));
                String[] patientData = new String[]{String.valueOf(patient.getID()), patient.fullName, String.valueOf(patient.getHeight()),
                        String.valueOf(patient.getWeight()), patient.getBirthDateAsString(), patient.getGender(), patient.getAppointmentDatesAsString(), patient.getDateAdded().toString(),
                        patient.getPrescribedDrugsAsString(), patient.getAllergiesAsString(), patient.getIllnessesAsString(),
                        patient.getSymptomsAsString(), patient.getLifestyleInformation(), String.valueOf(patient.getIsPregnant()),
                        patient.getAdditionalNotes()};
                for (int i = 0; i < patient_headers.length; i++) {
                    writer.write(patient_headers[i]);
                    writer.newLine();
                    writer.write(patientData[i]);
                    writer.newLine();
                }
                writer.close();
            }


        } catch (IOException e) {
            System.out.println(e);
        }
    }



    public boolean existsById(int id) {
        return patients.containsKey(id);
    }


    @Override
    public void save() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false));
            writer.write("username");
            writer.newLine();
            writer.write(username);
            writer.newLine();
            writer.write("password");
            writer.newLine();
            writer.write(password);
            writer.newLine();
            writer.write("patients");
            writer.newLine();
            String temp = "";
            for (int id : patients.keySet()) {
                temp += "," + id;
            }
            writer.write(temp.substring(1));
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void deletePatient(int id) {
        // remove from doctor csv
        try {
            this.patients.remove(id);
            save();
        } catch (Exception e) {
            System.out.println(e);
        }
        // remove csv file
        try {
            File file = new File("data/Patient " + id + ".csv");

            // using if else statement for testing purposes
            if (file.delete()) {
                System.out.println("File deleted successfully");
            } else {
                System.out.println("Failed to delete file");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void addPatient(Patient patient) {
        this.patients.put(patient.getID(), patient);
        this.savePatients();
        this.save();
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public ArrayList<Patient> getPatients() {
        return new ArrayList<>(this.patients.values());
    }

    public Map<String, String> getPatientData(int id) {
        Patient patient = patients.get(id);
        Map<String, String> data = new HashMap<>();
        String[] allData = patient.getAllData();
        for (int i = 0; i < patient_headers.length; i++) {
            data.put(patient_headers[i], allData[i]);
        }
        return data;
    }

    public ArrayList<String[]> getDrugsAsStringList(int id) {
        ArrayList<String[]> lst = new ArrayList<>();
        System.out.println(id);
        System.out.println(patients.keySet());
        ArrayList<Drug> drugs = patients.get(id).getDrugs();
        for (Drug drug : drugs) {
            String[] sub = {drug.drug_name, drug.getDosageAsString(), drug.getStart_date().toString(), drug.getEnd_date().toString()};
            lst.add(sub);
        }
        return lst;
    }
}

