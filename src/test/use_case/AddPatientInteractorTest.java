package test.use_case.addPatient;

import static org.junit.jupiter.api.Assertions.*;

import data_access.CSVDatabaseAccessInterface;
import data_access.CSVDatabaseAccessObject;
import entity.Patient;
import interface_adapter.addPatient.AddPatientPresenter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.addPatient.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class AddPatientInteractorTest {
    private AddPatientInteractor addPatientInteractor;
    private CSVDatabaseAccessObject databaseAccessObject;
    private FakeAddPatientPresenter fakePresenter;

    @BeforeEach
    void setUp() {
        databaseAccessObject = new CSVDatabaseAccessObject("data/Doctor a.csv");
        fakePresenter = new FakeAddPatientPresenter();
        addPatientInteractor = new AddPatientInteractor(databaseAccessObject, fakePresenter);
    }

    @Test
    void testAddPatientToDatabase() {
        String fullName = "Sarah Jane";
        String height = "1.75f";
        String weight = "70.0f";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String[] appointmentDates = {
                LocalDate.of(2023, 11, 20).format(formatter),
                LocalDate.of(2023, 9, 13).format(formatter),
                LocalDate.of(2023, 9, 4).format(formatter)
        };

        LocalDate dateAdded = LocalDate.now();
        String dateOfBirth = String.valueOf(LocalDate.now());
        ArrayList<String[]> prescribedDrugs = new ArrayList<>();
        String[] drugData = { "Aspirin", "100", "2023-01-01", "2023-01-10" };
        prescribedDrugs.add(drugData);

        String[] allergies = { "allergies"};

        String[] illnesses = {"hypertension"};

        String[] symptoms = {"headahcle"};

        // LIFESTYLE INFO
        String lifestyleInformation = "high consumption of fast food and alcohol";

        String isPregnant = "pregnant";
        String additionalNotes = "";

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        AddPatientInputData inputData = new AddPatientInputData(fullName, height, weight, dateOfBirth,
                "female", appointmentDates, prescribedDrugs,
                allergies, illnesses, symptoms, lifestyleInformation, isPregnant, additionalNotes);

        addPatientInteractor.execute(inputData);

        assertTrue(outContent.toString().contains("Added patient successfully"));
        System.setOut(originalOut);
    }

    @Test
    void testCallDisplay() {
        addPatientInteractor.display();
        assertTrue(fakePresenter.isDisplayCalled);
    }


    private static class FakeAddPatientPresenter implements AddPatientOutputBoundary {
        boolean isDisplayCalled = false;

        @Override
        public void display() {
            isDisplayCalled = true;
        }
    }

}