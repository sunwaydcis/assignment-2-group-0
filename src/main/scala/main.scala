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

// Case class to hold header indices for mapping CSV columns
case class HospitalDataHeader(
                               date: Int,
                               state: Int,
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

  // Create a buffered list to store hospital data
  val listHospital = ListBuffer[HospitalData]()

  // Read the CSV file into a list of strings
  val data = file.getLines().toList

  // Close file
  file.close()

  /** HEADER MAPPING & DATA PARSING **/
  // Extract header row and put them into array (split into column names)
  val header = data.head.split(",")

  // Map the column names to their respective indices for easy lookup
  val headerIndex = HospitalDataHeader(
    date = header.indexOf("date"),
    state = header.indexOf("state"),
    beds = header.indexOf("beds"),
    bedsCovid = header.indexOf("beds_covid"),
    bedsNonCrit = header.indexOf("beds_noncrit"),
    admittedPui = header.indexOf("admitted_pui"),
    admittedCovid = header.indexOf("admitted_covid"),
    admittedTotal = header.indexOf("admitted_total"),
    dischargedPui = header.indexOf("discharged_pui"),
    dischargedCovid = header.indexOf("discharged_covid"),
    dischargedTotal = header.indexOf("discharged_total"),
    hospCovid = header.indexOf("hosp_covid"),
    hospPui = header.indexOf("hosp_pui"),
    hospNonCovid = header.indexOf("hosp_noncovid")
  )

  // For each row in the CSV file, store it as a list of string
  for row <- data.drop(1) do
    val columns = row.split(",").toList
    listHospital += HospitalData(
      date = columns(headerIndex.date),
      state = columns(headerIndex.state),
      beds = columns(headerIndex.beds).toInt,
      bedsCovid = columns(headerIndex.bedsCovid).toInt,
      bedsNonCrit = columns(headerIndex.bedsNonCrit).toInt,
      admittedPui = columns(headerIndex.admittedPui).toInt,
      admittedCovid = columns(headerIndex.admittedCovid).toInt,
      admittedTotal = columns(headerIndex.admittedTotal).toInt,
      dischargedPui = columns(headerIndex.dischargedPui).toInt,
      dischargedCovid = columns(headerIndex.dischargedCovid).toInt,
      dischargedTotal = columns(headerIndex.dischargedTotal).toInt,
      hospCovid = columns(headerIndex.hospCovid).toInt,
      hospPui = columns(headerIndex.hospPui).toInt,
      hospNonCovid = columns(headerIndex.hospNonCovid).toInt
    )

  // Find the latest date in the dataset
  val latestDate = listHospital.maxBy(_.date).date
  val latestDateList = listHospital.filter(_.date == latestDate)

  /** QUESTION 1 **/
  // Question 1: Which state has the highest total hospital beds?
  println("-- Question 1 --")
  // Check if list is empty before proceeding
  if listHospital.nonEmpty then
    // Find the state with the maximum beds on the latest date
    val stateWithMaxBeds = latestDateList
      .maxByOption(_.beds) // Use `maxByOption` for safety in case of an empty list

    // Output
    stateWithMaxBeds match
      case Some(state) =>
        println(s"State with highest total hospital beds is ${state.state} with ${state.beds} beds")
      case None =>
        println(s"No data available for the latest date: $latestDate")
  else
    println("No valid hospital data found in the file.")

  /** QUESTION 2 **/
  // Question 2: Ratio of beds dedicated for COVID-19 to total hospital beds
  println("\n-- Question 2 --")
  if listHospital.nonEmpty then
    // Initialize variables
    var totalBeds = 0
    var totalCovidBeds = 0

    // Calculate total beds and dedicated COVID beds
    for data <- latestDateList do
      totalBeds += data.beds
      totalCovidBeds += data.bedsCovid

//    val (totalAvailableBeds, totalDedicatedCOVIDBeds) = latestDateList.foldLeft((0, 0)) {
//      case ((totalBeds, covidBeds), data) =>
//        (totalBeds + data.beds, covidBeds + data.bedsCovid)
//    }

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

  /** QUESTION 3 **/
  // Question 3: Averages of individuals in each category (suspected/probable, covid, and non-covid) admitted per state
end main