package com.example.espec.simondice

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    // Matriz que guardará la combinación del SimonDice
    var sentencia: MutableList<Int> = mutableListOf<Int>()
    // Matriz copia de la combinación para hacer comprobaciones de aciertos
    var sentenciaCopia: ArrayList<Int> = ArrayList()

    // Sentencia para hacer pruebas con las courrutinas
    //  val sentencia: IntArray = intArrayOf(0,1, 2, 3)
    // Saber en la ronda en la que se está
    var ronda = 1

    // Inicialización de un objeto Job, para luego cancelar la corrutina
    lateinit var myJob: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Botón para inciar la aplicación
        btnIniciar.setOnClickListener {
            start()

        }

    }

    // Función Start
    fun start() {

        // Llama a la función juego que crea las secuencias
        juego()
        // Inserta un String en el TextView del Layout
        txtTRonda.setText(ronda.toString())
        // Suma 1 a la ronda, por si acierta la secuencia muestra la ronda en la que te encuentras
        ronda += 1


    }// Fin fun Start


    fun juego() {

        // Función Kotlin para un elegir un número al azar
        val numAzar = (0..3).shuffled().first()
        // Guardamos ese valor en la matriz que almacena las sentencias
        sentencia.add(numAzar)
        // Hacemos una copia del ArrayList, para hacer la comprobaciones de aciertos
        sentenciaCopia.addAll(sentencia)

        // Variable que asigna el tiempo de retardo entre el parpadeo de un botón y otro
        var retardo = 1000
        // Variable que asignará el tiempo que tardá en cambiar de color el botón
        var parpadeoColor = 1000L

        // Inicio de la corrutina igualada al objeto Job, para luego poder cancelar esta corrutina
        myJob = GlobalScope.launch(Dispatchers.Main) {

            // Bucle que recorre el ArrayList
            for (i in sentencia.indices) {

                // Paro la corrutina entre secuenciás
                delay(retardo.toLong())
                // Dependiendo del número generado cambiara el color de uno de los botones
                when ((sentencia[i])) {
                    0 -> {
                        // Cambios del fondo del Botón
                        btnRed.setBackgroundColor(Color.parseColor("#8B0000"))
                        // Tiempo que tarda en el parpadeo
                        delay(parpadeoColor)
                        btnRed.setBackgroundColor(Color.parseColor("#FF0000"))
                    }//Rojo
                    1 -> {
                        btnGreen.setBackgroundColor(Color.parseColor("#556B2F"))
                        delay(parpadeoColor)
                        btnGreen.setBackgroundColor(Color.parseColor("#00FF00"))
                    }//Verde
                    2 -> {
                        btnBlue.setBackgroundColor(Color.parseColor("#00008B"))
                        delay(parpadeoColor)
                        btnBlue.setBackgroundColor(Color.parseColor("#0000FF"))
                    }//Azul
                    3 -> {
                        btnYellow.setBackgroundColor(Color.parseColor("#BDB76B"))
                        delay(parpadeoColor)
                        btnYellow.setBackgroundColor(Color.parseColor("#FFFF00"))
                    }//Amarillo
                    else -> { // Note the block
                        print("Fallo en la creación de un número al azar")
                    }
                }
            }
            // Aumenta la variable  de retardo entre el parpadeo de un botón y otro
            retardo += 2000

            // Llamada a la función activar botones, que habilita la función para poder clickar en ellos
            activarBotones()
        }// Fin de la Corrutina

    }// Fin fun Juego

    // Función para que el jugador pueda jugar
    fun activarBotones() {
        // setText para modo debug para saber en que parte del programa estás, en cada momento
        //txtTRonda.setText("Estas en activarBotones")

        // Mensaje para indicar al jugador que ya puede pulsar los botones
        toast("Te toca jugar")

        // Llamadas al evento OnClickListener de los botones del layout
        btnRed.setOnClickListener() {
            // Llamada a la función interactuar en el cuál se le pasa un Int como parametro para diferenciar cada botón
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

    // Función que recibe un Int que identifica cada botón
    fun interactuar(numB: Int) {

        // setText para modo debug para saber en que parte del programa estás, en cada momento
        //txtTRonda.setText("Estas en interactuar")

        // Condición si la primera posicón del ArrayList es correcta, la remueve para que si la lista sigue teniendo objetos vaya analizando los siguientes.
        if (sentenciaCopia.get(0) == numB) {
            sentenciaCopia.removeAt(0)

            // Si el Array llega a estar vacío, quiere decir que has acertado la secuencia de manera que saltar un mensaje para seguir y llamará a la función start para seguir con el programa
            if (sentenciaCopia.isEmpty()) {
                toast("Acertaste Seguimos")
                start()
            }
            // Si falla en cualquier posición, el programa mostrará un mensaje de que has pedido y llamara a la función reiniciar
        } else {

            toast("Perdiste")
            reiniciar()

        }


    }// Fin fun Interactuar

    // Función que reincia el programa, deja todas las variables a los valores por defecto, cancela la corrutina y limpia los ArrayList
    fun reiniciar() {

        // setText para modo debug para saber en que parte del programa estás, en cada momento
        //txtTRonda.setText("Estas en Reiniciar")
        
        myJob.cancel()
        ronda = 1
        sentencia.clear()
        sentenciaCopia.clear()
        toast("Reiniciar")
    }// Fin Fun reiniciar

}// Fin Main
