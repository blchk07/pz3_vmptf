package com.example.pz3_vmptf

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var level1Button: Button
    private lateinit var level2Button: Button
    private lateinit var level3Button: Button
    private lateinit var level4Button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        level1Button = findViewById(R.id.level1Button)
        level2Button = findViewById(R.id.level2Button)
        level3Button = findViewById(R.id.level3Button)
        level4Button = findViewById(R.id.level4Button)

        level1Button.setOnClickListener {
            val intent = Intent(this, Level1Activity::class.java)
            startActivity(intent)
        }

        level2Button.setOnClickListener {
            val intent = Intent(this, Level2Activity::class.java)
            startActivity(intent)
        }

        level3Button.setOnClickListener {
            val intent = Intent(this, Level3Activity::class.java)
            startActivity(intent)
        }

        level4Button.setOnClickListener {
            val intent = Intent(this, Level4Activity::class.java)
            startActivity(intent)
        }
    }
}