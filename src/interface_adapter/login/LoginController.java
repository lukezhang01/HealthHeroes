package interface_adapter.login;

import use_case.login.LoginInputData;
import use_case.login.LoginInputBoundary;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInputData;
public class LoginController {

    final LoginInputBoundary loginUseCaseInteractor;
    public LoginController(LoginInputBoundary loginUseCaseInteractor) {
        this.loginUseCaseInteractor = loginUseCaseInteractor;
    }


    public void handleLogin(String username, String password) {
        System.out.println("login controller passed");
        LoginInputData loginInputData = new LoginInputData(username, password);

        loginUseCaseInteractor.executeLogin(loginInputData);
    }
    public void handleSignup(){

        loginUseCaseInteractor.executeSignup();
    }
}
