package com.example.tic_tac_toe

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import java.util.*
import com.example.tic_tac_toe.databinding.ActivityMainBinding
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var originalButtonColors = mutableMapOf<Button, Int>()
    private var gameOver = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val resetButton = binding.resetButton
        resetButton.setOnClickListener { resetGame() }

        val buttons = arrayOf(
            binding.button1, binding.button2, binding.button3,
            binding.button4, binding.button5, binding.button6,
            binding.button7, binding.button8, binding.button9
        )

        for (button in buttons) {
            originalButtonColors[button] = button.backgroundTintList?.defaultColor ?: Color.WHITE
        }



    }

    fun buClick(view : View){
        if (!gameOver) {
            val buSelected = view as Button
            var cellId = 0
            when(buSelected.id){
                R.id.button1 -> cellId=1
                R.id.button2 -> cellId=2
                R.id.button3 -> cellId=3
                R.id.button4 -> cellId=4
                R.id.button5 -> cellId=5
                R.id.button6 -> cellId=6
                R.id.button7 -> cellId=7
                R.id.button8 -> cellId=8
                R.id.button9 -> cellId=9
            }
            playGame(cellId, buSelected)
        }
    }

    var player1 = ArrayList<Int>()
    var player2 = ArrayList<Int>()
    var activeplayer = 1

    private fun playGame(cellId : Int , buSelected : Button){

        if (!gameOver) {
            if (activeplayer == 1) {
                buSelected.text = "X"
                buSelected.setBackgroundColor(Color.parseColor("#009193"))
                player1.add(cellId)
                activeplayer = 2
                AutoPlay()
            } else {
                buSelected.text = "O"
                buSelected.setBackgroundColor(Color.parseColor("#FF9300"))
                player2.add(cellId)
                activeplayer = 1
            }

            buSelected.isEnabled = false
            checkWinner()
        }
    }

    private fun checkWinner(){
        var winner = -1
        //row1
        if(player1.contains(1) && player1.contains(2) && player1.contains(3)) {
            winner=1
        }
        if(player2.contains(1) && player2.contains(2) && player2.contains(3)) {
            winner=2
        }

        //row2
        if(player1.contains(4) && player1.contains(5) && player1.contains(6)) {
            winner=1
        }
        if(player2.contains(4) && player2.contains(5) && player2.contains(6)) {
            winner=2
        }

        //row3
        if(player1.contains(7) && player1.contains(8) && player1.contains(9)) {
            winner=1
        }
        if(player2.contains(7) && player2.contains(8) && player2.contains(9)) {
            winner=2
        }

        //col1
        if(player1.contains(1) && player1.contains(4) && player1.contains(7)) {
            winner=1
        }
        if(player2.contains(1) && player2.contains(4) && player2.contains(7)) {
            winner=2
        }

        //col2
        if(player1.contains(2) && player1.contains(5) && player1.contains(8)) {
            winner=1
        }
        if(player2.contains(2) && player2.contains(5) && player2.contains(8)) {
            winner=2
        }

        //col3
        if(player1.contains(3) && player1.contains(6) && player1.contains(9)) {
            winner=1
        }
        if(player2.contains(3) && player2.contains(6) && player2.contains(9)) {
            winner=2
        }

        //diagonal1
        if(player1.contains(1) && player1.contains(5) && player1.contains(9)) {
            winner=1
        }
        if(player2.contains(1) && player2.contains(5) && player2.contains(9)) {
            winner=2
        }

        //diagonal2
        if(player1.contains(3) && player1.contains(5) && player1.contains(7)) {
            winner=1
        }
        if(player2.contains(3) && player2.contains(5) && player2.contains(7)) {
            winner=2
        }

        // Check for a tie game
        if (winner == -1 && player1.size + player2.size == 9) {
            winner = 0 // Indicates a tie game
        }

        if (winner != -1) {
            gameOver = true // Set gameOver to true when a winner is determined
            if (winner == 0) {
                Toast.makeText(this, getString(R.string.tie_game), Toast.LENGTH_LONG).show()
            } else if (winner == 1) {
                Toast.makeText(this, getString(R.string.player1_wins), Toast.LENGTH_LONG).show()
            } else if (winner == 2) {
                Toast.makeText(this, getString(R.string.player2_wins), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun AutoPlay() {
        if (!gameOver) {
            try {
                // Your existing AutoPlay logic here

                var emptyCells = ArrayList<Int>()
                for (cellId in 1..9) {
                    if (!(player1.contains(cellId) || player2.contains(cellId))) {
                        emptyCells.add(cellId)
                    }
                }
                val r = Random()
                val randIndex = r.nextInt(emptyCells.size)
                val cellId = emptyCells[randIndex]

                var buSelected: Button
                when (cellId) {
                    1 -> buSelected = binding.button1
                    2 -> buSelected = binding.button2
                    3 -> buSelected = binding.button3
                    4 -> buSelected = binding.button4
                    5 -> buSelected = binding.button5
                    6 -> buSelected = binding.button6
                    7 -> buSelected = binding.button7
                    8 -> buSelected = binding.button8
                    9 -> buSelected = binding.button9
                    else -> buSelected = binding.button1
                }
                playGame(cellId, buSelected)
            } catch (e: Exception) {
                e.printStackTrace()
                // Log the exception to identify the issue
            }
        }
    }

    private fun resetGame() {
        // Clear player moves and enable all buttons
        player1.clear()
        player2.clear()

        for (button in originalButtonColors.keys) {
            button.text = ""
            button.isEnabled = true
            button.setBackgroundColor(originalButtonColors[button] ?: Color.WHITE)
        }

        // Reset active player to Player 1
        activeplayer = 1

        // Reset the game state
        gameOver = false
    }
}

