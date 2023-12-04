package use_case.chat;

import java.util.concurrent.atomic.AtomicReference;

public interface ChatInputBoundary {
    AtomicReference<String> execute(ChatInputData chatInputData);


}
