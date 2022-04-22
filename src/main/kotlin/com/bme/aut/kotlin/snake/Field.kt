package com.bme.aut.kotlin.snake

import com.bme.aut.kotlin.snake.eatables.Apple
import com.bme.aut.kotlin.snake.snake.Body
import com.bme.aut.kotlin.snake.snake.Snake
import com.sun.prism.image.Coords
import javafx.scene.canvas.GraphicsContext
import java.awt.Point
import java.util.*

@Suppress("UNUSED_VALUE")
class Field (val graphicsContext: GraphicsContext, val size: Int) {
    val field : Array<Array<Cell?>> = Array(size){Array(size) {null} }
    val snake: Snake = Snake(size)
    val apple: Apple = Apple()
    var applePoint: Point = setPosition()

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
                if(snake.head.posX == applePoint.x && snake.head.posY == applePoint.y){
                    applePoint = setPosition()
                    snake.incrementSize()
                }
                if(column == applePoint.x && row == applePoint.y)
                    field[column][row]!!.draw(apple)
            }
        }
    }

    fun setPosition(): Point{
        var point = Point((0 until size).random(), (0 until size).random())
        while (snake.getBodypart(point.x, point.y) != null){
            point = Point((0 until size).random(), (0 until size).random())
        }
        return point
    }
}