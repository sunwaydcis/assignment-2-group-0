import scala.io.Source
import scala.collection.immutable.*

/** DATA MODELS **/
// Case class for representing a row of hospital data
case class HospitalData(
                         date: String,
                         state: String,
                         beds: Int,
                         bedsCovid: Int,
                         bedsNonCrit: Int,
                         admittedPui: Int,
                         admittedCovid: Int,
                         admittedTotal: Int,
                         dischargedPui: Int,
                         dischargedCovid: Int,
                         dischargedTotal: Int,
                         hospCovid: Int,
                         hospPui: Int,
                         hospNonCovid: Int
                       )
// Companion Object for Hospital Data
object HospitalData:
  // Function to open file and read them in to a list of strings
  def openFile(filePath: String): List[String] =
    try
      val file = Source.fromFile(filePath)
      // if opening file success, read line
      try
        file.getLines().to(List)
      // close file after reading
      finally
        file.close()
    catch
      // print error message
      case e: Exception =>
        println("Unable to open file: " + e.getMessage)
        List.empty

  // Function to parse the data into lazy list
  def parseDataToLazyList(data: List[String]): LazyList[HospitalData] =
    try
      // Extract header row and put them into array (split into column names)
      val header = data.head.split(",")
      // Using zipWithIndex to create a map to match column to their respective indices
      val headerIndex = header.zipWithIndex.toMap
      // return using lazy list method
      data.drop(1).map {
        row =>
          val columns = row.split(",").toList
          HospitalData(
            date = columns(headerIndex("date")),
            state = columns(headerIndex("state")),
            beds = columns(headerIndex("beds")).toInt,
            bedsCovid = columns(headerIndex("beds_covid")).toInt,
            bedsNonCrit = columns(headerIndex("beds_noncrit")).toInt,
            admittedPui = columns(headerIndex("admitted_pui")).toInt,
            admittedCovid = columns(headerIndex("admitted_covid")).toInt,
            admittedTotal = columns(headerIndex("admitted_total")).toInt,
            dischargedPui = columns(headerIndex("discharged_pui")).toInt,
            dischargedCovid = columns(headerIndex("discharged_covid")).toInt,
            dischargedTotal = columns(headerIndex("discharged_total")).toInt,
            hospCovid = columns(headerIndex("hosp_covid")).toInt,
            hospPui = columns(headerIndex("hosp_pui")).toInt,
            hospNonCovid = columns(headerIndex("hosp_noncovid")).toInt
          )
      }.to(LazyList)
    catch
      case e: Exception =>
        println("Unable to parse line: " + e.getMessage)
        LazyList.empty

@main def main(): Unit =
  // Define the filepath and open the CSV
  val filePath = "src/main/resources/hospital.csv"
  
  // Call function to read the file into list of string
  val data = HospitalData.openFile(filePath)
  
  // Parse the data into a lazy list of HospitalData
  val listHospital = HospitalData.parseDataToLazyList(data)
  
  /** QUESTION 1 **/
  // Question 1: Which state has the highest total hospital beds?
  println("-- Question 1 --")
  // Check if list is empty before proceeding
  if listHospital.nonEmpty then
    // Find the state with the maximum beds on the latest date
    val stateWithMaxBeds = listHospital
      .maxByOption(_.beds) // Use `maxByOption` for safety in case of an empty list

    // Output
    stateWithMaxBeds match
      case Some(state) =>
        println(s"State with highest total hospital beds is ${state.state} with ${state.beds} beds")
      case None =>
        println(s"No data available.")
  else
    println("No valid hospital data found in the file.")

  /** QUESTION 2 **/
  // Question 2: Ratio of beds dedicated for COVID-19 to total hospital beds
  println("\n-- Question 2 --")
  if listHospital.nonEmpty then
    // Calculate total beds and covid beds using foldLeft
    val (totalBeds, totalCovidBeds) = listHospital.foldLeft((0, 0)) {
      case ((beds, covidBeds), data) =>
        (beds + data.beds, covidBeds + data.bedsCovid)
    }
    
    if totalBeds > 0 then
      // Calculate ratio
      val ratio = totalCovidBeds.toDouble / totalBeds.toDouble
      // Output
      println(s"Total beds: $totalBeds")
      println(s"Total beds dedicated to COVID-19: $totalCovidBeds")
      println(f"Ratio of COVID-19 dedicated beds to total beds: $ratio%.4f")
    else
      println("Division Error! No valid data available to calculate the ratio.")
  else
    println("No valid hospital data found in the file.")
  
  /** QUESTION 3 **/
  // Question 3: Averages of individuals in each category (suspected/probable, covid, and non-covid) admitted per state
  println("\n-- Question 3 --")
  println("Averages of individuals in each category (suspected/probable, covid, and non-covid) admitted per state")
  // Check if the hospital data list is not empty
  if listHospital.nonEmpty then

    val stateAverages: Unit = listHospital
      .groupBy(_.state)     // Group the data by state
      .foreach {            // Iterate through each state and calculate averages for each category
        case (state, data) =>
          // Calculate the total suspected and covid admissions
          val (totalSuspected, totalCovid) = data.foldLeft(0,0) {
            case ((suspectedSum, covidSum), data) =>
              (suspectedSum + data.admittedPui, covidSum + data.admittedCovid)
          }

          // Calculate average
          val (averageSuspected, averageCovid) =
            (totalSuspected.toDouble / data.length, totalCovid.toDouble / data.length)

          // Output the averages for the current state
          println(f" - $state: Suspected = $averageSuspected%.2f, COVID-19 = $averageCovid%.2f")
      }
  else
    // Handle case where there is no hospital data available
    println("No valid hospital data found in the file.")
  
end main