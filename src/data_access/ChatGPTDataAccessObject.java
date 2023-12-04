package data_access;

import com.fasterxml.jackson.core.JsonProcessingException;
import entity.Patient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public abstract class ChatGPTDataAccessObject implements ChatGPTHealthDataAccessInterface {

    private final String USER_FORMAT = """
            {"role": "user", "content": %s}%s
            """;

    private final String INSTRUCTION_FORMAT = """
            {"role": "system", "content": "%s"},
            """;
    private final String ASSISTANT_FORMAT = """
            {"role": "assistant", "content": %s}%s
            """;
    private ArrayList<String> assistantMessages;
    private ArrayList<String> userMessages;
    private String setUpPrompt = "";

    private boolean saveHistory = false;


    private static String getValidJSONString(String str) {
        ObjectMapper objectMapper = new ObjectMapper();
        String ret = str;
        try{
            ret = objectMapper.writeValueAsString(str);
        }catch(JsonProcessingException e){
            System.out.println(e);
        }
        return ret;
    }

    protected static String getStringMessageFromJSON(String json) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            // Convert JSON string to Map
            Map<String, Object> map = mapper.readValue(json, Map.class);
            // Extract the 'choices' list
            List<Map<String, Object>> choices = (List<Map<String, Object>>) map.get("choices");

            // Assuming you want to get the content of the first choice
            if (!choices.isEmpty()) {
                Map<String, Object> firstChoice = choices.get(0);
                Map<String, String> message = (Map<String, String>) firstChoice.get("message");
                String content = message.get("content");

                // return the content;
                return content;
            }
        } catch (IOException e) {
            e.printStackTrace();
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
            messageHistory.append(String.format(USER_FORMAT, getValidJSONString(userMessage), ","));
            // add assistant message
            messageHistory.append(String.format(ASSISTANT_FORMAT, getValidJSONString(assistantMessage), ","));
        }
//        System.out.println(messageHistory);
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
                String API_KEY = "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";
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
//                System.out.println("Response Code: " + responseCode);

                // If the response code is not successful, read the error stream
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    System.out.println(requestBody);
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
                if (this.saveHistory) {
                    this.assistantMessages.add((getValidJSONString(gptResponse)));
                    this.userMessages.add((getValidJSONString(message)));
                }
                return gptResponse.replace("\\n", "\n").replace("\\\\\\", "");
            } catch (IOException e){
                System.out.println(e);
                System.out.println("[GPT ERROR] retrying...");
            }
        }
        // shouldn't reach here unless API is dead.
        return "[A Fatal Error Has Occurred]";
    }

    public void setInstructionPrompt(String instructions) {
        // make the entire string into only one line for JSON.
        String singleLineInstructions = instructions.replaceAll("\\R", "\\\\n").replaceAll("\"", "\\\\\"");
        this.setUpPrompt = String.format(INSTRUCTION_FORMAT, singleLineInstructions);
    }

    protected ChatGPTDataAccessObject(boolean saveHistory) {
        this.saveHistory = saveHistory;
        this.assistantMessages = new ArrayList<>();
        this.userMessages = new ArrayList<>();
    }

}
