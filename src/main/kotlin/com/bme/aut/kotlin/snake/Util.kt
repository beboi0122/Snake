package com.example

import com.bme.aut.kotlin.snake.Game

fun getResource(filename: String): String {
    return Game::class.java.getResource(filename).toString()
}
