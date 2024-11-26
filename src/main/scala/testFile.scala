// https://github.com/MoH-Malaysia/covid19-public

import scala.io.Source
import scala.collection.mutable.*

@main def testFile(): Unit =
    // Which state has the highest total hospital bed?
    listMethod()
end testFile

case class HospitalDataByState() :

end HospitalDataByState

def listMethod(): Unit =
    // file path and open the csv
    val filepath = "src/main/resources/hospital.csv"
    val file = Source.fromFile(filepath)

    // Create a buffered list which stores a list of string
    val listHospital = ListBuffer[List[String]]()

    // For each row in the CSV file, store it as a list of string into the ListBuffer
    for (line <- file.getLines()) {
        listHospital += line.split(",").toList
    }

    // Header is the first row of the data
    val header = listHospital.head

    // Find out position/column of "state"
    val stateIndex = header.indexOf("state")

    // Since the list contains hospitals for each state and day,
    // Sort in descending order to get the latest date
    val sortedList = listHospital.tail.sortBy(row => row(stateIndex)).reverse
    val latestDate = sortedList.head.head
    // println(sortedList.head)
    println(latestDate)

    // Filter out the list using only the latestDate
    val latestList = sortedList.filter(row => row.head == latestDate)
    // println(latestList.length)

    // Sort it again using the column - Hospital Beds
    val highestHospitalBedsByStateList = latestList.sortBy(row => row(2).toInt).reverse

    // print the state with the highest hospital bed
    println("State with highest hospital bed: ")
    println(highestHospitalBedsByStateList.head(1) + ": " + highestHospitalBedsByStateList.head(2))

    // Question2
    val q2List = latestList

    var totalAvailableBeds: Int = 0
    var totalDedicatedCOVIDBeds: Int = 0

    for (row <- q2List) {
        totalAvailableBeds += row(2).toInt
        totalDedicatedCOVIDBeds += row(3).toInt
    }

    println("\nRatio of bed dedicated for COVID-19 to total of available hospital bed in the dataset:")
    println(totalAvailableBeds)
    println(totalDedicatedCOVIDBeds)
    println(totalDedicatedCOVIDBeds.toDouble / totalAvailableBeds.toDouble)


    file.close()
