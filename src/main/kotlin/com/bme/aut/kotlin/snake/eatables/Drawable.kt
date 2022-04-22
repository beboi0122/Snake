package com.bme.aut.kotlin.snake.eatables

import javafx.scene.canvas.GraphicsContext

interface Drawable {
    fun draw(graphicsContext: GraphicsContext, x : Int, y : Int)
}