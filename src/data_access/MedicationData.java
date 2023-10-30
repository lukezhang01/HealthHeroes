package data_access;

public class MedicationData implements Dataclass {
    private int id;
    private int dosage;
    private String start_date;
    private String end_date;


    public void MedicationData(int id, int dosage, String start_date, String end_date) {
        this.id = id;
        this.dosage = dosage;
        this.start_date = start_date;
        this.end_date = end_date;
    }
    @Override
    public int getID() {
        return this.id;
    }
}
