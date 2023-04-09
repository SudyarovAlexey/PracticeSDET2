package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class CustomersPage {

    public WebDriver driver;
    private final static int START_INDEX_TO_REMOVE = 3;
    private final static int END_INDEX_TO_REMOVE = 5;

    @FindBy(xpath = "//table//thead//td[1]")
    private WebElement cellPointerSort;

    @FindBy(xpath = "//input[@class='form-control ng-pristine ng-untouched ng-valid']")
    private WebElement inputTextForSearch;

    @FindBy(xpath = "//tbody//tr//td[1]")
    private List<WebElement> firstColumnElements;

    private String rowNumberTemplate = "//tbody//tr[substitution]//td";

    private List<WebElement> generateXpathRowNumber(String xpathTemplate, int substitutionValue){
        return driver.findElements(By.xpath(xpathTemplate
                .replace("substitution", String.valueOf(substitutionValue))));
    }

    @FindBy(xpath = "//tbody")
    private WebElement table;

    public CustomersPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Step("Сортировка отсортированного списка")
    public void sortCustomerList() {
        cellPointerSort.click();
    }

    @Step("Получение отсортированного списка")
    public List<String> getSortedCustomerList() {
        List<String> nameList = new ArrayList<>();
        for (WebElement element : firstColumnElements) {
            nameList.add(element.getText());
        }
        return nameList;
    }

    @Step("Ввод данных клиента для поиска в списке")
    public void inputCustomerDataForSearch(String name) {
        inputTextForSearch.sendKeys(name);
    }

    @Step("Получение данных поиска клиента")
    public List<String> getSearchedCustomerData(int rowNumber) {
        List<String> customerDataList = new ArrayList<>();
        for (WebElement element : generateXpathRowNumber(rowNumberTemplate, rowNumber)) {
            customerDataList.add(element.getText());
        }
        customerDataList.removeAll(customerDataList
                .subList(START_INDEX_TO_REMOVE, END_INDEX_TO_REMOVE));
        return customerDataList;
    }

    @Step("Проверка наличия в списке поиска только одного клиента")
    public int getSearchListSize() {
        return firstColumnElements.size();
    }

    @Step("Получение данных из списка пользователей")
    public String getTableData() {
        return table.getText();
    }

}
