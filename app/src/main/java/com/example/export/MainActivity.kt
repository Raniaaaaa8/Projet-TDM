package com.example.export


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.Charset


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var sourat:String?=null
        var racine:String?=null
        button.setOnClickListener {
            sourat= mot.text.toString() //get from input
            var tub=ReadDatafromcsvAya(sourat)//search
            if (tub!=null) aya.setText(tub.Text_AR)//display
        }
        getIdRacine.setOnClickListener {
            racine=getRacine.text.toString()
            var ele=ReadDatafromcsvRacine_des_mots_arabe(racine)
            if (ele!=null) displayIdRacine.setText(ele.idRacine)
        }


    }


    //get data from aya.csv
    //zsourat  is in format 1:1 aka sourat:aya
    open fun ReadDatafromcsvAya(zsourat: String?): ElementscsvAya {
        val tub = ElementscsvAya()
        val `is`: InputStream = resources.openRawResource(R.raw.aya)
        val reader: BufferedReader = BufferedReader(
            InputStreamReader(`is`, Charset.forName("UTF-8"))
        )
        var line = ""
        try {

            reader.readLine()

            while (reader.readLine().also { line=it } != null ) {

                val tokens = line.split(",").toTypedArray()
                //read data

                //Log.d("token3",tokens[3].toString())
                //Log.d("di",sourat)
                if (tokens[0]==zsourat) { //search in column Z
                    tub.setidSourat(tokens[1])
                    tub.Text_AR=tokens[3]
                    tub.NumAya=tokens[2]
                    tub.Z=tokens[0]
                    tub.nbMots=tokens[4]

                    break
                }
                //line= reader.readLine()
                //if (line==null) break
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return tub
    }


    //get data from Racine_des_mots_arabe.csv
    //zsourat  is in format 1:1 aka sourat:aya
    open fun ReadDatafromcsvRacine_des_mots_arabe(Racine: String?): ElementscsvRacine_des_mots_arabe {
        val tub = ElementscsvRacine_des_mots_arabe()
        val `is`: InputStream = resources.openRawResource(R.raw.racinedesmotsarabe)
        val reader: BufferedReader = BufferedReader(
            InputStreamReader(`is`, Charset.forName("UTF-8"))
        )
        var line = ""
        try {

            reader.readLine()

            while (reader.readLine().also { line=it } != null ) {

                val tokens = line.split(",").toTypedArray()
                //read data

                //Log.d("token3",tokens[3].toString())
                //Log.d("di",sourat)
                if (tokens[1]==Racine) { //search in column Z

                    tub.Racine=tokens[1]
                    tub.idRacine=tokens[0]
                    tub.NBLettre=tokens[7]
                    break
                }
                //line= reader.readLine()
                //if (line==null) break
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return tub
    }



}