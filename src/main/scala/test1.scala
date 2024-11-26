import scala.collection.mutable.ListBuffer
import scala.io.Source

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

@main def test1(): Unit =
  // file path and open the csv
  val filepath = "src/main/resources/hospital.csv"
  val file = Source.fromFile(filepath)

  // Create a buffered list which stores a list of string
  val listHospital = ListBuffer[HospitalData]()

  // Convert data to list
  val data = file.getLines().toList

  // Close file
  file.close()

  // Get Header and put them into array for index searching later
  val header = data.head.split(",")

  // Map respective index into HospitalDataHeader
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

  // For each row in the CSV file, store it as a list of string into the ListBuffer
  for (row <- data.drop(1)) {
    val column = row.split(",").toList
    listHospital += HospitalData(
      date = column(headerIndex.date),
      state = column(headerIndex.state),
      beds = column(headerIndex.beds).toInt,
      bedsCovid = column(headerIndex.bedsCovid).toInt,
      bedsNonCrit = column(headerIndex.bedsNonCrit).toInt,
      admittedPui = column(headerIndex.admittedPui).toInt,
      admittedCovid = column(headerIndex.admittedCovid).toInt,
      admittedTotal = column(headerIndex.admittedTotal).toInt,
      dischargedPui = column(headerIndex.dischargedPui).toInt,
      dischargedCovid = column(headerIndex.dischargedCovid).toInt,
      dischargedTotal = column(headerIndex.dischargedTotal).toInt,
      hospCovid = column(headerIndex.hospCovid).toInt,
      hospPui = column(headerIndex.hospPui).toInt,
      hospNonCovid = column(headerIndex.hospNonCovid).toInt,
    )
  }
  val latestDate = listHospital.maxBy(_.date).date
  val latestListHospital = listHospital.filter(_.date == latestDate)
  val stateWithMaxBeds = latestListHospital.maxBy(_.beds)
  println(stateWithMaxBeds)

  // latestListHospital.foreach(println(_))
  var start = System.currentTimeMillis()
  val totalDedicatedCovidBeds = latestListHospital.foldLeft(0)((sum,data) => sum + data.bedsCovid)
  val totalAvailableBeds = latestListHospital.foldLeft(0)((sum, data) => sum + data.beds)
  println(totalDedicatedCovidBeds.toDouble / totalAvailableBeds.toDouble)
  println(System.currentTimeMillis() - start)

  val stateAverages = latestListHospital
    .groupBy(_.state)
    .map { case (state, data) =>
      val avgAdmittedPui = data.map(_.admittedPui).sum.toDouble / data.size
      val avgAdmittedCovid = data.map(_.admittedCovid).sum.toDouble / data.size
      val avgAdmittedNonCovid = data.map(_.admittedPui).sum.toDouble / data.size
      (state, avgAdmittedPui, avgAdmittedCovid, avgAdmittedNonCovid)
    }

  // Print the averages for each state
  stateAverages.foreach { case (state, avgPui, avgCovid, avgNonCovid) =>
    println(s"State: $state, Average PUI Admitted: $avgPui, Average Covid Admitted: $avgCovid, Average Non-Covid Admitted: $avgNonCovid")
  }