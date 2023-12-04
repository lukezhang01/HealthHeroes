package test.use_case;

import static org.junit.jupiter.api.Assertions.*;
import static test.TestDataFactory.createTestPatient;

import entity.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.chat.*;
public class ChatInteractorTest {

    private ChatInteractor chatInteractor;
    private FakeChatGPTChatDataAccessObject fakeChatGPTChatDataAccessObject;
    private FakeChatGPTContextDataAccessObject fakeChatGPTContextDataAccessObject;
    private FakeFdaDatabaseAccessObject fakeFdaDatabaseAccessObject;
    private FakeCSVFileManagementDataAccessObject fakeCsvFileManagementDataAccessObject;
    private FakeChatOutputBoundary fakeChatPresenter;

    @BeforeEach
    void setUp() {
        Patient patient = createTestPatient();
        fakeChatGPTChatDataAccessObject = new FakeChatGPTChatDataAccessObject(patient);
        fakeChatGPTContextDataAccessObject = new FakeChatGPTContextDataAccessObject(patient);
        fakeFdaDatabaseAccessObject = new FakeFdaDatabaseAccessObject();
        fakeCsvFileManagementDataAccessObject = new FakeCSVFileManagementDataAccessObject();
        fakeChatPresenter = new FakeChatOutputBoundary();
        chatInteractor = new ChatInteractor(
                fakeChatGPTChatDataAccessObject,
                fakeChatGPTContextDataAccessObject,
                fakeFdaDatabaseAccessObject,
                fakeCsvFileManagementDataAccessObject,
                fakeChatPresenter
        );
    }

    @Test
    void testChatWithUserMessage() {
        ChatInputData inputData = new ChatInputData("Hello", true);
        chatInteractor.execute(inputData);

        assertTrue(fakeChatPresenter.chatUpdates.contains("User: Hello"));
        assertTrue(fakeChatPresenter.isUserChatEnabled);
    }

    @Test
    void testChatWithError() {
        ChatInputData inputData = new ChatInputData("[ERROR]", true);
        chatInteractor.execute(inputData);

        assertTrue(fakeChatPresenter.chatUpdates.contains("Popup: GPT error has occurred"));
    }


}