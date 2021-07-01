package com.github.wdestroier.mcflurry.browser;

import java.io.File;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ChromeWebBrowser implements WebBrowser {

	private ChromeDriver driver;
	private Duration timeoutDuration;

	static {
		System.setProperty("webdriver.chrome.driver",
				"chromedriver_win32" + File.separator + "chromedriver.exe");
	}

	public ChromeWebBrowser(Duration timeoutDuration) {
		this.timeoutDuration = timeoutDuration;
	}

	public ChromeWebBrowser() {
		this(Duration.ofMinutes(1));
	}

	@Override
	public void start() {
		driver = new ChromeDriver();
		//driver.manage().timeouts().implicitlyWait(timeoutDuration);
		//driver.manage().window().maximize();
	}

	@Override
	public void stop() {
		driver.close();
		driver = null;
	}

	@Override
	public void get(String url) {
		driver.get(url);
	}

	@Override
	public void click(String xpath) {
		var locator = By.xpath(xpath);

		wait(locator);

		driver.findElement(locator).click();
	}

	@Override
	public void send(String xpath, CharSequence... keys) {
		var locator = By.xpath(xpath);

		wait(locator);

		driver.findElement(locator).sendKeys(keys);
	}

	/**
	 * You can get the screenshot of the entire page with getScreenshot("/")
	 */
	@Override
	public String getScreenshot(String xpath) {
		var locator = By.xpath(xpath);

		wait(locator);

		var element = driver.findElement(locator);

		return element.getScreenshotAs(OutputType.BASE64);
	}

	private void wait(By locator) {
		new WebDriverWait(driver, timeoutDuration).until(
				ExpectedConditions.visibilityOfElementLocated(locator));
	}

}
