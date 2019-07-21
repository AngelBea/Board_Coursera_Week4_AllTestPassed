package board

import board.Direction.*
import java.lang.IllegalArgumentException

class SquareBoardClass(val cellMap : MutableMap<String, Cell> = mutableMapOf(), override val width: Int) : SquareBoard{
    init {
        for(i in 1..width){
            for (j in 1..width){
                cellMap.set("""$i$j""", Cell(i,j))
            }
        }
    }

    override fun getCellOrNull(i: Int, j: Int): Cell? {
        return cellMap["""$i$j"""]
    }

    override fun getCell(i: Int, j: Int): Cell {
        return cellMap["""$i$j"""] ?: throw IllegalArgumentException("No such cell on board")
    }

    override fun getAllCells(): Collection<Cell> {
        return cellMap.values
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        val cellList : MutableList<Cell> = mutableListOf()

        if(i < width){
            var newRange = jRange

            if(newRange.last > width){
                newRange = jRange.first..width
            }

            for(j in newRange){
                cellList.add(cellMap["""$i$j"""] ?: Cell(0,0))
            }
        }

        return cellList
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        val cellList : MutableList<Cell> = mutableListOf()

        if(j <= width){
            var newRange = iRange

            if(newRange.last > width){
                newRange = iRange.first..width
            }

            for(i in newRange){
                cellList.add(cellMap["""$i$j"""] ?: Cell(i,j))
            }
        }

        return cellList
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        return when(direction){
            UP ->  cellMap[("""${this.i}${this.j}""".toInt()-10).toString()]
            DOWN ->  cellMap[("""${this.i}${this.j}""".toInt()+10).toString()]
            LEFT ->  cellMap[("""${this.i}${this.j}""".toInt()-1).toString()]
            RIGHT ->  cellMap[("""${this.i}${this.j}""".toInt()+1).toString()]
        }
    }
}

class GameBoardClass<T>(val cellMap : MutableMap<String, Pair<Cell, T?>> = mutableMapOf(), override val width: Int) : GameBoard<T>{
    init {
        for(i in 1..width){
            for (j in 1..width){
                cellMap.set("""$i$j""", Cell(i,j) to null)
            }
        }
    }

    override fun set(cell: Cell, value: T?) {
        cellMap.set("""${cell.i}${cell.j}""", cell to value)
    }

    override fun get(cell: Cell): T? {
        return cellMap.get("""${cell.i}${cell.j}""")?.second
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> {
        return cellMap.values.filter { predicate.invoke(it.second) }.map { it.first }
    }

    override fun find(predicate: (T?) -> Boolean): Cell? {
        return cellMap.values.find { predicate.invoke(it.second) }?.first
    }

    override fun any(predicate: (T?) -> Boolean): Boolean {
        return cellMap.values.any { predicate.invoke(it.second) }
    }

    override fun all(predicate: (T?) -> Boolean): Boolean {
        return return cellMap.values.all { predicate.invoke(it.second) }
    }

    override fun getCellOrNull(i: Int, j: Int): Cell? {
        return cellMap.get("""$i$j""")?.first
    }

    override fun getCell(i: Int, j: Int): Cell {
       return cellMap.get("""$i$j""")?.first ?: throw IllegalArgumentException("The required cell does not exist")
    }

    override fun getAllCells(): Collection<Cell> {
        return cellMap.values.map { it.first }
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

fun createSquareBoard(width: Int): SquareBoard = SquareBoardClass(width = width)
fun <T> createGameBoard(width: Int): GameBoard<T> = GameBoardClass(width = width)

