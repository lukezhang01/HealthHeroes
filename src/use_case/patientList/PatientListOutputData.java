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

    public String getFullName() {
        return this.fullName;
    }

    public String getAppointmentDate() {
        return this.appointmentDate;
    }

    public String getDateAdded() {
        return this.getDateAdded();
    }

    public int getId() {
        return this.id;
    }
}
