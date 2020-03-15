import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;

public class AuthorizationTest {

    LoginPage loginPage;
    String validLogin = "vasya";
    String validPassword = "qwerty123";

    @BeforeEach
    void setUp(){
        Selenide.open("http://localhost:9999");
    }

    @Test
    void loginWithValidUserData(){
        VerificationPage verificationPage = new LoginPage()
                .setLoginInput(validLogin)
                .setPasswordInput(validPassword)
                .pressLoginBtn()
                .loadVerificationPage();
        verificationPage.verificationPageShouldBeLoaded();
    }

    @Test
    void loginWithInvalidPassword(){
        loginPage = new LoginPage()
                .setLoginInput(validLogin)
                .setPasswordInput(DataHelper.getUserPassword())
                .pressLoginBtn()
                .errorNotificationShouldAppear();
    }

    @Test
    void successAuthorization() throws SQLException {
        VerificationPage verificationPage = new LoginPage()
                .setLoginInput(validLogin)
                .setPasswordInput(validPassword)
                .pressLoginBtn()
                .loadVerificationPage();
        String userCode = DataHelper.getVerificationCode(validLogin);
        DashboardPage dashboardPage = verificationPage
                .setCodeInput(userCode)
                .verifyCorrectCode();
        dashboardPage.dashboardPageShouldBeLoaded();
    }

    @Test
    void authorizationWithWrongVerificationCode(){
        VerificationPage verificationPage = new LoginPage()
                .setLoginInput(validLogin)
                .setPasswordInput(validPassword)
                .pressLoginBtn()
                .loadVerificationPage();
        String userCode = DataHelper.getInvalidVerificationCode();
        verificationPage
                .setCodeInput(userCode)
                .verifyWrongCode()
                .errorNotificationShouldAppear();
    }

    @Test
    void authorizationWithEmptyVerificationCode(){
        VerificationPage verificationPage = new LoginPage()
                .setLoginInput(validLogin)
                .setPasswordInput(validPassword)
                .pressLoginBtn()
                .loadVerificationPage();
        String userCode = "";
        verificationPage
                .setCodeInput(userCode)
                .verifyWrongCode()
                .codeInputSubTextShouldAppear();
    }

    @Test
    void emptyVerificationCodeSubTextShouldHaveRedWarningMessage(){
        VerificationPage verificationPage = new LoginPage()
                .setLoginInput(validLogin)
                .setPasswordInput(validPassword)
                .pressLoginBtn()
                .loadVerificationPage();
        String userCode = "";
        verificationPage
                .setCodeInput(userCode)
                .verifyWrongCode()
                .codeInputSubTextShouldAppear()
                .codeInputSubTextShouldHaveRedEmptyFieldWarning();
    }

    @Test
    void userShouldBeBlockedAfterInputWrongVerCodeThreeTimes() throws SQLException {
        String userLogin = validLogin;
        VerificationPage verificationPage = new LoginPage()
                .setLoginInput(userLogin)
                .setPasswordInput(validPassword)
                .pressLoginBtn()
                .loadVerificationPage();
        String userCode = DataHelper.getInvalidVerificationCode();
        verificationPage
                .setCodeInput(userCode)
                .verifyWrongCode()
                .errorNotificationShouldAppear();
        try {
            verificationPage = new LoginPage()
                    .setLoginInput(userLogin)
                    .setPasswordInput(validPassword)
                    .pressLoginBtn()
                    .loadVerificationPage();
        } catch (Exception ex){
            // блок try catch введен, т.к. тест ведет себя нестабильно. Иногда после вводе неверного кода происходит
            // редирект на страницу ввода логина и пароля, а иногда нет.
        }
        verificationPage
                .setCodeInput(userCode)
                .verifyWrongCode()
                .errorNotificationShouldAppear();
        try{
            verificationPage = new LoginPage()
                .setLoginInput(userLogin)
                .setPasswordInput(validPassword)
                .pressLoginBtn()
                .loadVerificationPage();
        }
        catch (Exception ex){
        }
        verificationPage
                .setCodeInput(userCode)
                .verifyWrongCode()
                .errorNotificationShouldAppear();
        String userStatus = DataHelper.getUserStatus(userLogin);
        DataHelper.userStatusShouldNotBeActive(userStatus);
    }

    @Test
    void emptyLoginSubTextShouldAppear(){
        loginPage = new LoginPage()
                .setPasswordInput(validPassword)
                .pressLoginBtn()
                .loginSubTextShouldAppear();
    }

    @Test
    void emptyPasswordSubTextShouldAppear(){
        loginPage = new LoginPage()
                .setLoginInput(validLogin)
                .pressLoginBtn()
                .passwordSubTextShouldAppear();
    }

    @Test
    void emptyLoginAndPasswordSubTextShouldAppear(){
        loginPage = new LoginPage()
                .pressLoginBtn()
                .loginSubTextShouldAppear()
                .passwordSubTextShouldAppear();
    }

    @Test
    void emptyLoginSubTextWarningShouldAppear(){
        loginPage = new LoginPage()
                .setPasswordInput(validPassword)
                .pressLoginBtn()
                .loginSubTextShouldHaveRedEmptyFieldWarning();
    }

    @Test
    void emptyPasswordSubTextWarningShouldAppear(){
        loginPage = new LoginPage()
                .setLoginInput(validLogin)
                .pressLoginBtn()
                .passwordSubTextShouldHaveRedEmptyFieldWarning();
    }

}
