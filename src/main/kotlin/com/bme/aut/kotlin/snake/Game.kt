package com.bme.aut.kotlin.snake

import com.example.getResource
import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.event.EventHandler
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.image.Image
import javafx.scene.input.KeyCode
import javafx.scene.paint.Color
import javafx.scene.paint.Paint
import javafx.stage.Stage

class Game : Application() {
    private lateinit var field: Field
    val fieldSize = 16

    private val WIDTH = fieldSize * 32
    private val HEIGHT = fieldSize * 32

    private lateinit var mainScene: Scene
    private lateinit var graphicsContext: GraphicsContext

    private lateinit var space: Image
    private lateinit var sun: Image

    private var sunX = WIDTH / 3
    private var sunY = HEIGHT / 3

    private var lastFrameTime: Long = System.nanoTime()

    // use a set so duplicates are not possible
    private val currentlyActiveKeys = mutableSetOf<KeyCode>()

    override fun start(mainStage: Stage) {
        mainStage.title = "Event Handling"

        val root = Group()
        mainScene = Scene(root)
        mainStage.scene = mainScene

        val canvas = Canvas(WIDTH.toDouble(), HEIGHT.toDouble())
        root.children.add(canvas)

        prepareActionHandlers()

        graphicsContext = canvas.graphicsContext2D

        field = Field(graphicsContext, fieldSize)

        loadGraphics()

        // Main loop
        object : AnimationTimer() {
            override fun handle(currentNanoTime: Long) {
                tickAndRender(currentNanoTime)
            }
        }.start()

        mainStage.show()
    }

    private fun prepareActionHandlers() {
        mainScene.onKeyPressed = EventHandler { event ->
            currentlyActiveKeys.add(event.code)
        }
        mainScene.onKeyReleased = EventHandler { event ->
            currentlyActiveKeys.remove(event.code)
        }
    }

    private fun loadGraphics() {
        sun = Image(getResource("/sun.png"))
    }
    var timeNano: Long = 0
    private fun tickAndRender(currentNanoTime: Long) {
        // the time elapsed since the last frame, in nanoseconds
        // can be used for physics calculation, etc
        val elapsedNanos = currentNanoTime - lastFrameTime
        lastFrameTime = currentNanoTime
        timeNano += elapsedNanos

        // clear canvas
        graphicsContext.clearRect(0.0, 0.0, WIDTH.toDouble(), HEIGHT.toDouble())

        field.draw()

        updateSunPosition()

        if(timeNano >= 100_000_000){
            field.snake.move()
            timeNano = 0
        }


        // display crude fps counter
        val elapsedMs = elapsedNanos / 1_000_000
        if (elapsedMs != 0L) {
            graphicsContext.fill = Color.WHITE
            graphicsContext.fillText("${1000 / elapsedMs} fps", 10.0, 10.0)
        }
    }

    private fun updateSunPosition() {
        if (currentlyActiveKeys.contains(KeyCode.LEFT)) {
            field.snake.lookLeft()
        }
        if (currentlyActiveKeys.contains(KeyCode.RIGHT)) {
            field.snake.lookRight()
        }
        if (currentlyActiveKeys.contains(KeyCode.UP)) {
            field.snake.lookUp()
        }
        if (currentlyActiveKeys.contains(KeyCode.DOWN)) {
            field.snake.lookDown()
        }
    }

}
