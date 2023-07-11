package com.example.dispositivos_moviles_proyecto_gc_es1.logic.marvelLogic

import android.util.Log
import com.example.dispositivos_moviles_proyecto_gc_es1.data.connection.ApiConnection
import com.example.dispositivos_moviles_proyecto_gc_es1.data.connection.endpoints.MarvelEndPoints
import com.example.dispositivos_moviles_proyecto_gc_es1.logic.data.Heroes
import com.example.dispositivos_moviles_proyecto_gc_es1.logic.data.getMarvelCharsDB
import com.example.dispositivos_moviles_proyecto_gc_es1.logic.entities.marvel.database.MarvelCharsDB
import com.example.dispositivos_moviles_proyecto_gc_es1.logic.entities.marvel.getMarvelChar
import com.example.dispositivos_moviles_proyecto_gc_es1.ui.utilities.Dispositivos_moviles_proyecto_gc_es1

class MarvelLogic {

    suspend fun getMarvelCharacters(name:String, limit:Int): ArrayList<Heroes> {
        var call =
            ApiConnection.getService(ApiConnection.typeApi.Marvel, MarvelEndPoints::class.java)
        //val response = call.create(JikanEndpoint::class.java).getAllAnimes()
        val itemList = arrayListOf<Heroes>()

        if (call != null) {
            val response = call.getCharsStartWith(name, limit)

            if (response.isSuccessful) {
                response.body()!!.data.results.forEach() {
                    val m=it.getMarvelChar()
                    itemList.add(m)
                }
            } else {
                Log.d("UCE", response.toString())
            }

        }
        return itemList


    }
    suspend fun getAllMarvelCharacters(offset:Int, limit:Int): ArrayList<Heroes> {
        var call =
            ApiConnection.getService(ApiConnection.typeApi.Marvel, MarvelEndPoints::class.java)
        //val response = call.create(JikanEndpoint::class.java).getAllAnimes()
        val itemList = arrayListOf<Heroes>()

        if (call != null) {
            val response = call.getAllMarvelChar(offset, limit)

            if (response.isSuccessful) {
                response.body()!!.data.results.forEach() {
                    val m=it.getMarvelChar()
                    itemList.add(m)
                }
            } else {
                Log.d("UCE", response.toString())
            }

        }
        return itemList


    }
    suspend fun getAllMarvelCharsDB(): List<Heroes>{
        var items: ArrayList<Heroes> = arrayListOf()
        val itemsAux= Dispositivos_moviles_proyecto_gc_es1.getDbIntance().marvelDao().getAllCharacters()

        itemsAux.forEach{
            items.add(
                Heroes(
                    it.id,
                    it.heroe,
                    it.comic,
                    it.img)
                )


        }
        return items
    }
    suspend fun insertMarvelCharsToDB(items:List<Heroes>){
        var itemsDB= arrayListOf<MarvelCharsDB>()
        items.forEach{
            itemsDB.add(it.getMarvelCharsDB())
        }

        Dispositivos_moviles_proyecto_gc_es1.getDbIntance().marvelDao().insertMarvelChar(itemsDB)
    }
}