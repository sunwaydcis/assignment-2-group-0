//import scala.io.Source
//
////case class HospitalData(
////   date: String,
////   state: String,
////   beds: Int,
////   bedsCovid: Int,
////   bedsNonCrit: Int,
////   admittedPUI: Int,
////   admittedCovid: Int,
////   admittedTotal: Int,
////   dischargedPUI: Int,
////   dischargedCovid: Int,
////   dischargedTotal: Int,
////   hospCovid: Int,
////   hospPUI: Int,
////   hospNonCovid: Int
//// )
//
//@main def HospitalDataAnalysis(): Unit =
//    // Load the CSV
//    val filepath = "src/main/resources/hospital.csv"
//    val file = Source.fromFile(filepath)
//    val lines = file.getLines().drop(1) // Drop the header row
//
//    // Parse each line into HospitalData objects
//    val parsedData = lines.map { line =>
//      val column = line.split(",").map(_.trim)
//      val hospitalData = HospitalData(
//        date = column(0),
//        state = column(1),
//        beds = column(2).toInt,
//        bedsCovid = column(3).toInt,
//        bedsNonCrit = column(4).toInt,
//        admittedPUI = column(5).toInt,
//        admittedCovid = column(6).toInt,
//        admittedTotal = column(7).toInt,
//        dischargedPUI = column(8).toInt,
//        dischargedCovid = column(9).toInt,
//        dischargedTotal = column(10).toInt,
//        hospCovid = column(11).toInt,
//        hospPUI = column(12).toInt,
//        hospNonCovid = column(13).toInt
//      )
//      (hospitalData.state, hospitalData) // Create state tuple for grouping
//    }.toList // Convert to list
//
//    // Question 1: Which state has the highest total hospital beds?
//    println(s"Q1: State with the highest total hospital beds is [] with [] beds.")
//
//    // Question 2: Ratio of beds dedicated for COVID-19 to total hospital beds
//    println(f"Q2: The ratio of COVID-19 beds to total beds is []")
//
//    // Question 3: Averages of individuals in each category (suspected/probable, covid, and non-covid) admitted per state
//
