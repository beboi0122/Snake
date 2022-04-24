package com.bme.aut.kotlin.snake.snake

import com.bme.aut.kotlin.snake.Field

class Snake (val fieldSize: Int){
    val head: Head = Head(fieldSize, 3, 1)
    private var snake: ArrayList<Body> = ArrayList()
    var length: Int = 3
    var edge = true
    var block = false

    init{
        snake.add(head)
        snake.add(Body( snake[0],2, 1))
        for(num in 2 until length)
            snake.add(Body(snake[num-1],snake[num-1].posX, snake[num-1].posY))
    }

    fun incrementSize(){
        snake.add(Body(snake[length-1],snake[length-1].posX, snake[length-1].posY))
        length++
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
        if(edge) {
            if (!head.gonnaDie() && !head.dead) {
                for (bodyPart in snake.reversed()) {
                    bodyPart.move()
                }
            } else
                head.dead = true
        }else if(!head.dead){
            for (bodyPart in snake.reversed()) {
                bodyPart.move()
            }
        }

        for(num in 1 until length){
            if(head.posX == snake[num].posX && head.posY == snake[num].posY)
                head.dead = true
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