package data_access;
import java.util.List;
import java.net.*;
import java.io.*;
public class FdaDatabaseAccessObject {
    String apiKey = "AhsIyByI3vFVW2gRIsMVT9u4UbAgGTwgnQxxHPZi";
    String baseCall = "https://api.fda.gov/drug/drugsfda.json";

    //Add Drug Alerts
    //Add Recall Alerts
    //Add Recently Recalled Drugs
    public List<String> recentlyRecalled() throws IOException{

        String apiCall = "https://api.fda.gov/drug/enforcement.json?api_key="+apiKey+"&limit=5";
        URL url = new URL(apiCall);
        URLConnection connection = url.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        connection.getInputStream()));
        String inputLine;

        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);
        in.close();
    }
    //Add Drug Info Call

}
