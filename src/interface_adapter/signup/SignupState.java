package interface_adapter.signup;

public class SignupState {
    private String username = "";
    private String password = "";
    private String repeatPassword = "";
    private String country = "";
    private String dialogMessage = null;

    public SignupState(SignupState copy) {
        username = copy.username;
        password = copy.password;
        repeatPassword = copy.repeatPassword;
        country = copy.country;
        dialogMessage = copy.dialogMessage;
    }

    public SignupState() {
    }

    public String getUsername() { return username; }

    public String getPassword() { return password;    }

    public String getRepeatPassword() { return repeatPassword;    }

    public String getCountry() { return country;    }

    public String getDialogMessage() { return dialogMessage; }

    public void setUsername(String username) { this.username = username;    }

    public void setPassword(String password) { this.password = password;    }

    public void setRepeatPassword(String repeatPassword) { this.repeatPassword = repeatPassword;    }

    public void setCountry(String country) { this.country = country; }

    public void setDialogMessage(String error) { this.dialogMessage = error; }

    @Override
    public String toString() {
        return "SignupState{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", repeatPassword='" + repeatPassword + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
