package use_case.prescribe;
import entity.*;

public interface PrescribeUserDataInterface {

    Patient getPatient(int id);
    void save();
}
