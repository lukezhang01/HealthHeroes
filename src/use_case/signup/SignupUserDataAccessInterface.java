package use_case.signup;
import entity.*;

public interface SignupUserDataAccessInterface {
    boolean existsByName(String identifier);

    void save(Doctor doctor);
}
