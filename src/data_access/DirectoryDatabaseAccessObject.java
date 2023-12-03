package data_access;

import entity.Doctor;
import use_case.login.LoginUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class DirectoryDatabaseAccessObject implements LoginUserDataAccessInterface, SignupUserDataAccessInterface {
    public DirectoryDatabaseAccessObject() {

    }


    /**
     *
     * @param identifier
     * @return returns whether there already exists a doctor with the identifier
     */
    @Override
    public boolean existsByName(String identifier) {
        String fileName = "Doctor " + identifier + ".csv";
        String directoryPath = "data/";

        File directory = new File(directoryPath);
        File[] listOfFiles = directory.listFiles();

        boolean fileExists = false;

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile() && file.getName().equals(fileName)) {
                    fileExists = true;
                    break;
                }
            }
        }

        return fileExists;
    }


    /**
     * saves a new doctor to the directory in a new csv file
     */
    @Override
    public void saveNewDoctor() {

    }

    /**
     *
     * @param username
     * @param password
     * @return TRUE if the username and password is valid. NOTE: Assumes doctor exists
     */
    @Override
    public boolean passwordMatch(String username, String password) {
        File file = new File("data/Doctor " + username + ".csv");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            // read username lines
            reader.readLine();
            String realUsername = String.valueOf(reader.readLine());
            reader.readLine();
            String realPassword = String.valueOf(reader.readLine());
            if (realUsername.equals(username) && realPassword.equals(password)) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return false;
    }

}
