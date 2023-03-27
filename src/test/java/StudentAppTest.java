import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

public class StudentAppTest {

    WebDriver driver;
    WebDriverWait driverWait;

    Faker dataFaker = new Faker();
    private final String APP_URL = "http://app.acodemy.lv";

    @BeforeMethod
    public void initialize() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driverWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.get(APP_URL);
    }

    @AfterMethod
    public void tearDown() {
        driver.close();
        driver.quit();
    }

    @Test
    public void openStudentApp() {
        driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='ant-table-title']//button")));

        WebElement addStudentButton = driver.findElement(By.xpath("//div[@class='ant-table-title']//button"));
        addStudentButton.click();

        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));

        WebElement nameField = driver.findElement(By.id("name"));
        String name = dataFaker.harryPotter().character();
        nameField.sendKeys(name);

        WebElement emailField = driver.findElement(By.id("email"));
        emailField.sendKeys(dataFaker.internet().emailAddress());
//         driver.findElement(By.xpath("//div[@class='ant-form-item-control-input-content']//button")).click();

        WebElement genderField = driver.findElement(By.id("gender"));
        genderField.click();
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@title='OTHER']")));
        WebElement valueFromDropdown = driver.findElement(By.xpath("//div[@title='OTHER']"));
        valueFromDropdown.click();

        WebElement submitButton = driver.findElement(By.xpath("//div[@class='ant-form-item-control-input-content']//button"));
        submitButton.click();

        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className("ant-notification-notice-message")));
        WebElement notificationMessage = driver.findElement(By.className("ant-notification-notice-message"));
        WebElement notificationDescription = driver.findElement(By.className("ant-notification-notice-description"));
        Assert.assertEquals(notificationMessage.getText(),("Student successfully added"));
        Assert.assertEquals(notificationDescription.getText(), name + " was added to the system");

        WebElement popUpCloseButton =  driver.findElement(By.className("ant-notification-notice-close"));
        popUpCloseButton.click();
        Assert.assertTrue(driverWait.until(ExpectedConditions.invisibilityOf(popUpCloseButton)));


    }

}
