package pl.devcave

val runtime = Runtime.getRuntime()

object Cmd {
    fun execute(command: String) {

        ProcessBuilder("cmd", "/c", command).inheritIO().start().waitFor()
    }

    object bash {
        fun execute(command: String) {

            ProcessBuilder("cmd", "/c", "bash", "-i", "-c", command).inheritIO().start().waitFor()
        }
    }

}
