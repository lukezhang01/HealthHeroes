package use_case.prescribe;
import data_access.FdaDatabaseAccessObject;
import entity.*;

import java.io.IOException;
import java.time.LocalDate;

public class PrescribeOutputData {
    private String drugName;
    private FdaDatabaseAccessObject databaseobj;

    public PrescribeOutputData(Drug drug) {
        this.drugName = drug.drug_name;
        this.databaseobj = new FdaDatabaseAccessObject();
    }

    public String getDrugName() {
        return drugName;
    }

    public String getWarnings() throws IOException {
        try {
            return databaseobj.DrugInfo(1, "spl_product_data_elements", drugName).get(0).get("warnings").toString();
        } catch (NullPointerException e) {
            return "Warnings or Drug Not Found";
        }
    }

    public String getDescription() throws IOException {
        try {
            return databaseobj.DrugInfo(1, "spl_product_data_elements", drugName).get(0).get("description").toString();
        } catch (NullPointerException e) {
            return "Description or Drug Not Found";
        }
    }

    public String getInteractions() throws IOException {
        try {
            return databaseobj.DrugInfo(1, "spl_product_data_elements", drugName).get(0).get("drug_interactions").toString();
        } catch (NullPointerException e) {
            return "Interactions Info or Drug Not Found";
        }
    }

    public String getPregnancy() throws IOException {
        try {
            return databaseobj.DrugInfo(1, "spl_product_data_elements", drugName).get(0).get("pregnancy").toString();
        } catch (NullPointerException e) {
            return "Pregnancy Info or Drug Not Found";
        }
    }

    public String getNursing() throws IOException {
        try {
            return databaseobj.DrugInfo(1, "spl_product_data_elements", drugName).get(0).get("nursing_mothers").toString();
        } catch (NullPointerException e) {
            return "Nursing Mother Info or Drug Not Found";
        }
    }

    public String getUsage() throws IOException {
        try {
            return databaseobj.DrugInfo(1, "spl_product_data_elements", drugName).get(0).get("indications_and_usage").toString();
        } catch (NullPointerException e) {
            return "Usage Info or Drug Not Found";
        }
    }

    public String getAbuse() throws IOException {
        try {
            return databaseobj.DrugInfo(1, "spl_product_data_elements", drugName).get(0).get("abuse").toString();
        } catch (NullPointerException e) {
            return "Drug Abuse Info or Drug Not Found";
        }
    }

    public String getHandling() throws IOException {
        try {
            return databaseobj.DrugInfo(1, "spl_product_data_elements", drugName).get(0).get("storage_and_handling").toString();
        } catch (NullPointerException e) {
            return "Handling Info or Drug Not Found";
        }
    }

    public String getReactions() throws IOException {
        try {
            return databaseobj.DrugInfo(1, "spl_product_data_elements", drugName).get(0).get("adverse_reactions").toString();
        } catch (NullPointerException e) {
            return "Adverse Reactions or Drug Not Found";
        }
    }



    public static void main(String[] args) throws IOException{

        PrescribeOutputData data = new PrescribeOutputData(new Drug("ibuprofen",1, LocalDate.now(),LocalDate.now()));
        System.out.println(data.getWarnings());

    }

}
