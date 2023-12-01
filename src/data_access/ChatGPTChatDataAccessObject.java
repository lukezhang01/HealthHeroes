package data_access;

import entity.Patient;

public class ChatGPTChatDataAccessObject extends ChatGPTPatientDataAwareDataAccessObject{



    public String messageGPT(String message, String fdaDrugContext, boolean isSystem) {
        String gptMessage = "";
        if (!fdaDrugContext.isEmpty()) {
            // we have contextual information about the drugs
            gptMessage += "[FDA DRUG DATA]\\n";
            gptMessage += fdaDrugContext + "\\n";
            gptMessage += "[USER MESSAGE]\\n";
            gptMessage += message;
            return super.messageGPT(gptMessage);
        }
        if (isSystem) {
            gptMessage += "[SYSTEM]\\n";
            gptMessage += message;
            return super.messageGPT(gptMessage);
        }
        return super.messageGPT(message);
    }

    public ChatGPTChatDataAccessObject(Patient patient) {
        super(patient, "You are a doctor's assistant. You must keep your outputs as concise and accurate as possible. Do not format your output, keep the formatting simple. Remember you are chatting with a licensed doctor. Do not reference that you are an LLM or an AI model. For some messages you will be given FDA Drug Data, you may use this information on your responses. However, you must cite every sentence in your response that you used the FDA Drug Data. You also may use the basic contextual data provided. You must also utilize the patient's details in your responses. Do not remind the doctor to review the patient's details, that is your job. Sometimes you will be given a system prompt with the header [SYSTEM]. At that point, you should convey what has been done as indicated by the [SYSTEM] message. Otherwise, you should treat the message as a message from the doctor. You will be given details below:\\n", true);
    }
}
