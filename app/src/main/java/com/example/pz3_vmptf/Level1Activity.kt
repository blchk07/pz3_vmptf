package com.example.pz3_vmptf

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Level1Activity : AppCompatActivity() {

    private lateinit var messageTextView: TextView
    private lateinit var showMessageButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContentView(R.layout.activity_level1)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.level1Main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        messageTextView = findViewById(R.id.messageTextView)
        showMessageButton = findViewById(R.id.showMessageButton)

        showMessageButton.setOnClickListener {
            val message = "Вітаємо з першим додатком на Kotlin!"

            messageTextView.text = message
        }
    }
}