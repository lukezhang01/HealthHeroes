package data_access;
import java.util.Arrays;
import java.util.List;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Map;
public class FdaDatabaseAccessObject {
    String apiKey ;
    String baseCall;

    List<String> searchStrings;

    public FdaDatabaseAccessObject(){
        apiKey = "AhsIyByI3vFVW2gRIsMVT9u4UbAgGTwgnQxxHPZi";
        baseCall = "https://api.fda.gov/drug/drugsfda.json";
        searchStrings = Arrays.asList("spl_product_data_elements","boxed_warning","indications_and_usage","spl_unclassified_section_table","dosage_and_administration","dosage_forms_and_strengths","contraindications","warnings_and_cautions","adverse_reactions","drug_interactions","use_in_specific_populations","pregnancy","pediatric_use","geriatric_use","overdosage","description","clinical_pharmacology", "microbiology_table","mechanism_of_action","pharmacokinetics","nonclinical_toxicology","carcinogenesis_and_mutagenesis_and_impairment_of_fertility","animal_pharmacology_and_or_toxicology","clinical_studies","how_supplied","storage_and_handling","information_for_patients","spl_unclassified_section","spl_medguide","package_label_principal_display_panel","openfda","set_id","id","effective_time","version");
    }


    //Add Drug Alerts
    //Add Recall Alerts

    public List<Map<String, Object>> recentlyRecalled(Integer limit) throws IOException{
        /**
         * Returns a List of maps, with each item in the list being a map of information about a singular product recall
         * @param  limit  the number of recent product recalls wanted
         */
        try {
            // Building Json from Api Call
            String apiCall = "https://api.fda.gov/drug/enforcement.json?api_key="+apiKey+"&limit="+limit.toString();
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

    public List<Map<String, Object>> DrugInfo(Integer limit, String searchstring, String query) throws IOException{
        /**
         * Returns a List of maps, with each item in the list being a map of information about a singular drug
         * Should be called
         * @param  limit  the number of drugs that match the searchstring wanted
         * @param  searchString category to be searched(should be a string in searchStrings List)
         * @param query query to be searched
         * Sample call - DrugInfo(1,"drug_interactions","caffeine").get("adverse_reactions")
         *
         */
        try {
            // Building Json from Api Call
            String apiCall = "https://api.fda.gov/drug/label.json?search="+searchstring+":"+query+"&api_key="+apiKey+"&limit="+limit.toString();
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


    public static void main(String[] args) throws IOException {
        //Sample calls
        FdaDatabaseAccessObject obj = new FdaDatabaseAccessObject();
        //System.out.println(obj.recentlyRecalled(5).get(0).get("country"));
        System.out.println(obj.DrugInfo(1,"drug_interactions","caffeine").get(0).get("indications_and_usage"));
        System.out.println(obj.DrugInfo(1,"drug_interactions","caffeine").get(0).get("spl_unclassified_section_table"));
    }

}

