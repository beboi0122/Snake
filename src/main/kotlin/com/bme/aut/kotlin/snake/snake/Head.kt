package com.bme.aut.kotlin.snake.snake

class Head (val fieldSize: Int ,posX: Int, posY: Int) : Body(null, posX, posY,15, 80, 0) {
    var looking: Direction = Direction.RIGHT
    var lastMove: Direction = Direction.RIGHT
    var dead: Boolean = false

    fun moveRight(){
        if( posX + 1 == fieldSize)
            posX = 0
        else
            posX++
    }
    fun moveLeft(){
        if( posX == 0)
            posX = fieldSize-1
        else
            posX--
    }
    fun moveUp(){
        if( posY == 0)
            posY = fieldSize-1
        else
            posY--
    }
    fun moveDown(){
        if( posY + 1 == fieldSize)
            posY = 0
        else
            posY++
    }
    override fun move(){
        lastMove = when(looking){
            Direction.DOWN -> {
                moveDown()
                Direction.DOWN
            }
            Direction.UP ->{
                moveUp()
                Direction.UP
            }
            Direction.LEFT ->{
                moveLeft()
                Direction.LEFT
            }
            Direction.RIGHT ->{
                moveRight()
                Direction.RIGHT
            }
        }
    }

    fun gonnaDie(): Boolean{
        return when(looking){
            Direction.UP -> posY == 0
            Direction.DOWN -> posY + 1 == fieldSize
            Direction.RIGHT -> posX + 1 == fieldSize
            Direction.LEFT -> posX == 0
        }
    }

}