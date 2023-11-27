package use_case.ll;

public class SignupInputData {
    final private String username;
    final private String password;
    final private String repeatPassword;
    final private String country;

    public SignupInputData(String username, String password, String repeatPassword, String country) {
        this.username = username;
        this.password = password;
        this.repeatPassword = repeatPassword;
        this.country = country;
    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public String getCountry() {
        return country;
    }
}
