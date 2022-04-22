package com.bme.aut.kotlin.snake

import com.bme.aut.kotlin.snake.snake.Body
import com.bme.aut.kotlin.snake.snake.Snake
import javafx.scene.canvas.GraphicsContext

class Field (val graphicsContext: GraphicsContext, val size: Int) {
    val field : Array<Array<Cell?>> = Array(size){Array(size) {null} }
    val snake: Snake = Snake(size)

    init {
        for(column in 0 until size){
            for(row in 0 until size){
                if((column + row) % 2 == 0)
                    field[column][row] = Cell(graphicsContext, true,column*32, row*32)
                else
                    field[column][row] = Cell(graphicsContext, false,column*32, row*32)
            }
        }
    }

    fun draw(){
        for(column in 0 until size){
            for(row in 0 until size){
                field[column][row]!!.draw(snake.getBodypart(column, row))
            }
        }
    }
}