package pt.ipg.food_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
// Initialize and link the BottomNavigationView with the NavController
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.btw_nav)
        val navController = Navigation.findNavController(this,R.id.frag_host)

        NavigationUI.setupWithNavController(bottomNavigation, navController)
    }
}