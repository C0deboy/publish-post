package pl.devcave

import org.openqa.selenium.By
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import java.io.File
import javax.imageio.ImageIO
import javax.swing.filechooser.FileSystemView

object HeadingScraper {

    private val headingSelector = By.cssSelector(".post-preview")
    private val darkModeSelector = By.cssSelector(".dark-mode-btn")

    private val headingFixCss =
            """
            .post-preview {
                width: 474px;
                height: 249px;
                padding: 15px;
                margin-top: 80px;
                display: flex;
                flex-direction: column;
                justify-content: center;
            }

            .post-preview .tags {
                font-size: 0.88em;
            }

        """.trimIndent().replace("\n", "")

    private val headingFixScript =
            """
            var style = document.createElement('style');
            style.type = 'text/css';
            style.innerHTML = '$headingFixCss';
            document.getElementsByTagName('head')[0].appendChild(style);

        """.trimIndent()

    fun scrapPostImage() {

        SeleniumConfig.use {
            val driver = SeleniumConfig.getDriver()

            driver.get("https://devcave.pl")

            driver.findElement(darkModeSelector).click()

            val headingElement = driver.findElement(headingSelector)

            driver.executeScript(headingFixScript)

            getHeadingImage(driver, headingElement)

        }
    }

    private fun getHeadingImage(driver: ChromeDriver, headingElement: WebElement) {

        val screenshot = (driver as TakesScreenshot).getScreenshotAs<File>(OutputType.FILE)
        val fullImg = ImageIO.read(screenshot)

        val point = headingElement.location

        val eleWidth = headingElement.size.getWidth()
        val eleHeight = headingElement.size.getHeight()

        val eleScreenshot = fullImg.getSubimage(point.x, point.y, eleWidth, eleHeight)
        ImageIO.write(eleScreenshot, "png", screenshot)

        val home = FileSystemView.getFileSystemView().homeDirectory.absolutePath
        val screenshotLocation = File("$home/heading.png")

        screenshot.copyTo(screenshotLocation, overwrite = true)

        screenshotLocation.deleteOnExit()
    }

}