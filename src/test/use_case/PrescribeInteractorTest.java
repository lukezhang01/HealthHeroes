//package test.use_case;
//
//import entity.*;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import use_case.prescribe.*;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static test.TestDataFactory.createTestDrug;
//import static test.TestDataFactory.createTestPatient;
//
//public class PrescribeInteractorTest {
//
//    private PrescribeInteractor prescribeInteractor;
//    private FakePrescribeUserDataAccessObject fakeUserDataAccessObject;
//    private FakePrescribeOutputBoundary fakePrescribePresenter;
//
//    @BeforeEach
//    void setUp() {
//        fakeUserDataAccessObject = new FakePrescribeUserDataAccessObject();
//        fakePrescribePresenter = new FakePrescribeOutputBoundary();
//        prescribeInteractor = new PrescribeInteractor(fakeUserDataAccessObject, fakePrescribePresenter, new UserFactory());
//    }
//
//    @Test
//    void testPatientExists() throws IOException {
//        Patient patient = createTestPatient();
//        fakeUserDataAccessObject.addPatient(patient);
//
//        Drug drug = createTestDrug();
//        PrescribeInputData inputData = new PrescribeInputData(drug, 1);
//        prescribeInteractor.execute(inputData);
//
//        assertEquals(drug.drug_name, fakePrescribePresenter.lastOutputData.getDrugName());
//        assertEquals("SuccessView", fakePrescribePresenter.lastViewShown);
//    }
//
//    @Test
//    void testPatientDoesNotExist() throws IOException {
//        Drug drug = createTestDrug();
//        PrescribeInputData inputData = new PrescribeInputData(drug, 1);
//        prescribeInteractor.execute(inputData);
//
//        assertEquals("Patient does not exist", fakePrescribePresenter.lastErrorMessage);
//        assertEquals("FailView", fakePrescribePresenter.lastViewShown);
//    }
//
//    private static class FakePrescribeUserDataAccessObject implements PrescribeUserDataInterface {
//        private Map<Integer, Patient> patients = new HashMap<>();
//
//        public Patient getPatient(int id) {
//            return patients.get(id);
//        }
//
//        @Override
//        public void save() {
//
//        }
//
//        public void addPatient(Patient patient) {
//            patients.put(patient.getID(), patient);
//        }
//
//    }
//
//    private static class FakePrescribeOutputBoundary implements PrescribeOutputBoundary {
//        String lastErrorMessage;
//        PrescribeOutputData lastOutputData;
//        String lastViewShown;
//
//        @Override
//        public void prepareFailView(String errorMessage) {
//            this.lastErrorMessage = errorMessage;
//            this.lastViewShown = "FailView";
//        }
//
//        @Override
//        public void prepareSuccessView(PrescribeOutputData prescribeOutputData) {
//            this.lastOutputData = prescribeOutputData;
//            this.lastViewShown = "SuccessView";
//        }
//    }
//}
