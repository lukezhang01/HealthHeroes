package use_case.prescribe;
import entity.*;

public interface PrescribeUserDataInterface {

    public Patient getPatient(int id);
    void save();
}
