package com.example.activitat03

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale

class ResultActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_BMI = "extra_bmi"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_result)

        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
        ) { v, insets ->
            val systemBars =
                insets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }

        val imc = intent.getDoubleExtra(EXTRA_BMI, 0.0)
        mostrarResultado(imc)


        findViewById<View>(R.id.btnBack).setOnClickListener { finish() }
        findViewById<View>(R.id.btnRecalcular).setOnClickListener { finish() }
    }

    private fun mostrarResultado(imc: Double) {
        val tvCategoria = findViewById<TextView>(R.id.tvCategoria)
        val tvIMC = findViewById<TextView>(R.id.tvIMC)
        val tvDescripcion = findViewById<TextView>(R.id.tvDescripcion)

        val (categoria, color) = when {
            imc < 18.5 -> "Underweight" to "#FFC107"
            imc < 25.0 -> "Normal" to "#24D876"
            imc < 30.0 -> "Overweight" to "#FFC107"
            else -> "Obese" to "#EB1555"
        }

        tvIMC.text = String.format(Locale.US, "%.2f", imc)
        tvCategoria.text = categoria.uppercase()
        tvCategoria.setTextColor(Color.parseColor(color))
        tvDescripcion.text = categoria
    }
}