package test.use_case;
import static org.junit.jupiter.api.Assertions.*;
import static test.TestDataFactory.createTestPatient;

import app.ChatUseCaseFactory;
import data_access.*;
import entity.Patient;
import interface_adapter.chat.ChatController;
import interface_adapter.chat.ChatPresenter;
import interface_adapter.chat.ChatViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.chat.*;
import view.ChatView;

import java.util.concurrent.atomic.AtomicReference;

public class ChatInteractorTest {

    private int WAIT_LIMIT = 20000;

    private ChatInteractor chatInteractor;
    private ChatGPTChatDataAccessObject testChatGPTChatDataAccessObject;
    private ChatGPTContextDataAccessObject testGPTContextDataAccessObject;
    private FdaDatabaseAccessObject testFdaDatabaseAccessObject;
    private CSVFileManagementDataAccessObject testCsvFileManagementDataAccessObject;

    private ChatView testChatView;
    private ChatPresenter fakeChatPresenter;

    @BeforeEach
    void setUp() {
        Patient patient = createTestPatient();
        testChatGPTChatDataAccessObject = new ChatGPTChatDataAccessObject(patient);
        testGPTContextDataAccessObject = new ChatGPTContextDataAccessObject(patient);
        testFdaDatabaseAccessObject = new FdaDatabaseAccessObject();
        testCsvFileManagementDataAccessObject = new CSVFileManagementDataAccessObject();
        ChatViewModel chatViewModel = new ChatViewModel();
        fakeChatPresenter = new ChatPresenter(chatViewModel);
        chatInteractor = new ChatInteractor(
                testChatGPTChatDataAccessObject,
                testGPTContextDataAccessObject,
                testFdaDatabaseAccessObject,
                testCsvFileManagementDataAccessObject,
                fakeChatPresenter
        );
        ChatController chatController = new ChatController(chatInteractor);
        testChatView = new ChatView(chatController, chatViewModel);
    }

    @Test
    void testChatWithUserMessage() {
        ChatInputData inputData = new ChatInputData("Hello", true);
        AtomicReference<String> result = chatInteractor.execute(inputData);
        String currentValue = result.get();
        while (true) {
            if (!result.get().equals(currentValue)) {
                break;
            }
            try {
                Thread.sleep(10); // Sleep for 10 milliseconds
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore the interrupted status
                break;
            }
        }
        System.out.println("done waiting");
        assert(result.get().equals("RESPONSE"));
    }
    @Test
    void testChatWriteCSV() throws InterruptedException {
        ChatInputData inputData = new ChatInputData("Write to CSV the patient's details", true);
        AtomicReference<String> result = chatInteractor.execute(inputData);
        String currentValue = result.get();
        while (true) {
            if (!result.get().equals(currentValue)) {
                break;
            }
            try {
                Thread.sleep(10); // Sleep for 10 milliseconds
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore the interrupted status
                break;
            }
        }
        System.out.println("done waiting");
        assert(result.get().equals("WRITE_CSV"));
    }
    @Test
    void testChatAPICall() throws InterruptedException {
        ChatInputData inputData = new ChatInputData("Summarize Advil for me", true);
        AtomicReference<String> result = chatInteractor.execute(inputData);
        String currentValue = result.get();
        while (true) {
            if (!result.get().equals(currentValue)) {
                break;
            }
            try {
                Thread.sleep(10); // Sleep for 10 milliseconds
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore the interrupted status
                break;
            }
        }
        System.out.println("done waiting");
        assert(result.get().equals("API_CALL"));
    }



}