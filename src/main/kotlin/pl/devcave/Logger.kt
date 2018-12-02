package pl.devcave

object Logger {
    fun doing(taskName: String) {

        val padding = " ".repeat((60 - taskName.length) / 2)
        val padding2 =  if (taskName.length % 2 > 0) "$padding " else padding

        println(
                """

                 ____________________________________________________________
                |$padding$taskName$padding2|
                 ````````````````````````````````````````````````````````````

                """
        .trimIndent())
    }
}