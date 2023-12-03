package data_access;

import entity.Doctor;
import entity.Patient;
import use_case.login.LoginUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

import java.io.*;

public class DirectoryDatabaseAccessObject implements LoginUserDataAccessInterface, SignupUserDataAccessInterface {
    private final String[] headers = {"username", "password", "patients"};
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
    public void saveNewDoctor(String username, String password) {
        BufferedWriter writer;
        String[] info = {username, password, ""};
        try {
            String filePath = "data/Doctor " + username + ".csv";
            writer = new BufferedWriter(new FileWriter(filePath, false));
            for (int i = 0; i < headers.length; i++) {
                writer.write(headers[i]);
                writer.newLine();
                writer.write(info[i]);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e);
        }
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

    @Override
    public Doctor get(String username) {
        try {
            //System.out.println("test1");
            CSVDatabaseAccessObject databaseAccessObject = new CSVDatabaseAccessObject("data/Doctor " + username + ".csv");
            //System.out.println(databaseAccessObject.getUsername()+", "+databaseAccessObject.getPassword()+", "+databaseAccessObject.getPatients());
            return new Doctor(databaseAccessObject.getUsername(), databaseAccessObject.getPassword(), databaseAccessObject.getPatients());
        } catch (IOException e) {
            return null;
        }

    }


}
