package app;

import data_access.*;
import entity.Patient;
import interface_adapter.chat.ChatController;
import interface_adapter.chat.ChatPresenter;
import interface_adapter.chat.ChatViewModel;
import use_case.chat.ChatInteractor;
import view.ChatView;

public class ChatUseCaseFactory {


    private ChatUseCaseFactory() {} // prevent instantiation

    public static ChatView create(ChatViewModel chatViewModel, Patient patient) {
            ChatController controller = createChatUseCase(chatViewModel, patient);
            return new ChatView(controller, chatViewModel);
    }

    private static ChatController createChatUseCase(ChatViewModel chatViewModel, Patient patient) {
        FdaDatabaseAccessObject fdaDatabaseAccessObject = new FdaDatabaseAccessObject();
        ChatGPTChatDataAccessObject chatDataAcessObject = new ChatGPTChatDataAccessObject(patient);
        ChatGPTContextDataAccessObject dataContext = new ChatGPTContextDataAccessObject(patient);
        CSVFileManagementDataAccessInterface csvFileDatabaseObject = new CSVFileManagementDataAccessObject();
        chatViewModel.TITLE_LABEL = chatViewModel.TITLE_LABEL + " [Patient Name: " + patient.fullName + "| ID: " + patient.getID() + "]";
        ChatPresenter chatPresenter = new ChatPresenter(chatViewModel);
        ChatInteractor chatInteractor = new ChatInteractor(chatDataAcessObject, dataContext, fdaDatabaseAccessObject, csvFileDatabaseObject, chatPresenter);
        return new ChatController(chatInteractor);
    }
}
