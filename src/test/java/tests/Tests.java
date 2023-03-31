package tests;

import io.qameta.allure.Description;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ManagerPage;
import utils.Webdriver;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Tests {
    WebDriver driver = Webdriver.getChromeDriver();

    ManagerPage managerPage = new ManagerPage(driver);

    @BeforeMethod
    private void setup() {
        driver.get(managerPage.pageUrl);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    @Test
    @Description("Открытие главной страницы")
    public void openManagerPage() {
        Assert.assertTrue(managerPage.getHeader().isDisplayed());
    }

    @Test
    @Description("Добавление пользователя")
    public void addCustomer() {
        managerPage.goToTabCustomerAdd();
        managerPage.inputCustomerData("John", "Doe", "2000");
        String tableData = managerPage.getTableData();
        String expectedData = "John Doe 2000";
        Assert.assertTrue(tableData.contains(expectedData));
    }

    @Test
    @Description("Сортировка клиентов по имени")
    public void checkSortingList() {
        List<String> actualList = managerPage.getSortedChecklist();
        List<String> expectedList = new ArrayList<>(actualList);
        expectedList.sort(Comparator.reverseOrder());
        Assert.assertEquals(actualList, expectedList);
    }

    @Test
    @Description("Поиск клиента по имени")
    public void searchCustomerByName() {
        List<String> actualList = managerPage.findCustomer("Harry");
        List<String> expected = Arrays.asList("Harry", "Potter", "E725JB", "1004 1005 1006");
        Assert.assertEquals(actualList, expected);
    }

    @AfterClass
    private void tearDown() {
        driver.quit();
    }

}
