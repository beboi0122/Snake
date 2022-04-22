package com.bme.aut.kotlin.snake.snake

class Head (val fieldSize: Int ,posX: Int, posY: Int) : Body(null, posX, posY,15, 80, 0) {
    var looking: Direction = Direction.RIGHT
    var lastMove: Direction = Direction.RIGHT

    fun moveRight(){
        if( posX + 1 == fieldSize)
            println("DEAD")
        else
            posX++
    }
    fun moveLeft(){
        if( posX == 0)
            println("DEAD")
        else
            posX--
    }
    fun moveUp(){
        if( posY == 0)
            println("DEAD")
        else
            posY--
    }
    fun moveDown(){
        if( posY + 1 == fieldSize)
            println("DEAD")
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


}