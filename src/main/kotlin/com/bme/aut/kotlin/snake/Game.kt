package com.bme.aut.kotlin.snake

import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.input.KeyCode
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.stage.Stage

class Game : Application() {
    private lateinit var field: Field
    private var fieldSize = 16
    private var loop = false
    private var  edge = true

    private var WIDTH = fieldSize * 32
    private var HEIGHT = fieldSize * 32

    private lateinit var mainScene: Scene
    private lateinit var graphicsContext: GraphicsContext
    private val timer: Timer = Timer()
    private var gameStopped = false

    private var lastFrameTime: Long = System.nanoTime()

    // use a set so duplicates are not possible
    private val currentlyActiveKeys = mutableSetOf<KeyCode>()

    private lateinit var menuScene: Scene

    private lateinit var stage: Stage

    override fun start(mainStage: Stage) {
        stage = mainStage
        setupMenuScene()
        setupGameScene()

        mainStage.scene = menuScene
        mainStage.show()

    }

    private fun prepareActionHandlers() {
        mainScene.onKeyPressed = EventHandler { event ->
            currentlyActiveKeys.add(event.code)
        }
        mainScene.onKeyReleased = EventHandler { event ->
            currentlyActiveKeys.remove(event.code)
            if(event.code == KeyCode.SPACE){
                gameStopped = false
                stage.scene = menuScene
                stage.show()
                timer.stop()
            }
        }
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

        updateSnakeDirection()

        if(timeNano >= 200_000_000 - (200_000_000*(field.snake.length*0.03))){
            field.snake.move()
            timeNano = 0
        }

        if(field.snake.head.dead && loop){
            field=Field(graphicsContext, fieldSize)
            field.snake.edge = edge
        }else if(field.snake.head.dead && !loop){
            gameStopped = true
            graphicsContext.fill = Color.BLUE
            graphicsContext.font = Font.font("Serif",fieldSize*3.2)
            graphicsContext.fillText("GAME OVER", fieldSize*7.5, fieldSize*12.5)
            graphicsContext.font = Font.font("Serif",fieldSize*2.0)
            graphicsContext.fillText("Press spacebar to return to menu", fieldSize*3.75, fieldSize*21.9)
        }


        // display crude fps counter
        val elapsedMs = elapsedNanos / 1_000_000
        if (elapsedMs != 0L) {
            graphicsContext.fill = Color.WHITE
            graphicsContext.font = Font.font("Serif",10.0)
            graphicsContext.fillText("${1000 / elapsedMs} fps", 10.0, 10.0)
        }
    }

    private fun updateSnakeDirection() {
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



    fun setupMenuScene(){

        val label = Label("Size of the field:")
        label.font = Font.font("Serif", 20.0)


        val comboBox = ComboBox<Int>()
        for (num in 6..32)
            comboBox.items.add(num)
        comboBox.style = "-fx-font: 15px \"Serif\";"
        comboBox.value = 16
        comboBox.onAction = EventHandler {
            WIDTH = comboBox.value * 32
            HEIGHT = comboBox.value * 32
        }

        val startButton = Button()
        startButton.text = "Start Game"
        startButton.prefHeight(30.0)
        startButton.prefHeight(100.0)
        startButton.font = Font.font ("Serif", 30.0)
        startButton.onAction = EventHandler{
            fieldSize = comboBox.value
            setupGameScene()
            prepareActionHandlers()
            stage.scene = mainScene
            stage.show()
            field=Field(graphicsContext, fieldSize)
            field.snake.edge = edge
            timer.start()
        }

        val cbLoop = CheckBox("Loop the game")
        cbLoop.font = Font.font ("Serif", 15.0)
        val cbEdge = CheckBox("Edge of field")
        cbEdge.font = Font.font ("Serif", 15.0)
        cbEdge.isSelected = true

        cbLoop.onAction = EventHandler {
            loop = cbLoop.isSelected
        }
        cbEdge.onAction = EventHandler {
            edge = cbEdge.isSelected
        }

        val hbox = HBox(20.0)
        hbox.children.addAll(cbLoop, cbEdge)
        hbox.alignment = Pos.CENTER


        val vbox = VBox(20.0)
        vbox.children.addAll(label, comboBox, startButton, hbox)
        vbox.alignment = Pos.CENTER;

        menuScene = Scene(vbox, 512.0, 512.0)
    }

    fun setupGameScene(){
        val root = Group()

        val canvas = Canvas(WIDTH.toDouble(), HEIGHT.toDouble())
        root.children.add(canvas)

        graphicsContext = canvas.graphicsContext2D

        mainScene = Scene(root)
    }

    private inner class Timer: AnimationTimer() {
        override fun handle(currentNanoTime: Long) {
            tickAndRender(currentNanoTime)
        }
    }
}
