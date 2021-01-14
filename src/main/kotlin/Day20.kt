import java.io.File
import java.lang.StringBuilder

class Day20(filename: String) {
	/** Tile configuration
	```
	|           0
	|   ↑----------------→
	|   |                |
	|   |                |
	| 3 |                | 1
	|   |                |
	|   ←----------------↓
	|           2
	```
	*/
	private val tiles: Map<Int, Edges>

	init {
		var currentKey = 0

		val tiles = mutableMapOf<Int, Edges>()

		val lines = File(filename).readLines()
		var i = 0
		while (i < lines.size) {
			var line = lines[i]
			when {
				line.isEmpty() -> { }
				line.startsWith("Tile") -> currentKey = line.substring(5..8).toInt()
				else -> {
					val edge1 = StringBuilder()
					val edge3 = StringBuilder()
					var edge0 = ""
					var edge2 = ""
					for (t in 1..10) {
						line = lines[i]
						when (t) {
							1 -> edge0 = line
							10 -> edge2 = line.reversed()
						}
						edge1.append(line.last())
						edge3.append(line[0])
						i++
					}
					tiles[currentKey] = listOf(
						edge0,
						edge1.toString(),
						edge2,
						edge3.reverse().toString()
					)
				}
			}
			i++
		}
		this.tiles = tiles
	}

	fun getCornerProduct(): Long {
		val tileCounts = mutableMapOf<Int, Int>()
		for (k in tiles.keys) {
			tileCounts[k] = 0
		}

		for ((tileId, tile) in tiles) {
			for ((compareId, otherTile) in tiles) {
				if (compareId >= tileId) continue

				for (edge in tile.withIndex()) {
					val matchedTiles = mutableListOf<IndexedValue<String>>()
					for (oEdge in otherTile.withIndex()) {
						if (oEdge.value == edge.value || oEdge.value.reversed() == edge.value)
							matchedTiles.add(oEdge)
					}
					if (matchedTiles.isNotEmpty()) {
						println("$tileId[${edge.index}=${edge.value}]: $compareId[$matchedTiles]")

						tileCounts[tileId] = tileCounts[tileId]!! + 1
						tileCounts[compareId] = tileCounts[compareId]!! + 1
					}
				}
			}
		}

		var product = 1L
		for (tile in tileCounts) {
			if (tile.value == 2) {
				product *= tile.key.toLong()
			}
		}

		return product
	}
}



private typealias Edges = List<String>
