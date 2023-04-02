package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ManagerPage {

    private final WebDriver driver;

    @FindBy(xpath = "//button[@ng-class='btnClass1']")
    private WebElement buttonGoToAddCustomerPage;

    @FindBy(xpath = "//button[@ng-class='btnClass3']")
    private WebElement buttonGoToCustomersPage;

    public ManagerPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Step("Переход на страницу добавления клиента")
    public void goToAddCustomerPage() {
        buttonGoToAddCustomerPage.click();
    }

    @Step("Переход на страницу списка клиентов")
    public void goToCustomersPage() {
        buttonGoToCustomersPage.click();
    }

}
