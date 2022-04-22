package com.bme.aut.kotlin.snake.snake

import com.bme.aut.kotlin.snake.eatables.Drawable
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Paint

open class Body(
    val bodyAfore: Body?,
    var posX: Int,
    var posY: Int,
    val RGB_R: Int = 50,
    val RGB_G: Int = 255,
    val RGB_B: Int = 0
    ) : Drawable
{

    open fun move(){
        posX = bodyAfore!!.posX
        posY = bodyAfore.posY
    }

    override fun draw(graphicsContext: GraphicsContext, x: Int, y: Int) {
        graphicsContext.fill = Paint.valueOf("rgb(${RGB_R}, ${RGB_G}, ${RGB_B})")
        graphicsContext.fillOval(x.toDouble(), y.toDouble(), 32.0, 32.0)
    }
}