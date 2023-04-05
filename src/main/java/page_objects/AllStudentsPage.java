package page_objects;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

public class AllStudentsPage {

    private WebDriver driver;
    private WebDriverWait webDriverWait;

    public AllStudentsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @FindBy(how = How.XPATH, using = "//div[@class='ant-table-title']//button")
    public WebElement addStudentButton;

    public void waitAndClickOnAddStudentButton() {
        webDriverWait.until(elementToBeClickable(addStudentButton));
        addStudentButton.click();
    }
}
