package use_case.patientList;

import java.time.LocalDate;

public class PatientListOutputData {
    private final String fullName;
    private final int id;
    private String appointmentDate;
    private String dateAdded;
    public PatientListOutputData(String fullName, int id, String appointmentDate, String dateAdded) {
        this.fullName = fullName;
        this.id = id;
        this.appointmentDate = appointmentDate;
        this.dateAdded = dateAdded;
    }


}
