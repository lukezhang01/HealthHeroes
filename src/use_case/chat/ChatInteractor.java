package use_case.chat;

import data_access.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class ChatInteractor implements ChatInputBoundary {

    private final FdaDatabaseAccessInterface fdaDatabaseAccessObject;
    private final ChatGPTChatDataAccessObject chatGPTChatDataAccessObject;
    private final ChatGPTContextDataAccessObject chatGPTContextDataAccessObject;

    private final CSVFileManagementDataAccessInterface csvFileManagementDataAccessObject;
    private final ChatOutputBoundary chatPresenter;


    private String getAPIInfoFromApiCalls(HashMap<String, ArrayList<String>> apiCalls) {
        StringBuilder ret = new StringBuilder();
        for (String drugName: apiCalls.keySet()) {
            // loop through keys
            ArrayList<String> functions = apiCalls.get(drugName);
            for (String function: functions) {
                String infoFormat = "%s(%s): %s\\n";
                String info = "";
                String result = "Nothing Found";
                if (function.equals("getWarnings")) {
                    result = fdaDatabaseAccessObject.getWarnings(drugName);
                }else if(function.equals("getPregnancy")) {
                    result = fdaDatabaseAccessObject.getPregnancy(drugName);
                }else if(function.equals("getNursing")) {
                    result = fdaDatabaseAccessObject.getNursing(drugName);
                }else if(function.equals("getInteractions")) {
                    result = fdaDatabaseAccessObject.getInteractions(drugName);
                }else if(function.equals("getDescription")) {
                    result = fdaDatabaseAccessObject.getDescription(drugName);
                }else if(function.equals("getUsage")) {
                    result = fdaDatabaseAccessObject.getUsage(drugName);
                }else if(function.equals("getAbuse")) {
                    result = fdaDatabaseAccessObject.getAbuse(drugName);
                }else if(function.equals("getHandling")) {
                    result = fdaDatabaseAccessObject.getHandling(drugName);
                }else if(function.equals("getReactions")) {
                    result = fdaDatabaseAccessObject.getReactions(drugName);
                }
                ret.append(String.format(infoFormat, function, drugName, result));
            }
        }
        return ret.toString();
    }

    public ChatInteractor(ChatGPTChatDataAccessObject chatGPTChatDataAccessObject, ChatGPTContextDataAccessObject chatGPTContextDataAccessObject, FdaDatabaseAccessInterface fdaDatabaseAccessObject, CSVFileManagementDataAccessInterface csvFileManagementDataAccessObject, ChatOutputBoundary chatOutputBoundary) {
        this.chatGPTChatDataAccessObject = chatGPTChatDataAccessObject;
        this.chatGPTContextDataAccessObject = chatGPTContextDataAccessObject;
        this.fdaDatabaseAccessObject = fdaDatabaseAccessObject;
        this.csvFileManagementDataAccessObject = csvFileManagementDataAccessObject;
        this.chatPresenter = chatOutputBoundary;
    }

    @Override
    public AtomicReference<String> execute(ChatInputData inputData) {
        String message = inputData.getMessage();
        boolean isUser = inputData.isUser();
        AtomicReference<String> result = new AtomicReference<>("");
        if (message.equals("[ERROR]") | message.isEmpty()) {
            chatPresenter.preparePopupView("GPT error has occurred");
        } else{
            if (isUser) {
                System.out.println("USER SENT MESSAGE");
                // user just sent this message
                chatPresenter.setUserChatEnabled(false);
                chatPresenter.updateChat(message, true);
                // run this long ass task in a seperate thread
                new Thread(() -> {
                    // do AI response
                    // run model to figure out what to do with message request
                    String response = chatGPTContextDataAccessObject.messageGPT(message);
                    HashMap<String, ArrayList<String>> openFDAApiCalls = chatGPTContextDataAccessObject.getDrugAPICallsFromResponse(response);
                    ArrayList<String> csvData = chatGPTContextDataAccessObject.getCSVDataFromResponse(response);
                    // api result
                    System.out.println("CONTEXT PROCESSED");
                    String fdaData = "";
                    if (!openFDAApiCalls.isEmpty()) {
                        String apiResult = getAPIInfoFromApiCalls(openFDAApiCalls);
                        SwingUtilities.invokeLater(() -> {
                            chatPresenter.updateChat("..[QUERYING OPEN FDA API FOR: " + openFDAApiCalls.keySet() + "]..", false);
                        });
                        fdaData = chatGPTContextDataAccessObject.getFormattedAPIInformation(apiResult);
                        SwingUtilities.invokeLater(() -> {
                            chatPresenter.updateChat("..[DONE QUERYING OPEN FDA API]..", false);
                        });
                        result.set("API_CALL");
                    }
                    if (!csvData.isEmpty()) {
                        // there is a csv command
                        SwingUtilities.invokeLater(() -> {
                            chatPresenter.updateChat("..[WRITING TO CSV]..", false);
                            chatPresenter.updateChat("..[CREATING:" + csvData.get(0) + "]..", false);
                        });
                        // write to csv
                        csvFileManagementDataAccessObject.writeBasicCSV(csvData.get(0), csvData.get(1));
                        SwingUtilities.invokeLater(() -> {
                            chatPresenter.updateChat("..[DONE WRITING TO CSV]...", false);
                        });
                        // attempt to open CSV file
                        try {
                            File csvFile = new File(csvFileManagementDataAccessObject.getOutputDirectory() + csvData.get(0) + ".csv");
                            if (Desktop.isDesktopSupported()) {
                                Desktop.getDesktop().open(csvFile);
                            } else {
                                System.out.println("Cannot open CSV for user.");
                            }
                        } catch (IOException ignored) {
                        }
                        System.out.println("RESPONSE");
                        String aiResponse = chatGPTChatDataAccessObject.messageGPT("CSV file has been written to the ../reports directory", fdaData, true);
                        chatPresenter.updateChat(aiResponse, false);
                        chatPresenter.setUserChatEnabled(true);
                        result.set("WRITE_CSV");
                    } else {
                        System.out.println("RESPONSE");
                        String aiResponse = chatGPTChatDataAccessObject.messageGPT(message, fdaData, false);
                        chatPresenter.updateChat(aiResponse, false);
                        chatPresenter.setUserChatEnabled(true);
                        result.set("RESPONSE");
                    }
                }).start();
            }
        }
        return result;
    }
}
