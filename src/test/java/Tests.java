
import org.junit.rules.ErrorCollector;
import pages.database.NewDatabase;
import pages.iFrame.IFramePage;
import pages.images.ImageHelper;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class Tests {

    private WebDriver driver;

    public void prepareForDatabaseTest() {
        NewDatabase.createDatabase();
    }

    public void prepareForImageTest() throws IOException {
        ImageHelper.imgDownload();
    }

   @Before
   public void setUp() {
        System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
    }

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Test
    public void ImageTest() throws IOException {
        System.out.println("Precondition: downloading image");
        prepareForImageTest();

        try {
            System.out.println("Checking for image corresponds to example.jpeg in base64");
            Assert.assertEquals(ImageHelper.URLImgEncode(), ImageHelper.LocalImgEncode());
        } catch (Throwable t) {
            collector.addError(t);
        }
        System.out.println("I'm done with images");

    }

    @Test
    public void DataBaseTest() throws SQLException {
        System.out.println("Precondition: creating database");
        prepareForDatabaseTest();

        System.out.println("checking for corresponding population density is below 50 in the USA");
        NewDatabase.populationDensityIsLowerFifty();


        try {
            System.out.println("checking for corresponding sum of all four countries is less than 2 billion");
            Assert.assertTrue(NewDatabase.checkPopulationOfAllCountries() < 1000000000);
        } catch (Throwable t) {
            collector.addError(t);
        }
        System.out.println("I'm done boi");

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

        try {
            Assert.assertSame("https://www.bing.com/travelguide?q=Redmond", iFramePage.getFirstSearchResultLink());
        } catch (Throwable t) {
            collector.addError(t);
        }
        System.out.println("Done with IFrame");
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
