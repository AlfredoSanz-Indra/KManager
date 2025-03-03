package es.alfred.kmanager.data.ant

import es.alfred.kmanager.domain.dataapi.AntCommands
import es.alfred.kmanager.domain.model.AntResult


/**
 * @author Alfredo Sanz
 * @time 2025
 */
class AntCommandsImpl : AntCommands {

    override fun execAntGitCheckout(antTaskName: String, destBranch: String, microID: String): AntResult {

        val command: List<String> = "cmd /C start ant $antTaskName -Dcheckout-to-branch=$destBranch -Dproject-prop=$microID".split(" ")
        ProcessBuilder(command)
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .start()
            .waitFor()

        return AntResult("Ant Task smart Git checkout command launched")
    }

    override fun execAntGitCommandSmart(antTaskName: String,  microID: String): AntResult {

        val command: List<String> = "cmd /C start ant $antTaskName -Dproject-prop=$microID".split(" ")
        ProcessBuilder(command)
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .start()
            .waitFor()

        return AntResult("Ant Task smart Git launched")
    }

    override fun execAntGitCommand(antTaskName: String): AntResult {

        ProcessBuilder("cmd /C start ant $antTaskName".split(" "))
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .start()
            .waitFor()

        return AntResult("Ant Task Git launched")

        /* Alternative way
        val command: List<String> = "cmd /c start ant $antTaskName".split(" ")
        val arCommand = Array(command.size) { command[it] }
        println("doAction: Executing ant command ->  $command")
        Runtime.getRuntime().exec(arCommand)
        return AntResult("Ant Task launched")
        */
    }

    override fun execAntGitCommandSmartPush(destBranch: String): AntResult {
        ProcessBuilder("cmd /C start ant git-push -Dbranch-prop=$destBranch".split(" "))
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .start()
            .waitFor()

        return AntResult("Ant Task Git Push launched")
    }

    override fun execAntNodeCommandsSmart(antTaskName: String,  microID: String): AntResult {

        val command: List<String> = "cmd /C ant $antTaskName -Dproject-prop=$microID".split(" ")
        ProcessBuilder(command)
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .start()
            .waitFor()

        return AntResult("Ant Task Node launched")
    }

    override fun execAntNodeCommands(antTaskName: String): AntResult {

        ProcessBuilder("cmd /C ant $antTaskName".split(" "))
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .start()
            .waitFor()

        return AntResult("Ant Task Node launched")
    }

    override suspend fun runMongoServer(): AntResult {
        ProcessBuilder("cmd /C ant run-mongo".split(" "))
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .start()
            .waitFor()

        return AntResult("Ant Task Run-Mongo launched")
    }

    override suspend fun openMongoShell(): AntResult {
        ProcessBuilder("cmd /C ant open-mongosh".split(" "))
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .start()
            .waitFor()

        return AntResult("Ant Task Open mongosh launched")
    }

    override suspend fun execTest(input: String): AntResult {

            for (i in 1..1000000) {
                println("$input : $i")

            }
        return AntResult("Test finished")
    }
}