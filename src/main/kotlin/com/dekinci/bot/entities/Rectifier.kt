package com.dekinci.bot.entities

class BasicMap(val rivers: Array<River>, val mines: Set<Int>, val size: Int)

class Rectifier(dirtyRivers: Iterable<River>, dirtyMines: Iterable<Int>) {
    private val distinctRivers = dirtyRivers.distinct()
    private val distinctMines = dirtyMines.distinct()

    private val cleanToDirty: IntArray
    private val dirtyToClean: IntArray

    init {
        val sites = HashSet<Int>()
        distinctRivers.forEach {
            sites.add(it.target)
            sites.add(it.source)
        }

        cleanToDirty = IntArray(sites.size)
        sites.forEachIndexed { index, site -> cleanToDirty[index] = site }

        val maxDirty = sites.max()!!
        dirtyToClean = IntArray(maxDirty)
        sites.forEachIndexed { index, site -> dirtyToClean[site] = index }
    }

    fun asMap(): BasicMap {
        val rivers = Array(distinctRivers.size) {
            purify(distinctRivers[it])
        }

        val mines = distinctMines.map { purify(it) }.toHashSet()

        return BasicMap(rivers, mines, cleanToDirty.size)
    }

    fun purify(dirtySite: Int) = dirtyToClean[dirtySite]

    fun pollute(cleanSite: Int) = cleanToDirty[cleanSite]

    fun purify(dirtyRiver: River) = River(
            purify(dirtyRiver.target),
            purify(dirtyRiver.source))

    fun purify(dirtyRiver: StatedRiver) = StatedRiver(
            purify(dirtyRiver.target),
            purify(dirtyRiver.source),
            dirtyRiver.state)

    fun pollute(dirtyRiver: River) = River(
            pollute(dirtyRiver.target),
            pollute(dirtyRiver.source))

    fun pollute(dirtyRiver: StatedRiver) = StatedRiver(
            pollute(dirtyRiver.target),
            pollute(dirtyRiver.source),
            dirtyRiver.state)
}