package use_case.login;

import entity.Doctor;

public interface LoginUserDataAccessInterface {

    /**
     *
     * @param identifier
     * @return returns whether there already exists a doctor with the identifier
     *      returns TRUE if THERE EXISTS a doctor
     */
    boolean existsByName(String identifier);

    /**
     *
     * @param username
     * @param password
     * @return checks whether the username and password credentials are valid
     * NOTE: Assumes the doctor with the username already exists (make sure to call existByName first)
     */
    boolean passwordMatch(String username, String password);

}
