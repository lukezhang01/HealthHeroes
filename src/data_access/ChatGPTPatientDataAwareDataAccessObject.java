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
                "Appointment Dates: %s\\n" +
                "Is Pregnant: %s\\n" +
                "Life Style Information:\\n%s\\n" +
                "Additional Doctor Notes:\\n%s\\n";

        return String.format(detailsFormat,
                this.queryPatient.fullName,
                this.queryPatient.getWeightAsString(),
                this.queryPatient.getHeightAsString(),
                this.queryPatient.getAllergiesAsString(),
                this.queryPatient.getIllnessesAsString(),
                this.queryPatient.getSymptomsAsString(),
                this.queryPatient.getPrescribedDrugsAsString(),
                this.queryPatient.getAppointmentDatesAsString(),
                this.queryPatient.getIsPregnant(),
                this.queryPatient.getLifestyleInformation(),
                this.queryPatient.getAdditionalNotes()
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


    }
}
