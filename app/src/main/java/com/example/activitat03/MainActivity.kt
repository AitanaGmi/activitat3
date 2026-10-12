package com.example.activitat03

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.card.MaterialCardView
import com.google.android.material.slider.Slider

class MainActivity : AppCompatActivity() {

    private var isMaleSelected = true

    private lateinit var cardHome: MaterialCardView
    private lateinit var cardDona: MaterialCardView
    private lateinit var barraAltura: TextView
    private lateinit var sliderAltura: Slider
    private lateinit var textoPeso: TextView
    private lateinit var textoEdad: TextView

    private var peso = 60
    private var edad = 16

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_main)

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

        initComponent()
        initUI()
        initListener()
    }

    private fun initComponent() {
        cardHome = findViewById(R.id.seleccio_home)
        cardDona = findViewById(R.id.seleccio_dona)

        barraAltura = findViewById(R.id.BarraAltura)
        sliderAltura = findViewById(R.id.rsAltura)

        textoPeso = findViewById(R.id.valorPeso)
        textoEdad = findViewById(R.id.valorEdad)
    }

    private fun initUI() {
        peso = 60
        edad = 16

        textoPeso.text = peso.toString()
        textoEdad.text = edad.toString()

        sliderAltura.valueFrom = 120f
        sliderAltura.valueTo = 220f
        sliderAltura.stepSize = 1f
        sliderAltura.value = 160f

        // La unidad "cm" ya está en el layout, aquí solo va el número
        barraAltura.text = "160"

        setGenderColor()
    }

    private fun initListener() {

        cardHome.setOnClickListener {
            isMaleSelected = true
            setGenderColor()
        }

        cardDona.setOnClickListener {
            isMaleSelected = false
            setGenderColor()
        }

        sliderAltura.addOnChangeListener { _, value, _ ->
            barraAltura.text = value.toInt().toString()
        }

        findViewById<View>(R.id.SumarPeso).setOnClickListener {
            peso++
            textoPeso.text = peso.toString()
        }

        findViewById<View>(R.id.RestarPeso).setOnClickListener {
            if (peso > 1) {
                peso--
                textoPeso.text = peso.toString()
            }
        }

        findViewById<View>(R.id.SumarEdad).setOnClickListener {
            edad++
            textoEdad.text = edad.toString()
        }

        findViewById<View>(R.id.ResarEdad).setOnClickListener {
            if (edad > 1) {
                edad--
                textoEdad.text = edad.toString()
            }
        }

        // CALCULATE -> abre la pantalla de resultados
        findViewById<View>(R.id.btnCalcular).setOnClickListener {
            calcularIMC()
        }
    }

    // IMC = peso (kg) / altura (m)^2
    private fun calcularIMC() {
        val alturaMetros = sliderAltura.value / 100.0
        val imc = peso / (alturaMetros * alturaMetros)

        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra(ResultActivity.EXTRA_BMI, imc)
        startActivity(intent)
    }

    private fun setGenderColor() {
        cardHome.setCardBackgroundColor(
            getBackgroundColor(isMaleSelected)
        )

        cardDona.setCardBackgroundColor(
            getBackgroundColor(!isMaleSelected)
        )
    }

    private fun getBackgroundColor(isSelectedComponent: Boolean): Int {
        val colorReference = if (isSelectedComponent) {
            R.color.background_componet_selected
        } else {
            R.color.background_component
        }

        return ContextCompat.getColor(this, colorReference)
    }
}