package selenium;


// <input data-v-1f99f73c="" class="oxd-input oxd-input--active" name="username" placeholder="Username" autofocus="">
// flow tạo test case
// B1: Khởi tạo trình duyệt
// B2: Điều hướng tới website muốn test
// B3: tìm các element, locator (input, button, label,...)
// Để phục vụ viết các step test
// B4: Nhập dữ liệu, thao tác trên element
// B5: Verify kết quả (expect, actual)
// B6: Đóng trình duyệt và giải phóng tài nguyên


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.ConfigUtils;

import java.net.URL;
import java.time.Duration;

public class orangeHRMLoginTest {
// B1: Khởi tạo trình duyệt
// Driver là class giúp tương tác trên page
    private WebDriver driver;

    private WebDriverWait wait;

////    ULR của page Login
//    private static final String LOGIN_URL = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
//
//// username
//    private static final String USERNAME = "Admin";
//    private static final String PASSWORD = "admin123";

//    define các elements, locators
//    Cách 1:
//    private static final By USSERNAME_INPUT = By.xpath(By.xpath("//input[@name='username']");
//   Cách 2:
    private static final By USERNAME_INPUT = By.name("username");
//                                           By.cssSelector("input[name='username']")
//                                           By.cssSelector("input[placeholder='username']")


//    private static final By PASSWORD_INPUT = By.xpath("//input[@name='password']");
    private static final By PASSWORD_INPUT = By.cssSelector("input[type='password']");


    private static final By LOGIN_BTN = By.xpath("//button[@type='submit']");
//                                      By.cssSelector("button[type='submit']")
//                                      By.cssSelector("Button.oxd-button--main")

    private static final By USER_DROPDOWN = By.xpath("//li[@class='oxd-userdropdown']");
//                                        By.className("oxd-userdropdown")
//                                         By.cssSelector("Li.oxd-userdropdown")

    private static final By LOGOUT_LINK = By.linkText("Logout");
//                                        By.xpath("//a[text()='Logout']")
//                                        By.xpath("//a[@href='web/index.php/auth/logout']")


    //    Setup môi trường test
//    before method: Chạy trước mỗi test case
//    VD: khởi tạo driver
    @BeforeMethod
    public void setUp() {
// B1: setup chrome driver với webdriver manager
    WebDriverManager.chromedriver().setup();

// B2: cấu hình chrome
        ChromeOptions options = new ChromeOptions();
//        mở browser ở chế độ full screen
//        - Đảm bảo tất cả các elements đều hiển thị
//        - Test nhất quán trên mọi màn hình
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-infobars");

        driver = new ChromeDriver(options);
//  B3: Có thời gian đợi chrome setup
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

    }

@Test(description = "Test Login thành công")
    public void testLoginSuccess() throws InterruptedException {
//        B1: truy cập trang web login
        driver.get(ConfigUtils.getLoginUrl());
//        Thread.sleep(10000);
//    Đợi có điều kiện: đợi đến khi username input xuất hiện trên DOM (giao diện)
        wait.until(ExpectedConditions.visibilityOfElementLocated(USERNAME_INPUT));

//        B2: Tìm element input username và fill username vào
        WebElement usernameField = driver.findElement(USERNAME_INPUT);
        usernameField.sendKeys(ConfigUtils.getUserName());
        Thread.sleep(2000);

//        B3: Tìm element password và fill password
        WebElement passwordField = driver.findElement(PASSWORD_INPUT);
        passwordField.sendKeys(ConfigUtils.getPassword());
        Thread.sleep(2000);

//        B4: tìm element button login và click vào button
        WebElement loginButton = driver.findElement(LOGIN_BTN);
        loginButton.click();
//        Thread.sleep(2000);
//     Đợi có điều kiện: Đợi đến khi xuất hiện url có chứa "dashboard"
        wait.until(ExpectedConditions.urlContains("dashboard"));

//         B5: verify kết quả sau khi thao tác login
//        TH1: Kiểm tra ULR có chứa đường link là "Dashboard" không?
        String currentUlr = driver.getCurrentUrl();
        Assert.assertTrue(
                currentUlr.contains("dashboard"), "URL phải chứa 'dashboard' sau khi login");

    }

    @Test(description = "Test Logout thành công")
    public void testLogoutSuccess() throws InterruptedException {
    //        B1: truy cập trang web login
    driver.get(ConfigUtils.getLoginUrl());
//        Thread.sleep(10000);
//    Đợi có điều kiện: đợi đến khi username input xuất hiện trên DOM (giao diện)
    wait.until(ExpectedConditions.visibilityOfElementLocated(USERNAME_INPUT));

    //        B2: Tìm element input username và fill username vào
    WebElement usernameField = driver.findElement(USERNAME_INPUT);
    usernameField.sendKeys(ConfigUtils.getUserName());
    Thread.sleep(2000);

    //        B3: Tìm element password và fill password
    WebElement passwordField = driver.findElement(PASSWORD_INPUT);
    passwordField.sendKeys(ConfigUtils.getPassword());
    Thread.sleep(2000);

    //        B4: tìm element button login và click vào button
    WebElement loginButton = driver.findElement(LOGIN_BTN);
    loginButton.click();
//        Thread.sleep(2000);
//     Đợi có điều kiện: Đợi đến khi xuất hiện url có chứa "dashboard"
    wait.until(ExpectedConditions.urlContains("dashboard"));
    Thread.sleep(5000);

//    Logout
//    Click vào avatar để mở dropdown
    wait.until(ExpectedConditions.elementToBeClickable(USER_DROPDOWN)).click();
    Thread.sleep(2000);
    wait.until(ExpectedConditions.elementToBeClickable(LOGOUT_LINK)).click();

//    Đợi về trang login
    wait.until(ExpectedConditions.urlContains("auth/login"));

    String currentURL = driver.getCurrentUrl();
    Assert.assertTrue(
            currentURL.contains("auth/login"), "Sau khi logout phải trở về dashboard"
    );

}

//    after method: cleanup - Đóng browser, giải phóng tài nguyên
    @AfterMethod
    public void tearDown(){
        if(driver != null){
            driver.quit();
        }


    }


}
