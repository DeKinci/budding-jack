import com.dekinci.bot.entities.River
import com.dekinci.bot.game.map.GameMap
import com.dekinci.bot.game.tactics.PathFinder
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.collections.ArrayList

class PathFinderTest {
    /**
     *  0---1---2
     *  | \ | / |
     *  3   4   5
     *  | / | \ |
     *  6---7---8
     */
    @Test
    fun test() {
        val size = 9
        val minesList = listOf(3, 5)
        val riverList = "0,1 1,2 0,3 0,4 1,4 4,2 2,5 3,6 6,4 7,4 4,8 8,5 6,7 7,8"
                .split(" ")
                .map { River(it.split(",")[0].toInt(), it.split(",")[1].toInt()) }

//        println(riverList)
        val map = GameMap(size, riverList, minesList)
        val start = 3
        val path = listOf(start) + PathFinder(map).findPath(start, 5)
        println(path)
    }

    @Test
    fun test2() {
        val size = 1000
        val mines = hashSetOf<Int>()

        val r = Random()
        for (i in 0 .. 5)
            mines.add(r.nextInt(size))

        val rivers = hashSetOf<River>()
        for (i in 0 .. 10 * size) {
            val first = r.nextInt(size)
            var second = r.nextInt(size)
            while (first == second)
                second = r.nextInt(size)
            rivers.add(River(first, second))
        }

        val mineList = mines.toList()

        val map = GameMap(size, rivers.toList(), mineList)

        val start = mineList[0]

        val listPaths = ArrayList<List<Int>>()

        while (true) {
            val path = PathFinder(map).findPath(start, mineList[1], listPaths)
            if (!path.isEmpty())
                listPaths.add(path)
            else
                break
        }

        println(listPaths.size)
    }

    @Test
    fun testApproxK() {
        val size = 9
        val minesList = listOf(3, 5)
        val riverList = "0,1 1,2 0,3 0,4 1,4 4,2 2,5 3,6 6,4 7,4 4,8 8,5 6,7 7,8"
                .split(" ")
                .map { River(it.split(",")[0].toInt(), it.split(",")[1].toInt()) }

//        println(riverList)
        val map = GameMap(size, riverList, minesList)
        val start = 3

        val listPaths = ArrayList<List<Int>>()

        while (true) {
            val path = PathFinder(map).findPath(start, 5, listPaths)
            if (!path.isEmpty())
                listPaths.add(path)
            else
                break
        }

        println(listPaths.size)
    }
}