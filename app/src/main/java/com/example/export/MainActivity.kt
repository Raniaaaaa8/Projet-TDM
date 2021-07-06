package com.example.export


import android.annotation.SuppressLint
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
    @SuppressLint("SetTextI18n")
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
            if (ele!=null) {
                displayIdRacine.setText(ele.idRacine)
                //val idaya=ReadDatafromcsvMot_des_racines(ele.idRacine)

                //afficher les aya
                var ayat= ReadDatafromcsvMot_des_racines(ele.idRacine)//mutableListOf(idaya)// = arrayOf<ElementscsvMotsdesRacines>()
                //IdRacines.add(idaya)
                var str= ayat?.get(0)?.englishWord
                if(str==null) str=""
                Log.d("array aya",str)
                str=""
                var f:String?
                if (ayat != null) {
                    var cpt=1
                    for ( i in ayat ){
                        if (cpt==ayat.size) break
                        f=ReadDatafromcsvAya(ayat?.get(cpt)?.ID_Aya).Text_AR
                        if (f!=null) str=str+"  ***  "+f

                        Log.d("cpt", cpt.toString())
                        var str2=ayat?.get(cpt)?.ID_Aya
                        if (str2!=null)
                        Log.d("ayat",str2 )
                        if (f != null) {
                            Log.d("f",f)
                        }
                        cpt++
                    }

                }
                /*val ayastr=ReadDatafromcsvAya(ayat?.get(0)?.ID_Aya)*/
                if (str!=null) aya.setText(str)
                else aya.setText("Not found")
            }
            else displayIdRacine.setText("Not found")


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
                else if(tokens[1]=="99"){
                    break
                }
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
                else if(tokens[0]=="1799"){
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
    open fun ReadDatafromcsvMot_des_racines(IdRacine: String?): MutableList<ElementscsvMotsdesRacines>? {
        var tub = ElementscsvMotsdesRacines()
        var tub2 = ElementscsvMotsdesRacines()

        var ayat: MutableList<ElementscsvMotsdesRacines>? = mutableListOf(tub)// = arrayOf<ElementscsvMotsdesRacines>()

        val `is`: InputStream = resources.openRawResource(R.raw.motsdesracines1)
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
                if (tokens[2]==IdRacine) { //search in column Z
                        val tub2 = ElementscsvMotsdesRacines()


                        tub2.ID_Aya=tokens[1]
                        tub2.ID_Word=tokens[0]

                        tub2.idRacine=tokens[2]
                        tub2.englishWord=tokens[3]

                        ayat?.add(tub2)



                }
                //line= reader.readLine()
                //if (line==null) break

                if(tokens[0]=="77879"){
                    break
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return ayat
    }



}