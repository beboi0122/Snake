package com.bme.aut.kotlin.snake.eatables

import com.example.getResource
import javafx.scene.image.Image
import javafx.scene.canvas.GraphicsContext

class Apple : Drawable {
    var eaten: Boolean = false
    private companion object{
        val apple: Image = Image(getResource("/apple.png"))
    }
    override fun draw(graphicsContext: GraphicsContext, x: Int, y: Int) {
        graphicsContext.drawImage(apple, x.toDouble(), y.toDouble(), 32.0, 32.0)
    }
}