package data_access;


import entity.Patient;

import java.lang.reflect.Array;
import java.util.*;

public class ChatGPTContextDataAccessObject extends ChatGPTPatientDataAwareDataAccessObject {

    private final String[] OPENFDA_FUNCTIONS = {"getWarnings", "getPregnancy", "getNursing", "getInteractions", "getUsage", "getDescription", "getAbuse", "getHandling", "getReactions"};
    private final String MESSAGE_FORMAT = "[DOCTOR]\\n%s";
    private final String DATA_FORMAT = "[DATA]\\n%s";

    public String getFormattedAPIInformation(String apiOutput) {
        return this.messageGPT(String.format(DATA_FORMAT, apiOutput));
    };



    private boolean isOpenFDAFunction(String functionName) {
        for (String openfdaFunction : this.OPENFDA_FUNCTIONS) {
            if (openfdaFunction.equals(functionName)) {
                return true;
            }
        }
        return false;
    }
    public String messageGPT(String message){
        return super.messageGPT(String.format(MESSAGE_FORMAT, message));
    }
    public ArrayList<String> getCSVDataFromResponse(String message) {
        String[] lines = message.split("\n");
        ArrayList<String> result = new ArrayList<>();
        if (lines.length < 2) {
            // input is not valid as it is too short.
            return result;
        }

        String[] firstLineParts = lines[0].split(": ");
        if (firstLineParts.length != 2) {
            // check if input has two parts
            return result;
        }

        String functionName = firstLineParts[0].trim();
        String fileName = firstLineParts[1].trim();

        if (!"generateCSV".equals(functionName)) {
            // check if function name is correct
            return result;
        }

        // process csv data
        StringBuilder csvData = new StringBuilder();
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            csvData.append(line + "\n");
        }
        result.add(fileName);
        result.add(csvData.toString());
        return result;
    }
    public HashMap<String, ArrayList<String>> getDrugAPICallsFromResponse(String message) {
          // gets drug api calls from a string response
//        String ret = """
//        getWarnings: accutane
//        getPregnancy: accutane
//        getNursing: accutane
//        getInteractions: accutane
//        getInteractions: advil
//        getUsage: accutane
//        getUsage: advil
//        getReactions: accutane
//        getReactions: advil
//        """;
        HashMap<String, ArrayList<String>> apiCalls = new HashMap<>();
        if(!message.isEmpty()) {
            // there is valid functions to call
            String[] lines = message.split("\n");

            for (String line : lines) {
                // split by : delimiter
                String[] parts = line.split(": ");
                if (parts.length == 2) {
                    String functionName = parts[0].trim();
                    String drugName = parts[1].trim();
                    if (this.isOpenFDAFunction(functionName)) {
                        if (apiCalls.get(drugName) != null) {
                            // add a new function to the drug
                            apiCalls.get(drugName).add(functionName);
                        } else {
                            ArrayList<String> functionNames = new ArrayList<>();
                            functionNames.add(functionName);
                            apiCalls.put(drugName, functionNames);
                        }
                    }
                }
            }
        }
        return apiCalls;
    }
    public ChatGPTContextDataAccessObject(Patient patient) {
        super(patient,"""
             You are acting like a middleman between a doctor and an AI doctor assistant chatbot. Your job is to
             choose which functions to call to provide comprehensive data for the AI doctor assistant chatbot.
             There are two types of inputs you will get:\\n
                 ____________________\\n
                 1)\\n
                 FORMAT:\\n
                 [DOCTOR]\\n
                 ..doctor message goes here...\\n
                 2)\\n
                 FORMAT:\\n
                 [DATA]\\n
                 ..long string of API data goes here...\\n
                 ____________________\\n
                 Input Actions (This is what you will do when met with either input type 1 or type 2):\\n
                 1) Goal: given the doctor's message, figure out which functions to call to retrieve data required or which functions to call to fulfill orders. You must call generateCSV function to generate csv.\\n
                 Rules: \\n
                 - You are required to figure out the parameters required for the function call as well.\\n
                 - You must translate potential informal drug names to formal common drug names and make it all lowercase.\\n
                 - You can call functions multiple times if required. \\n
                 - You can only generate CSV when it explicitly asks you to generate a csv.
                 - For CSV files, only put in relevant information requested - nothing extra.
                 Here are the ONLY functions you can call:\\n
                 - getWarnings(String drugName): warnings about a drug\\n
                 - getPregnancy(String drugName): get FDA Pregnancy category information\\n
                 - getNursing(String drugName): information about excretion of the drug in human milk and effects on the nursing infant \\n
                 - getInteractions(String drugName): Information about and practical guidance on preventing clinically significant drug/drug and drug/food interactions that may occur. \\n
                 - getDescription(String drugName): description of the drug name
                 - getUsage(String drugName): This field may describe any relevant limitations of use. Indications for use, such as for the treatment, prevention, mitigation, cure, or diagnosis of a disease or condition, or of a manifestation of a recognized disease or condition, or for the relief of symptoms associated with a recognized disease or condition.\\n
                 - getAbuse(String drugName): Information about the types of abuse that can occur with the drug and adverse reactions pertinent to those types of abuse, primarily based on human data. \\n
                 - getHandling(String drugName): Information about safe storage and handling of the drug product.\\n
                 - getReactions(String drugName): possible reactions to the drug\\n\\n
                 - generateCSV(String csvData): function to call to generate csv file with csvData as a string in csv format.
                 Output Format:
                 - Your csvData must be in csv format and have good formatting (headings, titles, etc...).
                 - You may manipulate data and use your intuition on generating unique csv files.
                 - You must output in the format below. Group relevant function with same drugName calls together. Output ONLY "None" if no relevant calls are required.:
                 - YOU CAN ONLY EITHER CALL RETRIEVE FUNCTIONS OR UTIL FUNCTIONS - NOT BOTH.
                 IF RETRIEVE FUNCTION:
                 functionName1: drugName
                 functionName2: drugName
                 IF GENERATE CSV:
                 generateCSV: fileName (DON'T INCLUDE .csv) (don't make filename generic, include details to make files unique)
                 csvData (first line is headers, rest of lines are data, headers must be in all caps)
                 ...
                 2) Goal: format API data into readable string.\\n
                 Rules:\\n
                    - You will get API data as input from your previous functions. You must format this information in a readable format. \\n
                    - Use your judgment to remove confusing information or reword them.\\n
                 Output Format:\\n
                    - Make the format neat and as short as possible. Do not put with special markup formatting.\\n
            """, false);
    }
}
