import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private SelenideElement loginInput = $("[data-test-id='login'] input");
    private SelenideElement loginSubText = $("[data-test-id='login']").$(".input__sub");
    private SelenideElement passwordInput = $("[data-test-id='password'] input");
    private SelenideElement passwordSubText = $("[data-test-id='password']").$(".input__sub");
    private SelenideElement loginBtn = $("[data-test-id='action-login'");
    private SelenideElement errorNotification = $("[data-test-id='error-notification");

    private String warningColor = "rgba(255, 92, 92, 1)";

    public LoginPage setLoginInput(String loginInput) {
        this.loginInput.setValue(loginInput);
        return this;
    }

    public LoginPage setPasswordInput(String passwordInput) {
        this.passwordInput.setValue(passwordInput);
        return this;
    }

    public LoginPage pressLoginBtn() {
        this.loginBtn.click();
        return this;
    }

    public LoginPage errorNotificationShouldAppear(){
        errorNotification.waitUntil(Condition.appears, 5000);
        return this;
    }

    public LoginPage loginSubTextShouldAppear(){
        loginSubText.waitUntil(Condition.appears,5000);
        return this;
    }

    public LoginPage passwordSubTextShouldAppear(){
        passwordSubText.waitUntil(Condition.appears,5000);
        return this;
    }

    public LoginPage loginSubTextShouldHaveRedEmptyFieldWarning(){
        loginSubText.shouldBe(Condition.cssValue("color", warningColor));
        return this;
    }

    public LoginPage passwordSubTextShouldHaveRedEmptyFieldWarning(){
        passwordSubText.shouldBe(Condition.cssValue("color", warningColor));
        return this;
    }

    public VerificationPage loadVerificationPage(){
        return new VerificationPage();
    }
}
