package interface_adapter.login;

public class LoginState {
    private String username = "";
    private String usernameError = null;
    private String password = "";
    private boolean success = false;
    private String passwordError = null;
    private String dialogMessage = null;

    public LoginState(LoginState copy) {
        username = copy.username;
        usernameError = copy.usernameError;
        password = copy.password;
        passwordError = copy.passwordError;
        dialogMessage = copy.dialogMessage;
        success = copy.success;
    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public LoginState() {}

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDialogMessage() { return dialogMessage; }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSuccess(boolean success){
        this.success = success;
    }

    public boolean isSuccessfullyLoggedIn() {
        return this.success;
    }

    public void setDialogMessage(String dialogMessage) { this.dialogMessage = dialogMessage; }

}
