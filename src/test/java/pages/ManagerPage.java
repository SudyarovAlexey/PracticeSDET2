package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ManagerPage {

    public String pageUrl = "https://www.globalsqa.com/angularJs-protractor/BankingProject/#/manager";
    public WebDriver driver;

    @FindBy(xpath = "//strong[@class='mainHeading']")
    public WebElement header;

    @FindBy(xpath = "//button[@ng-class='btnClass1']")
    private WebElement tabAddCustomer;

    @FindBy(xpath = "//input[@ng-model='fName']")
    private WebElement inputFirstName;

    @FindBy(xpath = "//input[@ng-model='lName']")
    private WebElement inputLastName;

    @FindBy(xpath = "//input[@ng-model='postCd']")
    private WebElement inputPostCode;

    @FindBy(xpath = "//button[@class='btn btn-default']")
    private WebElement buttonAddCustomer;

    @FindBy(xpath = "//button[@ng-class='btnClass3']")
    private WebElement tabCustomers;

    @FindBy(xpath = "//*[contains(text(), 'First Name')]")
    private WebElement firstColumnToSort;

    @FindBy(xpath = "//input[@class='form-control ng-pristine ng-untouched ng-valid']")
    private WebElement inputSearchText;

    @FindBy(xpath = "//tbody//tr")
    private WebElement rowCount;

    public ManagerPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public WebElement getHeader() {
        return header;
    }

    @Step("Активация полей для добавления пользователя")
    public void goToTabCustomerAdd() {
        tabAddCustomer.click();
    }

    @Step("Внесение данных и добавление пользователя")
    public void inputCustomerData(String fName, String lName, String postCode) {
        WebElement tabWaiter = new WebDriverWait(driver, 4)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@ng-class='btnClass1']")));
        tabAddCustomer.click();
        WebElement inputSurname = new WebDriverWait(driver, 4)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='First Name']")));
        inputFirstName.sendKeys(fName);
        WebElement inputName = new WebDriverWait(driver, 4)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Last Name']")));
        inputLastName.sendKeys(lName);
        inputPostCode.sendKeys(postCode);
        buttonAddCustomer.click();
    }

    @Step("Получение данных таблицы пользователей")
    public String getTableData() {
        driver.switchTo().alert().accept();
        tabCustomers.click();
        return driver.findElement(By.xpath("//tbody")).getText();
    }

    @Step("Получение отсортированного списка")
    public List<String> getSortedChecklist() {
        WebElement element = new WebDriverWait(driver, 4)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@ng-class='btnClass3']")));
        tabCustomers.click();
        WebElement table = new WebDriverWait(driver, 4)
                .until(ExpectedConditions
                        .presenceOfElementLocated(By.xpath("//table[@class='table table-bordered table-striped']")));
        firstColumnToSort.click();
        List<String> nameList = new ArrayList<>();
        int rowCount = driver.findElements(By.xpath("//tbody//tr")).size();
        for (int i = 1; i < rowCount + 1; i++) {
            nameList.add(driver.findElement(By.xpath("//tbody//tr[" + i + "]/td[1]")).getText());
        }
        return nameList;
    }

    @Step("Поиск пользователя по имени")
    public List<String> findCustomer(String name) {
        tabCustomers.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        inputSearchText.sendKeys(name);
        List<String> customerData = new ArrayList<>();
        int cellsCount = driver.findElements(By.xpath("//tbody//td")).size();
        for (int i = 1; i < cellsCount; i++) {
            customerData.add(driver.findElement(By.xpath("//tbody//tr[1]/td[" + i + "]")).getText());
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return customerData;
    }

}
