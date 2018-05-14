package net.victor.fortniteretrofit.vistas

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog

import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_search.*
import net.victor.fortniteretrofit.R
import net.victor.fortniteretrofit.api.FortniteApiService
import net.victor.fortniteretrofit.model.LifeTimeStat
import org.jetbrains.anko.image
import org.jetbrains.anko.indeterminateProgressDialog
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private lateinit var statsAL: ArrayList<LifeTimeStat>
    lateinit var playerName: String
    lateinit var platform: String
    lateinit var puntuacion2: String
    lateinit var partidasJugadas2: String
    lateinit var partidasGanadas2: String
    lateinit var eliminaciones2: String
    lateinit var ratio2: String
    lateinit var porcentajeVictorias2: String

    companion object {
        val TAG = "****Victor****"
    }


    private val fortniteApiService by lazy {
        FortniteApiService.create()

    }

    public override fun onDestroy() {
        super.onDestroy()

        val id = android.os.Process.myPid()
        android.os.Process.killProcess(id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        fun loadStats() {


            var intent = getIntent().extras
            playerName = intent.getString("userName").toString()
            platform = intent.getString("platform").toString().toUpperCase()
            puntuacion2 = intent.getString("puntuacion").toString()
            partidasGanadas2 = intent.getString("partidasGanadas").toString()
            partidasJugadas2 = intent.getString("partidasJugadas").toString()
            eliminaciones2 = intent.getString("eliminaciones").toString()
            ratio2 = intent.getString("ratio").toString()
            porcentajeVictorias2 = intent.getString("porcentajeVictorias").toString()


            tvPlataforma.text = platform
            tvNombre.text = playerName
            tvPuntuacion.text = puntuacion2
            tvBajasTotales.text = eliminaciones2
            tvPartidasJugadas.text = partidasJugadas2
            tvPorVict.text = porcentajeVictorias2
            tvRatio.text = ratio2
            tvVictorias.text = partidasGanadas2


            if (platform == "PS4") {
                tvPlataforma.setTextColor(Color.BLUE)
                ivPlataforma.setImageResource(R.drawable.psn)

            } else if (platform == "XBOX") {
                tvPlataforma.setTextColor(Color.GREEN)
                ivPlataforma.setImageResource(R.drawable.xbox)

            } else if (platform == "PC") {
                tvPlataforma.setTextColor(Color.RED)
                ivPlataforma.setImageResource(R.drawable.epic)

            }

        }

        loadStats()


        fun refreshStats() {

            var intent = getIntent().extras
            var userNameRefresh = intent.getString("userName").toString()
            var platformRefresh = intent.getString("platform").toString()

            fortniteApiService.getData(platformRefresh, userNameRefresh)

                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { respuesta ->

                                this.statsAL = respuesta.lifeTimeStats
                                for (i in statsAL)
                                    if (i.key.equals("Score"))
                                        tvPuntuacion.text = i.value
                                    else if (i.key.equals("Matches Played"))
                                        tvPartidasJugadas.text = i.value
                                    else if (i.key.equals("Wins"))
                                        tvVictorias.text = i.value
                                    else if (i.key.equals("Win%"))
                                        tvPorVict.text = i.value
                                    else if (i.key.equals("Kills"))
                                        tvBajasTotales.text = i.value
                                    else if (i.key.equals("K/d"))
                                        tvRatio.text = i.value + " k/d"


                            },
                            { error ->
                                Toast.makeText(this, "No existe el usuario o no tiene datos en " + platform.toUpperCase(), Toast.LENGTH_SHORT).show()
                                Log.e(MainActivity.TAG,
                                        error.message)

                            }


                    )


        }

        idSwipe.setOnRefreshListener {
            refreshStats()
            //Si no lo indicamos que pare, la rueda del refresh no dejará de girar.
            idSwipe.isRefreshing = false
        }

    }
}





