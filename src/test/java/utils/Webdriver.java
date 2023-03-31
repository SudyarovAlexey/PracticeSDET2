package utils;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;

public class Webdriver {

    public static ChromeDriver getChromeDriver() {

        String path = System.getProperty("user.dir");
        String chromeDriverPath = path + File.separator + "webdrivers" + File.separator + "chromedriver111.exe";
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        return new ChromeDriver(options);
    }
}
