package entity;

import use_case.MedicationData;
import java.util.*;

public class Patient implements User {
    private int id;
    private String name;
    private String password;
    private ArrayList<MedicationData> medication_data;

    public void Patient(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public int getID() { return this.id; }

    public ArrayList<MedicationData> getMedicationData() { return this.medication_data; }

    public void setMedicationData() {}

    public MedicationData deleteMedicationData() { return null; }
}
