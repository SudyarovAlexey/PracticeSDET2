package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AddCustomerPage {

    private final WebDriver driver;

    @FindBy(xpath = "//input[@ng-model='fName']")
    private WebElement inputFirstName;

    @FindBy(xpath = "//input[@ng-model='lName']")
    private WebElement inputLastName;

    @FindBy(xpath = "//input[@ng-model='postCd']")
    private WebElement inputPostCode;

    @FindBy(xpath = "//button[@class='btn btn-default']")
    private WebElement buttonAddCustomer;

    @FindBy(xpath = "//button[@ng-class='btnClass3']")
    private WebElement buttonGoToCustomersPage;

    public AddCustomerPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Step("Внесение данных и добавление пользователя")
    public void inputDataAndCreateCustomer(String fName, String lName, String postCode) {
        inputFirstName.sendKeys(fName);
        inputLastName.sendKeys(lName);
        inputPostCode.sendKeys(postCode);
        buttonAddCustomer.click();
    }

    @Step("Закрытие окна alert")
    public void closeAlert() {
        driver.switchTo().alert().accept();
    }

    @Step("Переход на страницу списка клиентов")
    public void goToCustomersPage() {
        buttonGoToCustomersPage.click();
    }

}
