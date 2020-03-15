import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class VerificationPage {
    private SelenideElement codeInput = $("[data-test-id='code'] input");
    private SelenideElement codeInputSub = $(By.xpath("//*[@data-test-id='code']//*[@class='input__sub']"));
    private SelenideElement verificationBtn = $("[data-test-id='action-verify']");
    private SelenideElement errorNotification = $("[data-test-id='error-notification']");

    private String warningColor = "rgba(255, 92, 92, 1)";

    public VerificationPage(){
        waitUntilVerificationPageLoaded();
    }

    public VerificationPage setCodeInput(String codeInput) {
        this.codeInput.setValue(codeInput);
        return this;
    }

    public DashboardPage verifyCorrectCode(){
        this.verificationBtn.click();
        return page(DashboardPage.class);
    }

    public VerificationPage verifyWrongCode() {
        this.verificationBtn.click();
        return this;
    }

    public void verificationPageShouldBeLoaded(){
        codeInput.waitUntil(Condition.visible, 15000);
    }

    public void waitUntilVerificationPageLoaded(){
        codeInput.waitUntil(Condition.visible, 5000);
    }

    public VerificationPage errorNotificationShouldAppear(){
        errorNotification.waitUntil(Condition.appears, 5000);
        return this;
    }

    public VerificationPage codeInputSubTextShouldAppear(){
        codeInputSub.shouldBe(Condition.visible);
        return this;
    }

    public VerificationPage codeInputSubTextShouldHaveRedEmptyFieldWarning(){
        codeInputSub.shouldBe(Condition.cssValue("color", warningColor));
        return this;
    }

}
