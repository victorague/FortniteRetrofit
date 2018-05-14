package net.victor.fortniteretrofit.vistas

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_search.*
import net.victor.fortniteretrofit.R
import net.victor.fortniteretrofit.api.FortniteApiService
import net.victor.fortniteretrofit.model.LifeTimeStat
import org.jetbrains.anko.indeterminateProgressDialog
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*


class SearchActivity : AppCompatActivity() {

    private lateinit var statsAL: ArrayList<LifeTimeStat>

    private val fortniteApiService by lazy {
        FortniteApiService.create()

    }
    lateinit var platform: String
    lateinit var userName: String
    var platformId: Int = 0
    val myStrings = arrayOf("PS4", "PC", "XBOX")
    lateinit var puntuacion: String
    lateinit var partidasJugadas: String
    lateinit var partidasGanadas: String
    lateinit var eliminaciones: String
    lateinit var ratio: String
    lateinit var porcentajeVictorias: String
    lateinit var puntuacionxbl: String
    lateinit var partidasJugadasxbl: String
    lateinit var partidasGanadasxbl: String
    lateinit var eliminacionesxbl: String
    lateinit var ratioxbl: String
    lateinit var porcentajeVictoriasxbl: String

    var listSuggestion = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)



        /////////////////////////////////////////////SPINNER//////////////////////////////////////////////
        /*set an adapter with strings array*/
        spinner.adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, myStrings)
        /*set click listener*/
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                platform = myStrings[position].toLowerCase()


            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        ivDelete.setOnClickListener {
            textInputUser.text = null
        }


        /////////////////////////////////////////////SPINNER//////////////////////////////////////////////


        /////////////////////////////////////////////CLICK BOTON//////////////////////////////////////////////

        fun buscar() {


            userName = textInputUser.text.toString()

            var dialog = indeterminateProgressDialog("Loading data...")
            dialog.show()

            fortniteApiService.getData(platform, userName)

                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { respuesta ->

                                this.statsAL = respuesta.lifeTimeStats
                                for (i in statsAL)
                                    if (i.key.equals("Score"))
                                        puntuacion = i.value
                                    else if (i.key.equals("Matches Played"))
                                        partidasJugadas = i.value
                                    else if (i.key.equals("Wins"))
                                        partidasGanadas = i.value
                                    else if (i.key.equals("Win%"))
                                        porcentajeVictorias = i.value
                                    else if (i.key.equals("Kills"))
                                        eliminaciones = i.value
                                    else if (i.key.equals("K/d"))
                                        ratio = i.value + " k/d"


                                val intent = Intent(this, MainActivity::class.java)
                                intent.putExtra("userName", userName)
                                intent.putExtra("platform", platform)
                                intent.putExtra("puntuacion", puntuacion)
                                intent.putExtra("partidasJugadas", partidasJugadas)
                                intent.putExtra("partidasGanadas", partidasGanadas)
                                intent.putExtra("porcentajeVictorias", porcentajeVictorias)
                                intent.putExtra("eliminaciones", eliminaciones)
                                intent.putExtra("ratio", ratio)


                                startActivity(intent)
                                dialog.cancel()

                            },
                            { error ->
                                dialog.cancel()
                                Toast.makeText(this, "No existe el usuario o no tiene datos en " + platform.toUpperCase(), Toast.LENGTH_SHORT).show()
                                Log.e(MainActivity.TAG,
                                        error.message)

                            }


                    )


        }
        search_button.setOnClickListener {
            buscar()

        }

            /////////////////////////////////////////////CLICK BOTON//////////////////////////////////////////////


            //////////////////////////////////TEXTINPUTSUGGESTION///////////////////////////




            //////////////////////////////////TEXTINPUTSUGGESTION///////////////////////////


            ////////////////////////////////LUPA TECLADO/////////////////////////////

            textInputUser.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    buscar()
                    return@OnEditorActionListener true

                }
                false
            })
            ////////////////////////////////LUPA TECLADO/////////////////////////

        /*
        Metodo para añadir los nombres buscados al arrayList
         */

        fun addSuggestion(){
            var nombre:String = textInputUser.text.toString()

        }



        }


    }


