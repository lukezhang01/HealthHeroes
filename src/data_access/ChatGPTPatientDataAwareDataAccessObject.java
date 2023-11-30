package data_access;

import entity.Patient;

import java.time.LocalDate;
import java.util.TreeMap;


public class ChatGPTPatientDataAwareDataAccessObject extends ChatGPTDataAccessObject {


    private final String GPT_INSTRUCTIONS = "You are an intelligent, smart, careful, and appropriate medical assistant tool:\\nYou must follow the instructions below exactly:\\n-- INSTRUCTION --\\n%s\\n-- PATIENT --\\n%s\\nVariable Details:\\n-- INSTRUCTION -- : below is the an instruction that you must follow for the rest of the conversation.\\n-- PATIENT --: below is the string containing health details about the patient. You will answer every subsequent\\nmessage with respect to these details.";
    private final Patient queryPatient;

    private String getBasicContextualData() {
        // basic data for the LLM to use i.e time.
        String contextualFormat = """
             Current Date: %s \\n
        """;
        LocalDate currentDate = LocalDate.now(); // get current date
        return String.format(contextualFormat, currentDate);
    }

    private String getPatientDetailsAsString() {
        String detailsFormat =
                "Name: %s\\n" +
                "Weight: %s\\n" +
                "Height: %s\\n" +
                "Allergies: %s\\n" +
                "Illnesses: %s\\n" +
                "Current Symptoms: %s\\n" +
                "Prescribed Drugs: %s\\n" +
                "Appointment Dates: %s\\n";
        return String.format(detailsFormat,
                this.queryPatient.fullName,
                this.queryPatient.getWeight(),
                this.queryPatient.getHeight(),
                this.queryPatient.getAllergiesAsString(),
                this.queryPatient.getIllnessesAsString(),
                this.queryPatient.getSymptomsAsString(),
                this.queryPatient.getPrescribedDrugsAsString(),
                this.queryPatient.getAppointmentDatesAsString()
                );

    }

    public ChatGPTPatientDataAwareDataAccessObject(Patient patient, String instructions, boolean saveHistory) {
        super(saveHistory);
        this.queryPatient = patient;
        String setUpInstructions = "You must follow the instructions below exactly:\\n";
        // set up instruction prompt
        setUpInstructions += "[INSTRUCTIONS]:\\n";
        setUpInstructions += instructions;
        setUpInstructions += "[PATIENT DETAILS]:\\n";
        setUpInstructions += this.getPatientDetailsAsString();
        setUpInstructions += "[CONTEXTUAL DATA]:\\n";
        setUpInstructions += this.getBasicContextualData();
        // set up patient details
        // set up initial instruction prompt
        this.setInstructionPrompt(setUpInstructions);

        // instructions: \"You are a doctor's assistant. You must keep your outputs as concise and accurate as possible. Do not format your output, keep the formatting simple. Remember you are chatting with a licensed doctor. Do not reference that you are an LLM or an AI model. You will be given details below:\"\\n";
    }
}
