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
     * writes the information on the CURRENT doctor (that is logged in) to csv
     */
    void save();

    Doctor get(String username);

    /**
     * saves all current patients (ie. rewrites them to csv) of the current logged in doctor
     */
    void savePatients();
}
