package net.victor.fortniteretrofit.model

import android.media.Image

/**
 * Created by Usuario on 26/02/2018.
 */
data class Respuesta(val lifeTimeStats: ArrayList<LifeTimeStat>)

data class LifeTimeStat(val key:String, val value: String)
data class Spinner(val spinner: Spinner)
data class plataformaLista(val nombrePlaraforma:String, val icono:Image)