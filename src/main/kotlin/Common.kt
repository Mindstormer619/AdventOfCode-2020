import java.io.File

fun <T> readFileAndProcess(filePath: String, transform: (String) -> T): List<T> =
	File(filePath).useLines { sequence -> sequence.map(transform).toList() }

infix fun Int.mod(other: Int) = Math.floorMod(this, other)