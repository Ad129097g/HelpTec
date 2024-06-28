package com.helptec

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.helptec.Fragmentos.FragmentInicio
import com.helptec.Fragmentos.FragmentMostrarCursos
import com.helptec.Fragmentos.FragmentMisCursos
import com.helptec.Fragmentos.FragmentPerfil

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                R.id.navigation_home -> selectedFragment = FragmentInicio()
                R.id.navigation_courses -> selectedFragment = FragmentMostrarCursos()
                R.id.navigation_my_courses -> selectedFragment = FragmentMisCursos()
                R.id.navigation_profile -> selectedFragment = FragmentPerfil()
            }
            if (selectedFragment != null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .commit()
            }
            true
        }

        // Set the default selected item
        bottomNavigationView.selectedItemId = R.id.navigation_home
    }
}
