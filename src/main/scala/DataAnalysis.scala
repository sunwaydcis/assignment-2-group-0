import scala.io.Source

case class HospitalData(
                         date: String,
                         state: String,
                         beds: Int,
                         bedsCovid: Int,
                         bedsNonCrit: Int,
                         admittedPUI: Int,
                         admittedCovid: Int,
                         admittedTotal: Int,
                         dischargedPUI: Int,
                         dischargedCovid: Int,
                         dischargedTotal: Int,
                         hospCovid: Int,
                         hospPUI: Int,
                         hospNonCovid: Int
                       )

object DataAnalysis :
  def main(): Unit =
    // Load the CSV
    val filepath = "src/main/resources/hospital.csv"
    val file = Source.fromFile(filepath)
    val lines = file.getLines().drop(1) // Drop the header row

    // Question 1: Which state has the highest total hospital beds?
    println(s"Q1: State with the highest total hospital beds is [] with [] beds.")

    // Question 2: Ratio of beds dedicated for COVID-19 to total hospital beds
    println(f"Q2: The ratio of COVID-19 beds to total beds is []")

    // Question 3: Averages of individuals in each category (suspected/probable, covid, and non-covid) admitted per state

  end main
end DataAnalysis