package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import static utils.ConfigHelper.getConfig;

public class DriverManager {
    private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public static WebDriver getInstance() {
        if (driverThreadLocal.get() == null) {
            if (getConfig().getBoolean("student.app.run.locally")) {
                driverThreadLocal.set(configurelocal());
            } else {
                driverThreadLocal.set(configureRemote());
            }
        }
            return driverThreadLocal.get();
    }
    public static RemoteWebDriver configureRemote() {
        ChromeOptions options = new ChromeOptions();
        options.setPlatformName("Windows 10");
        options.setBrowserVersion("latest");
        Map<String, Object> sauceOptions = new HashMap<>();
        sauceOptions.put("build", "selenium-build-MJJWW");
        sauceOptions.put("name", "<your test name>");
        options.setCapability("sauce:options", sauceOptions);

        URL url = null;
        try {
            url = new URL("https://oauth-kristina.sergejeva2020-e6023:67ee7b7d-5685-448b-bce4-f2da9041391b@ondemand.eu-central-1.saucelabs.com:443/wd/hub");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return new RemoteWebDriver(url, options);
    }
    private static WebDriver configurelocal() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        return new ChromeDriver(options);
    }
}
