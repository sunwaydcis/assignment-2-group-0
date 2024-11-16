// https://github.com/MoH-Malaysia/covid19-public

import scala.io.Source
import scala.collection.mutable.ArrayBuffer

@main def main(): Unit =
    val filepath = "src/main/resources/hospital.csv"
    val file = Source.fromFile(filepath)
    var count = 0
    val arrHospital = ArrayBuffer[String]()
    for (line <- file.getLines()) {
        arrHospital += line
    }

    println(arrHospital.mkString("\n"))
    println()
    println(arrHospital(1))

    file.close()


def mymethod(): Unit =
    val filepath: String = "src/main/resources/hospital.csv"
    val delimiter = ","
    val file = Source.fromFile(filepath)
    for (line <- file.getLines()) {
        val fields = line.split(delimiter).map(_.trim)
        println(fields.mkString(", "))
    }
    file.close()