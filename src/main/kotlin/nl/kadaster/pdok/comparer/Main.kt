package nl.kadaster.pdok.comparer

import org.apache.commons.io.FileUtils
import java.io.File
import java.nio.charset.Charset
import java.nio.file.Paths
import java.nio.file.Files


var encoding = Charset.forName("UTF-8")!!

val localFiles = FileUtils.getFile("src", "main", "resources", "localfiles.txt")

val cloudFiles = FileUtils.getFile("src", "main", "resources", "cloudfiles.txt")

fun main(args: Array<String>) {

    val exists = mutableListOf<String>()
    val notExists = mutableListOf<String>()

    val localLines = Files.readAllLines(Paths.get(localFiles.path), encoding)
    val cloudLines = Files.readAllLines(Paths.get(cloudFiles.path), encoding)

    localLines.forEach { local ->
        var splitLocal = local.split("/")
        var localName = splitLocal.last()
        println("check $localName")
        var exisitsInCloud = false
        cloudLines.forEach{ cloud ->
            var cloudLocal = cloud.split("/")
            var cloudName = cloudLocal.last()
            if(localName.equals(cloudName)) {
                exists.add(localName)
                exisitsInCloud = true
            }
        }
        if(!exisitsInCloud && localName != "tellingen.csv")
        {
            notExists.add(localName)
        }

    }
    println("Exists: $exists")
    println("NotExists: $notExists")
}
