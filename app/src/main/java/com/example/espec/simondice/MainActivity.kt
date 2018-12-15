package com.example.espec.simondice

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    // Matriz que guardará la combinación del SimonDice
    var sentenciaCopia: ArrayList<Int> = ArrayList()
    var sentencia: MutableList<Int> = mutableListOf<Int>()
    // private var sentenciaCopia: ArrayList<Int> = ArrayList()
    // Sentencia para hacer pruebas con las courrutinas
    //  val sentencia: IntArray = intArrayOf(0,1, 2, 3)
    var ronda = 1

    var acierto = true

    // prueba
    lateinit var myJob: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Botón para inciar la aplicación
        btnIniciar.setOnClickListener {
            start()

        }

        //txtTRonda.setText((numAzar).toString())
        // Toast.makeText(getApplicationContext(), "Simon dice $MaxNum colores", Toast.LENGTH_SHORT)

    }

    // Función Start
    fun start() {


        juego()
        txtTRonda.setText(ronda.toString())
        ronda += 1


    }// Fin fun Start


    fun juego() {

        // Función Kotlin para un elegir un número al azar
        val numAzar = (0..3).shuffled().first()
        // Guardamos ese valor en la matriz que almacena las sentencias

        sentencia.add(numAzar)
        sentenciaCopia.addAll(sentencia)

        // Tiempo de retardo entre un botón y otro
        var retardo = 1000;
        myJob = GlobalScope.launch(Dispatchers.Main) {
            for (i in sentencia.indices) {

                delay(retardo.toLong())
                when ((sentencia[i])) {
                    0 -> {
                        btnRed.setBackgroundColor(Color.parseColor("#8B0000"))
                        delay(1500)
                        btnRed.setBackgroundColor(Color.parseColor("#FF0000"))
                    }//Rojo
                    1 -> {
                        btnGreen.setBackgroundColor(Color.parseColor("#556B2F"))
                        delay(1500)
                        btnGreen.setBackgroundColor(Color.parseColor("#00FF00"))
                    }//Verde
                    2 -> {
                        btnBlue.setBackgroundColor(Color.parseColor("#00008B"))
                        delay(1500)
                        btnBlue.setBackgroundColor(Color.parseColor("#0000FF"))
                    }//Azul
                    3 -> {
                        btnYellow.setBackgroundColor(Color.parseColor("#BDB76B"))
                        delay(1500)
                        btnYellow.setBackgroundColor(Color.parseColor("#FFFF00"))
                    }//Amarillo
                    else -> { // Note the block
                        print("Fallo en la creación de un número al azar")
                    }
                }
            }

            retardo += 2000

            activarBotones()
        }// Fin de la Corrutina
        // Igual meto aquí interactuar
        //  myJob.cancel()

    }// Fin fun Juego

    fun interactuar(numB: Int) {


        txtTRonda.setText("Estas en interactuar")

        if (sentenciaCopia.get(0) == numB) {
            sentenciaCopia.removeAt(0)

            if (sentenciaCopia.isEmpty()) {
                toast("Acertaste Seguimos")
                start()
            }
        } else {
            acierto = false
            toast("Perdiste")
            reiniciar()

        }


    }// Fin fun Interactuar

    fun activarBotones() {
        txtTRonda.setText("Estas en activarBotones")
        btnRed.setOnClickListener() {
            interactuar(0)
        }

        btnGreen.setOnClickListener() {
            interactuar(1)
        }

        btnBlue.setOnClickListener() {
            interactuar(2)
        }

        btnYellow.setOnClickListener() {
            interactuar(3)
        }

    }// Fin fun activarBotones


    fun reiniciar() {
        myJob.cancel()
        ronda = 0
        acierto = true;
        sentencia.clear()
        sentenciaCopia.clear()
        txtTRonda.setText("Reiniciar")
    }// Fin Fun reiniciar

}// Fin Main
