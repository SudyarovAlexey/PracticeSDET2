package tests;

import io.qameta.allure.Description;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.AddCustomerPage;
import pages.CustomersPage;
import pages.ManagerPage;
import utils.Webdriver;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Tests {

    public String mainPageUrl = "https://www.globalsqa.com/angularJs-protractor/BankingProject/#/manager";

    WebDriver driver;
    ManagerPage managerPage;
    AddCustomerPage addCustomerPage;
    CustomersPage customersPage;

    @BeforeTest
    public void setup() {
        driver = Webdriver.getChromeDriver();
        driver.get(mainPageUrl);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
        managerPage = new ManagerPage(driver);
        addCustomerPage = new AddCustomerPage(driver);
        customersPage = new CustomersPage(driver);
    }

    @Test
    @Description("Добавление пользователя")
    public void addCustomer() {
        managerPage.goToAddCustomerPage();
        //В данном случае title-ы одинаковые. Поставил в качестве демонстрации проверки
        /*замечаний о выносе переменных в поля не было.
         Но можно вынести все в виде полей private static final TITLE_NAME = "XYZ Bank" */
        addCustomerPage.inputDataAndCreateCustomer("John", "Doe", "2000");
        addCustomerPage.closeAlert();
        addCustomerPage.goToCustomersPage();
        String tableData = customersPage.getTableData();
        String expectedData = "John Doe 2000";
        Assert.assertTrue(tableData.contains(expectedData));
    }

    @Test
    @Description("Сортировка клиентов по имени")
    public void checkSortingList() {
        managerPage.goToCustomersPage();
        customersPage.sortCustomerList();
        List<String> actualList = customersPage.getSortedCustomerList();
        List<String> expectedList = new ArrayList<>(actualList);
        expectedList.sort(Comparator.reverseOrder());
        Assert.assertEquals(actualList, expectedList);
    }

    @Test
    @Description("Поиск клиента по имени")
    public void searchCustomerByName() {
        managerPage.goToAddCustomerPage();
        addCustomerPage.inputDataAndCreateCustomer("John", "Doe", "2000");
        addCustomerPage.closeAlert();
        addCustomerPage.goToCustomersPage();
        customersPage.inputCustomerDataForSearch("John");
        int actualSearchListSize = customersPage.getSearchListSize();
        Assert.assertEquals(actualSearchListSize, 1);
        int rowNumberForSearch = 1;
        List<String> actualList = customersPage.getSearchedCustomerData(rowNumberForSearch);
        List<String> expectedList = Arrays.asList("John", "Doe", "2000");
        Assert.assertEquals(actualList, expectedList);
    }

    @AfterTest
    private void tearDown() {
        driver.quit();
    }

}
