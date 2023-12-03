package use_case.signup;

public interface SignupUserDataAccessInterface {
    /**
     *
     * @param identifier
     * @return returns whether there already exists a doctor with the identifier
     *      returns TRUE if THERE EXISTS a doctor
     */
    boolean existsByName(String identifier);

    void saveNewDoctor(String username, String password);

}
