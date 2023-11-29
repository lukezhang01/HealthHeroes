package entity;
import java.util.*;

public class Doctor implements User {
    private String name;
    private String password;
    private ArrayList<Patient> patients;

    public Doctor(String name, String password, ArrayList<Patient> patients) {
        this.name = name;
        this.password = password;
        this.patients = patients;
    }

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }

    public void addPatient(Patient new_patient) {
        this.patients.add(new_patient);
    }

    public void removePatient(Patient patient) {
        this.patients.remove(patient);
    }

    public Patient getPatient(Integer id){
        for(int i=0; i<patients.size();i++){
            if(patients.get(i).getID() == id){
                return patients.get(i);
            }
        }
        return null;
    }
}
