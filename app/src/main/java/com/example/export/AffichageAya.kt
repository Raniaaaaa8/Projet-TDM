package com.example.export

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class AffichageAya : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_affichage_aya)
        val racine=intent.extras?.get("racine")


    }
}