package com.example.pz3_vmptf

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class Level3Activity : AppCompatActivity() {

    private lateinit var loadDataButton: Button
    private lateinit var statusTextView: TextView
    private lateinit var resultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContentView(R.layout.activity_level3)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.level3Main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        loadDataButton = findViewById(R.id.loadDataButton)
        statusTextView = findViewById(R.id.statusTextView)
        resultTextView = findViewById(R.id.resultTextView)

        loadDataButton.setOnClickListener {
            loadKharkivAlarmStatus()
        }
    }

    private fun loadKharkivAlarmStatus() {
        statusTextView.text = "Завантаження даних..."
        resultTextView.text = ""
        loadDataButton.isEnabled = false

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.getAlarmStatuses()

                val kharkivRegion = response.states["Харківська область"]

                if (kharkivRegion == null) {
                    statusTextView.text = "Харківську область не знайдено"
                    return@launch
                }

                val activeDistricts = kharkivRegion.districts.filter { district ->
                    district.value.enabled
                }

                val hasAlarm = activeDistricts.isNotEmpty()

                statusTextView.text = if (hasAlarm) {
                    "В деяких районах області є повітряна тривога"
                } else {
                    "Тривога по Харківській області не активна"
                }

                resultTextView.text = buildKharkivResultText(
                    activeDistricts = activeDistricts
                )

                Toast.makeText(
                    this@Level3Activity,
                    "Дані успішно отримано",
                    Toast.LENGTH_SHORT
                ).show()
            } catch (exception: Exception) {
                statusTextView.text = "Помилка під час отримання даних"
                resultTextView.text = exception.message ?: "Невідома помилка"

                Toast.makeText(
                    this@Level3Activity,
                    "Не вдалося отримати дані",
                    Toast.LENGTH_SHORT
                ).show()
            } finally {
                loadDataButton.isEnabled = true
            }
        }
    }

    private fun buildKharkivResultText(
        activeDistricts: Map<String, DistrictStatus>
    ): String {
        val builder = StringBuilder()

        builder.append("Харківська область\n\n")

        if (activeDistricts.isEmpty()) {
            builder.append("Активних тривог по районах немає.")
        } else {
            builder.append("Активні тривоги по районах:\n\n")

            activeDistricts.forEach { district ->
                val districtName = district.key
                val districtStatus = district.value

                builder.append("• $districtName\n")

                if (districtStatus.enabled_at != null) {
                    builder.append("  Увімкнено: ${districtStatus.enabled_at}\n")
                }

                if (districtStatus.disabled_at != null) {
                    builder.append("  Вимкнено: ${districtStatus.disabled_at}\n")
                }

                builder.append("\n")
            }
        }

        return builder.toString()
    }
}