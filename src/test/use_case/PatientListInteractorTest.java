package test.use_case;

import static org.junit.jupiter.api.Assertions.*;
import static test.TestDataFactory.createTestPatient;

import data_access.CSVDatabaseAccessObject;
import entity.Drug;
import entity.Patient;
import org.junit.jupiter.api.*;

import use_case.addPatient.AddPatientUseCase;
import use_case.patientList.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

// NOTE: this file tests PatientListInteractor, FetchPatientUseCase, AddPatientUseCase, and DeletePatientUseCase
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PatientListInteractorTest {
    private PatientListInteractor patientListInteractor;
    private FetchPatientsUseCase fetchPatientsUseCase;
    private AddPatientUseCase addPatientUseCase;
    private DeletePatientUseCase deletePatientUseCase;
    private CSVDatabaseAccessObject data = new CSVDatabaseAccessObject("data/Doctor 1.csv");

    public PatientListInteractorTest() throws IOException {
    }

    @BeforeEach
    void setUp(){
        fetchPatientsUseCase = new FetchPatientsUseCase(data);
        addPatientUseCase = new AddPatientUseCase(data);
        deletePatientUseCase = new DeletePatientUseCase(data);
        patientListInteractor = new PatientListInteractor(
                fetchPatientsUseCase,
                addPatientUseCase,
                deletePatientUseCase
        );
    }

    @Test
    @Order(1)
    void testFetchPatient() {
        ArrayList<PatientListOutputData> patients = patientListInteractor.fetchPatients();
        assertFalse(patients.isEmpty());
    }


    @Test
    @Order(2)
    void testAddPatient() {
        String fullName = "Sarah Jane";
        float height = 1.75f; // in meters
        float weight = 70.0f; // in kilograms

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String[] appointmentDates = {
                LocalDate.of(2023, 11, 20).format(formatter),
                LocalDate.of(2023, 9, 13).format(formatter),
                LocalDate.of(2023, 9, 4).format(formatter)
        };
        // set date added as right now
        LocalDate dateAdded = LocalDate.now(); // Current date as date added
        String dateOfBirth = String.valueOf(LocalDate.now());
        ArrayList<Object[]> prescribedDrugs = new ArrayList<>();

        // ALLERGIES
        ArrayList<String> allergies = new ArrayList<>();
        allergies.add("Pollen");

        // ILLNESSES
        ArrayList<String> illnesses = new ArrayList<>();
        illnesses.add("Hypertension");
        // SYMPTOMS
        ArrayList<String> symptoms = new ArrayList<>();
        symptoms.add("Headache");

        // LIFESTYLE INFO
        String lifestyleInformation = "high consumption of fast food and alcohol";

        boolean isPregnant = true;
        String additionalNotes = "";

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

//        patientListInteractor.addPatient(fullName, height, weight, dateOfBirth, "female", appointmentDates, prescribedDrugs,
//                allergies, illnesses, symptoms, lifestyleInformation, isPregnant, additionalNotes);

        assertTrue(outContent.toString().contains("Added patient successfully"));
        System.setOut(originalOut);
    }
    @Test
    @Order(3)
    void testDeletePatient() {
        // Setup to capture the standard output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        int patientId = 2;
        Patient patient = data.getPatient(patientId);
        patientListInteractor.deletePatient(patientId);

        // Check that the expected message was printed
        assertTrue(outContent.toString().contains("File deleted successfully"));

        // Reset
        data.addPatient(patient);
        System.setOut(originalOut);
    }

}