package entity;

import java.util.ArrayList;

public interface UserFactory {
    Doctor create(String username, String password, String country, ArrayList<Patient> patients);
}
