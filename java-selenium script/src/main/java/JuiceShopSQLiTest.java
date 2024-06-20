import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class JuiceShopSQLiTest {

    public static void main(String[] args) {
        // Set the path for the GeckoDriver
        System.setProperty("webdriver.gecko.driver", "D:/internship_java/geckodriver.exe");

        // Initialize WebDriver
        FirefoxOptions options = new FirefoxOptions();
        WebDriver driver = new FirefoxDriver(options);

        try {
            // Navigate to the login page
            String localLoginPageUrl = "http://localhost:3000";
            driver.get(localLoginPageUrl);

            // Locate the username and password input fields
            WebElement usernameField = driver.findElement(By.name("username"));
            WebElement passwordField = driver.findElement(By.name("password"));

            // Construct the SQL injection payload
            String maliciousUsername = "akash OR '1'='1";
            String validPassword = "12345"; // Replace with an actual valid password

            // Enter the malicious username and password
            usernameField.sendKeys(maliciousUsername);
            passwordField.sendKeys(validPassword);

            // Submit the form
            WebElement loginButton = driver.findElement(By.cssSelector("input[type='submit']"));
            loginButton.click();

            // Wait for the response
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

            // Analyze the response
            WebElement body = driver.findElement(By.tagName("body"));
            String bodyText = body.getText();

            if (bodyText.contains("Invalid username or password")) {
                System.out.println("SQL Injection attempt failed.");
            } else if (bodyText.contains("Welcome")) {
                System.out.println("SQL Injection attempt succeeded.");
            } else {
                System.out.println("Unexpected response.");
            }

            // Now navigate to the Juice Shop website
            String juiceShopUrl = "http://localhost:3000/";
            System.out.println("Navigating to: " + juiceShopUrl);
            driver.get(juiceShopUrl);

            // Wait for the page to load completely
            WebDriverWait wait = new WebDriverWait(driver, 60);  // Increased timeout
            wait.until(ExpectedConditions.titleContains("Juice Shop"));

            // Find the login button and attempt to click it
            WebElement juiceShopLoginButton = driver.findElement(By.id("loginButton"));

            // Wait for the overlay to become invisible or clickable
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("mat-dialog-0")));

            // Click on the login button
            juiceShopLoginButton.click();

            // Perform further actions after successful login click
            // For example, enter username, password, and submit the form

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}
