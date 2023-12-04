package test.shared;

import entity.Doctor;
import entity.Patient;
import entity.UserFactory;

import java.util.ArrayList;

public class FakeUserFactory implements UserFactory {
    private Doctor lastCreatedDoctor;
    @Override
    public Doctor create(String username, String password, String country, ArrayList<Patient> patients) {
        lastCreatedDoctor = new Doctor(username, password, patients);
        return lastCreatedDoctor;
    }

    public Doctor getLastCreatedDoctor() {
        return lastCreatedDoctor;
    }
}
