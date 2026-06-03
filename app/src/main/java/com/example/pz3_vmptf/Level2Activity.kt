package com.example.pz3_vmptf

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Level2Activity : AppCompatActivity() {

    private lateinit var taskEditText: EditText
    private lateinit var addTaskButton: Button
    private lateinit var tasksContainer: LinearLayout
    private lateinit var taskInfoTextView: TextView

    private var taskCount = 0
    private var completedTaskCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContentView(R.layout.activity_level2)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.level2Main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        taskEditText = findViewById(R.id.taskEditText)
        addTaskButton = findViewById(R.id.addTaskButton)
        tasksContainer = findViewById(R.id.tasksContainer)
        taskInfoTextView = findViewById(R.id.taskInfoTextView)

        addTaskButton.setOnClickListener {
            addTask()
        }

        updateTaskInfo()
    }

    private fun addTask() {
        val taskText = taskEditText.text.toString().trim()

        if (taskText.isEmpty()) {
            Toast.makeText(this, "Введіть текст завдання", Toast.LENGTH_SHORT).show()
            return
        }

        val checkBox = CheckBox(this)
        checkBox.text = taskText
        checkBox.textSize = 18f
        checkBox.setPadding(0, 8, 0, 8)

        checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                completedTaskCount++
                Toast.makeText(this, "Завдання виконано", Toast.LENGTH_SHORT).show()
            } else {
                completedTaskCount--
                Toast.makeText(this, "Завдання знову активне", Toast.LENGTH_SHORT).show()
            }

            updateTaskInfo()
        }

        tasksContainer.addView(checkBox)

        taskCount++
        taskEditText.text.clear()

        updateTaskInfo()

        Toast.makeText(this, "Завдання додано", Toast.LENGTH_SHORT).show()
    }

    private fun updateTaskInfo() {
        if (taskCount == 0) {
            taskInfoTextView.text = "Список завдань порожній"
        } else {
            taskInfoTextView.text = "Усього завдань: $taskCount | Виконано: $completedTaskCount"
        }
    }
}