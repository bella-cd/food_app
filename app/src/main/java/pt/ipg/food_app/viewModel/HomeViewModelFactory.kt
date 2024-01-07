package pt.ipg.food_app.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pt.ipg.food_app.db.MealDatabase

class HomeViewModelFactory (
   private val mealDatabase: MealDatabase
): ViewModelProvider.Factory{

     override fun <T : ViewModel> create(modelClass: Class<T>):T {
         return HomeViewModel(mealDatabase) as T
     }
}