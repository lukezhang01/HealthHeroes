package use_case.patientList;

public interface PatientListOutputBoundary {
    void prepareSuccessView(PatientListOutputData patients);

    void prepareFailView(String error);
}
