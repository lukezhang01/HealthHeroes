package use_case.login;

public interface LoginInputBoundary {
    void executeLogin(LoginInputData loginInputData);
    void executeSignup();
}
