//package use_case.chat;
//
//import data_access.ChatGPTHealthDataAccessInterface;
//import data_access.FdaDatabaseAccessObject;
//
//public class ChatInteractor implements ChatInputBoundary {
//
//    private final FdaDatabaseAccessObject fdaDatabaseAccessObject;
//    private final ChatGPTHealthDataAccessInterface chatGPTChatDataAccessObject;
//
//    private final ChatOutputBoundary chatPresenter;
//
//    public ChatInteractor(ChatGPTHealthDataAccessInterface chatGPTChatDataAccessObject, FdaDatabaseAccessObject fdaDatabaseAccessObject) {
//        this.chatGPTChatDataAccessObject = chatGPTChatDataAccessObject;
//        this.fdaDatabaseAccessObject = fdaDatabaseAccessObject;
//    }
//
//    @Override
//    public void execute(ChatInputData inputData) {
//        if (inputData.getMessage().equals("[ERROR]") && !inputData.getMessage().isEmpty()) {
//            chatPresenter.prepareErrorView("GPT error has occurred");
//        } else{
////
////            ChatOutputData chatOutputData = new ChatOutputData();
////            chatPresenter.updateChat(inputData.getMessage(), );
//        }
//    }
//}
