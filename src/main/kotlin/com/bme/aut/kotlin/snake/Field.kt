package com.bme.aut.kotlin.snake

import com.bme.aut.kotlin.snake.eatables.Apple
import com.bme.aut.kotlin.snake.snake.Body
import com.bme.aut.kotlin.snake.snake.Snake
import com.sun.prism.image.Coords
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import javafx.scene.paint.Paint
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import java.awt.Font.BOLD
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
                    field[column][row] = Cell(graphicsContext, true,column*32, row*32+32)
                else
                    field[column][row] = Cell(graphicsContext, false,column*32, row*32+32)
            }
        }
    }

    fun draw(){
        if(!snake.block){
            for(x in 0 until size) {
                graphicsContext.fill = Paint.valueOf("rgb(150, 150, 150)")
                graphicsContext.fillRect(x.toDouble()*32, 0.0, 32.0, 32.0)
            }
            graphicsContext.fill = Color.BLUE
            graphicsContext.font = Font.font("Serif",32.0)
            graphicsContext.fillText("Score: ${snake.length}", size*7.0, 28.0)
            graphicsContext.fillText("Level: ${(snake.length/15).toInt()+1}", size*18.0, 28.0)
        }else{
            for(x in 0 until size) {
                graphicsContext.fill = Color.RED
                graphicsContext.fillRect(x.toDouble()*32, 0.0, 32.0, 32.0)
            }
            graphicsContext.fill = Color.YELLOW
            graphicsContext.font = Font.font("Serif", FontWeight.BOLD ,size*2.0)
            graphicsContext.fillText("LEVEL UP", size*12.0, 28.0)
        }

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