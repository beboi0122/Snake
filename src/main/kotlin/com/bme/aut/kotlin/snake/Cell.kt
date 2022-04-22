package com.bme.aut.kotlin.snake

import com.bme.aut.kotlin.snake.eatables.Drawable
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Paint


class Cell(
    val graphicsContext: GraphicsContext,
    val orange: Boolean,
    val x : Int,
    val y : Int
    ) {


    fun draw(drawable: Drawable? = null){
        graphicsContext.fill = if(orange) Paint.valueOf("rgb(255, 153, 0)") else Paint.valueOf("rgb(255, 225, 170)")
        graphicsContext.fillRect(x.toDouble(), y.toDouble(), 32.0, 32.0)
        drawable?.draw(graphicsContext, x, y)

    }

}