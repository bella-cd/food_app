package pt.ipg.food_app.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import pt.ipg.food_app.HomeFragment
import pt.ipg.food_app.R
import pt.ipg.food_app.databinding.ActivityCategoryMealsBinding
import pt.ipg.food_app.databinding.ActivityMealBinding
import pt.ipg.food_app.viewModel.CategoryMealsViewModel

class CategoryMealsActivity : AppCompatActivity() {

    // View binding instance for the activity layout.
    lateinit var  binding : ActivityCategoryMealsBinding

    // ViewModel instance for handling data related to category meals.
    lateinit var  categoryMealsViewModel: CategoryMealsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        // Inflating the layout using view binding.
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initializing the ViewModel for CategoryMealsActivity.
        categoryMealsViewModel = ViewModelProvider(this)[CategoryMealsViewModel::class.java]

        categoryMealsViewModel.getMealByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)

        // Observing changes in the mealsLiveData and logging each meal's name.
        categoryMealsViewModel.observeMealsLiveData().observe(this, Observer { mealsList->
            mealsList.forEach {
                Log.d("test",it.strMeal)
            }
        })
    }
}