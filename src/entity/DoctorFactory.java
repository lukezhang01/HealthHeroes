package entity;

import java.util.ArrayList;

public class DoctorFactory implements UserFactory{

    @Override
    public Doctor create(String username, String password, String country, ArrayList<Patient> patients) {
        return new Doctor(username, password, patients);
    }
}
