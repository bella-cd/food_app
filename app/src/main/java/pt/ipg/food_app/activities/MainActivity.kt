package pt.ipg.food_app.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import pt.ipg.food_app.R
import pt.ipg.food_app.db.MealDatabase
import pt.ipg.food_app.viewModel.HomeViewModel
import pt.ipg.food_app.viewModel.HomeViewModelFactory

class MainActivity : AppCompatActivity() {

    // Lazily initialize the HomeViewModel using a ViewModelProvider and a custom factory.
    val viewModel : HomeViewModel by lazy {
        val mealDatabase =  MealDatabase.getInstance(this)
        val homeViewModelFactory = HomeViewModelFactory(mealDatabase)
        ViewModelProvider(this,homeViewModelFactory)[HomeViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
// Initialize and link the BottomNavigationView with the NavController
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.btw_nav)
        val navController = Navigation.findNavController(this, R.id.frag_host)

        NavigationUI.setupWithNavController(bottomNavigation, navController)
    }
}