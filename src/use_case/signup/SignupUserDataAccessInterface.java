package use_case.ll;

public interface SignupUserDataAccessInterface {
    boolean existsByName(String identifier);

    void save();
}
