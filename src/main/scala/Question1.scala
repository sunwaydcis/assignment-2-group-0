// https://github.com/MoH-Malaysia/covid19-public

import scala.io.Source
import scala.collection.mutable.*

@main def Question1(): Unit =
    // Which state has the highest total hospital bed?
    listMethod()
end Question1

def listMethod(): Unit =
    val filepath = "src/main/resources/hospital.csv"
    val file = Source.fromFile(filepath)

    val listHospital = ListBuffer[List[String]]()

    for (line <- file.getLines()) {
        listHospital += line.split(",").toList
    }

    val header = listHospital.head
    val stateIndex = header.indexOf("state")

    val sortedList = listHospital.tail.sortBy(row => row(stateIndex)).reverse

    val latestDate = sortedList.head.head
    println(sortedList.head)
    println(latestDate)

    var latestList = sortedList.filter(row => row.head == latestDate)
    println(latestList.length)

    latestList = latestList.sortBy(row => row(2).toInt).reverse
    println("State with highest hospital bed: ")
    println(latestList.head(1) + ": " + latestList.head(2))


