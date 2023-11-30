package interface_adapter.prescribe;
import use_case.prescribe.PrescribeOutputData;

import java.io.IOException;

public class PrescribeState {

    private boolean error;
    private String warnings;
    private String description;
    private String interactions;
    private String pregnancy;
    private String nursing;
    private String usage;
    private String handling;
    public String abuse;
    private String reactions;

    public PrescribeState(PrescribeOutputData outputData) throws IOException {
        this.warnings= outputData.getWarnings();
        this.description= outputData.getDescription();
        this.interactions= outputData.getInteractions();
        this.pregnancy= outputData.getPregnancy();
        this.nursing= outputData.getNursing();
        this.usage= outputData.getUsage();
        this.handling= outputData.getHandling();
        this.reactions = outputData.getReactions();
        this.abuse = outputData.getAbuse();
    }

    public PrescribeState(){
    }

    public String getStateAbuse(){
        return this.abuse;
    }

    public String getStateReactions(){
        return this.reactions;
    }

    public String getStateHandling(){
        return this.handling;
    }

    public String getStateUsage(){
        return this.usage;
    }

    public String getStateNursing(){
        return this.nursing;
    }

    public String getStatePregnancy(){
        return this.pregnancy;
    }

    public String getStateWarnings(){
        return this.warnings;
    }

    public String getStateInteractions(){
        return this.interactions;
    }

    public String getStateDescription(){
        return this.description;
    }

    public void setError(boolean error){this.error = error;}

}
