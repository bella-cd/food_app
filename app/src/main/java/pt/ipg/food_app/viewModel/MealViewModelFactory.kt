package pt.ipg.food_app.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pt.ipg.food_app.db.MealDatabase

// Factory class for creating the MealViewModel, providing the MealDatabase as a dependency.
class MealViewModelFactory (
   private val mealDatabase: MealDatabase
): ViewModelProvider.Factory{

     override fun <T : ViewModel> create(modelClass: Class<T>):T {
         return MealViewModel(mealDatabase) as T
     }
}