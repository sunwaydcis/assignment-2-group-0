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

@main def test1(): Unit =
  // file path and open the csv
  val filepath = "src/main/resources/hospital.csv"
  val file = Source.fromFile(filepath)

  // Create a buffered list which stores a list of string
  val listHospital = ListBuffer[HospitalData]()

  // Convert data to list
  val data = file.getLines().toList

  // Get Header first
  val header = data.head.split(",").toList
  println(header)

  // For each row in the CSV file, store it as a list of string into the ListBuffer
  for (row <- data.drop(1)) {
    val column = row.split(",").toList
    listHospital += HospitalData(
      date = column(header.indexOf("date")),
      state = column(header.indexOf("state")),
      beds = column(header.indexOf("beds")).toInt,
      bedsCovid = column(header.indexOf("beds_covid")).toInt,
      bedsNonCrit = column(header.indexOf("beds_noncrit")).toInt,
      admittedPui = column(header.indexOf("admitted_pui")).toInt,
      admittedCovid = column(header.indexOf("admitted_covid")).toInt,
      admittedTotal = column(header.indexOf("admitted_total")).toInt,
      dischargedPui = column(header.indexOf("discharged_pui")).toInt,
      dischargedCovid = column(header.indexOf("discharged_covid")).toInt,
      dischargedTotal = column(header.indexOf("discharged_total")).toInt,
      hospCovid = column(header.indexOf("hosp_covid")).toInt,
      hospPui = column(header.indexOf("hosp_pui")).toInt,
      hospNonCovid = column(header.indexOf("hosp_noncovid")).toInt,
    )
  }
  val latestDate = listHospital.maxBy(_.date).date

  val stateWithMaxBeds = listHospital.filter(HospitalData => HospitalData.date == latestDate).maxBy(_.beds)
  println(stateWithMaxBeds)
  file.close()