package use_case.login;

import entity.Doctor;

public interface LoginUserDataAccessInterface {
    boolean existsByName(String identifier);

    void save(Doctor doctor);

    Doctor get(String username);
}
