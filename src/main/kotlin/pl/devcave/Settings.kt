package pl.devcave

import com.beust.jcommander.Parameter

object Settings {

    @Parameter(names = ["-so", "--scrapOnly"], description = "Tool will only scrap image heading.")
    var scrapOnly = false;

    @Parameter(names = ["-d", "--delay"], description = "Number of seconds to wait before scraping image heading.")
    var scrapImageDelay = 10L
        get() { return if (scrapOnly) 0 else field * 1000L }
}