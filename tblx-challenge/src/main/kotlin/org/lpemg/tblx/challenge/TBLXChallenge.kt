package org.lpemg.tblx.challenge
import org.lpemg.tblx.challenge.api.APIServer
import org.lpemg.tblx.challenge.data.TBLXMongoDatabase
import kotlinx.cli.*
import org.lpemg.tblx.challenge.data.access.IEntryDataAccess
import org.lpemg.tblx.challenge.data.access.MongoEntryDAO
import java.nio.file.Files
import java.nio.file.Paths
import java.util.Properties

@ExperimentalCli
fun main(args: Array<String>) {
    class LoadData: Subcommand("load", "Load data from CSV") {
        val filePath by option(ArgType.String, shortName = "d", description = "Path to csv data file").required()
        var isSet: Boolean = false
        override fun execute() {
            isSet = true
        }
    }
    class StartServer: Subcommand("run", "Start API Server") {
        val port by option(ArgType.Int, shortName = "p", description = "Server port to listen in").required()
        var isSet: Boolean = false
        override fun execute() {
            isSet = true
        }
    }

    val parser = ArgParser("java -jar tblx-challenge-<version>-shaded.jar")

    val load = LoadData()
    val run = StartServer()
    val dbConnString by parser.option(ArgType.String, shortName = "db", description = "MongoDB Connection string").required()
    val propertiesFilePath by parser.option(ArgType.String, shortName = "properties", description = "tblx-challenge .properties file")

    parser.subcommands(load, run)
    parser.parse(args)

    val db = TBLXMongoDatabase(dbConnString)
    val dataAccess : IEntryDataAccess = MongoEntryDAO(db)

    if(load.isSet){
        DataLoader(dataAccess, CSVReader()).loadFromFilePath(load.filePath)
    }
    if(run.isSet){
        APIServer(dataAccess, getApiVersion(propertiesFilePath)).start(run.port)
    }
}

private fun getApiVersion(propertiesFilePath : String?): KotlinVersion {
    var propsPath = propertiesFilePath
    if(propsPath == null){
        propsPath = "tblx-challenge.properties"
    }
    val reader = Files.newBufferedReader(Paths.get(propsPath))

    val properties = Properties()
    properties.load(reader)

    val version = properties.getProperty("api.version").split(".")
    return KotlinVersion(version[0].toInt(), version[0].toInt(), version[0].toInt())
}

