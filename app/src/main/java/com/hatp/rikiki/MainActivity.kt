package com.hatp.rikiki

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.text.isDigitsOnly
import java.io.File

const val EXTRA_MESSAGE = "com.hatp.rikiki.MESSAGE"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        createAndVerifyStorageFiles()
    }

    fun sendMessage(view: View){

        val alertMessenger = AlertDialog.Builder(this@MainActivity)

        val editText = findViewById<EditText>(R.id.editText)
        val numOfPlayer = editText.text.toString()



        if (!numOfPlayer.isDigitsOnly()) {
            alertMessenger.setMessage("Please pick a number.")

            alertMessenger.setNeutralButton("Ok"){_, _ ->
                Toast.makeText(applicationContext, "Try again", Toast.LENGTH_SHORT).show()}
            val alertDialog: AlertDialog = alertMessenger.create()
            alertDialog.show()
        } else if (!(2 <= (numOfPlayer.toInt()) && (numOfPlayer.toInt()) <= 52)) {
            alertMessenger.setMessage("The number must be between 2 and 52.")

            alertMessenger.setNeutralButton("Ok"){_, _ ->
                Toast.makeText(applicationContext, "Try again", Toast.LENGTH_SHORT).show()}
            val alertDialog: AlertDialog = alertMessenger.create()
            alertDialog.show()
        } else if (numOfPlayer.toInt() > 6) {
            alertMessenger.setMessage("The game should be played with less that 6 players, " +
                    "otherwise the fun will drastically decrease. Do you want to use a new number ?")

            alertMessenger.setNegativeButton("Continue"){_, _ ->
                Toast.makeText(applicationContext, "Good luck", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, GetSettingsActivity::class.java).apply {
                    putExtra(EXTRA_MESSAGE, numOfPlayer)
                }
                startActivity(intent)
            }

            alertMessenger.setPositiveButton("New number") {_, _ ->
                Toast.makeText(applicationContext, "Try again", Toast.LENGTH_SHORT).show()
            }
            val alertDialog: AlertDialog = alertMessenger.create()
            alertDialog.show()
        } else {
            val intent = Intent(this, GetSettingsActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, numOfPlayer)
            }
            startActivity(intent)
        }
    }

    fun createAndVerifyStorageFiles() {

        val appFileRoot: String = this.applicationInfo.dataDir

        // scores
        val scoresFileName = "$appFileRoot/scores.csv"
        val scoresFile = File(scoresFileName)

        val scoresFileCreated : Boolean = scoresFile.createNewFile()

        // players
        val playersFileName = "$appFileRoot/players.csv"
        val playersFile = File(playersFileName)

        val playersFileCreated : Boolean = playersFile.createNewFile()
    }

    fun quitApp(view: View) {
        finish()
        System.exit(0)
    }

}