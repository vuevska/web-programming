package mk.finki.ukim.mk.lab.selenium;

import lombok.Getter;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class BalloonsPage extends AbstractPage {

    @FindBy(css = "tr[class=balloons]")
    private List<WebElement> balloonRows;


    @FindBy(css = ".delete-balloon")
    private List<WebElement> deleteButtons;


    @FindBy(css = ".edit-balloon")
    private List<WebElement> editButtons;


    @FindBy(css = ".add-balloon")
    private List<WebElement> addBalloonButton;

    public BalloonsPage(WebDriver driver) {
        super(driver);
    }

    public static BalloonsPage to(WebDriver driver) {
        get(driver, "/balloons");
        return PageFactory.initElements(driver, BalloonsPage.class);
    }

    public void assertElements(int rows, int delete, int edit, int add) {
        Assert.assertEquals("rows do not match", rows, this.getBalloonRows().size());
        Assert.assertEquals("delete does not match", delete, this.getDeleteButtons().size());
        Assert.assertEquals("edit does not match", edit, this.getEditButtons().size());
        Assert.assertEquals("add is visible", add, this.getAddBalloonButton().size());
    }
}


