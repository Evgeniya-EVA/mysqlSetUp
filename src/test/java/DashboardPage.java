import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {
    private SelenideElement pageName = $(By.xpath("//*[@data-test-id='dashboard']"));

    public DashboardPage() {
    }

    public void dashboardPageShouldBeLoaded(){
        pageName.waitUntil(Condition.visible, 15000);
    }
}
