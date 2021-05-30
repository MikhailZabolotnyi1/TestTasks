
import Pages.Database.Database;
import Pages.IFrame.IFramePage;
import Pages.Images.ImagePreDownload;
import Pages.Images.ImagesEncode;
import org.junit.*;
import org.openqa.selenium.Beta;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Tests {

    private WebDriver driver;

    public void prepareForDatabaseTest() {
        Database.createDatabase();
    }

    public void prepareForImageTest() throws IOException {
        ImagePreDownload.imgDownload();
    }

   @Before
   public void setUp() {
        System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
    }

    @Test
    public void ImageTest() throws IOException {
        System.out.println("Precondition: downloading image");
        prepareForImageTest();

        System.out.println("Checking for image corresponds to example.jpeg in base64");
        Assert.assertEquals(ImagesEncode.URLImgEncode(), ImagesEncode.LocalImgEncode());
    }

    @Test
    public void DataBaseTest() {
        System.out.println("Precondition: creating database");
        prepareForDatabaseTest();

        System.out.println("checking for corresponding population density is below 50 in the USA");
        Database.populationDensityIsLowerFifty();

        System.out.println("checking for corresponding sum of all four countries is less than 2 billion");
        Assert.assertTrue(Database.checkPopulationOfAllCountries() < 2000000000);

    }

    @Test
    public void IFrameTest() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().deleteAllCookies();
        IFramePage iFramePage = PageFactory.initElements(driver, IFramePage.class);

        System.out.println("Going to main page");
        driver.get("https://www.w3schools.com/tags/tryit.asp?filename=tryhtml_iframe");

        System.out.println("Replacing link to Bing.com");
        iFramePage.clickOnMirrorCodeLine().linkReplaceInTextArea();

        System.out.println("Click on 'Run' button ");
        iFramePage.clickOnRunButton();

        System.out.println("Loading Bing page...");

        Thread.sleep(2000); // Waiting for the bing page to be downloaded.

        System.out.println("Placing some keys to search input");
        iFramePage.switchToBing();
        iFramePage.placeKeysToInputSearch();

        System.out.println("Checking for a corresponding search tip and click");
        Assert.assertEquals("redmond washington", iFramePage.getFirstFoundSearch());
        iFramePage.clickOnFirstFoundSearch();

        Assert.assertEquals("https://www.bing.com/travelguide?q=Redmond", iFramePage.getFirstSearchResultLink());
    }

    @After
    public void tearDownAndClean() {
        System.out.println("Closing browser, clean up...");
        if (!(driver == null)) {
            driver.close();
        }
        File database = new File("countries.db");
        File exmampleImage = new File("example.jpeg");
        database.delete();
        exmampleImage.delete();

    }


}
