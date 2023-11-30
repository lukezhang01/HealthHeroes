package data_access;
import java.util.Arrays;
import java.util.List;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Map;

public class FdaDatabaseAccessObject implements FdaDatabaseAccessInterface{
    private final String apiKey ;

    public FdaDatabaseAccessObject(){
        apiKey = "AhsIyByI3vFVW2gRIsMVT9u4UbAgGTwgnQxxHPZi";
    }

    public List<Map<String, Object>> convertCallToList(String apiCall){
        try {
            // Building Json from Api Call
            URL url = new URL(apiCall);
            URLConnection connection = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder jsonData = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonData.append(line);
            }
            String json = jsonData.toString();
            reader.close();
            // Converting json data into a list of maps
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(json);
            JsonNode resultsArray = jsonNode.get("results");
            List<Map<String, Object>> listOfMaps = new ArrayList<>();
            for (JsonNode result : resultsArray) {
                Map<String, Object> resultMap = objectMapper.convertValue(result, Map.class);
                listOfMaps.add(resultMap);
            }
            return listOfMaps;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns a List of maps, with each item in the list being a map of information about a singular product recall
     * Returns recentlyRecalled products
     * @param  limit  the number of recent product recalls wanted
     */
    public List<Map<String, Object>> recentlyRecalled(Integer limit) {

        String apiCall = "https://api.fda.gov/drug/enforcement.json?api_key=" + apiKey + "&limit=" + limit.toString();
        return convertCallToList(apiCall);
    }

    /**
     * Returns a List of maps, with each item in the list being a map of information about a singular drug
     * Returns drugs that fit the specified query and searchString
     * Sample searchStrings - ("spl_product_data_elements","boxed_warning","indications_and_usage","spl_unclassified_section_table","dosage_and_administration","dosage_forms_and_strengths","contraindications","warnings_and_cautions","adverse_reactions","drug_interactions","use_in_specific_populations","pregnancy","pediatric_use","geriatric_use","overdosage","description","clinical_pharmacology", "microbiology_table","mechanism_of_action","pharmacokinetics","nonclinical_toxicology","carcinogenesis_and_mutagenesis_and_impairment_of_fertility","animal_pharmacology_and_or_toxicology","clinical_studies","how_supplied","storage_and_handling","information_for_patients","spl_unclassified_section","spl_medguide","package_label_principal_display_panel","openfda","set_id","id","effective_time","version");
     * @param  limit  the number of drugs that match the searchString wanted
     * @param  searchString category to be searched(string should be defined by openFDA api)
     * @param query query to be searched
     * Sample call - DrugInfo(1,"drug_interactions","caffeine").get("adverse_reactions")
     *
     */
    public List<Map<String, Object>> DrugInfo(Integer limit, String searchString, String query) {
        String apiCall = "https://api.fda.gov/drug/label.json?search=" + searchString + ":" + query + "&api_key=" + apiKey + "&limit=" + limit.toString();
        return convertCallToList(apiCall);
    }

    /**
     * Returns information related to an adverse event of a drug from a pharmacological class
     * Contains information about the drug in addition to information about the adverse event
     * @param  limit  the number of adverseEffects included
     * Sample call - adverseEffects(3,"nonsteroidal+anti-inflammatory+drug")
     */
    public List<Map<String, Object>> adverseEffects(Integer limit, String drugClass)  {
        String apiCall = "https://api.fda.gov/drug/event.json?search=patient.drug.openfda.pharm_class_epc:"+drugClass+"&api_key=" + apiKey + "&limit=" + limit.toString();
        return convertCallToList(apiCall);
    }

    /**
     * Returns common reactions and count from a class of drug
     * Contains information about the drug in addition to information about the adverse event
     * @param  limit  the number of adverseEffects included
     * Sample call - adverseEffects(3,"nonsteroidal+anti-inflammatory+drug")
     */
    public List<Map<String, Object>> commonReactions(Integer limit, String drugClass)  {
        String apiCall = "https://api.fda.gov/drug/event.json?search=patient.drug.openfda.pharm_class_epc:"+drugClass+"&api_key=" + apiKey + "&count=patient.reaction.reactionmeddrapt.exact";
        return convertCallToList(apiCall);
    }

    public String getWarnings(String drugName) {
        try {
            return this.DrugInfo(1, "spl_product_data_elements", drugName).get(0).get("warnings").toString();
        } catch (NullPointerException e) {
            return "Warnings or Drug Not Found";
        }
    }

    public String getDescription(String drugName) {
        try {
            return this.DrugInfo(1, "spl_product_data_elements", drugName).get(0).get("description").toString();
        } catch (NullPointerException e) {
            return "Description or Drug Not Found";
        }
    }

    public String getInteractions(String drugName) {
        try {
            return this.DrugInfo(1, "spl_product_data_elements", drugName).get(0).get("drug_interactions").toString();
        } catch (NullPointerException e) {
            return "Interactions Info or Drug Not Found";
        }
    }

    public String getPregnancy(String drugName) {
        try {
            return this.DrugInfo(1, "spl_product_data_elements", drugName).get(0).get("pregnancy").toString();
        } catch (NullPointerException e) {
            return "Pregnancy Info or Drug Not Found";
        }
    }

    public String getNursing(String drugName)  {
        try {
            return this.DrugInfo(1, "spl_product_data_elements", drugName).get(0).get("nursing_mothers").toString();
        } catch (NullPointerException e) {
            return "Nursing Mother Info or Drug Not Found";
        }
    }

    public String getUsage(String drugName)  {
        try {
            return this.DrugInfo(1, "spl_product_data_elements", drugName).get(0).get("indications_and_usage").toString();
        } catch (NullPointerException e) {
            return "Usage Info or Drug Not Found";
        }
    }

    public String getAbuse(String drugName)  {
        try {
            return this.DrugInfo(1, "spl_product_data_elements", drugName).get(0).get("abuse").toString();
        } catch (NullPointerException e) {
            return "Drug Abuse Info or Drug Not Found";
        }
    }

    public String getHandling(String drugName)  {
        try {
            return this.DrugInfo(1, "spl_product_data_elements", drugName).get(0).get("storage_and_handling").toString();
        } catch (NullPointerException e) {
            return "Handling Info or Drug Not Found";
        }
    }

    public String getReactions(String drugName)  {
        try {
            return this.DrugInfo(1, "spl_product_data_elements", drugName).get(0).get("adverse_reactions").toString();
        } catch (NullPointerException e) {
            return "Adverse Reactions or Drug Not Found";
        }
    }



    public static void main(String[] args)  {
//       Sample calls
         FdaDatabaseAccessObject obj = new FdaDatabaseAccessObject();
         System.out.println(obj.recentlyRecalled(5).get(0).get("product_description"));
         System.out.println(obj.DrugInfo(1,"drug_interactions","caffeine").get(0).get("indications_and_usage"));
         System.out.println(obj.DrugInfo(1,"drug_interactions","caffeine").get(0).get("spl_unclassified_section_table"));
         System.out.println(obj.DrugInfo(1,"drug_interactions","caffeine").get(0).get("drug_interactions"));
         System.out.println(obj.adverseEffects(3,"nonsteroidal+anti-inflammatory+drug").get(0).get("patient"));
         System.out.println(obj.commonReactions(3,"nonsteroidal+anti-inflammatory+drug"));
}
}
