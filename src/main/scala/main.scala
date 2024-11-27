import scala.collection.mutable.ListBuffer
import scala.io.Source

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


@main def main(): Unit =
  /** FILE & CSV **/
  // Define the filepath and open the CSV
  val filepath = "src/main/resources/hospital.csv"
  val file = Source.fromFile(filepath)

  var curr = System.currentTimeMillis()
  // Read the CSV file into a list of strings
  val data = file.getLines().to(LazyList)

  /** HEADER MAPPING & DATA PARSING **/
  // Extract header row and put them into array (split into column names)
  val header = data.head.split(",")

  // Using zipWithIndex to create a map to match column to their respective indices
  val headerIndex = header.zipWithIndex.toMap

  // Create a buffered list to store hospital data
  // lazy list method
  val listHospital: LazyList[HospitalData] = data.drop(1).map {
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
  }

  println(System.currentTimeMillis() - curr)
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
      println("No valid data available to calculate the ratio.")
  else
    println("No valid hospital data found in the file.")

  curr = System.currentTimeMillis()
  /** QUESTION 3 **/
  // Question 3: Averages of individuals in each category (suspected/probable, covid, and non-covid) admitted per state
  println("\n-- Question 3 --")

  // Check if the hospital data list is not empty
  if listHospital.nonEmpty then
    // Group the data by state
    val groupedByState = listHospital.groupBy(_.state)

    // Iterate through each state and calculate averages for each category
    groupedByState.foreach { case (state, data) =>
      // Initialize variables to store totals for each category
      var totalSuspected = 0
      var totalCovid = 0

      // Iterate over each row for the current state to accumulate totals for each category
      for row <- data do
        totalSuspected += row.admittedPui
        totalCovid += row.admittedCovid

      // Helper function to calculate the average, returning 0.0 if the data is empty
      def calculateAverage(total: Int, length: Int): Double =
        if length > 0 then total.toDouble / length else 0.0

      // Calculating averages for each category
      val averageSuspected = calculateAverage(totalSuspected, data.length)
      val averageCovid = calculateAverage(totalCovid, data.length)

      // Output the averages for the current state
      println(f" - $state: Suspected = $averageSuspected%.2f, COVID-19 = $averageCovid%.2f")
    }
  else
    // Handle case where there is no hospital data available
    println("No valid hospital data found in the file.")

  // Close file
  file.close()
  println(System.currentTimeMillis() - curr)
end main