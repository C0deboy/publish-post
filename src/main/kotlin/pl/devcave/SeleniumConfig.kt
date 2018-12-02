package pl.devcave

import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeDriverService
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.CapabilityType
import org.openqa.selenium.remote.DesiredCapabilities
import java.io.File
import java.util.logging.Level
import org.openqa.selenium.logging.LogType
import org.openqa.selenium.logging.LoggingPreferences
import java.util.logging.Logger


object SeleniumConfig : AutoCloseable {

    private val driver: ChromeDriver
    private val tempDriverFile: File

    init {
        val driverOutputSystem = this::class.java.classLoader.getResourceAsStream("chromedriver.exe")

        tempDriverFile = createTempFile()
        tempDriverFile.deleteOnExit()
        tempDriverFile.setExecutable(true)

        val outputStream = tempDriverFile.outputStream()
        driverOutputSystem.copyTo(outputStream);

        driverOutputSystem.close()
        outputStream.close()

        System.setProperty("webdriver.chrome.driver", tempDriverFile.absolutePath)

        val driverService = ChromeDriverService.Builder().withSilent(true).build()
        val chromeOptions = ChromeOptions()

        Logger.getLogger("org.openqa.selenium.remote").level = Level.OFF;

        chromeOptions.addArguments(
                "--silent",
                "--headless",
                "--start-maximized",
                "--window-size=1920,1080"
        )

        driver = ChromeDriver(driverService, chromeOptions)
    }

    fun getDriver() : ChromeDriver {
        return driver
    }

    override fun close() {
        driver.quit()
    }
}