package data_access;
import java.util.List;
import java.util.ArrayList;
import java.net.*;
import java.io.*;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

public class FdaDatabaseAccessObject {
    String apiKey ;
    String baseCall;

    public FdaDatabaseAccessObject(){
        apiKey = "AhsIyByI3vFVW2gRIsMVT9u4UbAgGTwgnQxxHPZi";
        baseCall = "https://api.fda.gov/drug/drugsfda.json";
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

    //Add Drug Info Call

    public static void main(String[] args) throws IOException {
        //Sample calls
        FdaDatabaseAccessObject obj = new FdaDatabaseAccessObject();
        System.out.println(obj.recentlyRecalled(5).get(0).get("Country"));
    }

}
