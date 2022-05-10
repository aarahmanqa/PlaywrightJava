package pages;

import base.BasePage;

public class LoginPage extends BasePage {
    String locUsername = "input[type='text']:not(input[name='q'])";
    String locPassword = "input[type='password']";
    String locLoginButton = "button[type='submit']>span";

    public boolean doLogin(String username, String password) {
        bf.fillValue(locUsername,username);
        bf.fillValue(locPassword,password);
        bf.click(locLoginButton);
        return false;
    }
}
