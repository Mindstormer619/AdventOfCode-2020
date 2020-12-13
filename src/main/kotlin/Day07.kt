@file:Suppress("FunctionName")

/**
 * Maps {bag color -> bag colors that contain this color}
 */
typealias ContainedGraph = MutableMap<String,MutableSet<String>>
/**
 * Maps {bag color -> bag configuration contained in this bag color}
 */
typealias BagGraph = MutableMap<String, Set<Bag>>

private val hasBagRegex = Regex("""(\w+ \w+) bags contain ([0-9].*)\.""")
private val noBagRegex = Regex("""(\w+ \w+) bags contain no other bags\.""")
private val bagRegex = Regex("""(\d+) (\w+ \w+) bags?""")

fun main() {
	println(`first part`())
	println(`second part`())
}

private const val fileName = "src/main/resources/day07.input"

// --------------------------------------------------------------------------------

private fun `first part`(): Int {
	val bagGraph: ContainedGraph = mutableMapOf()

	readFileAndProcess(fileName) { line ->
		if (hasBagRegex.matches(line)) {
			val groups = hasBagRegex.matchEntire(line)!!.groupValues
			val containedBagColors = groups[2].split(", ").map { bagRegex.matchEntire(it)!!.groupValues[2] }
			val containingBagColor = groups[1]
			for (bagColor in containedBagColors) {
				bagGraph.putIfAbsent(bagColor, mutableSetOf())
				bagGraph[bagColor]!!.add(containingBagColor)
			}
		}
	}

	val visitedBags = mutableSetOf<String>()
	bagGraph.parseGraph("shiny gold", visitedBags)
	return visitedBags.size
}

fun ContainedGraph.parseGraph(
	bag: String,
	visitedBags: MutableSet<String> = mutableSetOf()
) {
	if (bag in visitedBags) return
	val containingBags = this[bag]
	if (containingBags != null) {
		for (containingBag in containingBags) {
			parseGraph(containingBag, visitedBags)
			visitedBags.add(containingBag)
		}
	}
}

// --------------------------------------------------------------------------------

private fun `second part`(): Int {
	val bagGraph: BagGraph = mutableMapOf()

	readFileAndProcess(fileName) { line ->
		when {
			hasBagRegex.matches(line) -> {
				val groups = hasBagRegex.matchEntire(line)!!.groupValues
				val containingBagColor = groups[1]
				val bags = groups[2].split(", ").map {
					val bagData = bagRegex.matchEntire(it)!!.groupValues
					Bag(bagData[2], Integer.parseInt(bagData[1]))
				}.toSet()
				bagGraph[containingBagColor] = bags
			}
			noBagRegex.matches(line) -> {
				val emptyBag = noBagRegex.matchEntire(line)!!.groupValues[1]
				bagGraph[emptyBag] = mutableSetOf()
			}
		}
	}

	return bagGraph.getBagCount("shiny gold")
}

data class Bag(val color: String, val multiplicity: Int)

fun BagGraph.getBagCount(bagName: String, cache: MutableMap<String, Int> = mutableMapOf()): Int {
	var totalBags = 0
	for (bag in this[bagName]!!) {
		totalBags += bag.multiplicity * (cache[bag.color] ?: getBagCount(bag.color, cache))
		totalBags += bag.multiplicity
	}
	cache[bagName] = totalBags
	return totalBags
}

/*
bag (color) -> set( (color+multiplicity) )

{
	bag(color) -> set(bags that contain it)

}
 */