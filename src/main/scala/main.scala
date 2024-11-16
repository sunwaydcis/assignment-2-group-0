// https://github.com/MoH-Malaysia/covid19-public

import scala.io.Source
import scala.collection.mutable.*

@main def main(): Unit =
    // Which state has the highest total hospital bed ? 
    //arrayMethod()
    listMethod()
end main

def arrayMethod(): Unit =

    val filepath = "src/main/resources/hospital.csv"
    val file = Source.fromFile(filepath)

    val arrHospital = ArrayBuffer[String]()
    for (line <- file.getLines()) {
        arrHospital += line
    }
    var count = arrHospital.length
    file.close()

    //println(arrHospital.mkString("\n")) // Print all data
    //println(arrHospital(0)) // Header

def listMethod(): Unit =
    val filepath = "src/main/resources/hospital.csv"
    val file = Source.fromFile(filepath)

    val listHospital = ListBuffer[List[String]]()

    for (line <- file.getLines()) {
        listHospital += line.split(",").toList
    }

    println(listHospital.head)
    println(listHospital(1))
    val header = listHospital.head
    val stateIndex = header.indexOf("state")
    println(stateIndex)

    val totalBedsByState = ListBuffer[List[String]]()
    var total = 0
    var previousRow = List[String]()
    var hospitalData = listHospital.tail
    hospitalData = hospitalData.sortBy(row => row(stateIndex))(Ordering[String].reverse)
    for (row <- hospitalData) {
        var state = row(stateIndex)

        if (hospitalData.indexOf(row) == 0) {
            println(row(stateIndex) + ": ")
            total += row(2).toInt
        } else if (previousRow(stateIndex) == row(stateIndex)) {
            total += row(2).toInt
        } else {
            println(total)
            println(row(stateIndex) + ": ")
            total = 0
        }
        previousRow = row
    }

