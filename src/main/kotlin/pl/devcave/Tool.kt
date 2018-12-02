package pl.devcave

import com.beust.jcommander.JCommander
import com.beust.jcommander.ParameterException
import org.openqa.selenium.By
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import java.io.File
import java.util.*
import javax.imageio.ImageIO
import javax.swing.filechooser.FileSystemView
import kotlin.concurrent.schedule

fun main(args: Array<String>) {
    initSettings(args)

    if (!Settings.scrapOnly) {
        Logger.doing("Building blog")

        Cmd.bash.execute("buildblog")

        Logger.doing("Committing new post")

        Cmd.bash.execute("cd /mnt/e/Projects/devcave/_site && git add -A && git commit -m \"new post\"")
    }

    val delay = Settings.scrapImageDelay

    Logger.doing("Scrapping post image and opening fb in ${delay / 1000L} seconds")

    Timer().schedule(delay) {
        HeadingScraper.scrapPostImage()
        Cmd.execute("start https://www.facebook.com/devcavepl/ && PAUSE")
    }

}

private fun initSettings(args: Array<String>) {
    try {
        JCommander.newBuilder().addObject(Settings).build().parse(*args)
    } catch (e: ParameterException) {
        println(e.message + "\n")
        e.usage()
        System.exit(-1)
    }
}




