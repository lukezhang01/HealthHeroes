package use_case;
import java.time.*;

public class Drug implements Dataclass {
    public String drug_name;
    private float dosage;
    private LocalDate start_date;
    private LocalDate end_date;


    public Drug(String drug_name, float dosage, LocalDate start_date, LocalDate end_date) {
        this.drug_name = drug_name;
        this.dosage = dosage;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public LocalDate getStart_date() {
        return this.start_date;
    }

    public LocalDate getEnd_date() {
        return this.end_date;
    }

    public String getDosageAsString() {
        return String.valueOf(this.dosage);
    }

}
