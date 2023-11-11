package data_access;

import entity.Patient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class ChatGPTPatientDataAccessAbstractClass implements ChatGPTHealthDataAccessInterface {

    private final String USER_FORMAT = """
            {"role": "user", "content": "%s"}%s
            """;

    private final String INSTRUCTION_FORMAT = """
            {"role": "system", "content": "%s"},
            """;
    private final String ASSISTANT_FORMAT = """
            {"role": "assistant", "content": "%s"}%s
            """;
    private final String GPT_INSTRUCTIONS = "You are an intelligent, smart, careful, and appropriate medical assistant tool:\\nYou must follow the instructions below exactly:\\n-- INSTRUCTION --\\n%s\\n-- PATIENT --\\n%s\\nVariable Details:\\n-- INSTRUCTION -- : below is the an instruction that you must follow for the rest of the conversation.\\n-- PATIENT --: below is the string containing health details about the patient. You will answer every subsequent\\nmessage with respect to these details.";

    private ArrayList<String> assistantMessages;
    private ArrayList<String> userMessages;
    private String setUpPrompt;


    private static String getValidJSONString(String str){
        return str.replace("\\", "\\\\").replace("\"", "\\\"");
    }
    protected static String getStringMessageFromJSON(String json) {
        String patternString = "\"content\":\\s*\"((?:[^\"\\\\]|\\\\.)*)\"";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            String content = matcher.group(1);
            return getValidJSONString(content);
        }
        return "";
    }
    protected String getContextHistory() {
        StringBuilder messageHistory = new StringBuilder();
        int maxLength = Math.min(this.assistantMessages.size(), this.userMessages.size());
        for (int i = 0; i < maxLength; i++) {
            String assistantMessage = this.assistantMessages.get(i);
            String userMessage = this.userMessages.get(i);
            // add user message
            messageHistory.append(String.format(USER_FORMAT, userMessage, ","));
            // add assistant message
            messageHistory.append(String.format(ASSISTANT_FORMAT, assistantMessage, ","));
        }
        return messageHistory.toString();
    }
    @Override
    public String messageGPT(String message) {
        message = getValidJSONString(message);
        String newMessage = String.format(USER_FORMAT, message, "");
        int MAX_RETRIES = 1;
        for (int i = 0; i < MAX_RETRIES; i++) {
            try {
                String COMPLETION_URL = "https://api.openai.com/v1/chat/completions";
                URL url = new URL(COMPLETION_URL);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("POST");
                String API_KEY = "";
                connection.setRequestProperty("Authorization", "Bearer " + API_KEY);
                connection.setRequestProperty("Content-Type", "application/json");
                // gpt-4-turbo
                String GPT_MODEL = "gpt-4-1106-preview";
                String requestBody = String.format(
                        """
                        {
                        "model": "%s",
                        "messages": [
                        %s
                        ],
                        "temperature": 0.2,
                        "max_tokens": 3000
                        }""", GPT_MODEL, this.setUpPrompt + this.getContextHistory() + newMessage);
                // send request
                connection.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(requestBody);
                writer.flush();
                writer.close();
                // After sending the request, check the response code
                // == ERROR HANDLE CODE ==
                int responseCode = connection.getResponseCode();
                System.out.println("Response Code: " + responseCode);

// If the response code is not successful, read the error stream
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                    String line;
                    StringBuilder errorResponse = new StringBuilder();
                    while ((line = errorReader.readLine()) != null) {
                        errorResponse.append(line);
                    }
                    errorReader.close();
                    // Log the error response
                    System.out.println("Error response from the server: " + errorResponse.toString());
                    // Handle the error as appropriate in your application
                    return errorResponse.toString(); // or throw an exception
                }
                //
                // get response from GPT
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                String gptResponse = getStringMessageFromJSON(response.toString());
                // update context history
                this.assistantMessages.add(gptResponse);
                this.userMessages.add(message);
                return gptResponse.replace("\\n", "\n").replace("\\\\\\", "");
            } catch (IOException e){
                System.out.println(e);
                System.out.println("[GPT ERROR] retrying...");
            }
        }
        // shouldn't reach here unless API is dead.
        return "[A Fatal Error Has Occurred]";
    }

    protected ChatGPTPatientDataAccessAbstractClass(Patient patient, String setUpInstruction) {
        String patientDetailsTest = "Name: Doe\n" +
                "Gender: Male\n" +
                "Height: 173 cm\n" +
                "Weight: 190 lbs\n" +
                "Medication: Advil, Painkillers,\n" +
                "Allergies: Dust, Alcohol,\n" +
                "Illnesses: Autism, Asthma,\n" +
                "Doctor Comments:\n" +
                "- seems depressed.";

        String patientDetails = patientDetailsTest.replaceAll("\n", "\\n"); //patient.getDetailsAsString()
        this.setUpPrompt = String.format(INSTRUCTION_FORMAT, String.format(GPT_INSTRUCTIONS, setUpInstruction, patientDetails));
        this.assistantMessages = new ArrayList<>();
        this.userMessages = new ArrayList<>();
    }

}
