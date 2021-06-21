package pages.iFrame;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class IFramePage {
    WebDriver driver;

    public IFramePage(WebDriver driver) {
        this.driver = driver;
    }

    @FindBy (className = "CodeMirror")
    private WebElement codeMirror;

    @FindBy (xpath = "//*[@class=' CodeMirror-line '][7]//*[@class='cm-m-xml'][3]")
    private WebElement codeMirrorLine;

    @FindBy (css = "textarea")
    private WebElement textArea;

    @FindBy (xpath = "//*[@class='w3-button w3-bar-item w3-hover-white w3-hover-text-green']")
    private WebElement runButton;

    @FindBy (xpath = "//iframe[@id='iframeResult']")
    private WebElement iframeResult;

    @FindBy (xpath = "//iframe[@src='https://www.bing.com']")
    private WebElement iframeBing;

    @FindBy (xpath = "//input[@id='sb_form_q']")
    private WebElement inputSearch;

    @FindBy (xpath = "//*[@class='sa_tm_text']")
    private WebElement firstFoundSearch;

    @FindBy (xpath = "//*[@id='b_results']/li[1]//cite")
    private WebElement firstSearchResult;

    public IFramePage clickOnMirrorCodeLine(){
        codeMirrorLine.click();
        return this;
    }

    public IFramePage linkReplaceInTextArea(){
        WebElement textArea = codeMirror.findElement(By.cssSelector("textarea"));
        for(int i = 0; i < 18; i++) {
            textArea.sendKeys(Keys.BACK_SPACE);
        }
        textArea.sendKeys("www.bing.com\"");
        return this;
    }

    public IFramePage clickOnRunButton(){
        runButton.click();
        return this;
    }

    public IFramePage switchToBing() {
        driver.switchTo().frame(iframeResult);
        driver.switchTo().frame(iframeBing);
        return this;
    }

    public IFramePage placeKeysToInputSearch() {
        inputSearch.click();
        inputSearch.sendKeys("redmond ");
        inputSearch.sendKeys(Keys.SPACE);
        return this;
    }
    public String getFirstFoundSearch() {
        return firstFoundSearch.getText();
    }
    public  IFramePage clickOnFirstFoundSearch() {
        firstFoundSearch.click();
        return this;
    }

    public String getFirstSearchResultLink() {
        return firstSearchResult.getText();
    }

}
