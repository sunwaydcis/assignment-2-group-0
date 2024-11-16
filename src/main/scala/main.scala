// https://github.com/MoH-Malaysia/covid19-public
// test
import scala.io.Source

@main def main(): Unit = 
    val filepath: String = "src/main/resources/hospital.csv"
    val delimiter = ","
    val file = Source.fromFile(filepath)
    for (line <- file.getLines()) {
        val fields = line.split(delimiter).map(_.trim)
        println(fields.mkString(", "))
    }
    file.close()