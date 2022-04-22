package com.bme.aut.kotlin.snake.snake

import com.bme.aut.kotlin.snake.Field

class Snake (val fieldSize: Int){
    private val head: Head = Head(fieldSize, 3, 1)
    private var snake: ArrayList<Body> = ArrayList()

    init{
        snake.add(head)
        snake.add(Body( snake[0],2, 1))
        snake.add(Body(snake[1],1, 1))
    }

    fun lookRight(){
        if(head.lastMove != Direction.LEFT){
            head.looking = Direction.RIGHT
        }
    }
    fun lookLeft(){
        if(head.lastMove != Direction.RIGHT){
            head.looking = Direction.LEFT
        }
    }
    fun lookUp(){
        if(head.lastMove != Direction.DOWN){
            head.looking = Direction.UP
        }
    }
    fun lookDown(){
        if(head.lastMove != Direction.UP){
            head.looking = Direction.DOWN
        }
    }

    fun move(){
        for (bodyPart in snake.reversed()){
            bodyPart.move()
        }
    }

    fun getBodypart(column: Int, row: Int): Body?{
        for(body in snake){
            if (body.posX == column && body.posY == row)
                return body
        }
        return null
    }

}