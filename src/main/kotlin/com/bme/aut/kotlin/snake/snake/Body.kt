package com.bme.aut.kotlin.snake.snake

open class Body(
    val bodyAfore: Body?,
    var posX: Int,
    var posY: Int,
    val RGB_R: Int = 50,
    val RGB_G: Int = 255,
    val RGB_B: Int = 0
    ){

    open fun move(){
        posX = bodyAfore!!.posX
        posY = bodyAfore.posY
    }
}