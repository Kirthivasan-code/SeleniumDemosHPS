package tests;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class NewTest5 {
	WebDriver driver;
	@BeforeTest
	@Parameters({"browser"})
	public void beforeTest(String browservalue)
	{
		//System.setProperty("webdriver.chrome.driver","src/test/resources/drivers/chromedriver.exe");
		if(browservalue.contentEquals("chrome"))
		{
			driver=new ChromeDriver();
		}
		else if(browservalue.contentEquals("firefox"))
		{
			System.setProperty("webdriver.gecko.driver",
					"src/test/resources/drivers/geckodriver.exe");
			driver=new FirefoxDriver();
		}
		else {
			System.setProperty("webdriver.ie.driver",
					"src/test/resources/drivers/IEDriverServer.exe");
			driver=new InternetExplorerDriver();
		}
		
		driver.get("http://demowebshop.tricentis.com/login");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	@AfterTest
	public void afterTest()
	{
		driver.close();
	}
	@Test(dataProvider = "dp1")
	public void testDemoWebShopLogin(String username,String password)
	{

		driver.findElement(By.id("Email")).sendKeys(username);
		driver.findElement(By.id("Password")).sendKeys(password);
		driver.findElement(By.cssSelector("input[value='Log in']")).click();
		Assert.assertTrue(driver.findElement(By.linkText("Log out")).isDisplayed());
		driver.findElement(By.linkText("Log out")).click();
		driver.findElement(By.linkText("Log in")).click();
	}
	@DataProvider(name="dp1")
	public Object[][] getDatafromExcel()
	{
		return ReadExcelUtility.readDataFromExcel();
	}

}
