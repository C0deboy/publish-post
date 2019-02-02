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

        Cmd.bash.execute("goblog && cd _site && git add -A && git commit -m \"new post\"")

        Logger.doing("Pushing new post")

        Cmd.bash.execute("goblog && cd _site && git push origin HEAD")
    }

    val delay = Settings.scrapImageDelay

    Logger.doing("Scrapping post image and opening fb in ${delay / 1000L} seconds")

    Timer().schedule(delay) {
        val postTile = HeadingScraper.scrapPostImage()
        println("Scrapped for: $postTile")
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




