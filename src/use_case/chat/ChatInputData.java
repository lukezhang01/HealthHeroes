package use_case.chat;

import entity.Doctor;
import entity.Patient;

public class ChatInputData {
    final private Doctor doctor;
    final private int patientId;
    final private String message;


    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return doctor.getPatient(this.patientId);
    }

    public String getMessage() {
        return this.message;
    }

    public ChatInputData(Doctor doctor, int patientId, String message) {
        this.doctor = doctor;
        this.patientId = patientId;
        this.message = message;
    }
}
